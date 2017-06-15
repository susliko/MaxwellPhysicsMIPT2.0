package Maxwell.Experiment.graphics.frames;
import Maxwell.Experiment.graphics.panes.ArenaPane;
import Maxwell.Experiment.graphics.painters.Painter;
import Maxwell.Experiment.physics.Atom;

import javax.swing.*;
import java.util.List;



public class ArenaFrame extends JFrame {

    /**
     * Constructor of arena without any objects except particles on it
     * @param atoms reference to the list of particles
     */
    public ArenaFrame(List<Atom> atoms) {
        super("Газ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new ArenaPane(atoms));
    }

    /**
     *
     * @param atoms
     * @param painter re
     */
    public ArenaFrame(List<Atom> atoms, Painter painter) {
        super("Газ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new ArenaPane(atoms, painter));
    }
}
