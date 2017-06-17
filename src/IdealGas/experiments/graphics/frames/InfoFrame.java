package IdealGas.experiments.graphics.frames;

import IdealGas.ExpType;
import IdealGas.experiments.graphics.panes.BoltzmannInfoPane;
import IdealGas.experiments.graphics.panes.InfoPane;
import IdealGas.experiments.graphics.panes.KnudsenInfoPane;
import IdealGas.experiments.graphics.panes.MaxwellInfoPane;
import IdealGas.experiments.physics.Atom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;


public class InfoFrame extends JFrame {

    private InfoPane pane;

    public InfoFrame(ExpType expType, List<Atom> atoms) {
        super("Ход эксперимента");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        Dimension windowSize = getPreferredSize();

        setLocation(50, screenSize.height - (int)windowSize.getHeight() - 400);

        System.out.println(expType);
        switch (expType) {
            case MAXWELL:
                pane = new MaxwellInfoPane(atoms);
                break;
            case PISTON:
            case BOLTZMANN:
                pane = new BoltzmannInfoPane(atoms);
                break;
            case KNUDSEN:
                pane = new KnudsenInfoPane(atoms);
                break;
        }

        add(pane);

        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("TextField.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("TextField.background", Color.lightGray);
        UIManager.put("TextField.border", BorderFactory.createLineBorder(Color.BLACK));

        SwingUtilities.updateComponentTreeUI(this);
    }



    public void addListener(ActionListener listener) {
        pane.addListener(listener);
    }



    public void update() {
        pane.update();
    }
}
