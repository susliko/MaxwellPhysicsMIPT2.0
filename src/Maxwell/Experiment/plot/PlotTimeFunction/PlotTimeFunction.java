package Maxwell.Experiment.plot.PlotTimeFunction;

import Maxwell.Experiment.physics.Atom;
import Maxwell.Experiment.plot.Plot;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Maxwell.Experiment.Experiment.plotTPF;

public abstract class PlotTimeFunction implements Plot {
    final List<Atom> atoms;

    private final Map<String, TimeFunction> functions;

    final XYChart xyChart;

    private double currentTime = 0;

    private SwingWrapper<XYChart> swingWrapper;

    private JFrame plotFrame;


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


    void declareFunction(String name) {
        TimeFunction tf = new TimeFunction();
        List<Double> empty = new ArrayList<>(1);
        empty.add(0.0);
        functions.put(name, tf);
        xyChart.addSeries(name, null, empty);
    }

    void appendFunction(String name, double value) {
        functions.get(name).time.add(currentTime / 1000);
        functions.get(name).value.add(value);
    }


    abstract void updateFunctions();


    private void updateSeries() {
        for (Map.Entry<String, TimeFunction> series : functions.entrySet()) {
            xyChart.updateXYSeries(series.getKey(),
                    series.getValue().time,
                    series.getValue().value,
                    null);
        }
    }
}