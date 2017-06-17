package IdealGas.experiments.physics.processors;

import IdealGas.experiments.physics.Atom;

import java.util.List;

import static IdealGas.experiments.Experiment.*;



/**
 * Am implementation of @processors for Boltzmann experiment
 */
public class AtomProcessorBoltzmann implements AtomProcessor {

    @Override
    public void processAtoms(List<Atom> atoms) {
        for (Atom atom : atoms)
            if (atom.y <= HEIGHT - D)
                atom.vy += boltzmannAcceleration * gasTPF / 1000;
    }
}
