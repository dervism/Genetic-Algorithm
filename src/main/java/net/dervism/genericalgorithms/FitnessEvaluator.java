package net.dervism.genericalgorithms;

import java.util.List;

/**
 * Created by dervism on 08/03/14.
 */
public interface FitnessEvaluator<T extends Chromosome> {

    public int evalute(T chromosome);

    public int evalute(Population<T> population);

    public void sort(Population<T> population);

    public T getBest(Population<T> population);

}
