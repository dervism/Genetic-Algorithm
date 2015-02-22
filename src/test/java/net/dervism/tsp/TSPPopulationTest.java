package net.dervism.tsp;

import org.junit.Test;

/**
 * Created by dervism on 17/02/14.
 */
public class TSPPopulationTest {

    @Test
    public void testSelectBest() throws Exception {
        TSP tsp = new TSP();
        TSPPopulation tspPopulation = new TSPPopulation();
        TSPFitnessEvaluator tspFitnessEvaluator = new TSPFitnessEvaluator(16);

        tspPopulation.createPopulation(30);
        tspFitnessEvaluator.sort(tspPopulation);
        tspPopulation.selectBest(0.5);
        System.out.println(tspFitnessEvaluator.stat(tspPopulation.list()));
    }

    @Test
    public void testGetBest() throws Exception {

    }

    @Test
    public void testCalculateFitness() throws Exception {

    }
}
