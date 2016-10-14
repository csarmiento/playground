package co.runtime.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Class ...
 * <p/>
 * Creation: 5/27/14, 9:01 AM.
 */
public class Main {
    public static void main(String[] args) {
        JFrame parentFrame = new JFrame();
        parentFrame.setSize(500, 150);
        JLabel jl = new JLabel();
        jl.setText("Count : 0");

        parentFrame.add(BorderLayout.CENTER, jl);
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        parentFrame.setVisible(true);

        final JDialog dlg = new JDialog(parentFrame, "Progress Dialog", true);
        JProgressBar dpb = new JProgressBar(JProgressBar.HORIZONTAL);
        dpb.setIndeterminate(true);
        dlg.add(BorderLayout.CENTER, dpb);
        dlg.add(BorderLayout.NORTH, new JLabel("Progress..."));
        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dlg.setSize(300, 75);
        dlg.setLocationRelativeTo(parentFrame);

        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dlg.setVisible(true);
                return null;
            }
        };
        sw.execute();

        for (int i = 0; i <= 500; i++) {
            jl.setText("Count : " + i);
            if (i == 500) {
                dlg.setVisible(false);
                System.exit(0);

            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}