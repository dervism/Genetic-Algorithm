package net.dervism.genericalgorithms;

import java.util.Random;

/**
 * This class is only ment to be used a basic template.
 *
 * Created by dervism on 28.12.13.
 */
public class ChromosomeEncoder implements Encoder<Long> {

    // the start index
    private long first_val_shift = 7;
    private long second_val_shift = 14;

    // the number of bits to use for encoding
    private long index_mask = 0x7FL; // 7 bits
    private long first_val_mask = 0x7FL; // 7 bits
    private long second_val_mask = 0x7FL; // 7 bits

    @Override
    public BitChromosome createChromosome(Long... values) {
        return createChromosome(values[0], values[1], values[2]);
    }

    public BitChromosome createChromosome(long index, long first_val, long second_val) {

        long genes = 0x0L;

        genes = 0 | index | (first_val << first_val_shift) | (second_val << second_val_shift);

        BitChromosome bitChromosome = new BitChromosome(genes);

        return bitChromosome;
    }

    @Override
    public BitChromosome createRandomChromosome(Random random) {
        return null;
    }

    public int getIndex(BitChromosome bitChromosome) {
        return (int)(bitChromosome.genes & index_mask);
    }

    public int getFirstVal(BitChromosome bitChromosome) {
        return (int)((bitChromosome.genes >> first_val_shift) & first_val_mask);
    }

    public int getSecondVal(BitChromosome bitChromosome) {
        return (int)((bitChromosome.genes >> second_val_shift) & second_val_mask);
    }

}
