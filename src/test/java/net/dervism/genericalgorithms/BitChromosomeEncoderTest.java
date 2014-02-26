package net.dervism.genericalgorithms;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by dervism on 30.12.13.
 */
public class BitChromosomeEncoderTest {

    @Test
    public void testCreateChromosome() throws Exception {
        ChromosomeEncoder encoder = new ChromosomeEncoder();

        BitChromosome bitChromosome = encoder.createChromosome(32, 100, 82);
        
        assertTrue(encoder.getIndex(bitChromosome) == 32);
        assertTrue(encoder.getFirstVal(bitChromosome) == 100);
        assertTrue(encoder.getSecondVal(bitChromosome) == 82);

        System.out.println(bitChromosome);
    }

}
