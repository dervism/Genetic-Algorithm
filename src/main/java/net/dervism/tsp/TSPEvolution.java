package net.dervism.tsp;

import net.dervism.genericalgorithms.BitChromosome;
import net.dervism.genericalgorithms.Evolution;

import java.security.SecureRandom;

/**
 * Created by dervism on 14/02/14.
 */
public class TSPEvolution implements Evolution<BitChromosome> {

    private TSPEncoder tspEncoder;

    private SecureRandom random;

    private final int cityCount = 15;

    public TSPEvolution() {
        this.tspEncoder = new TSPEncoder();

        // no seed in this randomizer so we get different
        // evolution on every execution
        this.random = new SecureRandom();
    }

    /**
     * The simplest method for creating a new child. Two
     * random indexes are selected and switched places.
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
        long i = tspEncoder.getValue(parent, ix);
        long j = tspEncoder.getValue(parent, iy);

        BitChromosome bitChromosome = new BitChromosome(parent);

        // swap values
        tspEncoder.setValue(ix, j, bitChromosome);
        tspEncoder.setValue(iy, i, bitChromosome);

        return bitChromosome;
    }


    /**
     * This version of the mutation method is faster then
     * version that uses bitshifting.
     *
     * @param bitChromosome
     * @return
     */
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
        long[] array = tspEncoder.toArray(bitChromosome);

        // swap values in the array
        tspEncoder.swap(array, ix, iy);

        // encode the array into the bitChromosome again
        return tspEncoder.createChromosome(array);
    }

    /**
     * Crossover method that will create a new chromosome which is
     * a combination of two parent chromosomes. In this version
     * the mother chromosome is favoured. The crossover-point in
     * this method is selected after several executes of the
     * GA in order to find the most efficient parameters.
     *
     *
     * @param mother
     * @param father
     * @return
     */
    @Override
    public synchronized BitChromosome crossover(BitChromosome mother, BitChromosome father) {

        long[] left = tspEncoder.toArray(mother);
        long[] right = tspEncoder.toArray(father);
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

        return tspEncoder.createChromosome(child);
    }

}
