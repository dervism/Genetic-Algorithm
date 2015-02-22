package net.dervism.genericalgorithms;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.SecureRandomSeedGenerator;
import org.uncommons.maths.random.SeedException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An abstract class defining a population.
 *
 * Created by dervism on 14/02/14.
 */
public abstract class ChromosomePopulation<T extends Chromosome> implements Population<T> {

    protected List<T> population;

    public ChromosomePopulation() {
        this.population = new ArrayList<>();
    }

    public abstract List<T> createPopulation(int size);

    @Override
    public synchronized void selectBest(int reduce) {
        int newSize = population.size() - reduce;

        do {
            population.remove(population.size()-1);
        } while (population.size() > newSize);
    }

    @Override
    public int size() {
        return population.size();
    }

    public void add(T chromosome) {
        population.add(chromosome);
    }
}
