package no.dervism.tsp;

import org.junit.jupiter.api.Test;

/**
 * Created by dervism on 04/03/14.
 */
class TSPEngineTest {

    @Test
    void run() throws Exception {
        TSPEngine tspEngine = new TSPEngine(
                new TSPPopulation(),
                new TSPEvolution(),
                new TSPEncoder(),
                new TSPFitnessEvaluator(16)
        );
        tspEngine.run();
    }

}
