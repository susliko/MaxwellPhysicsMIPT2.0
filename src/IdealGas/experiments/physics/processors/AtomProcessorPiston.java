package IdealGas.experiments.physics.processors;

import IdealGas.experiments.physics.Atom;

import java.util.List;

import static IdealGas.experiments.Experiment.*;

public class AtomProcessorPiston implements AtomProcessor {
    private double y = HEIGHT / 2;
    private double vy = 0;

    @Override
    public void processAtoms(List<Atom> atoms) {
        double dv = 0;
        for (Atom atom : atoms) {
            if (atom.y <= y) {
                atom.y = y + D;
                if (atom.vy <= 0 || atom.vy > 0 && vy > atom.vy) {
                    dv += 2 * atom.vy - 2 * vy;
                    atom.vy = -atom.vy + 2 * vy;
                }
            }
        }
        dv /= pistonWeight;
        y += vy * gasTPF / 1000;
        vy += 100 * gasTPF / 1000 + dv;
        if (y >= HEIGHT || y < 0)
            vy = -vy;
    }

    public double getPistonY() {
        return y;
    }
}