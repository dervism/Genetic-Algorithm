package net.dervism.genericalgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dervism on 14/02/14.
 */
public abstract class ChromosomePopulation implements Population {

    public abstract List<Chromosome> createPopulation(int size);

    public List<Chromosome> createEmptyPopulation(int size) {

        List<Chromosome> pop = new ArrayList<Chromosome>(size);

        for (int i = 0; i < size; i++) {
            pop.add(new Chromosome(0L));
        }

        return pop;
    }

}
