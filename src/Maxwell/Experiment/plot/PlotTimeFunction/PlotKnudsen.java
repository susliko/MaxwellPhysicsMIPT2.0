package Maxwell.Experiment.plot.PlotTimeFunction;

import Maxwell.Experiment.physics.Atom;

import java.util.List;

import static Maxwell.Experiment.Experiment.WIDTH;

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
        declareFunction("СКС в левом сосуде");
        declareFunction("СКС в правом сосуде");
    }


    /**
     * Counts and updates root mean square speed
     */
    @Override
    void updateFunctions() {
        double leftRTS = 0;
        int leftNum = 0;
        double rightRTS = 0;
        int rightNum = 0;
        for (Atom atom : atoms) {
            if (atom.x < WIDTH / 2) {
                leftNum++;
                leftRTS += atom.vx * atom.vx + atom.vy * atom.vy;
            } else {
                rightNum++;
                rightRTS += atom.vx * atom.vx + atom.vy * atom.vy;
            }
        }
        leftRTS = Math.sqrt(leftRTS / leftNum);
        rightRTS = Math.sqrt(rightRTS / rightNum);
        appendFunction("СКС в левом сосуде", leftRTS);
        appendFunction("СКС в правом сосуде", rightRTS);
    }
}