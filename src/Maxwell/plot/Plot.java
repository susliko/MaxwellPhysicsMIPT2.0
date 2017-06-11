package Maxwell.plot;

import Maxwell.physics.Atom;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.util.*;

/**
 * Class used for building plots.
 * Uses XChart library, see <a href="http://knowm.org/open-source/xchart/">XChart</a>.
 */
public class Plot {
    /**
     * Reference to a list of atoms.
     */
    private final List<Atom> atoms;

    /**
     * Plot frame.
     */
    private final SwingWrapper<XYChart> swingWrapper;

    /**
     * Number of histogram bars.
     */
    private final int numberOfBars;

    /**
     * Range of histogram bar.
     */
    private int resolution;

    /**
     * XChart object for building plot.
     */
    private XYChart xyChart;

    /**
     * Maxwell Distribution parameter. 4PI*(m / (2ktPI)) ^ (3/2).
     */
    private double a;

    /**
     * Maxwell Distribution parameter. m / (2kT).
     */
    private double b;


    /**
     * Plot class constructor.
     * Builds Maxwell distribution, reserves data series for real distribution,
     * constructs plot frame.
     *
     * @param atoms array with information about atoms.
     * @param avgV average value of velocity.
     */
    public Plot(List<Atom> atoms, double avgV) {
        this.atoms = atoms;

        numberOfBars = 20;
        resolution = 3 * (int)avgV / numberOfBars;

        b = 4 / (Math.PI * avgV * avgV);
        a = 4 * Math.PI * Math.pow(b / Math.PI, 1.5);


        xyChart = (new XYChartBuilder()).width(600).height(600).title("Experiment results").build();
        xyChart.setXAxisTitle("Velocity, m/s");
        xyChart.setYAxisTitle("Probability");
        xyChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        xyChart.getStyler().setDecimalPattern("#,###.##");
        xyChart.getStyler().setYAxisMax(0.25);

        ArrayList<Double> emptyY = new ArrayList<>(1);
        emptyY.add(0.0);
        XYSeries maxwell = xyChart.addSeries("Maxwell Distribution", null, emptyY);
        maxwell.setMarker(SeriesMarkers.NONE);
        updateMaxwellDistribution();

        XYSeries real = xyChart.addSeries("Real Distribution", null, emptyY);
        real.setMarker(SeriesMarkers.NONE);
        real.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        updateRealDistribution();

        swingWrapper = new SwingWrapper<>(xyChart);
        swingWrapper.displayChart();
    }


    /**
     * Updates and redraws the frame.
     */
    public void render() {
        updateRealDistribution();
        SwingUtilities.invokeLater(swingWrapper::repaintChart);
    }


    /**
     * Counts probability of having velocity in range [<code>v</code>..{@link Plot#resolution}].
     * Uses Maxwell distribution.
     *
     * @param v beginning velocity of range.
     * @return probability of having required velocity.
     */
    private double maxwellProbability(double v) {
        return (a * v*v * Math.exp(-b * v*v));
    }


    /**
     * Updates Maxwell distribution series.
     *
     * @see Plot#maxwellProbability(double)
     */
    private void updateMaxwellDistribution(){
        ArrayList<Double> maxwellDistributionX = new ArrayList<>();
        ArrayList<Double> maxwellDistributionY = new ArrayList<>();
        maxwellDistributionX.add(0.0);
        maxwellDistributionY.add(maxwellProbability(0.0));
        for (int i = 0; i < numberOfBars; i++) {
            double currV = i * resolution;
            double nextV = (i + 1) * resolution;
            maxwellDistributionX.add(currV + resolution / 2);
            maxwellDistributionY.add(resolution * (maxwellProbability(currV) + maxwellProbability(nextV)) / 2);
        }
        xyChart.updateXYSeries("Maxwell Distribution",
                               maxwellDistributionX,
                               maxwellDistributionY,
                               null);
    }


    /**
     * Updated real distribution series.
     *
     * @see Atom
     */
    private void updateRealDistribution() {
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