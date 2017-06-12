package Maxwell;

import Maxwell.Experiment.Experiment;
import Maxwell.frames.Dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Master implements ActionListener {

    private final Experiment experiment = new Experiment();

    private final Dialog dialog = new Dialog();

    public void run() {
        dialog.setListener(this);
        dialog.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();

        if (cmd.equals("stop")) {
            experiment.stop();
            dialog.setVisible(true);
        }
        if (cmd.equals("run")) {
            dialog.setVisible(false);
//           final ExpType exp = dialog.getExperiment();
            ExpType exp = ExpType.BOLTZMANN; // TODO
            int velocity = dialog.getVelocity();
            int N = dialog.getN();
            Thread thread = new Thread(() -> experiment.start(exp, velocity, N));
            thread.start();
        }
    }
}
