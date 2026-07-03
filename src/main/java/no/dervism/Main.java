package no.dervism;

import no.dervism.tsp.*;

public class Main {
    public static void main(String[] args) {
        TSPEngine tspEngine = new TSPEngine(
                new TSPPopulation(),
                new TSPEvolution(),
                new TSPEncoder(),
                new TSPFitnessEvaluator()
        );
        tspEngine.run();
    }
}
