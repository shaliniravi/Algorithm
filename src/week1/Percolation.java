package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Compilation: javac Percolation.java Execution: API only. Must be called from another class.
 * Dependencies: algs4.jar, WeightedQuickUnionUF.java
 * This program estimates the value of the percolation threshold via Monte Carlo simulation.
 * Author : Shalini
 */

public class Percolation {

  private final int size; //size of the N * N matrix
  private boolean[] field; // boolean array to block or open the site
  WeightedQuickUnionUF quickUnionUF1; // Used to avoid backwash
  WeightedQuickUnionUF quickUnionUF2; 
  private final int topRoot; //virtual top root
  private final int bottomRoot; //virtual bottom root
  private final int rowLength;

  /**
   * Initialize the object and creates N-by-N grid, with all sites blocked.
   * @param gridSize 
   */
  public Percolation(int gridSize) {

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

  /**
   * @param row
   * @param column
   * @returns the 1D coordinate value of the given field's 2D grid coordinate.
   */
  private int xyTo1D(int row, int  column) {

    row = row - 1;
    column =  column - 1;
    return (row * rowLength + column);
  }

  // Validating integers
  private void validate(int row, int column) {

    if (row < 1 || row > rowLength || column < 1 || column > rowLength) {
      throw new java.lang.IndexOutOfBoundsException(
          "The values must be between 1 and " + rowLength);
    }


  }

  // creates N-by-N grid, with all sites blocked
  private void createGrid() {
    for (int i = 0; i < size; i++) {
      field[i] = false;
    }
  }

  /**
   * Opens the given site if it is not already open.
   * @param row
   * @param column
   * 
   */
  public void open(int row, int column) {

    validate(row, column);
    int position = xyTo1D(row, column);
    if (!isOpen(row, column)) {
      field[position] = true;
      unionNeighbors(row, column, position);
    }


  }

  /**
   * @param row
   * @param column
   * @param position This Method connects the adjacent fields.
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
    if (row > 1 && (field[top])) {
      quickUnionUF1.union(myIndex, top);
      quickUnionUF2.union(myIndex, top);

    }

    // connects to down neighbor
    if (row < rowLength && (field[down])) {
      quickUnionUF1.union(myIndex, down);
      quickUnionUF2.union(myIndex, down);

    }
    // connects to right neighbor
    if (column > 1 && (field[right])) {
      quickUnionUF1.union(myIndex, right);
      quickUnionUF2.union(myIndex, right);

    }

    // connects to left neighbor
    if (column < rowLength && (field[left])) {
      quickUnionUF1.union(myIndex, left);
      quickUnionUF2.union(myIndex, left);

    }

  }

  /**
   * @param row
   * @param column
   * @return true if the field is open
   */
  public boolean isOpen(int row, int column) {
    validate(row, column);
    int pos = xyTo1D(row, column);
    return field[pos];

  }

  /**
   * @param row
   * @param column
   * @return true if the site is full
   */
  public boolean isFull(int row, int column) {
    validate(row, column);
    if (isOpen(row, column)) {

      int index = xyTo1D(row, column);
      if (quickUnionUF2.connected(index, topRoot)) {

        return true;
      }
      return false;
    }
    return false;

  }

  /**
   * @return true if the system percolates
   */
  public boolean percolates() {
    if (quickUnionUF1.connected(topRoot, bottomRoot)) {
      return true;
    } else {
      return false;
    }

  }

}
