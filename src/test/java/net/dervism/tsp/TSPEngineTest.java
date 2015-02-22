package net.dervism.tsp;

import org.junit.Test;

/**
 * Created by dervism on 04/03/14.
 */
public class TSPEngineTest {

    @Test
    public void testRun() throws Exception {
        TSPEngine tspEngine = new TSPEngine(
                new TSPPopulation(),
                new TSPEvolution(),
                new TSPEncoder(),
                new TSPFitnessEvaluator(16)
        );
        tspEngine.run();
    }

}
