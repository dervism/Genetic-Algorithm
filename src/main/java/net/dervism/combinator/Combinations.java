package net.dervism.combinator;

import org.uncommons.maths.combinatorics.CombinationGenerator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dervism on 06/09/14.
 */
public class Combinations {


    public static void createCombinations(List<Integer> list, int k) {
        CombinationGenerator<Integer> combination
                = new CombinationGenerator<Integer>(list, k);

        System.out.println("Total: " + combination.getTotalCombinations());

        while (combination.hasMore()) {
            System.out.println(combination.nextCombinationAsList());
        }

    }

}
