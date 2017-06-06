package Maxwell;

import Maxwell.physics.Atom;
import Maxwell.arena.Arena;
import Maxwell.physics.Drawer;
import Maxwell.physics.Physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Master {

    public static final int HEIGHT = 1000;
    public static final int WIDTH  = 1000;
    public static final int D = 4;
    private static final int numberOfAtoms = 4000;

    private final List<Atom> atoms = new ArrayList<>();
    private final Drawer   drawer  = new Drawer(atoms);
    private final Physics physics  = new Physics(atoms);


    public Master() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            physics.addAtom(x, y, 200, 200);
        }
    }



    public void run() {
        int dt = 40;
        Arena arena = new Arena();
        arena.setVisible(true);
        arena.setAtoms(drawer);

        double timeSinceLastUpdate = 0;
        double timer = System.currentTimeMillis();
        while (true) {
            timeSinceLastUpdate += System.currentTimeMillis() - timer;
            timer = System.currentTimeMillis();

            while (timeSinceLastUpdate > dt) {
                physics.update(dt);
                timeSinceLastUpdate -= dt;
            }

            arena.pack();
            arena.repaint();
        }
    }
}
