package net.dervism.genericalgorithms;

/**
 * A 64-bit immutable BitChromosome that contains several genes.
 *
 * Implementation is based on the encoding proposed at the Chess Programming Wiki:
 * https://chessprogramming.wikispaces.com/Encoding+Moves
 *
 * Created by dervism on 27.12.13.
 */
public class BitChromosome implements Chromosome {

    public long genes;

    public BitChromosome() {
        this(0L);
    }

    public BitChromosome(long genes) {
        this.genes = genes;
    }

    public BitChromosome(BitChromosome c) {
        this.genes |= c.genes;
    }

    public BitChromosome setNthBit(long n) {
        BitChromosome c = new BitChromosome(genes | 1L << n);
        return new BitChromosome(c);
    }

    // ^ = exclusive or, xor
    public BitChromosome flipNthBit(long n) {
        BitChromosome c = new BitChromosome(genes ^ (1L << n));
        return new BitChromosome(c);
    }

    // if n'th bit is 1, then return true, otherwise false
    public boolean getNthBit(long n) {
        return !((genes & (1L << n)) == 0);
    }

    public int getValue(int index, long mask, long shift) {
        return (int)((genes >> (shift * index)) & mask);
    }

    public BitChromosome setValue(int index, long mask, long shift, long value) {
        long updated = genes & ~(mask << (shift * index));
        updated |= (value << (shift * index));
        return new BitChromosome(updated);
    }

    public BitChromosome clearNthBit(long n) {
        BitChromosome c = new BitChromosome(genes & ~(1L << n));
        return new BitChromosome(c);
    }

    public long[] toArray() {
        return toArray(0xFL, 4);
    }
    public long[] toArray(long mask, long shift) {
        long[] content = new long[16];
        for (int i = 0; i < content.length; i++) {
            content[i] = getValue(i, mask, shift);
        }
        return content;
    }

    public byte[] toBytes() {
        return longToByte(genes);
    }

    public static byte[] longToByte(long l) {
        // a long occupies 8 bytes
        byte[] ret = new byte[8];

        // do a unsigned right shift
        // - push everything to the right and insert 0's from the left
        ret[0] = (byte)(l >>> 56);
        ret[1] = (byte)(l >>> 48);
        ret[2] = (byte)(l >>> 40);
        ret[3] = (byte)(l >>> 32);
        ret[4] = (byte)(l >>> 24);
        ret[5] = (byte)(l >>> 16);
        ret[6] = (byte)(l >>>  8);
        ret[7] = (byte)(l >>>  0);

        return ret;
    }

    public static long byteToLong(byte[] bytes) {
        return (bytes[0] << 56)
              + ((bytes[1] & 0xFF) << 48)
              + ((bytes[2] & 0xFF) << 40)
              + ((bytes[3] & 0xFF) << 32)
              + ((bytes[4] & 0xFF) << 24)
              + ((bytes[5] & 0xFF) << 16)
              + ((bytes[6] & 0xFF) << 8)
              + (bytes[7] & 0xFF);
    }

    // BitChromosome class is immutable, however, if you want to allow
    // mutability then use this setter.
    private void setGenes(long genes) {
        this.genes = genes;
    }

    /**
     * Prints the binary string representation of this BitChromosome's bits
     * @return
     */
    @Override
    public String toString() {
        String ret = "";
        String group = "";
        for(int i = 0; i < Long.numberOfLeadingZeros(genes); i++) ret += '0';
        ret += Long.toBinaryString(genes);
        for (int i = 1; i <= ret.length(); i++) {
            if ((i % 4) == 0) {
                group += ret.charAt(i-1) + " ";
            }
            else group += ret.charAt(i-1);
        }
        ret = group;
        return ret;
    }
}
