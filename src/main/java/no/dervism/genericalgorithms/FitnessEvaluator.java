package no.dervism.genericalgorithms;

/**
 * Created by dervism on 08/03/14.
 */
public interface FitnessEvaluator<T extends Chromosome> {

    public int evaluate(T chromosome);

    public int evaluate(Population<T> population);

    public void sort(Population<T> population);

    public T getBest(Population<T> population);

    /**
     * Determines whether a candidate fitness score is better than the current
     * best fitness score. The default implementation minimizes fitness (lower is
     * better), which suits problems such as the TSP. Evaluators for problems that
     * need to maximize fitness (such as the knapsack problem) should override
     * this method.
     *
     * @param candidateFitness the fitness score of the candidate solution
     * @param currentBestFitness the fitness score of the current best solution
     * @return true if the candidate is strictly better than the current best
     */
    default boolean isBetter(int candidateFitness, int currentBestFitness) {
        return candidateFitness < currentBestFitness;
    }

}
