package Maxwell.physics;


import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Drawer extends JPanel {

    private final int HEIGHT;
    private final int WIDTH ;
    private final int D;

    private final List<Atom> atoms;

    public Drawer(List<Atom> atoms) {
        this.HEIGHT = Maxwell.Master.HEIGHT;
        this.WIDTH = Maxwell.Master.WIDTH;
        this.atoms = atoms;
        this.D = Maxwell.Master.D;
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        for (Atom atom : atoms) {
            g2.fillOval((int)atom.x, (int)atom.y, D, D);
        }
    }
}



