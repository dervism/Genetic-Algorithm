package net.dervism.tsp;

import net.dervism.genericalgorithms.BitChromosome;
import net.dervism.genericalgorithms.FitnessEvaluator;
import net.dervism.genericalgorithms.Population;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.SecureRandomSeedGenerator;
import org.uncommons.maths.random.SeedException;

import java.security.SecureRandom;
import java.util.*;

/**
 * Created by dervism on 14/02/14.
 */
public class TSPPopulation implements Population<BitChromosome> {

    private List<BitChromosome> population;

    private TSPEncoder tspEncoder;

    private Random random;

    public TSPPopulation() {
        this.tspEncoder = new TSPEncoder();
        try {
            random = new MersenneTwisterRNG(new SecureRandomSeedGenerator());
        } catch (SeedException e) {
            random = new SecureRandom();
        }
    }

    public void setRandom(SecureRandom random) {
        this.random = random;
    }

    @Override
    public List<BitChromosome> createPopulation(int size) {
        population = new ArrayList<>(size*4);

        for (int i = 0; i < size; i++) {
            BitChromosome bitChromosome = tspEncoder.createRandomChromosome(random);
            population.add(bitChromosome);
        }

        return population;
    }

    /**
     * Keep the best solutions, and remove the
     * bad ones.
     */
    public void selectBest(double selectionRate) {
        selectBest((int) (population.size() * selectionRate));
    }

    public synchronized void selectBest(int reduce) {
        int newSize = population.size() - reduce;

        do {
            population.remove(population.size()-1);
        } while (population.size() > newSize);
    }


    /**
     * Divide population in two and select a parent
     * from the best group of chromosomes.
     *
     * @return
     */
    public synchronized BitChromosome[] selectParents(double selectionRate) {
        BitChromosome[] parents = new BitChromosome[2];

        int selection = (int) (population.size() * selectionRate);

        int mother = random.nextInt(selection);

        if (mother > population.size()) {
            throw new IllegalArgumentException("Ops");
        }

        parents[0] = population.get(mother);

        int father = random.nextInt(selection);

        if (mother == father) {
            do {
                father = random.nextInt(selection);
            } while (mother == father);
        }
        parents[1] = population.get(father);

        return parents;
    }

    public BitChromosome[] selectParents() {
        return selectParents(0.8);
    }

    public void add(BitChromosome bitChromosome) {
        population.add(bitChromosome);
    }

    @Override
    public BitChromosome get(int index) {
        return population.get(index);
    }

    @Override
    public List<BitChromosome> list() {
        return population;
    }

    @Override
    public int size() {
        return population.size();
    }
}
