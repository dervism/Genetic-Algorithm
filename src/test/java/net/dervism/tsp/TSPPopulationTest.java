package net.dervism.tsp;

import org.junit.Test;

import java.util.Random;

/**
 * Created by dervism on 17/02/14.
 */
public class TSPPopulationTest {

    @Test
    public void testSelectBest() throws Exception {
        TSP tsp = new TSP();
        TSPPopulation tspPopulation = new TSPPopulation();
        TSPFitnessEvalutor tspFitnessEvalutor = new TSPFitnessEvalutor(16);

        tspPopulation.createPopulation(30);
        tspFitnessEvalutor.sort(tspPopulation);
        tspPopulation.selectBest(0.5);
        System.out.println(tspFitnessEvalutor.stat(tspPopulation.list()));
    }

    @Test
    public void testGetBest() throws Exception {

    }

    @Test
    public void testCalculateFitness() throws Exception {

    }
}
