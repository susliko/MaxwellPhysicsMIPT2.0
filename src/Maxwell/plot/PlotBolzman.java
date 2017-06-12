package Maxwell.plot;

import Maxwell.physics.Atom;

import java.util.ArrayList;
import java.util.List;

/**
 * Processes Bolzman distribution series
 */
public class PlotBolzman extends Plot{
    /**
     * Bolzman distribution parameter. m / kT.
     */
    private double a;

    /**
     * Height of arena.
     */
    private final int height;

    /**
     * Bolzman distribution parameter. n0.
     */
    private int n0 = 0;

    /**
     * Sets distribution parameters and draws plot.
     *
     * @param atoms array with information about atoms.
     * @param avgV average value of velocity.
     * @param acceleration acceleration in force field
     * @param height height of arena.
     */
    public PlotBolzman(List<Atom> atoms, double avgV, double acceleration, int height) {
        super(atoms, "Bolzman distribution");
        this.height = height;

        numberOfBars = 20;
        resolution = height / numberOfBars;

        a = 8 * acceleration / (avgV * avgV * Math.PI);
        n0 = atoms.size() / numberOfBars;

        updateDistribution();
        updateRealDistribution();

        xyChart.setXAxisTitle("Height, m");
        xyChart.setYAxisTitle("Concentration * dV");
        render();
    }

    /**
     * Counts concentration for required height.
     * Uses Bolzman distribution.
     *
     * @param height beginning height of range.
     * @return concentration.
     */
    @Override
    double distribution(double height) {
        return (n0 * Math.exp(-a * height));
    }


    /**
     * Updates real distribution series.
     *
     * @see Atom
     */
    @Override
    void updateRealDistribution() {
        ArrayList<Integer> realDistribution = new ArrayList<>(numberOfBars);
        for (int i = 0; i < numberOfBars; i++)
            realDistribution.add(i, 0);
        for (Atom atom : atoms) {
            int barIndex = (height - (int)atom.y) / resolution;
            if (barIndex >= numberOfBars)
                continue;
            realDistribution.set(barIndex, realDistribution.get(barIndex) + 1);
        }

        ArrayList<Double> xData = new ArrayList<>();
        ArrayList<Double> yData = new ArrayList<>();
        for (int i = 0; i < numberOfBars; i++) {
            xData.add((double)i * resolution);
            xData.add((double)(i + 1) * resolution);
            yData.add((double)realDistribution.get(i));
            yData.add((double)realDistribution.get(i));
        }
        n0 = (int)(Math.exp(3 * a * resolution / 2) * realDistribution.get(1) / resolution);
        updateDistribution();
        xyChart.updateXYSeries("Real Distribution", xData, yData, null);
    }
}