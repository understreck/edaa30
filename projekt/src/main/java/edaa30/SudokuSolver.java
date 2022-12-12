package main.java.edaa30;

public interface SudokuSolver{

    boolean solve();
    boolean legal(int digit, int row, int col);
    /**
     * Puts digit in the box row, col.
     *
     * @param row   The row
     * @param col   The column
     * @param digit The digit to insert in box row, col
     * @throws IllegalArgumentException if row, col or digit is outside the range
     *                                  [0..9]
     */
    void set(int row, int col, int digit) throws IllegalArgumentException;
    /**
     * Removes the digit in the box row, col.
     *
     * @param row   The row
     * @param col   The column
     * @throws IllegalArgumentException if row, col or digit is outside the range
     *                                  [0..9]
     */
    void remove(int row, int col) throws IllegalArgumentException;

    /**
     * Empties the grid.
     */
    void clear();
    /**
     * Fills the grid with the digits in m. The digit 0 represents an empty box.
     *
     * @param matrix the matrix with the digits to insert
     * @throws IllegalArgumentException if m has the wrong dimension or contains
     *                                  values outside the range [0..9]
     */
    void setMatrix(int[][] matrix) throws IllegalArgumentException;

    /**
     * Returns a matrix with the current values.
     *
     * @return integer matrix with current values
     */
    int[][] getMatrix();
}
