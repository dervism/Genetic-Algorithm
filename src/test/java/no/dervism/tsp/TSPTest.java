package no.dervism.tsp;

import org.junit.jupiter.api.Test;

/**
 * Created by dervism on 17/02/14.
 */
class TSPTest {

    @Test
    void evolutionaryIncreasingGA() throws Exception {
        TSP tsp = new TSP();
        //tsp.evolutionaryIncreasingGA(1, 0.85, 0.15, 100, 0.6);
    }

    @Test
    void evolutionaryGA() throws Exception {
        TSP tsp = new TSP();
        //tsp.evolutionaryGA(1, 0.85, 0.15, 100);
    }

    @Test
    void randomizedGA() throws Exception {
        TSP tsp = new TSP();
        //tsp.runRandomizedGA(4);
    }

    @Test
    void bruteforceTsp() throws Exception {
        TSP tsp = new TSP();
        //tsp.runBruteForceTSP();
    }

}
