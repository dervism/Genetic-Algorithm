## A simple demonstration of an engine for running genetic algorithms.

### How to test
To run the Travelling Salesman Problem, simply run the TSPEngineTest. You can also run TSPTest
which contains among others a brute force algorithm.

### How to evaluate the performance
Because a genetic algorithm (GA) only simulates an evolution, there is no guarante for finding the best solution in one execution.
This means that when running the GA on a problem where the search space is extremely large, we'll never be able to tell
wheter or not the result in fact was the best one. To determine speed and performance of the GA in this implementation, the
TSPFitnessEvaluator is hardcoded with a known best-route. When executing the algorithm, we can easily see how close or far the result
is from the actual best result.

## How to use the engine
A genetic algorithm is constructed using a few building blocks:

* Chromosome - A citizen, typically an encoded class (bits, integers), representing some object
* Population - A list of chromosomes. Should have functionality addig and removing chromosomes.
* Encoder - An encoder is class that should be able to create a chromosome, ie. converting objects to bit-representation.
* Evolution - Should implements crossover and mutation functions.
* Evaluation - When a new chromosome is created by either a crossover or mutation, the evaluation function should
measure its performance and give it a "score".
