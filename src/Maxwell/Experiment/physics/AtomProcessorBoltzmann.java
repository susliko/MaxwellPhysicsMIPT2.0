package Maxwell.Experiment.physics;

import java.util.List;

import static Maxwell.Experiment.Experiment.*;



/**
 * Am implementation of @AtomProcessor for Boltzmann experiment
 */
public class AtomProcessorBoltzmann implements AtomProcessor {

    @Override
    public void processAtoms(List<Atom> atoms) {
        for (Atom atom : atoms)
            if (atom.y <= HEIGHT - D)
                atom.vy += boltzmannAcceleration * gasTPF / 1000;
    }
}
