package net.dervism.genericalgorithms;

/**
 * Created by dervism on 14/02/14.
 */
public interface Evolution {

    public BitChromosome mutate(BitChromosome bitChromosome);

    public BitChromosome crossover(BitChromosome left, BitChromosome right);

}
