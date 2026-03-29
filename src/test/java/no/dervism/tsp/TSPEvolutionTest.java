package no.dervism.tsp;

import no.dervism.genericalgorithms.BitChromosome;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by dervism on 14/02/14.
 */
class TSPEvolutionTest {

    @Test
    void mutate() throws Exception {
        TSPEncoder tspEncoder = new TSPEncoder();
        TSPEvolution tspEvolution = new TSPEvolution();
        Random random = new Random(4567);

        // create a randomized bit sequenze
        BitChromosome bitChromosome = tspEncoder.createRandomChromosome(random);
        IO.println(bitChromosome);

        // print the integers in the bitChromosome before the mutation
        long[] array = tspEncoder.toArray(bitChromosome);
        IO.println(Arrays.toString(array));

        // change it
        BitChromosome mutatedBitChromosome = tspEvolution.mutate(bitChromosome);

        // print out the new bitChromosome
        long[] mutated = tspEncoder.toArray(mutatedBitChromosome);
        IO.println(Arrays.toString(mutated));

        Arrays.sort(mutated);
        for (int i = 1; i < mutated.length; i++) {
            // should have all values 0 to 15
            assertEquals(i, mutated[i]);

            // should not contain duplicates
            if (mutated[i - 1] > mutated[i]) {
                fail();
            }
        }
    }

    @Test
    void benchmarkMutation() {
        TSP tsp = new TSP();
        TSPEvolution tspEvolution = new TSPEvolution();
        TSPPopulation tspPopulation = new TSPPopulation();

        List<BitChromosome> population = tspPopulation.createPopulation(2000);

        long start = System.currentTimeMillis();
        for (BitChromosome bitChromosome : population) {
            BitChromosome child = tspEvolution.mutate(bitChromosome);
        }
        start = System.currentTimeMillis() - start;

        IO.println("Bitwise mutation: " + start + " millis.");

        start = System.currentTimeMillis();
        for (BitChromosome bitChromosome : population) {
            BitChromosome child = tspEvolution.arrayMutate(bitChromosome);
        }
        start = System.currentTimeMillis() - start;

        IO.println("Array mutation: " + start + " millis.");
    }

    @Test
    void crossover() throws Exception {
        TSPEncoder tspEncoder = new TSPEncoder();
        TSPEvolution tspEvolution = new TSPEvolution();

        BitChromosome mother = tspEncoder.createRandomChromosome(new Random());
        long[] m = tspEncoder.toArray(mother);
        IO.println(Arrays.toString(m));

        BitChromosome father = tspEncoder.createRandomChromosome(new Random());
        long[] f = tspEncoder.toArray(father);
        IO.println(Arrays.toString(f));

        BitChromosome child = tspEvolution.crossover(mother, father);
        long[] c = tspEncoder.toArray(child);
        IO.println(Arrays.toString(c));

        Arrays.sort(c);
        for (int i = 1; i < c.length; i++) {
            // should have all values 0 to 15
            assertEquals(i, c[i]);

            // should not contain duplicates
            if (c[i - 1] > c[i]) {
                fail();
            }
        }

    }

    @Test
    void reproduce() throws Exception {
        Random random = new Random();
        int mutationRate = 100 - (int)(100 * 0.60);
        int c = 0;
        IO.print("Mutated ");
        for (int i = 0; i < 30; i++) {
            int rate = random.nextInt(100);
            if (rate > mutationRate) {
                IO.print(i + " ");
                c++;
            }
        }
        IO.print(" " + c + "/" + 30);
    }
}
