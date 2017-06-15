package Maxwell.experiments.plot.PlotDistribution;

import Maxwell.experiments.physics.Atom;
import Maxwell.experiments.plot.Plot;
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
 * Processes plots with physical distributions.
 * Chart consists of theoretical distribution drawn with line and
 * histogram displaying real results
 */
public abstract class PlotDistribution implements Plot {
    /**
     * List of atoms.
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
     * Plot frame
     */
    private JFrame plotFrame;


    /**
     * Initializes the chart and theoretical and real series
     *
     * @param atoms reference to a list of atoms
     * @param distributionName name of theoretical distribution
     */
    PlotDistribution(List<Atom> atoms, String distributionName) {
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
     *
     * @return mean square speed. Not root mean square speed
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
     * Counts distribution point as {@link #resolution} * x, where x is histogram bar center
     *
     * @see #distribution(double)
     */
    void updateDistribution(){
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


    abstract void updateRealDistribution();
}
