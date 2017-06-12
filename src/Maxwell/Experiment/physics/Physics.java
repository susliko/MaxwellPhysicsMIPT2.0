package Maxwell.Experiment.physics;

import Maxwell.Experiment.Experiment;

import java.util.ArrayList;
import java.util.List;


public class Physics {

    private final int HEIGHT;
    private final int WIDTH ;
    private final int D;

    private final List<Atom> atoms;

    private final int gridHeight;
    private final int gridWidth;
    private final ArrayList<Atom>[] grid;


    public Physics(List<Atom> atoms) {
        this.HEIGHT = Experiment.HEIGHT;
        this.WIDTH = Experiment.WIDTH;
        this.atoms = atoms;
        this.D = Experiment.D;
        this.gridHeight  = this.HEIGHT / D;
        this.gridWidth = this.WIDTH / D;
        this.grid = (ArrayList<Atom>[])new ArrayList[gridWidth * gridHeight];
    }



    public void update(int dt) {

        for (Atom atom : atoms) {
            atom.vy += 10;
            atom.x += atom.vx * dt / 1000;
            atom.y += atom.vy * dt / 1000;
            processBorderCollisions(atom);
        }

        for (ArrayList<Atom> particles : grid) {
            if (particles != null)
                particles.clear();
        }


        for (Atom atom : atoms) {
            int i = (int)atom.y / D;
            int j = (int)atom.x / D;
            int n = i * gridWidth + j;
            if (grid[n] == null) {
                grid[n] = new ArrayList<>();
            }
            grid[n].add(atom);
        }


        for (int i = 0; i < grid.length; ++i) {

            ArrayList<Atom> cell = grid[i];

            if (cell != null)
            for (Atom atom1 : cell) {

                for (Atom atom2 : cell) {
                    if (atom1 != atom2) {
                        processCollision(atom1, atom2);
                    }
                }

                if (i >= gridWidth && i % gridWidth < gridWidth - 1) {
                    ArrayList<Atom> upDiagCell = grid[i - gridWidth + 1];
                    if (upDiagCell != null) {
                        for (Atom atom2 : upDiagCell) {
                            processCollision(atom1, atom2);
                        }
                    }
                }

                if (i % gridWidth < gridWidth - 1) {
                    ArrayList<Atom> rightCell = grid[i + 1];
                    if (rightCell != null) {
                        for (Atom atom2 : rightCell) {
                            processCollision(atom1, atom2);
                        }
                    }
                }

                if (i + gridWidth + 1 < grid.length && i % gridWidth < gridWidth - 1) {
                    ArrayList<Atom> downDiagCell = grid[i + gridWidth + 1];
                    if (downDiagCell != null) {
                        for (Atom atom2 : downDiagCell) {
                            processCollision(atom1, atom2);
                        }
                    }
                }

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



    private void processBorderCollisions(Atom atom) {
        if (atom.x + D > WIDTH)  { atom.x = WIDTH - D; atom.vx = -atom.vx; }
        if (atom.y + D > HEIGHT) { atom.y = HEIGHT - D; atom.vy = -atom.vy; }
        if (atom.x < 0) { atom.x = 0; atom.vx = -atom.vx; }
        if (atom.y < 0) { atom.y = 0; atom.vy = -atom.vy; }
    }



    private void processCollision(Atom atom1, Atom atom2) {
        double dx = atom1.x - atom2.x;
        double dy = atom1.y - atom2.y;
        double lenSqr = dx * dx + dy * dy;

        if (lenSqr < D * D) {

            if (lenSqr == 0) {
//                System.out.println("lenSqr is null");
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
