package IdealGas.experiments.graphics.painters;

import IdealGas.experiments.physics.processors.AtomProcessorPiston;

import java.awt.*;

import static IdealGas.experiments.Experiment.WIDTH;

public class PainterPiston extends Painter {
    private final AtomProcessorPiston piston;

    public PainterPiston(AtomProcessorPiston piston) {
        this.piston = piston;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.ORANGE);
        g2.drawLine(0, (int)Math.ceil(piston.getPistonY()), WIDTH, (int)Math.ceil(piston.getPistonY()));
    }
}
