package Maxwell;

import Maxwell.frames.Dialog;
import Maxwell.physics.Atom;
import Maxwell.frames.Arena;
import Maxwell.physics.Drawer;
import Maxwell.physics.Physics;
import Maxwell.plot.Plot;
import Maxwell.plot.PlotMaxwell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Master implements ActionListener {

    public static final int HEIGHT = 700;
    public static final int WIDTH  = 700;
    public static final int D = 5;
    private static final int numberOfAtoms = 1000;

    private final List<Atom> atoms = new ArrayList<>();

    private final JPanel drawer = new Drawer(atoms);
    private final Physics physics  = new Physics(atoms);
    private final Arena arena = new Arena();
    private final Dialog dialog = new Dialog();

    private Plot plotMaxwell;


    private void generateAtoms(int velocity) {
        Random random = new Random(System.currentTimeMillis());
        int v = (int)Math.floor(Math.sqrt(Math.pow(velocity, 2) / 2));
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            physics.addAtom(x, y, v, v);
        }
        plotMaxwell = new PlotMaxwell(atoms, velocity);
    }



    public void run() {
        dialog.setListener(this);
        dialog.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();

        if (cmd.equals("stop")) {
//            experiments.stop;
            dialog.setVisible(true);
        }
        if (cmd.equals("run")) {
            System.out.println(dialog.getVelocity());
            System.out.println(dialog.getN());
//            dialog.setVisible(false);
//            ExpType exp = dialog.getExperiment();
//            int velocity = dialog.getVelocity();
//            int N = dialog.getN();
//            experiments.run(exp, velocity, N);
        }

    }
}
