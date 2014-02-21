package net.dervism.genericalgorithms;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by dervism on 30.12.13.
 */
public class ChromosomeEncoderTest {

    @Test
    public void testCreateChromosome() throws Exception {
        ChromosomeEncoder encoder = new ChromosomeEncoder();

        Chromosome chromosome = encoder.createChromosome(32, 100, 82);
        
        assertTrue(encoder.getIndex(chromosome) == 32);
        assertTrue(encoder.getFirstVal(chromosome) == 100);
        assertTrue(encoder.getSecondVal(chromosome) == 82);

        System.out.println(chromosome);
    }

}
