package Maxwell.Experiment.frames;

import Maxwell.Experiment.physics.Atom;

import javax.swing.*;
import java.util.List;



public class Arena extends JFrame {
    public Arena(List<Atom> atoms) {
        super("Газ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new Drawer(atoms));
    }

    public Arena(List<Atom> atoms, WallPainter wallPainter) {
        super("Газ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new Drawer(atoms, wallPainter));
    }
}
