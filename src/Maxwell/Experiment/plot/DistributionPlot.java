package Maxwell.Experiment.plot;

import Maxwell.Experiment.physics.Atom;
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
 * Processes plots.
 * Uses XChart library, see <a href="http://knowm.org/open-source/xchart/">XChart</a>.
 */
public abstract class DistributionPlot implements Plot{
    /**
     * Reference to a list of atoms.
     */
    List<Atom> atoms;

    /**
     * Swing chart panel
     */
    private SwingWrapper<XYChart> swingWrapper;

    /**
     * Number of histogram bars.
     */
    int numberOfBars;

    /**
     * Range of histogram bar.
     */
    double resolution;

    /**
     * XChart object for building plot.
     */
    XYChart xyChart;

    /**
     * Name of theoretical distribution
     */
    String distributionName;

    /**
     * DistributionPlot JFrame
     */
    private JFrame plotFrame;


    /**
     * Initializes the chart and theoretical and real series
     *
     * @param atoms reference to a list of atoms
     * @param distributionName name of theoretical distribution
     */
    DistributionPlot(List<Atom> atoms, String distributionName) {
        this.atoms = atoms;
        this.distributionName = distributionName;

        xyChart = (new XYChartBuilder()).width(600).height(600).title("Результаты эксперимента").build();
        xyChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        xyChart.getStyler().setDecimalPattern("#,###.##");

        ArrayList<Double> emptyY = new ArrayList<>(1);
        emptyY.add(0.0);
        XYSeries theoretical = xyChart.addSeries(distributionName, null, emptyY);
        theoretical.setMarker(SeriesMarkers.NONE);

        XYSeries real = xyChart.addSeries("Экспериментальное распределние", null, emptyY);
        real.setMarker(SeriesMarkers.NONE);
        real.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

        swingWrapper = new SwingWrapper<>(xyChart);
        plotFrame = swingWrapper.displayChart();
    }

    @Override
    public void dispose() {
        plotFrame.dispose();
    }


    @Override
    public void render() {
        updateRealDistribution();
        SwingUtilities.invokeLater(swingWrapper::repaintChart);
    }


    /**
     * Counts mean square speed
     */
    double meanSquareSpeed() {
        double sumV = 0;
        for (Atom atom : atoms) {
            sumV += atom.vx * atom.vx + atom.vy * atom.vy;
        }
        return sumV / atoms.size();
    }


    abstract double distribution(double x);


    /**
     * Updates distribution series.
     *
     * @see DistributionPlot#distribution(double)
     */
    void updateDistribution(){
        ArrayList<Double> distributionX = new ArrayList<>();
        ArrayList<Double> distributionY = new ArrayList<>();
        distributionX.add(0.0);
        distributionY.add(resolution * distribution(0.0));
        for (int i = 0; i < numberOfBars; i++) {
            double currX = i * resolution;
            double nextX = (i + 1) * resolution;
            distributionX.add(currX + resolution / 2);
            distributionY.add(resolution * (distribution(currX) + distribution(nextX)) / 2);
        }
        xyChart.updateXYSeries(distributionName,
                distributionX,
                distributionY,
                null);
    }


    abstract void updateRealDistribution();
}
