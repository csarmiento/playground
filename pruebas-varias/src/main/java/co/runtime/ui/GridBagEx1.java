package co.runtime.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Class ...
 * <p/>
 * Creation: 5/23/14, 6:24 PM.
 */
public class GridBagEx1 {
    private static JFrame f;

    public static void main(String args[]) {
        f = new JFrame("GridBag Layout Example");
        GridBagEx1 ex1 = new GridBagEx1();

        ex1.init();

        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(f.getPreferredSize());
        f.setVisible(true);
    }

    protected void makebutton(String name,
                              GridBagLayout gridbag,
                              GridBagConstraints c) {
        Button button = new Button(name);
        gridbag.setConstraints(button, c);
        f.add(button);
    }

    public void init() {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        f.setFont(new Font("SansSerif", Font.PLAIN, 14));
        f.setLayout(gridbag);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        makebutton("Button1", gridbag, c);
        makebutton("Button2", gridbag, c);
        makebutton("Button3", gridbag, c);

        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        makebutton("Button4", gridbag, c);

        c.weightx = 0.0;                //reset to the default
        makebutton("Button5", gridbag, c); //another row

        c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last in row
        makebutton("Button6", gridbag, c);

        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        makebutton("Button7", gridbag, c);

        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER; //next-to-last in row
        makebutton("ButtonX", gridbag, c);


        c.gridwidth = 1;                //reset to the default
        c.gridheight = 2;
        c.weighty = 1.0;
        makebutton("Button8", gridbag, c);

        c.weighty = 0.0;                //reset to the default
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        c.gridheight = 1;               //reset to the default
        makebutton("Button9", gridbag, c);
        makebutton("Button10", gridbag, c);

        f.setSize(300, 100);
    }
}
