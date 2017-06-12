package Maxwell.Experiment.frames;

import javax.swing.*;
import Maxwell.Experiment.physics.Drawer;


public class Arena extends JFrame {

    public Arena() {
        super("Maxwell");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setAtoms(Drawer drawer) {
        getContentPane().add(drawer);
    }
}
