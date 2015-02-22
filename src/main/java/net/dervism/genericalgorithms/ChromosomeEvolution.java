package net.dervism.genericalgorithms;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.SecureRandomSeedGenerator;
import org.uncommons.maths.random.SeedException;

import java.security.SecureRandom;
import java.util.Random;

/**
 * This class handles the evolution of bit-encoded chromosomes.
 *
 * Created by dervism on 14/02/14.
 *
 */
public abstract class ChromosomeEvolution implements Evolution<BitChromosome> {

    /**
     * Should use the MersenneTwisterRNG random generator as default.
     * Uses the SecureRandom as a fall-back solution.
     */
    protected Random random;

    public ChromosomeEvolution() {
        try {
            random = new MersenneTwisterRNG(new SecureRandomSeedGenerator());
        } catch (SeedException e) {
            random = new SecureRandom();
        }
    }

    @Override
    public BitChromosome mutate(BitChromosome bitChromosome) {
        return null;
    }

    @Override
    public BitChromosome crossover(BitChromosome mother, BitChromosome father) {
        return null;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
