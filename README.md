## A simple demonstration of an engine for running genetic algorithms.

### How to test
To run the Travelling Salesman Problem, simply run the TSPEngineTest. You can also run the TSPTest JUnit
test which contains among others a brute force algorithm.

### How to evaluate the performance
Because a genetic algorithm (GA) only simulates an evolution, there is no guarante for finding the best solution in one execution.
This means that when running the GA on a problem where the search space is extremely large, we'll never be able to tell
wheter or not the result in fact was the best one. To determine speed and performance of the GA in this implementation, the
TSPFitnessEvalutor is hardcoded with a known best-route. When executing the algorithm, we can easily see how close or far the result
is from the actual best result.
