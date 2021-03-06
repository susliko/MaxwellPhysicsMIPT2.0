package IdealGas.experiments.plot.PlotTimeFunction;

import IdealGas.experiments.physics.Atom;

import java.util.List;

import static IdealGas.experiments.Experiment.D;
import static IdealGas.experiments.Experiment.WIDTH;


/**
 * Processes results of Knudsen experiment.
 */
public class PlotKnudsen extends PlotTimeFunction{
    /**
     * Sets styles of chart
     *
     * @param atoms list of atoms
     * @see PlotTimeFunction#atoms
     */
    public PlotKnudsen(List<Atom> atoms) {
        super(atoms);
        xyChart.setXAxisTitle("Время, сек");
        xyChart.setYAxisTitle("СКС, пикс/сек");
        xyChart.getStyler().setMarkerSize(0);
//        xyChart.getStyler().setYAxisMax(5.0);
        declareFunction("СКС в левом сосуде");
        declareFunction("СКС в правом сосуде");
    }


    /**
     * Counts and updates root mean square speed
     */
    @Override
    void updateFunctions() {
        double leftRMS = 0;
        double leftNum = 0;
        double rightRMS = 0;
        double rightNum = 0;
        for (Atom atom : atoms) {
            if (atom.x < WIDTH / 2) {
                leftNum++;
                leftRMS += atom.vx * atom.vx + atom.vy * atom.vy;
            } else if (atom.x > WIDTH / 2 + 2 * D) {
                rightNum++;
                rightRMS += atom.vx * atom.vx + atom.vy * atom.vy;
            }
        }
        if (leftNum != 0) {
            leftRMS = Math.sqrt(leftRMS / leftNum);
            System.out.println(leftRMS);
            appendFunction("СКС в левом сосуде", leftRMS);
        }
        if (rightNum != 0) {
            rightRMS = Math.sqrt(rightRMS / rightNum);
            System.out.println(rightRMS);
            appendFunction("СКС в правом сосуде", rightRMS);
        }
    }
}