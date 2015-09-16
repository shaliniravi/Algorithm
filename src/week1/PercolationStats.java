package week1;

import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Compilation: javac PercolationStats.java 
 * Execution: java PercolationStats 200
 * 100 Dependencies: algs4.jar, Percolation.java 
 * This program estimates the
 * value of the percolation threshold via Monte Carlo simulation. 
 * Author : Shalini
 */
public class PercolationStats {

  private Percolation percolation;
  private final double[] threshold;
  private final int testcases;

  /**
   * Constructor which performs T independent experiments on an N-by-N grid.
   * 
   * @param sites
   *          No of Sites
   * @param testCases
   *          No of TestCases
   */
  public PercolationStats(int sites, int testCases) {

    testcases = testCases;
    // base case
    if (sites <= 0 || testCases <= 0) {
      throw new IllegalArgumentException();
    }
    threshold = new double[testCases];

    for (int i = 0; i < testCases; i++) {
      percolation = new Percolation(sites);
      int count = 0;
      int row = StdRandom.uniform(sites);
      int column = StdRandom.uniform(sites);

      // perform until the system percolates
      while (!percolation.percolates()) {

        // checks if the given site is open
        if (!percolation.isOpen(row, column)) {
          percolation.open(row, column);
          count++;
        }
      }

      threshold[i] = count / (sites * sites);
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
    double confidenceHi = mean + 1.96 * stddev / Math.sqrt(testcases);
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
    double confidenceLo = mean - 1.96 * stddev / Math.sqrt(testcases);
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

    Out out = new Out();
    int testRun = Integer.parseInt(args[0]);
    int grid = Integer.parseInt(args[1]);

    PercolationStats percolationStats = new PercolationStats(testRun, grid);

    double mean = percolationStats.mean();
    double stddev = percolationStats.stddev();
    double confidenceLo = percolationStats.confidenceLo();
    double confidenceHi = percolationStats.confidenceHi();

    out.printf("mean                    = %f\n", mean);
    out.printf("stddev                  = %f\n", stddev);
    out.printf("95%% confidence interval = %f, %f\n", confidenceLo,
        confidenceHi);
    out.close();
  }
}
