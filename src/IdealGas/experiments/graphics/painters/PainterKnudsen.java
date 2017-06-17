package IdealGas.experiments.graphics.painters;

import java.awt.*;

import static IdealGas.experiments.Experiment.*;



public class PainterKnudsen extends Painter {
    @Override
    public void paint(Graphics g) {
        Graphics2D g2= (Graphics2D)g;
        g2.setColor(Color.ORANGE);
        g2.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT / (knudsenNumberOfHoles + 1) - D);
        for (int i = 2; i < knudsenNumberOfHoles + 1; i++) {
            g2.drawLine(WIDTH / 2, (i - 1) * HEIGHT / (knudsenNumberOfHoles + 1) + D,
                        WIDTH / 2, i * HEIGHT / (knudsenNumberOfHoles + 1) - D);
        }
        g2.drawLine(WIDTH / 2,  knudsenNumberOfHoles * HEIGHT / (knudsenNumberOfHoles + 1) + D,
                WIDTH / 2, HEIGHT);
    }
}
