package net.dervism.genericalgorithms;

/**
 * This class handles the evolution of bit-encoded chromosomes.
 *
 * Created by dervism on 14/02/14.
 *
 */
public abstract class ChromosomeEvolution implements Evolution {

    @Override
    public BitChromosome mutate(BitChromosome bitChromosome) {
        return null;
    }

    @Override
    public BitChromosome crossover(BitChromosome mother, BitChromosome father) {
        return null;
    }


}
