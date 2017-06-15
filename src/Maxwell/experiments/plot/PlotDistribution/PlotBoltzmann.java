package Maxwell.experiments.plot.PlotDistribution;

import Maxwell.experiments.physics.Atom;

import java.util.ArrayList;
import java.util.List;

import static Maxwell.experiments.Experiment.HEIGHT;
import static Maxwell.experiments.Experiment.boltzmannAcceleration;

/**
 * Processes Bolzman distribution charts
 */
public class PlotBoltzmann extends PlotDistribution {
    /**
     * Bolzman distribution parameter. m / kT.
     */
    private double a;

    /**
     * Bolzman distribution parameter. n0.
     */
    private int n0 = 0;

    /**
     * Sets distribution parameters and draws plot.
     *
     * @param atoms array with information about atoms.
     */
    public PlotBoltzmann(List<Atom> atoms) {
        super(atoms, "Распределение Больцмана");

        numberOfBars = 25;
        resolution = HEIGHT / numberOfBars;

        a = 2 * boltzmannAcceleration / meanSquareSpeed();

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
            int barIndex = (int)Math.floor((HEIGHT - atom.y) / resolution);
            if (barIndex < 0 || barIndex >= numberOfBars)
                continue;
            realDistribution.set(barIndex, realDistribution.get(barIndex) + 1);
        }

        a = 2 * boltzmannAcceleration / meanSquareSpeed();

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