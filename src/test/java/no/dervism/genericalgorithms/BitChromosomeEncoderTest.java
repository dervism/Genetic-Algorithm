package no.dervism.genericalgorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by dervism on 30.12.13.
 */
class BitChromosomeEncoderTest {

    @Test
    void createChromosome() throws Exception {
        ChromosomeEncoder encoder = new ChromosomeEncoder();

        BitChromosome bitChromosome = encoder.createChromosome(32, 100, 82);

        assertEquals(32, encoder.getIndex(bitChromosome));
        assertEquals(100, encoder.getFirstVal(bitChromosome));
        assertEquals(82, encoder.getSecondVal(bitChromosome));

        System.out.println(bitChromosome);
    }

}
