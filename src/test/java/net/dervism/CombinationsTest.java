package net.dervism;

import net.dervism.combinator.Combinations;
import org.junit.Test;

import java.util.Arrays;

public class CombinationsTest {

    @Test
    public void testCreateCombinations() throws Exception {
        Combinations.createCombinations(Arrays.asList(8, 10, 31, 20, 26, 2, 11, 25, 29, 5), 7);
    }
}