package Maxwell.plot;

import Maxwell.physics.Atom;

import java.util.*;

public class PlotMaxwell extends Plot{
    /**
     * Maxwell Distribution parameter. 4PI*(m / (2ktPI)) ^ (3/2).
     */
    private double a;

    /**
     * Maxwell Distribution parameter. m / (2kT).
     */
    private double b;


    /**
     * PlotMaxwell class constructor.
     * Builds Maxwell distribution, reserves data series for real distribution,
     * constructs plot frame.
     *
     * @param atoms array with information about atoms.
     * @param avgV average value of velocity.
     */
    public PlotMaxwell(List<Atom> atoms, double avgV) {
        super(atoms, "Maxwell distribution");
        this.atoms = atoms;

        numberOfBars = 20;
        resolution = 3 * (int)avgV / numberOfBars;

        b = 4 / (Math.PI * avgV * avgV);
        a = 4 * Math.PI * Math.pow(b / Math.PI, 1.5);

        updateDistribution();
        updateRealDistribution();

        xyChart.setXAxisTitle("Velocity, m/s");
        xyChart.setYAxisTitle("Probability");
        xyChart.getStyler().setYAxisMax(0.25);
        render();
    }


    /**
     * Counts probability of having required velocity <code>v</code>.
     * Uses Maxwell distribution.
     *
     * @param v velocity.
     * @return probability of having required velocity.
     */
    @Override
    double distribution(double v) {
        return (a * v*v * Math.exp(-b * v*v));
    }


    /**
     * Updated real distribution series.
     *
     * @see Atom
     */
    @Override
    void updateRealDistribution() {
        ArrayList<Integer> realDistribution = new ArrayList<>(numberOfBars);
        for (int i = 0; i < numberOfBars; i++)
            realDistribution.add(i, 0);
        for (Atom atom : atoms) {
            int barIndex = (int)(Math.sqrt(atom.vx * atom.vx + atom.vy * atom.vy) - 1) / resolution;
            if (barIndex >= numberOfBars)
                continue;
            realDistribution.set(barIndex, realDistribution.get(barIndex) + 1);
        }

        ArrayList<Double> xData = new ArrayList<>();
        ArrayList<Double> yData = new ArrayList<>();
        for (int i = 0; i < numberOfBars; i++) {
            xData.add((double)i * resolution);
            xData.add((double)(i + 1) * resolution);
            yData.add((double)realDistribution.get(i) / atoms.size());
            yData.add((double)realDistribution.get(i) / atoms.size());
        }
        xyChart.updateXYSeries("Real Distribution", xData, yData, null);
    }
}