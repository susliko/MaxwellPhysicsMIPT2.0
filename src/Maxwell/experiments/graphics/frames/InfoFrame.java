package Maxwell.experiments.graphics.frames;

import Maxwell.ExpType;
import Maxwell.experiments.graphics.panes.BoltzmannInfoPane;
import Maxwell.experiments.graphics.panes.InfoPane;
import Maxwell.experiments.graphics.panes.KnudsenInfoPane;
import Maxwell.experiments.graphics.panes.MaxwellInfoPane;
import Maxwell.experiments.physics.Atom;

import javax.swing.*;
import java.util.List;


public class InfoFrame extends JFrame {


    public InfoFrame(ExpType expType, List<Atom> atoms) {
        super("Ход эксперимента");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        switch (expType) {
            case MAXWELL:
                add(new MaxwellInfoPane(atoms));
                break;
            case BOLTZMANN:
                add(new BoltzmannInfoPane(atoms));
                break;
            case KNUDSEN:
                add(new KnudsenInfoPane(atoms));
                break;
        }
    }
}
