package no.dervism.tsp;

import no.dervism.genericalgorithms.BitChromosome;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by dervism on 17/02/14.
 */
class TSPTest {

    private TSPEngine newEngine() {
        return new TSPEngine(
                new TSPPopulation(),
                new TSPEvolution(),
                new TSPEncoder(),
                new TSPFitnessEvaluator()
        );
    }

    @Test
    void evolutionaryIncreasingGA() throws Exception {
        TSPEngine engine = newEngine();
        BitChromosome best = engine.execute(0, 1, 0.85, 0.15, 100, 0.6);
        assertNotNull(best);
    }

    @Test
    void evolutionaryGA() throws Exception {
        TSPEngine engine = newEngine();
        BitChromosome best = engine.executeEvolutionary(1, 0.85, 0.15, 100);
        assertNotNull(best);
    }

    @Test
    void randomizedGA() throws Exception {
        TSPEngine engine = newEngine();
        BitChromosome best = engine.executeRandomized(0, 1, 200);
        assertNotNull(best);
    }

    @Test
    void bruteforceTsp() throws Exception {
        TSPEngine engine = newEngine();
        // Brute-forcing all permutations takes ~2 days, so it is not run here.
        // engine.executeBruteForceTSP();
    }

}
