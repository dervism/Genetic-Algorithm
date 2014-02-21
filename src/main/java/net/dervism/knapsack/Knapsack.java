package net.dervism.knapsack;

import net.dervism.genericalgorithms.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dervism on 19/02/14.
 */
public class Knapsack {

    public int[] objectValue;
    public int[] objectSpace;

    public int[] knapsack;
    public int knapsackSpace;

    public Random random;

    public Knapsack() {
        random = new Random(1234);
        createObjectsAndValues(64);
    }

    private void createObjectsAndValues(int objects) {
        objectValue = new int[objects];
        objectSpace = new int[objects];
        knapsack = new int[32];
        knapsackSpace = 3000;

        for (int i = 0; i < objectValue.length; i++) {
            objectValue[i] = random.nextInt(200);
            objectSpace[i] = random.nextInt(50);
        }
    }

    public List<Chromosome> createRandomPopulation(int size) {
        List<Chromosome> population = new ArrayList<>(size * 2);
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            Chromosome chromosome = new Chromosome();
            for (int j = 0; j < 64; j++) {
                if (rand.nextBoolean())
                    chromosome = chromosome.setNthBit(j);
            }
            population.add(chromosome);
        }

        return population;
    }

    public int calcFitness(Chromosome chromosome) {
        int fitness = 0;

        for (int i = 0; i < 64; i++) {
            if (chromosome.getNthBit(i)) {
                fitness += objectValue[i];
            }
        }

        return fitness;
    }

    public int calcSpace(Chromosome chromosome) {
        int totalSpace = 0;

        for (int i = 0; i < 64; i++) {
            if (chromosome.getNthBit(i)) {
                totalSpace += objectSpace[i];
            }
        }

        return totalSpace;
    }

    public int itemsInside(Chromosome chromosome) {
        int items = 0;

        for (int i = 0; i < 64; i++) {
            if (chromosome.getNthBit(i)) {
                items++;
            }
        }

        return items;
    }

    public void run() {

    }

}
