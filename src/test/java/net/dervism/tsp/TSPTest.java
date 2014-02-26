package net.dervism.tsp;

import org.junit.Test;

/**
 * Created by dervism on 17/02/14.
 */
public class TSPTest {

    @Test
    public void evolutionaryGA() throws Exception {
        TSP tsp = new TSP();
        tsp.evolutionaryGA(1, 0.85, 0.3);
    }

    @Test
    public void randomGreedyCrossOver() throws Exception {
        TSP tsp = new TSP();
        tsp.runRandomGreedyCrossOverGA(1);
    }

    @Test
    public void randomizedGA() throws Exception {
        TSP tsp = new TSP();
        //tsp.runRandomizedGA(4);
    }

    @Test
    public void bruteforceTsp() throws Exception {
        TSP tsp = new TSP();
        //tsp.runBruteForceTSP();
    }

}
