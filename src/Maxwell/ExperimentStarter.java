package Maxwell;

import Maxwell.Experiment.Experiment;
import Maxwell.graphics.frames.DialogFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ExperimentStarter implements ActionListener {

    private final Experiment experiment = new Experiment();

    private final DialogFrame dialog = new DialogFrame();

    public void run() {
        experiment.setListener(this);
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
            ExpType exp = dialog.getExperiment();
            int velocity = dialog.getVelocity();
            int N = dialog.getN();
            Thread experimentThread = new Thread(() -> experiment.start(exp, velocity, N));
            experimentThread.start();
        }
    }
}
