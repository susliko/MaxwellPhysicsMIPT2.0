package Maxwell.frames;

import javax.swing.*;
import Maxwell.physics.Drawer;


public class Arena extends JFrame {

    public Arena() {
        super("Maxwell");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setAtoms(JPanel drawer) {
        getContentPane().add(drawer);
    }
}
