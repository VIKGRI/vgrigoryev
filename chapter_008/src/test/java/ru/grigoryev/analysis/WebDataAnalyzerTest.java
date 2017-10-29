package ru.grigoryev.analysis;

import org.junit.Test;

/**
 * Class for testing WebDataAnalyzer class.
 *
 * @author vgrigoryev
 * @version 1
 * @since 28.10.2017
 */
public class WebDataAnalyzerTest {
    /**
     * Method for testing WebDataAnalyzer's methods.
     */
    @Test
    public void whenSetSpecifiedPeriodicityThenSystemReadsFromWebAntWritesToDatabaseEach10Seconds() {

        try {
            WebDataAnalyzer analyzer = new WebDataAnalyzer("http://www.sql.ru/forum/job/1");
            analyzer.setPeriodicity(8640); // It corresponds to launching program each 10 seconds.
            analyzer.launchSystem();
            int idle = 1000;
            while (idle > 0) {
                idle--;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}