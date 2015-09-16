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
	private boolean[] field;
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
		field = new boolean[size];
		topRoot = size;
		bottomRoot = size + 1;
		quickUnionUF = new WeightedQuickUnionUF(size + 2);
		createGrid();

	}

	// mapping 2D coordinates to 1D coordinate
	private int xyTo1D(int x, int y) {		
		return (x*rowLength + y);
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
		for (int i = 0; i < size ; i++) {
			
				field[i] = false;
			
			
		}
	}

	// open site (row i, column j) if it is not open already
	public void open(int i, int j) {
		
		validate(i, j);
		i = i - 1;
		j = j - 1;
		
		int position = xyTo1D(i, j);
		if(!isOpen(i, j)){
			field[position] = true;
			unionNeighbors(i,j, position);
		}
		

	}

	private void unionNeighbors(int i, int j, int position) {
		
		int myIndex = position;
		int top = xyTo1D(i-1, j);
		int down = xyTo1D(i+1, j);
		int right = xyTo1D(i, j-1);
		int left = xyTo1D(i, j+1);
		
		if(i != 0 &&  field[top]) {
			quickUnionUF.union(myIndex, top);
		}
		if(i != 0 &&  field[top]) {
			quickUnionUF.union(myIndex, top);
		}
		if(i != 0 &&  field[top]) {
			quickUnionUF.union(myIndex, top);
		}
		if(i != 0 &&  field[top]) {
			quickUnionUF.union(myIndex, top);
		}
		if(i ==0) {
			quickUnionUF.union(myIndex, topRoot);
		}
		
		if (j == rowLength - 1) {
			quickUnionUF.union(myIndex, bottomRoot);
		}
		
		
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		validate(i, j);	
		int pos = xyTo1D(i-1, j-1);
		return field[pos];

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
