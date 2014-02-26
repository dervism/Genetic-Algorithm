package net.dervism.tsp;

import net.dervism.genericalgorithms.BitChromosome;
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
        BitChromosome bitChromosome = tspEncoder.createRandomChromosome(random);
        System.out.println(bitChromosome);

        // print the integers in the bitChromosome before the mutation
        long[] array = tspEncoder.toArray(bitChromosome);
        System.out.println(Arrays.toString(array));

        // change it
        BitChromosome mutatedBitChromosome = tspEvolution.mutate(bitChromosome);

        // print out the new bitChromosome
        long[] mutated = tspEncoder.toArray(mutatedBitChromosome);
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

        List<BitChromosome> population = tspPopulation.createPopulation(2000);

        long start = System.currentTimeMillis();
        for (BitChromosome bitChromosome : population) {
            BitChromosome child = tspEvolution.mutate(bitChromosome);
        }
        start = System.currentTimeMillis() - start;

        System.out.println("Bitwise mutation: " + start + " millis.");

        start = System.currentTimeMillis();
        for (BitChromosome bitChromosome : population) {
            BitChromosome child = tspEvolution.arrayMutate(bitChromosome);
        }
        start = System.currentTimeMillis() - start;

        System.out.println("Array mutation: " + start + " millis.");
    }

    @Test
    public void testCrossover() throws Exception {
        TSPEncoder tspEncoder = new TSPEncoder();
        TSPEvolution tspEvolution = new TSPEvolution(new TSP());

        BitChromosome mother = tspEncoder.createRandomChromosome(new Random());
        long[] m = tspEncoder.toArray(mother);
        System.out.println(Arrays.toString(m));

        BitChromosome father = tspEncoder.createRandomChromosome(new Random());
        long[] f = tspEncoder.toArray(father);
        System.out.println(Arrays.toString(f));

        BitChromosome child = tspEvolution.crossover(mother, father);
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
