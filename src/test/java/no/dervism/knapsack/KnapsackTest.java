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

        BitChromosome bitChromosome = population.get(0);
        System.out.println(bitChromosome);

        int fitness = ks.calcFitness(bitChromosome);
        System.out.println("Fitness: " + fitness);

        int space = ks.calcSpace(bitChromosome);
        System.out.println("Space: " + space);

        int items = ks.itemsInside(bitChromosome);
        System.out.println("Items: " + items);
    }

}
