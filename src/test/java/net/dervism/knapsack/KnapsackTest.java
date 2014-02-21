package net.dervism.knapsack;

import net.dervism.genericalgorithms.Chromosome;
import org.junit.Test;

import java.util.List;

/**
 * Created by dervism on 19/02/14.
 */
public class KnapsackTest {

    @Test
    public void testCreateRandomPopulation() throws Exception {
        Knapsack ks = new Knapsack();

        List<Chromosome> population = ks.createRandomPopulation(10);

        Chromosome chromosome = population.get(0);
        System.out.println(chromosome);

        int fitness = ks.calcFitness(chromosome);
        System.out.println("Fitness: " + fitness);

        int space = ks.calcSpace(chromosome);
        System.out.println("Space: " + space);

        int items = ks.itemsInside(chromosome);
        System.out.println("Items: " + items);
    }

}
