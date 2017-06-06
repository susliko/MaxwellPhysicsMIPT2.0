package Maxwell.physics;


import java.util.List;


@SuppressWarnings("SameParameterValue")
public class Physics {

    private final int HEIGHT;
    private final int WIDTH ;
    private final int D;

    private final List<Atom> atoms;

    
    public Physics(List<Atom> atoms) {
        this.HEIGHT = Maxwell.Master.HEIGHT;
        this.WIDTH = Maxwell.Master.WIDTH;
        this.atoms = atoms;
        this.D = Maxwell.Master.D;
    }

    
    
    public void addAtom(double x, double y, double vx, double vy) {
        Atom atom = new Atom(x, y, vx, vy);
        atoms.add(atom);
    }
    
    

    public void update(int dt) {
        for (Atom atom : atoms) {
            atom.x += atom.vx * dt / 1000;
            atom.y += atom.vy * dt / 1000;
            processBorderCollisions(atom);
        }
        for (int i = 0; i < atoms.size(); ++i) {
            for (int j = i + 1; j < atoms.size(); ++j) {
                processCollision(atoms.get(i), atoms.get(j));
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
        double dx =atom1.x- atom2.x;
        double dy =atom1.y- atom2.y;
        double lenSqr = dx * dx + dy * dy;

        if (lenSqr < D * D) {
            double alpha1 = (atom1.vx * dx +atom1.vy* dy) / (lenSqr);
            double alpha2 = (atom2.vx * dx + atom2.vy * dy) / (lenSqr);
            double p1x = alpha1 * dx;
            double p1y = alpha1 * dy;
            double p2x = alpha2 * dx;
            double p2y = alpha2 * dy;

           atom1.vx=atom1.vx- p1x + p2x;
           atom1.vy=atom1.vy- p1y + p2y;
            atom2.vx = atom2.vx - p2x + p1x;
            atom2.vy = atom2.vy - p2y + p1y;

           atom1.x= atom2.x + dx * D / Math.sqrt(lenSqr);
           atom1.y= atom2.y + dy * D / Math.sqrt(lenSqr);
        }
    }
}
