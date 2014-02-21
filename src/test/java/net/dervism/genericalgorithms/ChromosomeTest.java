package net.dervism.genericalgorithms;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;

import static org.junit.Assert.*;

/**
 * Created by dervism on 27.12.13.
 */
public class ChromosomeTest {
    @Test
    public void testSetNthBit() throws Exception {

        // construct a BigInteger as reference
        // BigInteger has no long constuctor, so do a byte conversion first
        BigInteger b = new BigInteger(Chromosome.longToByte(0));

        // set the most and least significant bit
        long bi = b.setBit(63).setBit(0).longValue();

        String ref = "";
        for(int i = 0; i < Long.numberOfLeadingZeros(bi); i++) ref += '0';
        ref += Long.toBinaryString(bi);
        System.out.println(ref);

        Chromosome s = new Chromosome();
        long l = s.setNthBit(63).setNthBit(0).genes;

        // print using the 'format' method
        String str = String.format("%064d", new BigInteger(Long.toBinaryString(l)));
        System.out.println(str);

        // print using the 'numberOfLeadingZeros' technique
        // see: http://stackoverflow.com/a/10401425
        String str2 = "";
        for(int i = 0; i < Long.numberOfLeadingZeros(l); i++) str2 += '0';
        str2 += Long.toBinaryString(l);
        System.out.println(str2);

        // check our Chromosome output with the BigInteger reference output
        assertTrue(ref.equals(str));
        assertTrue(str.equals(str2));
    }

    @Test
    public void testGetNthBit() throws Exception {
        Chromosome c = new Chromosome(0);
        System.out.println("toString: " + c);
    }

    @Test
    public void testClearNthBit() throws Exception {
        Chromosome s = new Chromosome();
        long l = s.setNthBit(63).setNthBit(0).setNthBit(31).genes;

        String str = String.format("%064d", new BigInteger(Long.toBinaryString(l)));
        System.out.println(str);

        // Chromosome is an immutable class so we must create a new one first
        Chromosome s2 = new Chromosome(l);
        Chromosome chromosome1 = s2.clearNthBit(63);
        long l2 = chromosome1.genes;

        String str3 = "";
        for(int i = 0; i < Long.numberOfLeadingZeros(l2); i++) str3 += '0';
        str3 += Long.toBinaryString(l2);
        System.out.println(str3);

        assertFalse(chromosome1.getNthBit(63));

        Chromosome s3 = new Chromosome(l2);
        Chromosome chromosome2 = s3.flipNthBit(63);
        long l3 = chromosome2.genes;

        str = String.format("%064d", new BigInteger(Long.toBinaryString(l3)));
        System.out.println(str);

        assertTrue(chromosome2.getNthBit(63));
    }

    @Test
    public void testLongToByte() throws Exception {
        long l = 7L;
        Chromosome c = new Chromosome();
        byte[] ret = c.longToByte(l);

        String str = Long.toBinaryString(l);
        String str2 = Long.toBinaryString(ret[7]);

        assertEquals(str, str2);
        assertTrue(toBinary(ret).endsWith(str));

        long l2 = c.byteToLong(ret);

        assertEquals(l2,l);
    }

    public String toBinary( byte[] bytes ) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }
}
