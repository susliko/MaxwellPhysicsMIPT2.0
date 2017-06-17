package IdealGas.experiments.physics.processors;

import IdealGas.experiments.physics.Atom;

import java.util.List;



/**
 * Instances of this interface are invoked every frame to process all particles
 */
public interface AtomProcessor {
    void processAtoms(List<Atom> atoms);
}
