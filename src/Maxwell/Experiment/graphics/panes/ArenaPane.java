package Maxwell.Experiment.graphics.panes;


import Maxwell.Experiment.Experiment;
import Maxwell.Experiment.graphics.painters.AtomsPainter;
import Maxwell.Experiment.graphics.painters.Painter;
import Maxwell.Experiment.physics.Atom;

import javax.swing.*;
import java.awt.*;
import java.util.List;



public class ArenaPane extends JPanel {

    private final int HEIGHT;
    private final int WIDTH ;

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



