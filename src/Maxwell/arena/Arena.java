package Maxwell.arena;

import javax.swing.*;
import Maxwell.physics.Drawer;

import java.awt.event.KeyListener;


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
