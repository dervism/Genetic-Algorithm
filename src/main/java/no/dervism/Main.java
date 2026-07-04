package no.dervism;

import no.dervism.knapsack.*;
import no.dervism.tsp.*;

import java.io.IOException;

public class Main {
    static void main(String[] args) throws IOException {

        System.out.println("1 TSP, 2 Knapsack, 3 Exit");
        int input = System.in.read() - '0';

        System.out.println(input);

        if (input == 1) {
            TSPEngine tspEngine = new TSPEngine(
                    new TSPPopulation(),
                    new TSPEvolution(),
                    new TSPEncoder(),
                    new TSPFitnessEvaluator()
            );
            tspEngine.run();
        } else if (input == 2) {
            System.out.println("Please wait...");
            KnapsackEngine knapsackEngine = new KnapsackEngine(
                    new KnapsackPopulation(),
                    new KnapsackEvolution(),
                    new KnapsackEncoder(),
                    new KnapsackFitnessEvaluator()
            );
            knapsackEngine.run();
        }
    }
}
