package Maxwell.Experiment;

import Maxwell.ExpType;
import Maxwell.Experiment.frames.Arena;
import Maxwell.Experiment.frames.WallPainterKnudsen;
import Maxwell.Experiment.physics.Atom;
import Maxwell.Experiment.physics.AtomProcessorKnudsen;
import Maxwell.Experiment.physics.Physics;
import Maxwell.Experiment.plot.Plot;
import Maxwell.Experiment.plot.PlotBoltzmann;
import Maxwell.Experiment.plot.PlotMaxwell;
import Maxwell.Experiment.plot.PlotTimeFunction.PlotKnudsen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Experiment {

    public static final int HEIGHT = 700;
    public static final int WIDTH  = 700;
    public static final int D = 2;
    public static final int gasTPF = 20;
    public static final int plotTPF = 1000;

    public static final double boltzmannAcceleration = 100;

    public static final int knudsenNumberOfHoles = 10;

    private boolean active = false;

    public void start(ExpType expType, int velocity, int numberOfAtoms) {
        active = true;

        final List<Atom> atoms = new ArrayList<>();
        final Arena arena;
        final Physics physics;
        final Plot plot;

        switch (expType) {
            case MAXWELL:
                arena = new Arena(atoms);
                generateAtomsFullArena(atoms, velocity, numberOfAtoms);
                plot = new PlotMaxwell(atoms);
                physics = new Physics(atoms);
                break;
            case BOLTZMANN:
                arena = new Arena(atoms);
                generateAtomsFullArena(atoms, velocity, numberOfAtoms);
                plot = new PlotBoltzmann(atoms);
                physics = new Physics(atoms, atoms1 -> {
                    for (Atom atom : atoms)
                        if (atom.y <= HEIGHT - D)
                            atom.vy += boltzmannAcceleration * gasTPF / 1000;
                });
                break;
            case KNUDSEN:
                arena = new Arena(atoms, new WallPainterKnudsen());
                generateAtomsKnudsen(atoms, velocity, numberOfAtoms);
                plot = new PlotKnudsen(atoms);
                physics = new Physics(atoms, new AtomProcessorKnudsen(numberOfAtoms));
                break;
            default:
                arena = null;
                plot = null;
                physics = null;
                break;
        }

        if (plot == null)
            return;

        arena.setVisible(true);

        double sinceGasUpdate = 0;
        double sincePlotUpdate = 0;
        double gasTimer = System.currentTimeMillis();
        double plotTimer = System.currentTimeMillis();

        while (active) {
            sinceGasUpdate += System.currentTimeMillis() - gasTimer;
            sincePlotUpdate += System.currentTimeMillis() - plotTimer;
            gasTimer  = System.currentTimeMillis();
            plotTimer = System.currentTimeMillis();

            while (sinceGasUpdate > gasTPF) {
                physics.update();
                sinceGasUpdate -= gasTPF;
            }

            if (sincePlotUpdate > plotTPF) {
                plot.render();
                sincePlotUpdate = 0;
            }

            arena.pack();
            arena.repaint();
        }

        plot.dispose();
        arena.dispose();
    }



    public void stop() {
        active = false;
    }



    private void generateAtomsFullArena(List<Atom> atoms, int velocity, int numberOfAtoms) {
        Random random = new Random(System.currentTimeMillis());
        double v = Math.floor(Math.sqrt(Math.pow(velocity, 2) / 2));
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            Atom atom = new Atom(x, y, v, v);
            atoms.add(atom);
        }
    }

    private void generateAtomsKnudsen(List<Atom> atoms, int velocity, int numberOfAtoms) {
        Random random = new Random(System.currentTimeMillis());
        double v = Math.floor(Math.sqrt(Math.pow(velocity, 2) / 2));
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH / 2);
            int y = random.nextInt(HEIGHT);
            Atom atom = new Atom(x, y, -v, v);
            atoms.add(atom);
        }
    }
}