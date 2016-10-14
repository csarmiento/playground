package co.runtime.ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Class ...
 * <p/>
 * Creation: 5/23/14, 5:58 PM.
 */
public class ActionsPanel extends JPanel {
    private GroupingTesterUI mainWindow;

    private JButton generateClustersButton;

    public ActionsPanel(GroupingTesterUI parentWindow) {
        mainWindow = parentWindow;

        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 5, 0), new TitledBorder("Actions")));
        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);

        initializeComponents();
        setupLayout(gridbag);
    }

    private void setupLayout(GridBagLayout gridbag) {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.EAST;

        c.weightx = 1.0;
        gridbag.setConstraints(generateClustersButton, c);
        add(generateClustersButton);
    }

    private void initializeComponents() {
        generateClustersButton = new JButton(new GenerateClustersAction("Generate Clustering File",
                mainWindow));
    }
}