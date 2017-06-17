package IdealGas.experiments.physics.processors;

import IdealGas.experiments.physics.Atom;

import java.util.ArrayList;
import java.util.List;

import static IdealGas.experiments.Experiment.*;



/**
 * Am implementation of @processors for IdealGas experiment
 */
public class AtomProcessorKnudsen implements AtomProcessor {
    private final List<Boolean> isLeftSide;

    public AtomProcessorKnudsen(int numberOfAtoms) {
        isLeftSide = new ArrayList<>(numberOfAtoms);
        for (int i = 0; i < numberOfAtoms; i++)
            isLeftSide.add(true);
    }



    @Override
    public void processAtoms(List<Atom> atoms) {
        for (int i = 0; i < atoms.size(); i++) {
            Atom atom = atoms.get(i);
            if (isLeftSide.get(i)) {
                if (atom.vx > 0 && atom.x > WIDTH / 2) {
                    if (isNotInHole(atom)) {
                        atom.vx = -atom.vx;
                    } else {
                        isLeftSide.set(i, false);
                    }
                }
            } else {
                if (atom.vx < 0 && atom.x < WIDTH / 2) {
                    if (isNotInHole(atom)) {
                        atom.vx = -atom.vx;
                    } else {
                        isLeftSide.set(i, true);
                    }
                }
            }
        }
    }



    private boolean isNotInHole(Atom atom) {
        for (int i = 1; i < knudsenNumberOfHoles; i++)
            if (atom.y >= i * HEIGHT / knudsenNumberOfHoles - D / 2
                    && atom.y <= i * HEIGHT / knudsenNumberOfHoles + D / 2)
                return false;
        return true;
    }
}
