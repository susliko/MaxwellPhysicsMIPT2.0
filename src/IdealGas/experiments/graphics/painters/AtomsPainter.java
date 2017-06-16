package IdealGas.experiments.graphics.painters;


import IdealGas.experiments.physics.Atom;

import java.util.List;
import java.awt.*;

import static IdealGas.experiments.Experiment.D;

public class AtomsPainter extends Painter {

    @Override
    public void paint(Graphics g, List<Atom> atoms) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        for (Atom atom : atoms) {
            g2.fillOval((int)atom.x, (int)atom.y, D, D);
        }
    }
}
