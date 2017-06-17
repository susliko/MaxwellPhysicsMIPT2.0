package IdealGas.experiments.plot.PlotTimeFunction;

import IdealGas.experiments.physics.processors.AtomProcessorPiston;

import static IdealGas.experiments.Experiment.HEIGHT;

public class PlotPiston extends PlotTimeFunction{
    private AtomProcessorPiston piston;

    public PlotPiston (AtomProcessorPiston piston) {
        super(null);
        this.piston = piston;
        declareFunction("Высота поршня");
        xyChart.setXAxisTitle("Время, сек");
        xyChart.setYAxisTitle("Высота поршня, пикс");
        xyChart.getStyler().setMarkerSize(0);
        xyChart.getStyler().setYAxisMax((double)HEIGHT);
        xyChart.getStyler().setYAxisMin(0.0);
    }

    @Override
    void updateFunctions() {
        appendFunction("Высота поршня", HEIGHT - piston.getPistonY());
    }
}
