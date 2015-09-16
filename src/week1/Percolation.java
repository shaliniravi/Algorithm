package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Compilation: javac Percolation.java 
 * Dependencies: algs4.jar, WeightedQuickUnionUF.java 
 * This program is an API to used to find threshold via Monte Carlo simulation.
 * Author : Shalini
 */

public class Percolation {

  private final int size; // size of the N * N matrix
  private final boolean[] field; // boolean array to block or open the site
  private WeightedQuickUnionUF quickUnionUF1; // Used to avoid backwash
  private WeightedQuickUnionUF quickUnionUF2;
  private final int topRoot; // virtual top root
  private final int bottomRoot; // virtual bottom root
  private final int rowLength;

  /**
   * Constructor to initialize objects.
   * 
   * @param gridSize row size of the given grid
   */
  public Percolation(int gridSize) {

    // base case
    if (gridSize <= 0) {
      throw new IllegalArgumentException("N must be greater than 0");
    }

    size = gridSize * gridSize;
    rowLength = gridSize;
    field = new boolean[size];
    topRoot = size;
    bottomRoot = size + 1;
    quickUnionUF1 = new WeightedQuickUnionUF(size + 2);
    quickUnionUF2 = new WeightedQuickUnionUF(size + 2);
    createGrid();

  }

  // creates N-by-N grid, with all sites blocked
  private void createGrid() {
    for (int i = 0; i < size; i++) {
      field[i] = false;
    }
  }

  /**
   * Checks if the site is open and also connected to the top virtual row.
   * 
   * @param row row value in the matrix
   * @param column column value in the matrix
   * @return true or false whether the site is full or not
   */
  public boolean isFull(int row, int column) {

    // validate the row and column values
    validate(row, column);

    // Checks if the site is open
    if (isOpen(row, column)) {

      final int index = xyTo1D(row, column);
      if (quickUnionUF2.connected(index, topRoot)) {

        return true;
      }
      return false;
    }
    return false;

  }

  /**
   * Checks if the site (row i, column j) is opens or not.
   * 
   * @param row row value in the matrix
   * @param column column value in the matrix
   * @return true or false whether the site is open or not
   */
  public boolean isOpen(int row, int column) {

    // validate the row and column values
    validate(row, column);
    final int pos = xyTo1D(row, column);
    return field[pos];
  }

  //
  /**
   * Opens the site (row i, column j) if it is not open already.
   * 
   * @param row row value in the matrix
   * @param column column value in the matrix
   */
  public void open(int row, int column) {

    // validate the row and column values
    validate(row, column);
    final int position = xyTo1D(row, column);
    if (!isOpen(row, column)) {
      field[position] = true;
      unionNeighbors(row, column, position);
    }


  }

  /**
   * Checks if the virtual top root and bottom root are connected.
   *
   * @return true if the system percolates
   */
  public boolean percolates() {

    // checks if the virtual top root and bottom root are connected.
    if (quickUnionUF1.connected(topRoot, bottomRoot)) {
      return true;
    } else {
      return false;
    }

  }


  /**
   * Connects the adjacent sites.
   * 
   * @param row row value in the matrix
   * @param column column value in the matrix
   * @param position current position of the site
   */
  private void unionNeighbors(int row, int column, int position) {

    final int myIndex = position;
    final int top = xyTo1D(row - 1, column);
    final int down = xyTo1D(row + 1, column);
    final int right = xyTo1D(row, column - 1);
    final int left = xyTo1D(row, column + 1);

    // connects to top virtual root
    if (row == 1) {
      quickUnionUF1.union(myIndex, topRoot);
      quickUnionUF2.union(myIndex, topRoot);

    }

    // connects to bottom virtual root
    if (row == rowLength) {
      quickUnionUF1.union(myIndex, bottomRoot);
    }

    // connects to top neighbor
    if (row > 1 && field[top]) {
      quickUnionUF1.union(myIndex, top);
      quickUnionUF2.union(myIndex, top);

    }

    // connects to down neighbor
    if (row < rowLength && field[down]) {
      quickUnionUF1.union(myIndex, down);
      quickUnionUF2.union(myIndex, down);

    }
    // connects to right neighbor
    if (column > 1 && field[right]) {
      quickUnionUF1.union(myIndex, right);
      quickUnionUF2.union(myIndex, right);

    }

    // connects to left neighbor
    if (column < rowLength && field[left]) {
      quickUnionUF1.union(myIndex, left);
      quickUnionUF2.union(myIndex, left);

    }

  }

  /**
   * Validating integers.
   * 
   * @param row row value in the matrix
   * @param column column value in the matrix
   */
  private void validate(int row, int column) {

    if (row < 1 || row > rowLength || column < 1 || column > rowLength) {
      throw new java.lang.IndexOutOfBoundsException(
          "The values must be between 1 and " + rowLength);
    }


  }


  /**
   * @param row row value in the matrix
   * @param column column value in the matrix
   * @return 1D coordinate value of the given field's 2D grid coordinate.
   */
  private int xyTo1D(int row, int column) {

    row = row - 1;
    column = column - 1;
    return row * rowLength + column;
  }

}
