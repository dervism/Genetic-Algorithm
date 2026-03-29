package no.dervism.tsp;

import org.junit.jupiter.api.Test;

/**
 * Created by dervism on 17/02/14.
 */
class TSPPopulationTest {

    @Test
    void selectBest() throws Exception {
        TSP tsp = new TSP();
        TSPPopulation tspPopulation = new TSPPopulation();
        TSPFitnessEvaluator tspFitnessEvaluator = new TSPFitnessEvaluator(16);

        tspPopulation.createPopulation(30);
        tspFitnessEvaluator.sort(tspPopulation);
        tspPopulation.selectBest(0.5);
        System.out.println(tspFitnessEvaluator.stat(tspPopulation.list()));
    }

    @Test
    void getBest() throws Exception {

    }

    @Test
    void calculateFitness() throws Exception {

    }
}
