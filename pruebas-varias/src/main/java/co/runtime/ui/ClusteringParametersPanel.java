package co.runtime.ui;

import co.runtime.dto.ClusteringConfig;
import co.runtime.exceptions.UnsetParameterException;
import co.runtime.utils.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Class ...
 * <p/>
 * Creation: 5/23/14, 5:58 PM.
 */
@SuppressWarnings("serial")
public class ClusteringParametersPanel extends JPanel {
    private JLabel thresholdLabel, minSizeLabel, maxSizeLabel, stopWordsLabel;
    private JTextField minSizeTextField, maxSizeTextField;
    private JTextArea stopWordsTextArea;
    private JSlider thresholdSlider;

    public ClusteringParametersPanel() {
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 5, 0), new TitledBorder("Clustering Parameters")));
        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);

        initializeComponents();
        setupLayout(gridbag);
    }

    private void setupLayout(GridBagLayout gridbag) {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;

        c.weightx = 1.0;
        gridbag.setConstraints(minSizeLabel, c);
        add(minSizeLabel);
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        c.weightx = 4.0;
        gridbag.setConstraints(minSizeTextField, c);
        add(minSizeTextField);

        c.gridwidth = 1;                //reset to the default
        c.weightx = 1.0;
        gridbag.setConstraints(maxSizeLabel, c);
        add(maxSizeLabel);
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        c.weightx = 4.0;
        gridbag.setConstraints(maxSizeTextField, c);
        add(maxSizeTextField);

        c.gridwidth = 1;                //reset to the default
        c.gridheight = 4;
        c.weightx = 1.0;
        c.weighty = 1.0;
        gridbag.setConstraints(stopWordsLabel, c);
        add(stopWordsLabel);
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbag.setConstraints(stopWordsTextArea, c);
        add(stopWordsTextArea);

        c.gridwidth = 1;                //reset to the default
        c.gridheight = 1;               //reset to the default
        c.weightx = 1.0;
        c.weighty = 0.0;                //reset to the default
        gridbag.setConstraints(thresholdLabel, c);
        add(thresholdLabel);
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        c.weightx = 4.0;                //reset to the default
        gridbag.setConstraints(thresholdSlider, c);
        add(thresholdSlider);
    }

    private void initializeComponents() {
        thresholdLabel = new JLabel("Threshold: (45%)");
        thresholdSlider = new JSlider(0, 100);
        thresholdSlider.setMajorTickSpacing(10);
        thresholdSlider.setMinorTickSpacing(1);
        thresholdSlider.setPaintTicks(true);
        thresholdSlider.setPaintLabels(true);
        thresholdSlider.setValue(45);
        thresholdSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                thresholdLabel.setText("Threshold: (" + thresholdSlider.getValue() + "%)");
            }
        });

        minSizeLabel = new JLabel("Clusters Min Size");
        maxSizeLabel = new JLabel("Clusters Max Size");

        minSizeTextField = new JTextField();
        minSizeTextField.setText("0");
        minSizeTextField.setEditable(false);
        minSizeTextField.setToolTipText("Still don't know what to do with clusters with less than this number");
        maxSizeTextField = new JTextField();

        stopWordsLabel = new JLabel("Stop Words");
        stopWordsTextArea = new JTextArea();
        stopWordsTextArea.setLineWrap(true);
        stopWordsTextArea.setWrapStyleWord(true);
        String stopWords = StandardAnalyzer.STOP_WORDS_SET.toString();
        stopWordsTextArea.setText(StringUtils.sortDelimitedString(stopWords.substring(1, stopWords.length() - 1), " ,", ","));
    }

    public ClusteringConfig getConfiguration() throws UnsetParameterException {
        ClusteringConfig cfg = new ClusteringConfig();
        try {
            cfg.setMinClusterSize(Integer.parseInt(minSizeTextField.getText()));
            cfg.setMaxClusterSize(Integer.parseInt(maxSizeTextField.getText()));
            cfg.setSimilarityThreshold((float) thresholdSlider.getValue() / 100);
            cfg.setStopWords(stopWordsTextArea.getText());
            return cfg;
        } catch (NumberFormatException e) {
            throw new UnsetParameterException("Invalid configuration, check Min/Max cluster size");
        }
    }
}
