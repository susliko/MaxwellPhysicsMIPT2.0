package Maxwell;

import Maxwell.physics.Atom;
import Maxwell.plot.Plot;
import Maxwell.arena.Arena;
import Maxwell.physics.Drawer;
import Maxwell.physics.Physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Master {

    public static final int HEIGHT = 1000;
    public static final int WIDTH  = 1000;
    public static final int D = 10;
    private static final int numberOfAtoms = 500;

    private final List<Atom> atoms = new ArrayList<>();

    private final Drawer    drawer = new Drawer(atoms);
    private final Physics physics  = new Physics(atoms);
    private final Arena arena = new Arena();

    private final Plot plot = new Plot(atoms);


    public Master() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            physics.addAtom(x, y, 200, 200);
        }
    }



    public void run() {
        int gasTPF = 40;
        int plotTPF = 1000;

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
