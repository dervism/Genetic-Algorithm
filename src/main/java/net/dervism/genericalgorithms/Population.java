package net.dervism.genericalgorithms;

import java.util.List;

/**
 * Created by dervism on 14/02/14.
 */
public interface Population {

    public List<Chromosome> createPopulation(int size);

}
