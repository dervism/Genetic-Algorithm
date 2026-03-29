package no.dervism.genericalgorithms;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dervism on 27.12.13.
 */
public class BitChromosomeTest {
    @Test
    void setNthBit() throws Exception {

        // construct a BigInteger as reference
        // BigInteger has no long constuctor, so do a byte conversion first
        BigInteger b = new BigInteger(BitChromosome.longToByte(0));

        // set the most and least significant bit
        long bi = b.setBit(63).setBit(0).longValue();

        String ref = "";
        for(int i = 0; i < Long.numberOfLeadingZeros(bi); i++) ref += '0';
        ref += Long.toBinaryString(bi);
        IO.println(ref);

        BitChromosome s = new BitChromosome();
        long l = s.setNthBit(63).setNthBit(0).genes;

        // print using the 'format' method
        String str = "%064d".formatted(new BigInteger(Long.toBinaryString(l)));
        IO.println(str);

        // print using the 'numberOfLeadingZeros' technique
        // see: http://stackoverflow.com/a/10401425
        String str2 = "";
        for(int i = 0; i < Long.numberOfLeadingZeros(l); i++) str2 += '0';
        str2 += Long.toBinaryString(l);
        IO.println(str2);

        // check our BitChromosome output with the BigInteger reference output
        assertEquals(ref, str);
        assertEquals(str, str2);
    }

    @Test
    void getNthBit() throws Exception {
        BitChromosome c = new BitChromosome(0);
        IO.println("toString: " + c);
    }

    @Test
    void clearNthBit() throws Exception {
        BitChromosome s = new BitChromosome();
        long l = s.setNthBit(63).setNthBit(0).setNthBit(31).genes;

        String str = "%064d".formatted(new BigInteger(Long.toBinaryString(l)));
        IO.println(str);

        // BitChromosome is an immutable class so we must create a new one first
        BitChromosome s2 = new BitChromosome(l);
        BitChromosome bitChromosome1 = s2.clearNthBit(63);
        long l2 = bitChromosome1.genes;

        String str3 = "";
        for(int i = 0; i < Long.numberOfLeadingZeros(l2); i++) str3 += '0';
        str3 += Long.toBinaryString(l2);
        IO.println(str3);

        assertFalse(bitChromosome1.getNthBit(63));

        BitChromosome s3 = new BitChromosome(l2);
        BitChromosome bitChromosome2 = s3.flipNthBit(63);
        long l3 = bitChromosome2.genes;

        str = "%064d".formatted(new BigInteger(Long.toBinaryString(l3)));
        IO.println(str);

        assertTrue(bitChromosome2.getNthBit(63));
    }

    @Test
    void longToByte() throws Exception {
        long l = 7L;
        BitChromosome c = new BitChromosome();
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
