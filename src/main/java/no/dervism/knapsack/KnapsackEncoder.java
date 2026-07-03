package no.dervism.knapsack;

import no.dervism.genericalgorithms.BitChromosome;
import no.dervism.genericalgorithms.Encoder;

import java.util.Random;

/**
 * Encodes a knapsack solution into a {@link BitChromosome}. Each of the 64 bits
 * represents one object: if the n'th bit is set, the n'th object is placed in
 * the knapsack.
 *
 * Created by dervism on 19/02/14.
 */
public class KnapsackEncoder implements Encoder<Integer, BitChromosome> {

    // the number of objects that can be encoded in a 64-bit chromosome
    public static final int OBJECTS = 64;

    /**
     * Creates a chromosome where the bits at the given indexes are set,
     * meaning those objects are included in the knapsack.
     *
     * @param indexes the indexes of the objects to include
     * @return the encoded chromosome
     */
    @Override
    public BitChromosome createChromosome(Integer... indexes) {
        BitChromosome bitChromosome = new BitChromosome();
        for (int index : indexes) {
            bitChromosome = bitChromosome.setNthBit(index);
        }
        return bitChromosome;
    }

    @Override
    public BitChromosome createRandomChromosome(Random random) {
        BitChromosome bitChromosome = new BitChromosome();
        for (int i = 0; i < OBJECTS; i++) {
            if (random.nextBoolean()) {
                bitChromosome = bitChromosome.setNthBit(i);
            }
        }
        return bitChromosome;
    }

    /**
     * Returns true if the object at the given index is included in the knapsack.
     *
     * @param bitChromosome the chromosome to decode
     * @param index the object index
     * @return whether the object is present
     */
    public boolean contains(BitChromosome bitChromosome, int index) {
        return bitChromosome.getNthBit(index);
    }

    /**
     * Counts the number of objects present in the knapsack.
     *
     * @param bitChromosome the chromosome to decode
     * @return the number of objects
     */
    public int itemsInside(BitChromosome bitChromosome) {
        int items = 0;
        for (int i = 0; i < OBJECTS; i++) {
            if (bitChromosome.getNthBit(i)) {
                items++;
            }
        }
        return items;
    }
}
