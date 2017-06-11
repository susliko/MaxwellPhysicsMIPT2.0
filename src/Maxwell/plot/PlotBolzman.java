package Maxwell.plot;

import Maxwell.physics.Atom;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used for building plots.
 * Uses XChart library, see <a href="http://knowm.org/open-source/xchart/">XChart</a>.
 */
public class PlotBolzman {
    /**
     * Reference to a list of atoms.
     */
    private final List<Atom> atoms;

    /**
     * PlotMaxwell frame.
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
     * Bolzman Distribution parameter. m / kT.
     */
    private double a;

    /**
     * Height of arena.
     */
    private final int height;

    private int n0;

    /**
     * PlotMaxwell class constructor.
     * Builds Maxwell distribution, reserves data series for real distribution,
     * constructs plot frame.
     *
     * @param atoms array with information about atoms.
     * @param avgV average value of velocity.
     * @param height height of arena
     */
    public PlotBolzman(List<Atom> atoms, double avgV, double acceleration, int height) {
        this.atoms = atoms;
        this.height = height;

        numberOfBars = 50;
        resolution = height / numberOfBars;

        a = 8 * acceleration / (avgV * avgV * Math.PI);


        xyChart = (new XYChartBuilder()).width(600).height(600).title("Experiment results").build();
        xyChart.setXAxisTitle("Height, m");
        xyChart.setYAxisTitle("Concentration, 1 / m^3");
        xyChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        xyChart.getStyler().setDecimalPattern("#,###.##");

        ArrayList<Double> emptyY = new ArrayList<>(1);
        emptyY.add(0.0);
        XYSeries bolzman = xyChart.addSeries("Bolzman Distribution", null, emptyY);
        bolzman.setMarker(SeriesMarkers.NONE);
        n0 = atoms.size() / numberOfBars;
        updateBolzmanDistribution();

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
     * Counts concentration in range [<code>height</code>..{@link PlotBolzman#resolution}].
     * Uses Bolzman distribution.
     *
     * @param height beginning height of range.
     * @return concentration.
     */
    private double bolzmanConcentration(double height) {
        return (n0 * Math.exp(-a * height));
    }


    /**
     * Updates Maxwell distribution series.
     *
     * @see PlotMaxwell#maxwellProbability(double)
     */
    private void updateBolzmanDistribution(){
        ArrayList<Double> bolzmanDistributionX = new ArrayList<>();
        ArrayList<Double> bolzmanDistributionY = new ArrayList<>();
        bolzmanDistributionX.add(0.0);
        bolzmanDistributionY.add(resolution * bolzmanConcentration(0.0));
        for (int i = 0; i < numberOfBars; i++) {
            double currH = i * resolution;
            double nextH = (i + 1) * resolution;
            bolzmanDistributionX.add(currH + resolution / 2);
            bolzmanDistributionY.add(resolution * (bolzmanConcentration(currH) + bolzmanConcentration(nextH)) / 2);
        }
        xyChart.updateXYSeries("Bolzman Distribution",
                bolzmanDistributionX,
                bolzmanDistributionY,
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
        updateBolzmanDistribution();
        xyChart.updateXYSeries("Real Distribution", xData, yData, null);
    }
}
