package co.runtime.ui;

import co.runtime.dto.ClusteringConfig;
import co.runtime.exceptions.UnsetParameterException;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Class ...
 * <p/>
 * Creation: 5/23/14, 5:58 PM.
 */
@SuppressWarnings("serial")
public class FileSelectionPanel extends JPanel {
    private GroupingTesterUI mainWindow;

    private JLabel inputFileLabel, outputFileLabel;
    private JTextField inputFileTextField, outputFileTextField;
    private JButton inputFileButton, outputFileButton;
    private JFileChooser inputFileChooser, outputFileChooser;

    public FileSelectionPanel(GroupingTesterUI parentWindow) {
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 5, 0), new TitledBorder("File Selection")));
        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);

        initializeComponents();
        setupLayout(gridbag);

        mainWindow = parentWindow;
    }

    private void setupLayout(GridBagLayout gridbag) {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;

        c.weightx = 2.0;
        gridbag.setConstraints(inputFileLabel, c);
        add(inputFileLabel);
        c.weightx = 12.0;
        gridbag.setConstraints(inputFileTextField, c);
        add(inputFileTextField);
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        c.weightx = 1.0;
        gridbag.setConstraints(inputFileButton, c);
        add(inputFileButton);

        c.gridwidth = 1;                //reset to the default
        c.weightx = 2.0;
        gridbag.setConstraints(outputFileLabel, c);
        add(outputFileLabel);
        c.weightx = 12.0;
        gridbag.setConstraints(outputFileTextField, c);
        add(outputFileTextField);
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        c.weightx = 1.0;
        gridbag.setConstraints(outputFileButton, c);
        add(outputFileButton);
    }

    private void initializeComponents() {
        inputFileLabel = new JLabel("Input File");
        inputFileTextField = new JTextField();
//        inputFileTextField.setEditable(false);
        inputFileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text Files", "txt", "log");
        inputFileChooser.setFileFilter(filter);
        inputFileButton = new JButton("...");
        inputFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile(inputFileChooser, inputFileTextField, false);
            }
        });

        outputFileLabel = new JLabel("Output File");
        outputFileTextField = new JTextField();
        outputFileChooser = new JFileChooser();
        outputFileButton = new JButton("...");
        outputFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile(outputFileChooser, outputFileTextField, true);
            }
        });
    }

    private void selectFile(JFileChooser chooser, JTextField field, boolean saveFile) {
        int returnVal;
        if (saveFile) {
            returnVal = chooser.showSaveDialog(mainWindow);
        } else {
            returnVal = chooser.showOpenDialog(mainWindow);
        }
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            field.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    public ClusteringConfig getConfiguration() throws UnsetParameterException {
        String outPath = outputFileTextField.getText();
        String inPath = inputFileTextField.getText();
        if ((outPath != null && inPath != null && !outPath.isEmpty() && !inPath.isEmpty())) {
            ClusteringConfig cfg = new ClusteringConfig();
            cfg.setInputFile(new File(inPath));
            cfg.setOutputFile(new File(outPath));
            return cfg;
        } else {
            throw new UnsetParameterException("Invalid input or output file");
        }
    }
}
