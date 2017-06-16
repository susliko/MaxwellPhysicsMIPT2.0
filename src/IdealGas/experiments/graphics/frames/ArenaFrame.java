package IdealGas.experiments.graphics.frames;
import IdealGas.experiments.graphics.panes.ArenaPane;
import IdealGas.experiments.graphics.painters.Painter;
import IdealGas.experiments.physics.Atom;

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
     * C
     * @param atoms reference to the list of particles
     * @param painter reference to the instance that is able to paint
     */
    public ArenaFrame(List<Atom> atoms, Painter painter) {
        super("Газ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new ArenaPane(atoms, painter));
    }
}
