package Maxwell;

import Maxwell.frames.Dialog;
import Maxwell.physics.Atom;
import Maxwell.frames.Arena;
import Maxwell.physics.Drawer;
import Maxwell.physics.Physics;
import Maxwell.plot.Plot;
import Maxwell.plot.PlotMaxwell;
import Maxwell.plot.PlotBolzman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Master {

    public static final int HEIGHT = 700;
    public static final int WIDTH  = 700;
    public static final int D = 5;
    private static final int numberOfAtoms = 1000;

    private final List<Atom> atoms = new ArrayList<>();

    private final Drawer drawer = new Drawer(atoms);
    private final Physics physics  = new Physics(atoms);
    private final Arena arena = new Arena();
    private final Dialog dialog = new Dialog();

    private Plot plotBolzman;


    private void generateAtoms(int velocity) {
        Random random = new Random(System.currentTimeMillis());
        int v = (int)Math.floor(Math.sqrt(Math.pow(velocity, 2) / 2));
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            physics.addAtom(x, y, v, v);
        }
        plotBolzman = new PlotMaxwell(atoms, velocity);
    }



    public void run() {
        int gasTPF = 40;
        int plotTPF = 1000;

        dialog.setVisible(true);
        int velocity = dialog.getVelocity();
        dialog.dispose();
        generateAtoms(velocity);

        plotBolzman.display();
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
                plotBolzman.render();
                sincePlotUpdate = 0;
            }

            arena.pack();
            arena.repaint();
        }
    }
}
