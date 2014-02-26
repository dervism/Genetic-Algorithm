package net.dervism.tsp;

import net.dervism.genericalgorithms.BitChromosome;
import net.dervism.genericalgorithms.Evolution;

import java.security.SecureRandom;

/**
 * Created by dervism on 14/02/14.
 */
public class TSPEvolution implements Evolution {

    private TSP tsp;

    private SecureRandom random;

    private final int cityCount = 15;

    public TSPEvolution(TSP tsp) {
        this.tsp = tsp;

        this.random = new SecureRandom();
    }

    /**
     * The simplest method for creating a new child.
     *
     * @param parent
     * @return
     */
    @Override
    public BitChromosome mutate(BitChromosome parent) {
        // create random indexes
        int ix = 1 + random.nextInt(cityCount);
        int iy = 1 + random.nextInt(cityCount);

        // make sure they are unique
        if (ix == iy) {
            do {
                iy = 1 + random.nextInt(cityCount);
            } while (ix == iy);
        }

        // get the values from each index
        long i = tsp.tspEncoder.getValue(parent, ix);
        long j = tsp.tspEncoder.getValue(parent, iy);

        BitChromosome bitChromosome = new BitChromosome(parent);

        // swap values
        tsp.tspEncoder.setValue(ix, j, bitChromosome);
        tsp.tspEncoder.setValue(iy, i, bitChromosome);

        return bitChromosome;
    }


    public BitChromosome arrayMutate(BitChromosome bitChromosome) {
        int ix = 1 + random.nextInt(cityCount);
        int iy = 1 + random.nextInt(cityCount);

        // make sure they are unique
        if (ix == iy) {
            do {
                iy = 1 + random.nextInt(cityCount);
            } while (ix == iy);
        }

        // convert bitChromosome to array
        long[] array = tsp.tspEncoder.toArray(bitChromosome);

        // swap values in the array
        tsp.tspEncoder.swap(array, ix, iy);

        // encode the array into the bitChromosome again
        return tsp.tspEncoder.createChromosome(array);
    }

    @Override
    public synchronized BitChromosome crossover(BitChromosome mother, BitChromosome father) {

        long[] left = tsp.tspEncoder.toArray(mother);
        long[] right = tsp.tspEncoder.toArray(father);
        long[] child = new long[left.length];

        // counter for values from mother
        int c = 0;

        // counter for values from father
        int k = 0;

        // set the start city
        child[0] = left[0];

        //int leftIndex = ((left.length-1)/2) + 1;
        int leftIndex = 9;

        // first half from mother
        for (int i = 1; i < leftIndex; i++) {
            child[++c] = left[i];
        }

        // update the start index of next half
        k = c;

        // the rest from father
        for (int i = 1; i < right.length; i++) {
            boolean copied = false;

            // insert only the values not already copied from mother
            for (int j = 1; j < c; j++) {
                if (child[j] == right[i]) {
                    copied = true;
                    break;
                }
            }

            if (!copied) {
                child[k++] = right[i];
            }
        }

        return tsp.tspEncoder.createChromosome(child);
    }

}
