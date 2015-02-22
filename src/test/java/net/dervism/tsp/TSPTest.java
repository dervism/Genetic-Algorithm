package net.dervism.tsp;

import org.junit.Test;

/**
 * Created by dervism on 17/02/14.
 */
public class TSPTest {

    @Test
    public void evolutionaryIncreasingGA() throws Exception {
        TSP tsp = new TSP();
        tsp.evolutionaryIncreasingGA(1, 0.85, 0.15, 100, 0.6);
    }

    @Test
    public void evolutionaryGA() throws Exception {
        TSP tsp = new TSP();
        tsp.evolutionaryGA(1, 0.85, 0.15, 100);
    }

    @Test
    public void randomizedGA() throws Exception {
        TSP tsp = new TSP();
        tsp.runRandomizedGA(4);
    }

    @Test
    public void bruteforceTsp() throws Exception {
        TSP tsp = new TSP();
        tsp.runBruteForceTSP();
    }

}
