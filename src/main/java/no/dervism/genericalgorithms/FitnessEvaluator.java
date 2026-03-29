package no.dervism.genericalgorithms;

/**
 * Created by dervism on 08/03/14.
 */
public interface FitnessEvaluator<T extends Chromosome> {

    public int evaluate(T chromosome);

    public int evaluate(Population<T> population);

    public void sort(Population<T> population);

    public T getBest(Population<T> population);

}
