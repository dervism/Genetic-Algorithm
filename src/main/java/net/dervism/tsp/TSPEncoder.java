package net.dervism.tsp;

import net.dervism.genericalgorithms.BitChromosome;
import net.dervism.genericalgorithms.Encoder;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

/**
 * Created by dervism on 13/02/14.
 */
public class TSPEncoder implements Encoder<Long> {

    // the start index
    private long shift = 4;

    // the number of bits to use for encoding
    private long mask = 0xFL; // 4 bits, equals 15 as max value pr group of 4 bits

    private int start = 0;

    @Override
    public BitChromosome createChromosome(Long... cities) {
        return createChromosome(ArrayUtils.toPrimitive(cities));
    }

    public BitChromosome createChromosome(long... cities) {

        long genes = 0x0L;

        // starting point, the right most city index
        genes = 0 | cities[0];

        for (int i = 1; i < cities.length; i++) {
            long city = cities[i];
            genes |= (city << (shift * i));
        }

        BitChromosome bitChromosome = new BitChromosome(genes);

        return bitChromosome;
    }

    @Override
    public BitChromosome createRandomChromosome(Random random) {

        long genes = 0x0L;

        long[] cities = new long[16];

        // initial values, 0 - 15
        for (int i = 0; i < cities.length; i++) {
            cities[i] = (long)i;
        }

        // randomize
        for (int i = 0; i < cities.length; i++) {
            swap(cities, i, random.nextInt(16));
        }

        // randomize more
        for (int i = 0; i < cities.length; i++) {
            swap(cities, i, random.nextInt(16));
        }

        // set start city
        swap(cities, 0, indexOf(cities, start));

        // starting point, the right most city index
        genes = 0 | cities[0];

        // finally encode the array
        for (int i = 1; i < cities.length; i++) {
            long city = cities[i];
            genes |= (city << (shift * i));
        }

        BitChromosome bitChromosome = new BitChromosome(genes);

        return bitChromosome;
    }

    public long nextLong(Random r, int n) {
        long x = 1;
        long y = n;
        return x + ((long)(r.nextDouble()*(y-x)));
    }

    public void swap(long[] a, int i, int j) {
        long tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public int indexOf(long[] values, int val) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == val) return i;
        }
        return -1;
    }

    /**
     * Decodes the right most value.
     * Calling this method is the same as calling getValue(bitChromosome, 0).
     *
     * @param bitChromosome
     * @return
     */
    public int getIndex(BitChromosome bitChromosome) {
        return (int)(bitChromosome.genes & mask);
    }

    /**
     * Decodes a  4-bit value at the given index.
     *
     * @param bitChromosome
     * @param index
     * @return
     */
    public int getValue(BitChromosome bitChromosome, int index) {
        return (int)((bitChromosome.genes >> (shift * index)) & mask);
    }

    public void setValue(int index, long value, BitChromosome bitChromosome) {
        bitChromosome.genes &= ~(mask << (shift * index));
        bitChromosome.genes |= (value << (shift * index));
    }

    public long[] toArray(BitChromosome bitChromosome) {
        long[] content = new long[16];
        for (int i = 0; i < content.length; i++) {
            content[i] = getValue(bitChromosome, i);
        }
        return content;
    }

    public Long[] toObjectArray(BitChromosome bitChromosome) {
        return ArrayUtils.toObject(toArray(bitChromosome));
    }

}
