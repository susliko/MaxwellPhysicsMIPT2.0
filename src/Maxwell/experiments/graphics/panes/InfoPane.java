package Maxwell.experiments.graphics.panes;


import Maxwell.experiments.physics.Atom;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.util.List;


public abstract class InfoPane extends JPanel {

    public final int HEIGHT = 350;
    public final int WIDTH = 600;

    final List<Atom> atoms;

    InfoPane(java.util.List<Atom> atoms) {
        this.atoms = atoms;
        setBackground(Color.lightGray);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }



    public abstract void addListener(ActionListener listener);



    public abstract void update();
}
