package net.dervism.genericalgorithms;

/**
 * This class handles the evolution of bit-encoded chromosomes.
 *
 * Created by dervism on 14/02/14.
 *
 */
public abstract class ChromosomeEvolution implements Evolution {

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        return null;
    }

    @Override
    public Chromosome crossover(Chromosome mother, Chromosome father) {
        return null;
    }


}
