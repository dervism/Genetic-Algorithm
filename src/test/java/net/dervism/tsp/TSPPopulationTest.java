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
        TSPEncoder tspEncoder = new TSPEncoder();
        TSPEvolution tspEvolution = new TSPEvolution(tsp);
        TSPPopulation tspPopulation = new TSPPopulation(tsp);
        tspPopulation.setTsp(tsp);
        Random random = new Random(4567);

        tspPopulation.createPopulation(30);
        tspPopulation.selectBest(0.5);
        System.out.println(tspPopulation.stat());

    }

    @Test
    public void testGetBest() throws Exception {

    }

    @Test
    public void testCalculateFitness() throws Exception {

    }
}
