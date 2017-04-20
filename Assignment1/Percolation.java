/**
 * Created by kandronovs on 17.13.4.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[][] openedCells;
    private int virtualTopCell;
    private int virtualBottomCell;
    private WeightedQuickUnionUF quickUnion;
    private WeightedQuickUnionUF quickUnion2;
    private int gridSideLength;

    /**
     * @param gridSideLength - length of the grid side.
     */
    public Percolation(int gridSideLength) {
        if (gridSideLength <= 0) {
            throw new IllegalArgumentException("Grid side length is less tan 0");
        }
        virtualTopCell = gridSideLength * gridSideLength;
        virtualBottomCell = gridSideLength * gridSideLength + 1;
        this.gridSideLength = gridSideLength;

        openedCells = new boolean[gridSideLength][gridSideLength];
        quickUnion = new WeightedQuickUnionUF(gridSideLength * gridSideLength + 2);
        quickUnion2 = new WeightedQuickUnionUF(gridSideLength * gridSideLength + 1);

    }

    /**
     * @param row - row in the grid.
     * @param col - column in the grid.
     */
    public void open(int row, int col) {
        checkIndex(row, col);
        openedCells[row - 1][col - 1] = true;
        
        // Union any 1 row element with virtual top cell
        if (row == 1) {
            quickUnion.union(col - 1, virtualTopCell);
            quickUnion2.union(col - 1, virtualTopCell);
        }

        if (row == gridSideLength) quickUnion.union((row - 1) * gridSideLength + col - 1, virtualBottomCell);

        if (row > 1 && isOpen(row - 1, col)) {
            quickUnion.union((row - 1) * gridSideLength + col - 1, (row - 2) * gridSideLength + col - 1);
            quickUnion2.union((row - 1) * gridSideLength + col - 1, (row - 2) * gridSideLength + col - 1);
        }
        if (row < gridSideLength && isOpen(row + 1, col)) {
            quickUnion.union((row - 1) * gridSideLength + col - 1, row * gridSideLength + col - 1);
            quickUnion2.union((row - 1) * gridSideLength + col - 1, row * gridSideLength + col - 1);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            quickUnion.union((row - 1) * gridSideLength + col - 1, (row - 1) * gridSideLength + col - 2);
            quickUnion2.union((row - 1) * gridSideLength + col - 1, (row - 1) * gridSideLength + col - 2);
        }
        if (col < gridSideLength && isOpen(row, col + 1)) {
            quickUnion.union((row - 1) * gridSideLength + col - 1, (row - 1) * gridSideLength + col);
            quickUnion2.union((row - 1) * gridSideLength + col - 1, (row - 1) * gridSideLength + col);
        }
    }

    /**
     * @param row - row number in the grid.
     * @param col - column number in the grid.
     * @return - (@code true) if cell with given row and column index is open, (@code false) if it doesn't open.
     */
    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        return openedCells[row - 1][col - 1];
    }

    /**
     * @param row - row number in the grid.
     * @param col - column number in the grid.
     * @return - (@code true) if cell is connected to the top virtual cell(@code virtualTopCell), and (@code false) if it doesn't.
     */
    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        return quickUnion2.connected((row - 1) * gridSideLength + col - 1, virtualTopCell);
    }

    /**
     * @return - number of open cells on the grid.
     */
    public int numberOfOpenSites() {
        int trueCount = 0;
        for (int row = 0; row < gridSideLength; row++) {
            for (int col = 0; col < gridSideLength; col++) {
                if (openedCells[row][col]) {
                    trueCount++;
                }
            }
        }
        return trueCount;
    }


    /**
     * @return - (@code true) if the grid percolates, (@code false) if it doesn't.
     */
    public boolean percolates() {
        return quickUnion.connected(virtualTopCell, virtualBottomCell);
    }

    /** 
     *  check if the given parameters are valid for grid.
     * @param row - row in the grid.
     * @param col - column in the grid.
     */
    private void checkIndex(int row, int col) {
        if (row < 1 || row > gridSideLength) {
            throw new IndexOutOfBoundsException("Index of row: " + row
                    + " is not between 0 and " + gridSideLength);
        }
        if (col < 1 || col > gridSideLength) {
            throw new IndexOutOfBoundsException("Index of column: " + col
                    + " is not between 0 and " + gridSideLength);
        }
    }
}
