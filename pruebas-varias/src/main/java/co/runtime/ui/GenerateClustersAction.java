package co.runtime.ui;

import co.runtime.clustering.LuceneKeywordClustering;
import co.runtime.clustering.types.Cluster;
import co.runtime.dto.ClusteringConfig;
import co.runtime.utils.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class ...
 * <p/>
 * Creation: 5/27/14, 2:51 PM.
 */
public class GenerateClustersAction extends AbstractAction {
    private GroupingTesterUI mainWindow;
    private int totalClusters;

    public GenerateClustersAction(String name, GroupingTesterUI mainWindow) {
        super(name);
        this.mainWindow = mainWindow;
    }

    private void performClustering(ClusteringConfig cfg, Analyzer analyzer) throws IOException {
        LuceneKeywordClustering lc = new LuceneKeywordClustering(cfg.getInputFile(), cfg.getSimilarityThreshold(), analyzer);
        java.util.List<Cluster> clusters = lc.generateClusters(cfg.getMinClusterSize(), cfg.getMaxClusterSize());
        totalClusters = clusters.size();
        FileWriter fw = new FileWriter(cfg.getOutputFile());

        int clusterNumber = 1;
        for (Cluster c : clusters) {
            fw.write("-------------------- Cluster #" + clusterNumber +
                    " (" + c.getId().getName() + ") - " +
                    "with (" + c.getElements().size() + ") elements --------------------");
            fw.write(System.getProperty("line.separator"));
            for (String kw : c.getElements()) {
                fw.write(kw);
                fw.write(System.getProperty("line.separator"));
            }
            clusterNumber++;
            fw.write(System.getProperty("line.separator"));
        }
        fw.close();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        ClusteringConfig cfg = mainWindow.getConfiguration();
        Analyzer analyzer;
        if (cfg != null) {
            // Creates the stopwords Set
            CharArraySet swSet = new CharArraySet(LuceneKeywordClustering.LUCENE_VERSION,
                    StringUtils.getStopWordsCollection(cfg.getStopWords()), true);
            // StandardAnalyzer based in stopwords
            analyzer = new StandardAnalyzer(LuceneKeywordClustering.LUCENE_VERSION, swSet);
            showProcessingModal(cfg, analyzer);
        }
    }

    private void showProcessingModal(final ClusteringConfig cfg, final Analyzer analyzer) {
        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                GenerateClustersAction.this.performClustering(cfg, analyzer);
                return null;
            }
        };

        final JDialog dialog = new JDialog(mainWindow, "Generating Clusters...", Dialog.ModalityType.APPLICATION_MODAL);

        mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("state")) {
                    if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        dialog.dispose();
                        JOptionPane.showMessageDialog(mainWindow, totalClusters + " clusters created.\nSee output file for detail.",
                                "Clustering Done", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
        mySwingWorker.execute();

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel("Please wait......."), BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.pack();
        dialog.setSize(350, 80);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setLocationRelativeTo(mainWindow);
        dialog.setVisible(true);
    }
}