package Maxwell;

import Maxwell.physics.Atom;
import Maxwell.plot.Plot;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Master {
    final private ArrayList<Atom> atoms;
    final private Plot plot;

    public Master() {
        int n = 200;
        atoms = new ArrayList<>(n);
        plot = new Plot(atoms);
    }

    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                plot.render();
            }
        }, 0, 1000);
    }

    ArrayList<Atom> getAtoms() {
        return atoms;
    }
}
