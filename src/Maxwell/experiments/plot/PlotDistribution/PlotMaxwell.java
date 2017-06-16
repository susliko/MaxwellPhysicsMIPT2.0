package Maxwell.experiments.plot.PlotDistribution;

import Maxwell.experiments.physics.Atom;

import java.util.*;

/**
 * Processes Maxwell distribution charts
 */
public class PlotMaxwell extends PlotDistribution {
    /**
     * Maxwell Distribution parameter. m / kt.
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
     */
    public PlotMaxwell(List<Atom> atoms) {
        super(atoms, "Распределение Максвелла");
        this.atoms = atoms;

        numberOfBars = 60;
        double mss = meanSquareSpeed();
        resolution = 3.5 * Math.sqrt(mss) / numberOfBars;
        b = 1 / mss;
        a = 2 * Math.PI * b / Math.PI;
        updateDistribution();
        updateRealDistribution();

        xyChart.getStyler().setDecimalPattern("#,###.####");
        xyChart.setXAxisTitle("Скорость, пикс/сек");
        xyChart.setYAxisTitle("Вероятность");
        xyChart.getStyler().setYAxisMax(1.5 * resolution * distribution(Math.sqrt(mss)));
        render();
    }


    /**
     * Counts probability of having required velocity <code>v</code>.
     * Uses two-dimensional Maxwell distribution.
     *
     * @param v velocity.
     * @return probability of having required velocity.
     */
    @Override
    double distribution(double v) {
        return (a * v * Math.exp(-b * v*v));
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
            int barIndex = (int)Math.floor(Math.sqrt(atom.vx * atom.vx + atom.vy * atom.vy) / resolution);
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
        xyChart.updateXYSeries("Экспериментальное распределние", xData, yData, null);
    }
}