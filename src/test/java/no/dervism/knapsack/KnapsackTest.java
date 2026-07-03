package no.dervism.knapsack;

import no.dervism.genericalgorithms.BitChromosome;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by dervism on 19/02/14.
 */
class KnapsackTest {

    private KnapsackEngine newEngine() {
        return new KnapsackEngine(
                new KnapsackPopulation(),
                new KnapsackEvolution(),
                new KnapsackEncoder(),
                new KnapsackFitnessEvaluator()
        );
    }

    @Test
    void evolutionaryIncreasingGA() throws Exception {
        KnapsackEngine engine = newEngine();
        BitChromosome best = engine.execute(0, 1, 0.85, 0.15, 100, 0.6);
        assertNotNull(best);
    }

    @Test
    void evolutionaryGA() throws Exception {
        KnapsackEngine engine = newEngine();
        BitChromosome best = engine.executeEvolutionary(1, 0.85, 0.15, 100);
        assertNotNull(best);
    }

    @Test
    void randomizedGA() throws Exception {
        KnapsackEngine engine = newEngine();
        BitChromosome best = engine.executeRandomized(0, 1, 200);
        assertNotNull(best);
    }

    @Test
    void bestSolutionFitsInKnapsack() throws Exception {
        KnapsackEncoder encoder = new KnapsackEncoder();
        KnapsackFitnessEvaluator fitnessEvaluator = new KnapsackFitnessEvaluator();
        KnapsackEngine engine = new KnapsackEngine(
                new KnapsackPopulation(),
                new KnapsackEvolution(),
                encoder,
                fitnessEvaluator
        );

        BitChromosome best = engine.execute(0, 1, 0.85, 0.15, 100, 0.6);
        assertNotNull(best);

        int space = fitnessEvaluator.calcSpace(best);
        int value = fitnessEvaluator.calcValue(best);
        int items = encoder.itemsInside(best);

        IO.println("Value: " + value + ", Space: " + space + ", Items: " + items);

        assertTrue(space <= fitnessEvaluator.knapsackSpace);
    }
}
