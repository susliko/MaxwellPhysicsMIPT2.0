package Maxwell.experiments.plot.PlotTimeFunction;

import Maxwell.experiments.physics.Atom;
import Maxwell.experiments.plot.Plot;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Maxwell.experiments.Experiment.plotTPF;

/**
 * Processes plots with functions depending on time.
 * Chart may consist of any number of functions.
 */
public abstract class PlotTimeFunction implements Plot {
    /**
     * List of atoms
     */
    final List<Atom> atoms;

    /**
     * Map of functions depending on time.
     * <code>key</code> - name of function
     * <code>value</code> - time function
     * @see TimeFunction
     */
    private final Map<String, TimeFunction> functions;

    /**
     * XYChart object for building plot.
     */
    final XYChart xyChart;

    /**
     * Time counter. Contains current time in ms.
     */
    private double currentTime = 0;

    /**
     * Swing chart panel
     */
    private SwingWrapper<XYChart> swingWrapper;

    /**
     * Plot frame.
     */
    private JFrame plotFrame;


    /**
     * Sets plot default styles and initializes function and atom lists.
     *
     * @param atoms list of atoms
     */
    PlotTimeFunction(List<Atom> atoms) {
        this.atoms = atoms;
        this.functions = new HashMap<>();

        xyChart = (new XYChartBuilder()).width(600).height(600).title("Результаты эксперимента").build();
        xyChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideN);
        xyChart.getStyler().setDecimalPattern("#,###.##");

        swingWrapper = new SwingWrapper<>(xyChart);
        plotFrame = swingWrapper.displayChart();
    }


    @Override
    public void render() {
        updateFunctions();
        updateSeries();
        currentTime += plotTPF;
        SwingUtilities.invokeLater(swingWrapper::repaintChart);
    }


    @Override
    public void dispose() {
        plotFrame.dispose();
    }


    /**
     * Adds empty function to {@link #functions} map.
     * Function has to be declared for correct chart building.
     *
     * @param name name of function and appropriate data series
     */
    void declareFunction(String name) {
        TimeFunction tf = new TimeFunction();
        List<Double> empty = new ArrayList<>(1);
        empty.add(0.0);
        functions.put(name, tf);
        xyChart.addSeries(name, null, empty);
    }


    /**
     * Appends point to function.
     * Time is initialized with {@link #currentTime}
     *
     * @param name name of function and appropriate data series
     * @param value value of function at current time
     */
    void appendFunction(String name, double value) {
        functions.get(name).time.add(currentTime / 1000);
        functions.get(name).value.add(value);
    }


    /**
     * Updates function values every {@link #render} call.
     */
    abstract void updateFunctions();


    /**
     * Updates chart series every {@link #render} call.
     */
    private void updateSeries() {
        for (Map.Entry<String, TimeFunction> series : functions.entrySet()) {
            xyChart.updateXYSeries(series.getKey(),
                    series.getValue().time,
                    series.getValue().value,
                    null);
        }
    }
}