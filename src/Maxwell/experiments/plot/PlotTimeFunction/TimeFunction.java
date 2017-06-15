package Maxwell.experiments.plot.PlotTimeFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores time function data
 */
class TimeFunction {
    /**
     * List time moments
     */
    final List<Double> time;

    /**
     * List of values in appropriate time moments
     */
    final List<Double> value;


    /**
     * Initializes fields
     *
     * @see #time
     * @see #value
     */
    TimeFunction() {
        time = new ArrayList<>();
        value = new ArrayList<>();
    }
}
