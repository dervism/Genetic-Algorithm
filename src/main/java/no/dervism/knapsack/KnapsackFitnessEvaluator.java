package no.dervism.knapsack;

import no.dervism.genericalgorithms.BitChromosome;
import no.dervism.genericalgorithms.FitnessEvaluator;
import no.dervism.genericalgorithms.Population;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Evaluates a knapsack solution. This is a maximization problem, so a higher
 * fitness score is better. A feasible solution is scored as the total value of
 * the packed objects, while a solution that exceeds the capacity is penalized
 * with a negative score proportional to the overflow. The
 * {@link no.dervism.genericalgorithms.GeneticAlgorithmEngine} is told to
 * maximize by overriding {@link #isBetter(int, int)}.
 *
 * Created by dervism on 19/02/14.
 */
public class KnapsackFitnessEvaluator implements FitnessEvaluator<BitChromosome> {

    public int[] objectValue;
    public int[] objectSpace;
    public int knapsackSpace;

    private Random random;

    public KnapsackFitnessEvaluator() {
        this(KnapsackEncoder.OBJECTS, 3000);
    }

    public KnapsackFitnessEvaluator(int objects, int knapsackSpace) {
        this.knapsackSpace = knapsackSpace;
        this.objectValue = new int[objects];
        this.objectSpace = new int[objects];
        this.random = new Random(1234);
        createObjectsAndValues();
    }

    private void createObjectsAndValues() {
        for (int i = 0; i < objectValue.length; i++) {
            objectValue[i] = random.nextInt(200);
            objectSpace[i] = random.nextInt(50);
        }
    }

    public int calcValue(BitChromosome bitChromosome) {
        int value = 0;
        for (int i = 0; i < objectValue.length; i++) {
            if (bitChromosome.getNthBit(i)) {
                value += objectValue[i];
            }
        }
        return value;
    }

    public int calcSpace(BitChromosome bitChromosome) {
        int totalSpace = 0;
        for (int i = 0; i < objectSpace.length; i++) {
            if (bitChromosome.getNthBit(i)) {
                totalSpace += objectSpace[i];
            }
        }
        return totalSpace;
    }

    @Override
    public int evaluate(BitChromosome chromosome) {
        int space = calcSpace(chromosome);
        int value = calcValue(chromosome);

        // penalize solutions that do not fit in the knapsack with a negative
        // score proportional to the overflow (worse than any feasible solution)
        if (space > knapsackSpace) {
            return knapsackSpace - space;
        }

        // feasible solution: the more value, the higher (better) the score
        return value;
    }

    /**
     * The knapsack problem maximizes fitness, so a candidate is better when its
     * score is strictly higher than the current best.
     */
    @Override
    public boolean isBetter(int candidateFitness, int currentBestFitness) {
        return candidateFitness > currentBestFitness;
    }

    @Override
    public int evaluate(Population<BitChromosome> population) {
        sort(population);
        return evaluate(getBest(population));
    }

    @Override
    public BitChromosome getBest(Population<BitChromosome> population) {
        return population.get(0);
    }

    @Override
    public void sort(Population<BitChromosome> population) {
        Collections.sort(population.list(), new ChromosomeComparator());
    }

    public class ChromosomeComparator implements Comparator<BitChromosome> {
        @Override
        public int compare(BitChromosome o1, BitChromosome o2) {
            Integer fitness1 = evaluate(o1);
            Integer fitness2 = evaluate(o2);
            // sort descending so the highest (best) fitness ends up first
            return fitness2.compareTo(fitness1);
        }
    }

    /**
     * Prints out stats about the population.
     *
     * @return
     */
    public String stat(List<BitChromosome> population) {
        String ret = "";
        int c = 1;
        for (BitChromosome chromosome : population) {
            ret += c++ + " " + evaluate(chromosome) + ", ";
        }
        return ret;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
