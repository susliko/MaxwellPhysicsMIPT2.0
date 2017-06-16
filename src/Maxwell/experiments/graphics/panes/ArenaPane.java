package Maxwell.experiments.graphics.panes;


import Maxwell.experiments.Experiment;
import Maxwell.experiments.graphics.painters.AtomsPainter;
import Maxwell.experiments.graphics.painters.Painter;
import Maxwell.experiments.physics.Atom;

import javax.swing.*;
import java.awt.*;
import java.util.List;



public class ArenaPane extends JPanel {

    public final int HEIGHT;
    public final int WIDTH ;

    private final List<Atom> atoms;

    private final Painter atomsPainter;
    private final Painter wallPainter;

    public ArenaPane(List<Atom> atoms) {
        this.HEIGHT = Experiment.HEIGHT;
        this.WIDTH = Experiment.WIDTH;
        this.atoms = atoms;
        this.atomsPainter = new AtomsPainter();
        this.wallPainter = null;
    }

    public ArenaPane(List<Atom> atoms, Painter wallPainter) {
        this.HEIGHT = Experiment.HEIGHT;
        this.WIDTH = Experiment.WIDTH;
        this.atoms = atoms;
        this.atomsPainter = new AtomsPainter();
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

        atomsPainter.paint(g, atoms);

        if (wallPainter != null) {
            wallPainter.paint(g);
        }
    }
}



