package Maxwell.graphics.frames;

import Maxwell.ExpType;
import Maxwell.graphics.panes.DialogPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;



public class DialogFrame extends JFrame {

    private DialogPane pane;

    public DialogFrame() {
        super("Maxwell");
        pane = new DialogPane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - pane.WIDTH) / 2, (screenSize.height - pane.HEIGHT) / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(pane);
        pack();
    }



    public void setListener(ActionListener listener) {
        pane.setListener(listener);
    }



    public int getVelocity() {
        return pane.getVelocity();
    }



    public int getN() {
        return pane.getN();
    }



    public ExpType getExperiment() {
        return pane.getExperiment();
    }
}