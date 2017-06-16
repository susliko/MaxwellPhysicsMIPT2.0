package IdealGas.experiments.graphics.panes;


import IdealGas.experiments.physics.Atom;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public abstract class InfoPane extends JPanel {

    public final int HEIGHT = 350;
    public final int WIDTH = 600;

    final List<Atom> atoms;

    JButton stopButton;
    Map<String, JLabel> labelMap = new TreeMap<>();
    Map<String, JTextField> textFieldMap = new TreeMap<>();

    InfoPane(java.util.List<Atom> atoms) {
        this.atoms = atoms;
        setBackground(Color.lightGray);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        stopButton = new JButton("Остановить");
        stopButton.setPreferredSize(new Dimension(200, 50));
        stopButton.setBorderPainted(false);
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }



    public void addListener(ActionListener listener) {
        stopButton.setActionCommand("stop");
        stopButton.addActionListener(listener);
    }



    public abstract void update();



    double getAvgSpeed() {
        double avgSpeed = 0;
        int numberOfAtoms = atoms.size();

        for (Atom atom : atoms) {
            double speedSquare = Math.pow(atom.vx, 2) + Math.pow(atom.vy, 2);
            avgSpeed += Math.sqrt(speedSquare);
        }
        avgSpeed /= numberOfAtoms;
        return avgSpeed;
    }



    double getRmsSpeed() {
        double rmsSpeed = 0;
        int numberOfAtoms = atoms.size();

        for (Atom atom : atoms) {
            double speedSquare = Math.pow(atom.vx, 2) + Math.pow(atom.vy, 2);
            rmsSpeed += speedSquare;
        }
        rmsSpeed = Math.sqrt(rmsSpeed / numberOfAtoms);
        return rmsSpeed;
    }
}
