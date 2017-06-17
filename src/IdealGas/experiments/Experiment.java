package IdealGas.experiments;

import IdealGas.ExpType;
import IdealGas.experiments.graphics.frames.ArenaFrame;
import IdealGas.experiments.graphics.frames.InfoFrame;
import IdealGas.experiments.graphics.painters.PainterKnudsen;
import IdealGas.experiments.graphics.painters.PainterPiston;
import IdealGas.experiments.physics.Atom;
import IdealGas.experiments.physics.processors.AtomProcessorBoltzmann;
import IdealGas.experiments.physics.processors.AtomProcessorKnudsen;
import IdealGas.experiments.physics.Physics;
import IdealGas.experiments.physics.processors.AtomProcessorPiston;
import IdealGas.experiments.plot.Plot;
import IdealGas.experiments.plot.PlotDistribution.PlotBoltzmann;
import IdealGas.experiments.plot.PlotDistribution.PlotMaxwell;
import IdealGas.experiments.plot.PlotTimeFunction.PlotKnudsen;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Experiment {

    // Size of the arena in pixels
    public static final int HEIGHT = 700;
    public static final int WIDTH  = 700;
    // Diameter of particles in pixels
    public static final int D = 2;
    // ArenaFrame is updated every "gasTPF" milliseconds
    public static final int gasTPF = 20;
    // Plot is updated every "plotTPF" milliseconds
    public static final int plotTPF = 1000;

    // Stares the increase of every particle velocity along Y axis per one frame
    public static final double boltzmannAcceleration = 100;

    // Number of holes in vertical line if ExpType - KNUDSEN
    public static final int knudsenNumberOfHoles = 5;

    public static int pistonWeight = 10000;

    // Shows whether experiment is running
    private boolean active = false;


    /**
     * Runs specified experiment
     *
     * @param expType determines which experiment is intended to run
     * @param velocity stores start velocity of every particle along
     *                 axes ( X and Y, both velocities are equal )
     * @param numberOfAtoms stores total number of particles
     */
    public void start(ExpType expType, int velocity, int numberOfAtoms, ActionListener listener) {
        active = true;

        final List<Atom> atoms = new ArrayList<>();
        final InfoFrame infoScreen = new InfoFrame(expType, atoms);
        final ArenaFrame arena;
        final Physics physics;
        final Plot plot;

        infoScreen.addListener(listener);

        // Running different experiments according to the specified value
        switch (expType) {
            case MAXWELL:
                arena = new ArenaFrame(atoms);
                generateAtomsFullArena(atoms, velocity, numberOfAtoms);
                plot = new PlotMaxwell(atoms);
                physics = new Physics(atoms);
                break;
            case BOLTZMANN:
                arena = new ArenaFrame(atoms);
                generateAtomsFullArena(atoms, velocity, numberOfAtoms);
                plot = new PlotBoltzmann(atoms);
                // see @AtomProcessorBoltzmann
                physics = new Physics(atoms, new AtomProcessorBoltzmann());
                break;
            case KNUDSEN:
                arena = new ArenaFrame(atoms, new PainterKnudsen());
                generateAtomsKnudsen(atoms, velocity, numberOfAtoms);
                plot = new PlotKnudsen(atoms);
                // see @AtomProcessorKnudsen
                physics = new Physics(atoms, new AtomProcessorKnudsen(numberOfAtoms));
                break;
            case PISTON:
                AtomProcessorPiston piston = new AtomProcessorPiston();
                arena = new ArenaFrame(atoms, new PainterPiston(piston));
                generateAtomsPiston(atoms, velocity, numberOfAtoms);
                plot = null;
                physics = new Physics(atoms, piston);
                break;
            default:
                arena = null;
                plot = null;
                physics = null;
        }

        arena.setVisible(true);
        infoScreen.setVisible(true);

        double sinceGasUpdate = 0;
        double sincePlotUpdate = 0;
        double gasTimer = System.currentTimeMillis();
        double plotTimer = System.currentTimeMillis();

        // Main loop
        while (active) {
            sinceGasUpdate += System.currentTimeMillis() - gasTimer;
            sincePlotUpdate += System.currentTimeMillis() - plotTimer;
            gasTimer  = System.currentTimeMillis();
            plotTimer = System.currentTimeMillis();

            boolean gasWasUpdated = false;
            while (sinceGasUpdate > gasTPF) {
                physics.update();
                sinceGasUpdate -= gasTPF;
                gasWasUpdated = true;
            }

            if (sincePlotUpdate > plotTPF) {
                infoScreen.update();
                if (plot != null)
                    plot.render();
                sincePlotUpdate = 0;
            }

            if (gasWasUpdated) {
                arena.pack();
                arena.repaint();
                infoScreen.pack();
                infoScreen.repaint();
            }
        }
        if (plot != null)
            plot.dispose();
        arena.dispose();
        infoScreen.dispose();
    }


    /**
     * It does what you think it does ( © C++ STL ;) )
     */
    public void stop() {
        active = false;
    }


    /**
     * Generates particles distributed at the arena randomly
     *
     * Generates atoms having root mean square speed = <code>velocity</code>.
     * Velocity vector is pointed on right-down corner
     *
     * @param atoms reference to particles list
     * @param velocity start root mean square speed
     * @param numberOfAtoms total number of particles
     */
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


    /**
     * Generates particles distributed at left half (!) of the arena randomly
     *
     * @param atoms reference to particles list
     * @param velocity start velocity along X and Y axes for every particle
     * @param numberOfAtoms total number of particles
     */
    private void generateAtomsKnudsen(List<Atom> atoms, int velocity, int numberOfAtoms) {
        Random random = new Random(System.currentTimeMillis());
        double v = Math.floor(Math.sqrt(Math.pow(velocity, 2) / 2));
        for (int i = 1; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH / 2);
            int y = random.nextInt(HEIGHT);
            Atom atom = new Atom(x, y, -v, v);
            atoms.add(atom);
        }
    }


    /**
     * Generates particles distributed at the arena randomly
     *
     * Generates atoms having root mean square speed as module of velocity.
     * Velocity vector is pointed randomly
     *
     * @param atoms reference to particles list
     * @param velocity start root mean square speed
     * @param numberOfAtoms total number of particles
     */
    private void generateAtomsPiston(List<Atom> atoms, int velocity, int numberOfAtoms) {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            double cos = 2 * Math.random() - 1;
            double sin = (random.nextInt() % 2 == 0)?(-1):(1)*Math.sqrt(1 - cos * cos);
            Atom atom = new Atom(x, y, cos * velocity, sin * velocity);
            atoms.add(atom);
        }
    }
}