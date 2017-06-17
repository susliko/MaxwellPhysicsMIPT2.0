package IdealGas.experiments.physics;

import IdealGas.experiments.Experiment;
import IdealGas.experiments.physics.processors.AtomProcessor;

import java.util.ArrayList;
import java.util.List;

import static IdealGas.experiments.Experiment.gasTPF;



/**
 * Represents an instance which is responsible for all physics logic
 */
public class Physics {

    // See comments in @experiments, fields are the same
    private final int HEIGHT;
    private final int WIDTH ;
    private final int D;

    private final List<Atom> atoms;

    // Size of rectangles in the grid
    private final int gridHeight;
    private final int gridWidth;
    // The grid is a split of arena into rectangles, each rectangle stores
    // particles in it at the moment. This is used to reach O(N) collision algorithm
    private final ArrayList<Atom>[] grid;

    // Instance which is invoked every frame to process every particle
    private final AtomProcessor atomProcessor;

    public Physics(List<Atom> atoms, AtomProcessor atomProcessor) {
        this.HEIGHT = Experiment.HEIGHT;
        this.WIDTH = Experiment.WIDTH;
        this.atoms = atoms;
        this.D = Experiment.D;
        this.gridHeight  = this.HEIGHT / D;
        this.gridWidth = this.WIDTH / D;
        this.grid = (ArrayList<Atom>[])new ArrayList[gridWidth * gridHeight];
        this.atomProcessor = atomProcessor;
    }

    public Physics(List<Atom> atoms) {
        this.HEIGHT = Experiment.HEIGHT;
        this.WIDTH = Experiment.WIDTH;
        this.atoms = atoms;
        this.D = Experiment.D;
        this.gridHeight  = this.HEIGHT / D;
        this.gridWidth = this.WIDTH / D;
        this.grid = (ArrayList<Atom>[])new ArrayList[gridWidth * gridHeight];
        this.atomProcessor = null;
    }


    /**
     * Checks collisions, updates coordinates
     * Invoked every frame
     */
    public void update() {
        // Call of @processors instance
        if (atomProcessor != null)
            atomProcessor.processAtoms(atoms);

        // Checking border collisions
        for (Atom atom : atoms) {
            atom.x += atom.vx * gasTPF / 1000;
            atom.y += atom.vy * gasTPF / 1000;
            processBorderCollisions(atom);
        }

        // Clearing the grid
        for (ArrayList<Atom> particles : grid) {
            if (particles != null)
                particles.clear();
        }

        // Filling the grid
        for (Atom atom : atoms) {
            int i = (int)atom.y / D;
            int j = (int)atom.x / D;
            int n = i * gridWidth + j;
            if (n < 0 || n >= grid.length)
                continue;
            if (grid[n] == null) {
                grid[n] = new ArrayList<>();
            }
            grid[n].add(atom);
        }

        // Processing every cell in the grid
        for (int i = 0; i < grid.length; ++i) {

            ArrayList<Atom> cell = grid[i];

            // If there are particles in the current cell, process collisions
            if (cell != null)
            for (Atom atom1 : cell) {

                // Collisions with other atoms in the current cell
                for (Atom atom2 : cell) {
                    if (atom1 != atom2) {
                        processCollision(atom1, atom2);
                    }
                }

                // * * x
                // * c *
                // * * *
                // c - current cell, x - checked cell
                if (i >= gridWidth && i % gridWidth < gridWidth - 1) {
                    ArrayList<Atom> upDiagCell = grid[i - gridWidth + 1];
                    if (upDiagCell != null) {
                        for (Atom atom2 : upDiagCell) {
                            processCollision(atom1, atom2);
                        }
                    }
                }

                // * * *
                // * c x
                // * * *
                // c - current cell, x - checked cell
                if (i % gridWidth < gridWidth - 1) {
                    ArrayList<Atom> rightCell = grid[i + 1];
                    if (rightCell != null) {
                        for (Atom atom2 : rightCell) {
                            processCollision(atom1, atom2);
                        }
                    }
                }

                // * * *
                // * c *
                // * * x
                // c - current cell, x - checked cell
                if (i + gridWidth + 1 < grid.length && i % gridWidth < gridWidth - 1) {
                    ArrayList<Atom> downDiagCell = grid[i + gridWidth + 1];
                    if (downDiagCell != null) {
                        for (Atom atom2 : downDiagCell) {
                            processCollision(atom1, atom2);
                        }
                    }
                }

                // * * *
                // * c *
                // * x *
                // c - current cell, x - checked cell
                if (i + gridWidth < grid.length) {
                    ArrayList<Atom> downCell = grid[i + gridWidth];
                    if (downCell != null) {
                        for (Atom atom2 : downCell) {
                            processCollision(atom1, atom2);
                        }
                    }
                }
            }
        }
    }


    /**
     * Processes collision of the specified atom with all borders of the arens
     * @param atom reference to a particle which needs to be processed
     */
    private void processBorderCollisions(Atom atom) {
        if (atom.x + D > WIDTH && atom.vx > 0) atom.vx = -atom.vx;
        if (atom.y + D > HEIGHT && atom.vy > 0) atom.vy = -atom.vy;
        if (atom.x < 0 && atom.vx < 0) atom.vx = -atom.vx;
        if (atom.y < 0 && atom.vy < 0) atom.vy = -atom.vy;
    }


    /**
     * Process perfectly elastic collision if particles intersect
     * @param atom1 first particle in collision check
     * @param atom2 second particle in collision check
     */
    private void processCollision(Atom atom1, Atom atom2) {
        double dx = atom1.x - atom2.x;
        double dy = atom1.y - atom2.y;
        double lenSqr = dx * dx + dy * dy;

        if (lenSqr < D * D) {

            if (lenSqr == 0) {
                return; // Running away from your problems
            }

            double alpha1 = (atom1.vx * dx + atom1.vy * dy) / (lenSqr);
            double alpha2 = (atom2.vx * dx + atom2.vy * dy) / (lenSqr);
            double p1x = alpha1 * dx;
            double p1y = alpha1 * dy;
            double p2x = alpha2 * dx;
            double p2y = alpha2 * dy;

            atom1.vx = atom1.vx - p1x + p2x;
            atom1.vy = atom1.vy - p1y + p2y;
            atom2.vx = atom2.vx - p2x + p1x;
            atom2.vy = atom2.vy - p2y + p1y;

            atom1.x = atom2.x + dx * D / Math.sqrt(lenSqr);
            atom1.y = atom2.y + dy * D / Math.sqrt(lenSqr);
        }
    }
}
