package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int[][] NEIGHBOURS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private WeightedQuickUnionUF grid2;
    private WeightedQuickUnionUF grid;
    private int topSiteDummyVal;
    private int bottomSiteDummyVal;
    private int gridSize; /* Grid dimension */
    private int numOpenSites;
    private boolean[][] gridState;

    /**
     * Create n-by-n grid, with all sites blocked. Must take time proportional to n^2.
     * @param n
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than zero.");
        }
        numOpenSites = 0;
        gridSize = n;
        grid = new WeightedQuickUnionUF(n * n + 2);
        grid2 = new WeightedQuickUnionUF(n * n + 1);
        topSiteDummyVal = 0;
        bottomSiteDummyVal = n * n + 1;
        gridState = new boolean[n][n];
    }

    private int xyTo1D(int row, int col) {
        return (row) * gridSize + col;
    }

    private boolean isCoordsOutOfBounds(int row, int col) {
        return (row < 0 || row >= gridSize || col < 0 || col >= gridSize);
    }

    /**
     * Open site (row, col) if it is not open already.
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (isCoordsOutOfBounds(row, col)) {
            throw new IndexOutOfBoundsException("Coordinates are out of bounds: (" + row + ", " + col + ")");
        }

        if (!gridState[row][col]) {
            gridState[row ][col] = true;
            numOpenSites++;
            for (int[] neighbour : NEIGHBOURS) {
                int newRow = row + neighbour[0];
                int newCol = col + neighbour[1];

                if (newRow < 0) {
                    grid.union(xyTo1D(row, col), topSiteDummyVal);
                    grid2.union(xyTo1D(row, col), topSiteDummyVal);
                } else if (newRow >= gridSize) {
                    grid.union(xyTo1D(row, col), bottomSiteDummyVal);
                } else {
                    if (!isCoordsOutOfBounds(newRow, newCol) && isOpen(newRow, newCol)) {
                        grid.union(xyTo1D(newRow, newCol), xyTo1D(row, col));
                        grid2.union(xyTo1D(newRow, newCol), xyTo1D(row, col));
                    }
                }

            }
        }

    }

    /**
     * Is site (row, col) open?
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        if (isCoordsOutOfBounds(row, col)) {
            throw new IndexOutOfBoundsException("Coordinates are out of bounds: (" + row + ", " + col + ")");
        }

        return gridState[row][col];
    }

    /**
     * Is site (row, col) full?
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        if (isCoordsOutOfBounds(row, col)) {
            throw new IndexOutOfBoundsException("Coordinates are out of bounds: (" + row + ", " + col + ")");
        }
        return isOpen(row, col) && grid2.connected(xyTo1D(row, col), topSiteDummyVal);
    }

    /**
     * Number of open sites.
     * @return
     */
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    /**
     * Does the system percolate?
     * @return
     */
    public boolean percolates() {
        return grid.connected(topSiteDummyVal, bottomSiteDummyVal);
    }

    /**
     * Test client (optional).
     *
     * Usage: PercolationStats n T
     * @param args
     */
    public static void main(String[] args) {
    }


}
