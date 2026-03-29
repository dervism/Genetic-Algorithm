package no.dervism.knapsack;

import no.dervism.genericalgorithms.BitChromosome;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Created by dervism on 19/02/14.
 */
class KnapsackTest {

    @Test
    void createRandomPopulation() throws Exception {
        Knapsack ks = new Knapsack();

        List<BitChromosome> population = ks.createRandomPopulation(10);

        BitChromosome bitChromosome = population.getFirst();
        IO.println(bitChromosome);

        int fitness = ks.calcFitness(bitChromosome);
        IO.println("Fitness: " + fitness);

        int space = ks.calcSpace(bitChromosome);
        IO.println("Space: " + space);

        int items = ks.itemsInside(bitChromosome);
        IO.println("Items: " + items);
    }

}
