package co.runtime.ui;


import co.runtime.dto.ClusteringConfig;
import co.runtime.exceptions.UnsetParameterException;

import javax.swing.*;
import java.awt.*;

/**
 * Class ...
 * <p/>
 * Creation: 5/23/14, 3:29 PM.
 */
@SuppressWarnings("serial")
public class GroupingTesterUI extends JFrame {
    private FileSelectionPanel fileSelectionPanel;
    private ClusteringParametersPanel clusteringParametersPanel;
    private ActionsPanel actionsPanel;

    public GroupingTesterUI() {
        // Basic set up
        setTitle("Grouping Tester");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        getContentPane().setLayout(new BorderLayout());

        clusteringParametersPanel = new ClusteringParametersPanel();
        fileSelectionPanel = new FileSelectionPanel(this);
        actionsPanel = new ActionsPanel(this);

        getContentPane().add(fileSelectionPanel, BorderLayout.NORTH);
        getContentPane().add(clusteringParametersPanel, BorderLayout.CENTER);
        getContentPane().add(actionsPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        GroupingTesterUI ui = new GroupingTesterUI();
        ui.setVisible(true);
    }

    public ClusteringConfig getConfiguration() {
        try {
            ClusteringConfig fileCfg = fileSelectionPanel.getConfiguration();
            ClusteringConfig clCfg = clusteringParametersPanel.getConfiguration();
            clCfg.setInputFile(fileCfg.getInputFile());
            clCfg.setOutputFile(fileCfg.getOutputFile());
            return clCfg;
        } catch (UnsetParameterException e) {
            showErrorMessage(e);
            return null;
        }
    }

    public void showErrorMessage(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
