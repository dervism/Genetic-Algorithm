package net.dervism.genericalgorithms;

/**
 * Interface that defines methods needed to implement Evolution
 * functions in a Genetic Algorithm.
 *
 * Created by dervism on 14/02/14.
 */
public interface Evolution<T extends Chromosome> {

    public BitChromosome mutate(T chromosome);

    public BitChromosome crossover(T left, T right);

}
