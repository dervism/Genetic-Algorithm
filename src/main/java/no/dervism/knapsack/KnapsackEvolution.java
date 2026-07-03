package no.dervism.knapsack;

import no.dervism.genericalgorithms.BitChromosome;
import no.dervism.genericalgorithms.Evolution;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.SecureRandomSeedGenerator;
import org.uncommons.maths.random.SeedException;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by dervism on 19/02/14.
 */
public class KnapsackEvolution implements Evolution<BitChromosome> {

    private Random random;

    private final int objects = KnapsackEncoder.OBJECTS;

    public KnapsackEvolution() {
        // no seed in this randomizer so we get different
        // evolution on every execution
        try {
            random = new MersenneTwisterRNG(new SecureRandomSeedGenerator());
        } catch (SeedException _) {
            random = new SecureRandom();
        }
    }

    /**
     * Flips a single random bit, meaning an object is either added to or
     * removed from the knapsack.
     *
     * @param parent
     * @return
     */
    @Override
    public BitChromosome mutate(BitChromosome parent) {
        int index = random.nextInt(objects);
        return parent.flipNthBit(index);
    }

    /**
     * Single-point crossover. The bits up to a random crossover point are
     * taken from the mother, the rest from the father.
     *
     * @param mother
     * @param father
     * @return
     */
    @Override
    public synchronized BitChromosome crossover(BitChromosome mother, BitChromosome father) {
        int point = 1 + random.nextInt(objects - 1);

        BitChromosome child = new BitChromosome();

        for (int i = 0; i < point; i++) {
            if (mother.getNthBit(i)) {
                child = child.setNthBit(i);
            }
        }

        for (int i = point; i < objects; i++) {
            if (father.getNthBit(i)) {
                child = child.setNthBit(i);
            }
        }

        return child;
    }
}
