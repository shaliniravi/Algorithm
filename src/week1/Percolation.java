package week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author shalini
 * 
 */
public class Percolation {

	private int size;
	private boolean[][] field;
	WeightedQuickUnionUF quickUnionUF;
	int topRoot;
	int bottomRoot;
	int index;
	int rowLength;

	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {

		if (N <= 0) {
			throw new IllegalArgumentException("N must be greater than 0");
		}

		size = N * N;
		rowLength = N;
		field = new boolean[rowLength][rowLength];
		topRoot = size;
		bottomRoot = size + 1;
		quickUnionUF = new WeightedQuickUnionUF(size + 2);
		createGrid();
		//createRoot();

	}

	// Create Virtual root
	private void createRoot() {

	}

	// mapping 2D coordinates to 1D coordinate
	private int xyTo1D(int x, int y) {
		return 0;
	}

	// Validating integers
	private void validate(int x, int y) {

		if (x < 1 || x > rowLength || y < 1 || y> rowLength){
			throw new java.lang.IndexOutOfBoundsException(
					"the values must be between 1 and " + size / 2);
		}

	
	}

	// create N-by-N grid, with all sites blocked
	private void createGrid() {
		for (int i = 0; i < size / 2; i++) {
			for (int j = 0; j < size / 2; j++) {
				field[i][j] = false;
				index++;
			}
		}
	}

	// open site (row i, column j) if it is not open already
	public void open(int i, int j) {
		validate(i, j);
		if(!isOpen(i, j)){
			field[i -1][j -1] = true;
			unionNeighbors(i,j);
		}
		

	}

	private void unionNeighbors(int i, int j) {
		
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		validate(i, j);	
		return field[i-1][j-1];

	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		return false;

	}

	// does the system percolate?
	public boolean percolates() {

		if (quickUnionUF.connected(topRoot, bottomRoot)) {
			return true;
		} else {
			return false;
		}

	}

}
