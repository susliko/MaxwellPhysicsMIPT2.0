package Maxwell;

import Maxwell.Experiment.Experiment;
import Maxwell.frames.Dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Master implements ActionListener, KeyListener {

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
            System.out.println(dialog.getVelocity());
            System.out.println(dialog.getN());
            System.out.println(dialog.getExperiment());
            dialog.setVisible(false);
            ExpType exp = dialog.getExperiment();
            int velocity = dialog.getVelocity();
            int N = dialog.getN();
            Thread thread = new Thread(() -> experiment.start(exp, velocity, N));
            thread.start();
        }
    }



    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }



    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }



    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
