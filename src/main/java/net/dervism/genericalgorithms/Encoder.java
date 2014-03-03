package net.dervism.genericalgorithms;

import java.util.Random;

/**
 * Interface defining methods needed to encode a dataset / set of parameters into
 * a Chromosome.
 *
 * Created by dervism on 14/02/14.
 */

public interface Encoder<T, C extends Chromosome> {

    public C createChromosome(T... values);

    public C createRandomChromosome(Random random);

}
