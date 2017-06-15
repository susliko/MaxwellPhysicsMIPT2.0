package Maxwell.Experiment.graphics.painters;

import Maxwell.Experiment.physics.Atom;

import java.util.List;
import java.awt.*;



public abstract class Painter {

    public void paint(Graphics g) {}
    public void paint(Graphics g, List<Atom> objects) {}
}