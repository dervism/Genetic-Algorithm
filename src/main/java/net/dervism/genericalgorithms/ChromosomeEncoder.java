package net.dervism.genericalgorithms;

import java.util.Random;

/**
 * Created by dervism on 28.12.13.
 */
public class ChromosomeEncoder implements Encoder {

    // the start index
    private long first_val_shift = 7;
    private long second_val_shift = 14;

    // the number of bits to use for encoding
    private long index_mask = 0x7FL; // 7 bits
    private long first_val_mask = 0x7FL; // 7 bits
    private long second_val_mask = 0x7FL; // 7 bits

    @Override
    public Chromosome createChromosome(long... values) {
        return createChromosome(values[0], values[1], values[2]);
    }

    public Chromosome createChromosome(long index, long first_val, long second_val) {

        long genes = 0x0L;

        genes = 0 | index | (first_val << first_val_shift) | (second_val << second_val_shift);

        Chromosome chromosome = new Chromosome(genes);

        return chromosome;
    }

    @Override
    public Chromosome createRandomChromosome(Random random) {
        return null;
    }

    public int getIndex(Chromosome chromosome) {
        return (int)(chromosome.genes & index_mask);
    }

    public int getFirstVal(Chromosome chromosome) {
        return (int)((chromosome.genes >> first_val_shift) & first_val_mask);
    }

    public int getSecondVal(Chromosome chromosome) {
        return (int)((chromosome.genes >> second_val_shift) & second_val_mask);
    }

}
