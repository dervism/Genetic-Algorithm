package net.dervism.genericalgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dervism on 14/02/14.
 */
public abstract class ChromosomePopulation implements Population {

    public abstract List<BitChromosome> createPopulation(int size);

    public List<BitChromosome> createEmptyPopulation(int size) {

        List<BitChromosome> pop = new ArrayList<BitChromosome>(size);

        for (int i = 0; i < size; i++) {
            pop.add(new BitChromosome(0L));
        }

        return pop;
    }

}
