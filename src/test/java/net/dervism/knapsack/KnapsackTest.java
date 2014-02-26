package net.dervism.knapsack;

import net.dervism.genericalgorithms.BitChromosome;
import org.junit.Test;

import java.util.List;

/**
 * Created by dervism on 19/02/14.
 */
public class KnapsackTest {

    @Test
    public void testCreateRandomPopulation() throws Exception {
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
