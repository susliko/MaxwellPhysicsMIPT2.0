package IdealGas.graphics.frames;

import IdealGas.ExpType;
import IdealGas.graphics.panes.DialogPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class DialogFrame extends JFrame {

    private DialogPane pane;

    public DialogFrame() {
        super("IdealGas");
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