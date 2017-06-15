package Maxwell.Experiment.physics;

import java.util.List;



/**
 * Instances of this interface are invoked every frame to process all particles
 */
public interface AtomProcessor {
    void processAtoms(List<Atom> atoms);
}
