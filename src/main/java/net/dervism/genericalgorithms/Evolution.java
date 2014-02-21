package net.dervism.genericalgorithms;

/**
 * Created by dervism on 14/02/14.
 */
public interface Evolution {

    public Chromosome mutate(Chromosome chromosome);

    public Chromosome crossover(Chromosome left, Chromosome right);

}
