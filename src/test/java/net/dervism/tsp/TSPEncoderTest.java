package net.dervism.tsp;

import net.dervism.genericalgorithms.BitChromosome;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by dervism on 13/02/14.
 */
public class TSPEncoderTest {

    @Test
    public void testCreateChromosome() throws Exception {
        TSPEncoder tspEncoder = new TSPEncoder();

        long[] cities = new long[16];

        for (int i = 0; i < cities.length; i++) {
            cities[i] = i;
        }

        BitChromosome bitChromosome = tspEncoder.createChromosome(cities);
        System.out.println(bitChromosome);

        Assert.assertEquals(0, tspEncoder.getIndex(bitChromosome));

        for (int i = 0; i < cities.length; i++) {
            Assert.assertEquals(i, tspEncoder.getValue(bitChromosome, i));
        }
    }

    @Test
    public void testCreateRandomChromosome() throws Exception {
        TSPEncoder tspEncoder = new TSPEncoder();
        Random random = new Random(123);
        BitChromosome bitChromosome = tspEncoder.createRandomChromosome(random);
        System.out.println(bitChromosome);
        long[] array = tspEncoder.toArray(bitChromosome);
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));

        // should only contain unique values
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                Assert.fail();
            }
        }
    }

    @Test
    public void testSetValue() throws Exception {
        TSPEncoder tspEncoder = new TSPEncoder();

        // create an empty long
        long l1 = 0b0000000000000000000000000000000000000000000000000000000000000000L;
        BitChromosome c = new BitChromosome(l1);

        // set every group of 4 bit to value 15 (16 groups * 4 bits in every group = 64 bits)
        for (int i = 0; i <= 16; i++) {
            tspEncoder.setValue(i, 0xF, c);
        }

        // every group of 4 bits should be 15 (0b1111)
        for (int i = 0; i <= 16; i++) {
            Assert.assertEquals(0xF, tspEncoder.getValue(c, i));
        }

        // create a new random bit sequence
        BitChromosome bitChromosome = tspEncoder.createRandomChromosome(new Random(4567));
        System.out.println(bitChromosome);

        // index 11 should have value 8
        Assert.assertEquals(8, tspEncoder.getValue(bitChromosome, 11));

        // set index 11 to be value 15
        tspEncoder.setValue(11, 15, bitChromosome);
        System.out.println(bitChromosome);

        // index 11 should be updated to value 15
        Assert.assertEquals(15, tspEncoder.getValue(bitChromosome, 11));
    }

}
