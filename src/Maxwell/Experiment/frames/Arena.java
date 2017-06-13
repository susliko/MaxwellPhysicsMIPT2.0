package Maxwell.Experiment.frames;

import javax.swing.*;
import java.awt.*;


public class Arena extends JFrame {

    public Arena() {
        super("Maxwell");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setAtoms(JPanel drawer) {
        add(drawer);
    }
}
