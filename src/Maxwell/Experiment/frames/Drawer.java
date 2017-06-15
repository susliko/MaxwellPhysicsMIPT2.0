package Maxwell.Experiment.frames;


import Maxwell.Experiment.Experiment;
import Maxwell.Experiment.physics.Atom;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Drawer extends JPanel {

    private final int HEIGHT;
    private final int WIDTH ;
    private final int D;

    private final List<Atom> atoms;

    private final WallPainter wallPainter;

    Drawer(List<Atom> atoms) {
        this.HEIGHT = Experiment.HEIGHT;
        this.WIDTH = Experiment.WIDTH;
        this.atoms = atoms;
        this.D = Experiment.D;
        this.wallPainter = null;
    }

    Drawer(List<Atom> atoms, WallPainter wallPainter) {
        this.HEIGHT = Experiment.HEIGHT;
        this.WIDTH = Experiment.WIDTH;
        this.atoms = atoms;
        this.D = Experiment.D;
        this.wallPainter = wallPainter;
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        for (Atom atom : atoms) {
            g2.fillOval((int)atom.x, (int)atom.y, D, D);
        }
        if (wallPainter != null)
            wallPainter.paintWalls(g);
    }
}



