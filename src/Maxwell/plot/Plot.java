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
 * Processes plots.
 * Uses XChart library, see <a href="http://knowm.org/open-source/xchart/">XChart</a>.
 */
public abstract class Plot {
    /**
     * Reference to a list of atoms.
     */
    List<Atom> atoms;

    /**
     * Plot frame.
     */
    private SwingWrapper<XYChart> swingWrapper;

    /**
     * Number of histogram bars.
     */
    int numberOfBars;

    /**
     * Range of histogram bar.
     */
    int resolution;

    /**
     * XChart object for building plot.
     */
    XYChart xyChart;

    /**
     * Name of theoretical distribution
     */
    private String distributionName;

    /**
     * Initializes the chart and theoretical and real series
     *
     * @param atoms reference to a list of atoms
     * @param distributionName name of theoretical distribution
     */
    Plot(List<Atom> atoms, String distributionName) {
        this.atoms = atoms;
        this.distributionName = distributionName;

        xyChart = (new XYChartBuilder()).width(600).height(600).title("Experiment results").build();
        xyChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        xyChart.getStyler().setDecimalPattern("#,###.##");

        ArrayList<Double> emptyY = new ArrayList<>(1);
        emptyY.add(0.0);
        XYSeries theoretical = xyChart.addSeries(distributionName, null, emptyY);
        theoretical.setMarker(SeriesMarkers.NONE);

        XYSeries real = xyChart.addSeries("Real Distribution", null, emptyY);
        real.setMarker(SeriesMarkers.NONE);
        real.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

        swingWrapper = new SwingWrapper<>(xyChart);
        swingWrapper.displayChart();

    }



//    /**
//     *  Sets the chart visible
//     */
//    public void display() {
//    }


    /**
     * Updates and redraws the frame
     */
    public void render() {
        updateRealDistribution();
        SwingUtilities.invokeLater(swingWrapper::repaintChart);
    }

    abstract double distribution(double x);


    /**
     * Updates distribution series.
     *
     * @see Plot#distribution(double)
     */
    void updateDistribution(){
        ArrayList<Double> maxwellDistributionX = new ArrayList<>();
        ArrayList<Double> maxwellDistributionY = new ArrayList<>();
        maxwellDistributionX.add(0.0);
        maxwellDistributionY.add(resolution * distribution(0.0));
        for (int i = 0; i < numberOfBars; i++) {
            double currV = i * resolution;
            double nextV = (i + 1) * resolution;
            maxwellDistributionX.add(currV + resolution / 2);
            maxwellDistributionY.add(resolution * (distribution(currV) + distribution(nextV)) / 2);
        }
        xyChart.updateXYSeries(distributionName,
                maxwellDistributionX,
                maxwellDistributionY,
                null);
    }

    abstract void updateRealDistribution();
}
