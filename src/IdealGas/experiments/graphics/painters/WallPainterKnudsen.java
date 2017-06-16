package IdealGas.experiments.graphics.painters;

import java.awt.*;

import static IdealGas.experiments.Experiment.*;



public class WallPainterKnudsen extends Painter {
    @Override
    public void paint(Graphics g) {
        Graphics2D g2= (Graphics2D)g;
        g2.setColor(Color.ORANGE);
        g2.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT / knudsenNumberOfHoles - D);
        for (int i = 2; i < knudsenNumberOfHoles; i++) {
            g2.drawLine(WIDTH / 2, (i - 1) * HEIGHT / knudsenNumberOfHoles + D,
                        WIDTH / 2, i * HEIGHT / knudsenNumberOfHoles - D);
        }
        g2.drawLine(WIDTH / 2,  (knudsenNumberOfHoles - 1) * HEIGHT / knudsenNumberOfHoles + D,
                WIDTH / 2, HEIGHT);
    }
}
