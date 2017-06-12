package Maxwell;

import Maxwell.Experiment.Experiment;
import Maxwell.frames.Dialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Master implements ActionListener {

    private final Dialog dialog = new Dialog();

    public void run() {
        dialog.setListener(this);
        dialog.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();

        if (cmd.equals("stop")) {
//            experiments.stop;
            dialog.setVisible(true);
        }
        if (cmd.equals("run")) {
            System.out.println(dialog.getVelocity());
            System.out.println(dialog.getN());
//            dialog.setVisible(false);
//            ExpType exp = dialog.getExperiment();
//            int velocity = dialog.getVelocity();
//            int N = dialog.getN();
//            experiments.run(exp, velocity, N);
        }

    }
}
