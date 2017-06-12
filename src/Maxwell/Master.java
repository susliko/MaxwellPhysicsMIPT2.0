package Maxwell;

import Maxwell.Experiment.Experiment;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Master {
    public void run() {
        Experiment experiment = new Experiment();
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.schedule(() -> {
            experiment.stop();
            System.out.println("Stop this shit");
        }, 20, TimeUnit.SECONDS);

        experiment.start(200, 100);
        System.out.println("Experiment stopped");
    }
}
