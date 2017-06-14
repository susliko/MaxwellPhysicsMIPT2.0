package Maxwell.Experiment.plot;

import Maxwell.Experiment.physics.Atom;

import java.util.ArrayList;
import java.util.List;

/**
 * Processes Bolzman distribution series
 */
public class PlotBoltzmann extends DistributionPlot {
    /**
     * Bolzman distribution parameter. m / kT.
     */
    private double a;

    /**
     * Height of arena.
     */
    private final int height;

    /**
     * Gravitational acceleration
     */
    private final double acceleration;

    /**
     * Bolzman distribution parameter. n0.
     */
    private int n0 = 0;

    /**
     * Sets distribution parameters and draws plot.
     *
     * @param atoms array with information about atoms.
     * @param acceleration acceleration in force field
     * @param height height of arena.
     */
    public PlotBoltzmann(List<Atom> atoms, double acceleration, int height) {
        super(atoms, "Распределение Больцмана");
        this.height = height;
        this.acceleration = acceleration;

        numberOfBars = 25;
        resolution = height / numberOfBars;

        a = 2 * acceleration / meanSquareSpeed();

        xyChart.setXAxisTitle("Высота, пикс");
        xyChart.setYAxisTitle("Концентрация * dV");
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


    @Override
    void updateDistribution() {
        ArrayList<Double> distributionX = new ArrayList<>();
        ArrayList<Double> distributionY = new ArrayList<>();
        for (int i = 0; i < numberOfBars; i++) {
            double currY = i * resolution;
            distributionX.add(currY + resolution / 2);
            distributionY.add(distribution(currY) * resolution);
        }
        xyChart.updateXYSeries(distributionName,
                distributionX,
                distributionY,
                null);
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
            int barIndex = (int)Math.floor((height - atom.y) / resolution);
            if (barIndex < 0 || barIndex >= numberOfBars)
                continue;
            realDistribution.set(barIndex, realDistribution.get(barIndex) + 1);
        }

        a = 2 * acceleration / meanSquareSpeed();

        ArrayList<Double> xData = new ArrayList<>();
        ArrayList<Double> yData = new ArrayList<>();
        for (int i = 0; i < numberOfBars; i++) {
            xData.add((double)i * resolution);
            xData.add((double)(i + 1) * resolution);
            yData.add((double)realDistribution.get(i));
            yData.add((double)realDistribution.get(i));
        }
        n0 = (int)(realDistribution.get(0) / resolution);
        updateDistribution();
        xyChart.updateXYSeries("Экспериментальное распределние", xData, yData, null);
    }
}