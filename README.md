## A general purpose engine for running genetic algorithms

This project provides a small, reusable engine for building and running genetic
algorithms. It ships with two example problems: the Traveling Salesman Problem
(TSP) and the Knapsack problem.

The engine was first developed in 2013 after listening to NASA talking about
how they use genetic algorithms to cluster satellites in formations.

### How to run
The `Main` class exposes an interactive menu.

Type `1` to run the Travelling Salesman Problem or `2` to run the Knapsack
problem, then press Enter. Type `3` to exit.

You can build and run the project with Maven:

```
mvn compile
mvn exec:java -Dexec.mainClass="no.dervism.Main"
```

### How to evaluate the performance
Because a genetic algorithm (GA) only simulates an evolution, there is no
guarantee for finding the best solution in one execution. This means that when
running the GA on a problem where the search space is extremely large, we'll
never be able to tell whether the result in fact was the best one. To
determine speed and performance of the GA in this implementation, the
`TSPFitnessEvaluator` is hardcoded with a known best-route. When executing the
algorithm, we can easily see how close or far the result is from the actual best
result.

## How to use the engine
A genetic algorithm is constructed using a few building blocks:

* Chromosome - A citizen, typically an encoded class (bits, integers), representing some object.
* Population - A list of chromosomes. Should have functionality for adding and removing chromosomes.
* Encoder - An encoder is a class that should be able to create a chromosome, i.e. converting objects to a bit-representation.
* Evolution - Should implement crossover and mutation functions.
* Evaluation - When a new chromosome is created by either a crossover or mutation, the evaluation function should measure its performance and give it a "score".

These building blocks are wired together through the `GeneticAlgorithmEngine`,
which drives the evolution. Each example problem (TSP and Knapsack) provides its
own implementation of these blocks (e.g. `TSPPopulation`, `TSPEvolution`,
`TSPEncoder`, `TSPFitnessEvaluator` and their Knapsack counterparts).
