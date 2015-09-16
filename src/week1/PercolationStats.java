package week1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Compilation: javac PercolationStats.java 
 * Execution: java PercolationStats 200
 * 100 Dependencies: algs4.jar, Percolation.java 
 * This program estimates the
 * value of the percolation threshold via Monte Carlo simulation. 
 * Author :Shalini
 */
public class PercolationStats {

  private Percolation percolation;
  private final double[] threshold;
  private final int tests;

  /**
   * Constructor which performs T independent experiments on an N-by-N grid.
   * 
   * @param sites
   *          No of Sites
   * @param testCases
   *          No of TestCases
   */
  public PercolationStats(int sites, int testCases) {

    tests = testCases;
    // base case
    if (sites <= 0 || testCases <= 0) {
      throw new IllegalArgumentException();
    }
    threshold = new double[tests];

    for (int i = 0; i < tests; i++) {
      percolation = new Percolation(sites);
      int count = 0;
      
      // perform until the system percolates
      while (!percolation.percolates()) {
        int row = StdRandom.uniform(sites) + 1;
        int column = StdRandom.uniform(sites) + 1;

        // checks if the given site is open
        if (!percolation.isOpen(row, column)) {
          percolation.open(row, column);
          count++;
        }
      }

      threshold[i] = Integer.valueOf(count).doubleValue()  / (sites * sites);
    }
  }

  /**
   * Calculates high end point of 95% confidence interval.
   * 
   * @return high end point of 95% confidence interval
   */
  public double confidenceHi() {
    double mean = mean();
    double stddev = stddev();
    double confidenceHi = mean + 1.96 * stddev / Math.sqrt(tests);
    return confidenceHi;
  }

  /**
   * Calculates low end point of 95% confidence interval.
   * 
   * @return low end point of 95% confidence interval
   */
  public double confidenceLo() {
    double mean = mean();
    double stddev = stddev();
    double confidenceLo = mean - 1.96 * stddev / Math.sqrt(tests);
    return confidenceLo;
  }

  /**
   * Calculates mean of percolation threshold.
   * 
   * @return mean of percolation threshold
   */
  public double mean() {
    return StdStats.mean(threshold);

  }

  /**
   * Calculates standard deviation of percolation threshold.
   * 
   * @return standard deviation of percolation threshold
   */
  public double stddev() {
    return StdStats.stddev(threshold);

  }

 
  /**
   * Test client.
   * 
   * @param args  takes No of Sites and No of test Cases
   */
  public static void main(String[] args) {

    int grid = Integer.parseInt(args[0]);
    int testRun = Integer.parseInt(args[1]);    
   
    PercolationStats percolationStats = new PercolationStats(grid, testRun);

    StdOut.printf("mean                    = %f\n", percolationStats.mean());
    StdOut.printf("stddev                  = %f\n",  percolationStats.stddev());
    StdOut.printf("95%% confidence interval = %f, %f\n",  percolationStats.confidenceLo(),
        percolationStats.confidenceHi());
    
  }
}
