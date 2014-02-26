package net.dervism.genericalgorithms;

import java.util.Random;

/**
 * Created by dervism on 14/02/14.
 */

public interface Encoder<T> {

    public BitChromosome createChromosome(T... values);

    public BitChromosome createRandomChromosome(Random random);

}
