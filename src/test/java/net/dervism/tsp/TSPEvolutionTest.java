package net.dervism.tsp;

import net.dervism.genericalgorithms.Chromosome;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by dervism on 14/02/14.
 */
public class TSPEvolutionTest {

    @Test
    public void testMutate() throws Exception {
        TSPEncoder tspEncoder = new TSPEncoder();
        TSPEvolution tspEvolution = new TSPEvolution(new TSP());
        Random random = new Random(4567);

        // create a randomized bit sequenze
        Chromosome chromosome = tspEncoder.createRandomChromosome(random);
        System.out.println(chromosome);

        // print the integers in the chromosome before the mutation
        long[] array = tspEncoder.toArray(chromosome);
        System.out.println(Arrays.toString(array));

        // change it
        Chromosome mutatedChromosome = tspEvolution.mutate(chromosome);

        // print out the new chromosome
        long[] mutated = tspEncoder.toArray(mutatedChromosome);
        System.out.println(Arrays.toString(mutated));

        Arrays.sort(mutated);
        for (int i = 1; i < mutated.length; i++) {
            // should have all values 0 to 15
            Assert.assertEquals(i, mutated[i]);

            // should not contain duplicates
            if (mutated[i - 1] > mutated[i]) {
                Assert.fail();
            }
        }
    }

    @Test
    public  void testBenchmarkMutation() {
        TSP tsp = new TSP();
        TSPEncoder tspEncoder = new TSPEncoder();
        TSPEvolution tspEvolution = new TSPEvolution(tsp);
        TSPPopulation tspPopulation = new TSPPopulation(tsp);
        Random random = new Random(4567);

        List<Chromosome> population = tspPopulation.createPopulation(2000);

        long start = System.currentTimeMillis();
        for (Chromosome chromosome : population) {
            Chromosome child = tspEvolution.mutate(chromosome);
        }
        start = System.currentTimeMillis() - start;

        System.out.println("Bitwise mutation: " + start + " millis.");

        start = System.currentTimeMillis();
        for (Chromosome chromosome : population) {
            Chromosome child = tspEvolution.arrayMutate(chromosome);
        }
        start = System.currentTimeMillis() - start;

        System.out.println("Array mutation: " + start + " millis.");
    }

    @Test
    public void testCrossover() throws Exception {
        TSPEncoder tspEncoder = new TSPEncoder();
        TSPEvolution tspEvolution = new TSPEvolution(new TSP());

        Chromosome mother = tspEncoder.createRandomChromosome(new Random());
        long[] m = tspEncoder.toArray(mother);
        System.out.println(Arrays.toString(m));

        Chromosome father = tspEncoder.createRandomChromosome(new Random());
        long[] f = tspEncoder.toArray(father);
        System.out.println(Arrays.toString(f));

        Chromosome child = tspEvolution.crossover(mother, father);
        long[] c = tspEncoder.toArray(child);
        System.out.println(Arrays.toString(c));

        Arrays.sort(c);
        for (int i = 1; i < c.length; i++) {
            // should have all values 0 to 15
            Assert.assertEquals(i, c[i]);

            // should not contain duplicates
            if (c[i - 1] > c[i]) {
                Assert.fail();
            }
        }

    }

    @Test
    public void testReproduce() throws Exception {
        Random random = new Random();
        int mutationRate = 100 - (int)(100 * 0.60);
        int c = 0;
        System.out.print("Mutated ");
        for (int i = 0; i < 30; i++) {
            int rate = random.nextInt(100);
            if (rate > mutationRate) {
                System.out.print(i + " ");
                c++;
            }
        }
        System.out.print(" " + c + "/" + 30);
    }
}
