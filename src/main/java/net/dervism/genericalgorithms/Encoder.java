package net.dervism.genericalgorithms;

import java.util.Random;

/**
 * Created by dervism on 14/02/14.
 */

public interface Encoder {

    public Chromosome createChromosome(long... values);

    public Chromosome createRandomChromosome(Random random);

}
