package no.dervism;

import no.dervism.combinator.Combinations;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class CombinationsTest {

    @Test
    void createCombinations() throws Exception {
        Combinations.createCombinations(Arrays.asList(8, 10, 31, 20, 26, 2, 11, 25, 29, 5), 7);
    }
}