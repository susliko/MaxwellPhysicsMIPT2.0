package Maxwell.Experiment.plot;

/**
 * Class for plotting experiment results
 * Uses XChart library
 * @see <a href="http://knowm.org/open-source/xchart/">XChart</a>.
 */
public interface Plot {
    /**
     *  Hides chart frame
     */
    void render();

    /**
     * Updates and redraws the frame
     */
    void dispose();
}