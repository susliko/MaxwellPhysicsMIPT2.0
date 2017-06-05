package Maxwell;

import Maxwell.physics.Atom;
import Maxwell.plot.Plot;

import java.util.ArrayList;

public class Master {
    final private ArrayList<Atom> atoms;

    public Master() {
        int n = 200;
        atoms = new ArrayList<>(n);
    }

    public void run() {

    }

    ArrayList<Atom> getAtoms() {
        return atoms;
    }
}
