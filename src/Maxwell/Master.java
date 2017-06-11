package Maxwell;

import Maxwell.frames.Dialog;
import Maxwell.physics.Atom;
import Maxwell.plot.Plot;
import Maxwell.frames.Arena;
import Maxwell.physics.Drawer;
import Maxwell.physics.Physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Master {

    public static final int HEIGHT = 1000;
    public static final int WIDTH  = 960;
    public static final int D = 4;
    private static final int numberOfAtoms = 1000;

    private final List<Atom> atoms = new ArrayList<>();

    private final Drawer    drawer = new Drawer(atoms);
    private final Physics physics  = new Physics(atoms);
    private final Arena arena = new Arena();
    private final Dialog dialog = new Dialog();

    private final Plot plot = new Plot(atoms);


    private void generateAtoms(int velocity) {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            physics.addAtom(x, y, velocity, velocity);
        }
    }



    public void run() {
        int gasTPF = 40;
        int plotTPF = 1000;

        dialog.setVisible(true);
        int velocity = dialog.getVelocity();
        dialog.dispose();
        generateAtoms(velocity);

        plot.display();
        arena.setVisible(true);
        arena.setAtoms(drawer);

        double sinceGasUpdate = 0;
        double sincePlotUpdate = 0;
        double gasTimer = System.currentTimeMillis();
        double plotTimer = System.currentTimeMillis();


        while (true) {
            sinceGasUpdate += System.currentTimeMillis() - gasTimer;
            sincePlotUpdate += System.currentTimeMillis() - plotTimer;
            gasTimer  = System.currentTimeMillis();
            plotTimer = System.currentTimeMillis();

            while (sinceGasUpdate > gasTPF) {
                physics.update(gasTPF);
                sinceGasUpdate -= gasTPF;
            }

            if (sincePlotUpdate > plotTPF) {
                plot.render();
                sincePlotUpdate = 0;
            }

            arena.pack();
            arena.repaint();
        }
    }
}
