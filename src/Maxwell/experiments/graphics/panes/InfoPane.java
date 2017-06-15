package Maxwell.experiments.graphics.panes;


import Maxwell.experiments.Experiment;
import Maxwell.experiments.physics.Atom;

import javax.swing.*;
import java.awt.*;


public abstract class InfoPane extends JPanel {

    final int HEIGHT;
    final int WIDTH ;

    private final java.util.List<Atom> atoms;

    InfoPane(java.util.List<Atom> atoms) {
        this.HEIGHT = Experiment.HEIGHT;
        this.WIDTH = Experiment.WIDTH;
        this.atoms = atoms;
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
