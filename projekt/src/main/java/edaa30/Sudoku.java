package main.java.edaa30;

public final class Sudoku implements SudokuSolver {
    public static final int ROWS = 9;
    public static final int COLUMNS = ROWS;

    private int[][] m_board = new int[ROWS][COLUMNS]; //collumn major (x, y)

    /**
     * Set a single element on the board
     *
     * @param x     1 - 9, left is 1
     * @param y     1 - 9, top is 1
     * @param value 1 - 9, 0 for empty
     * @throws IllegalArgumentException
     */
    public void set_element(int x, int y, int value) throws IllegalArgumentException {
        if (x < 1 || x > COLUMNS) throw new IllegalArgumentException(
                String.format("Argument 'int x = %d' is out of bounds.", x));
        if (y < 1 || y > ROWS) throw new IllegalArgumentException(
                String.format("Argument 'int y = %d' is out of bounds.", y));
        if (value < 0 || value > 9) throw new IllegalArgumentException(
                String.format("Argument 'int value = %d' is out of bounds.", value));

        m_board[y - 1][x - 1] = value;
    }

    /**
     * Set an entire row
     *
     * @param index  1 - 9, left is 1
     * @param column has to be 9 long
     * @throws IllegalArgumentException
     */
    public void set_column(int index, int[] column) throws IllegalArgumentException {
        if (index < 1 || index > COLUMNS) throw new IllegalArgumentException(
                String.format("Argument 'int index = %d' is out of bounds.", index));
        if (column == null) throw new IllegalArgumentException("Argument 'int[] column' is null.");
        if (column.length != COLUMNS) throw new IllegalArgumentException(
                String.format("Argument 'int[] column : column.length = %d' is not the correct length.", column.length));

        for (int row = 0; row < COLUMNS; ++row) {
            set_element(index, row, column[row]);
        }
    }

    /**
     * Set an entire column
     *
     * @param index 1 - 9, top is 1
     * @param row   has to be 9 long
     * @throws IllegalArgumentException
     */
    public void set_row(int index, int[] row) throws IllegalArgumentException {
        if (index < 1 || index > COLUMNS) throw new IllegalArgumentException(
                String.format("Argument 'int index = %d' is out of bounds.", index));
        if (row == null) throw new IllegalArgumentException("Argument 'int[] row' is null.");
        if (row.length != COLUMNS) throw new IllegalArgumentException(
                String.format("Argument 'int[] row : row.length = %d' is not the correct length.", row.length));

        for (int column = 0; column < COLUMNS; ++column) {
            set_element(column, index, row[column]);
        }
    }

    @Override
    public boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int row, int col) {
        if(legal(m_board) == false) return false;

        return solve(0, 0, m_board);
    }

    private static boolean solve(int row, int col, int[][] matrix) {
        if(col >= 9) {
            return solve(row + 1, 0, matrix);
        }
        if(row >= 9) {
            return true;
        }
        if(matrix[row][col] != 0) {
            return solve(row, col + 1, matrix);
        }

        for(int i = 1; i <= 9; ++i) {
            if(legal_placement(row, col, i, matrix)) {
                matrix[row][col] = i;
                if (solve(row, col + 1, matrix)) return true;
                matrix[row][col] = 0;
            }
        }

        return false;
    }

    @Override
    public boolean legal(int digit, int row, int col) {
        return legal_placement(row - 1, col - 1, digit, m_board);
    }

    private static boolean legal_placement(int row, int col, int digit, int[][] matrix) {
        if(digit == 0) return true;

        var topLeftX = col / 3;
        var topLeftY = row / 3;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                var index = 3 * i + j;
                if (matrix[row][index] == digit && index != col) {
                    return false;
                }
                if (matrix[index][col] == digit && index != (row)) {
                    return false;
                }
                if (matrix[3 * topLeftY + i][3 * topLeftX + j] == digit &&
                        (3 * topLeftY + i) != row && (3 * topLeftX + j) != col) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean legal(int[][] matrix) {
        if (matrix.length != ROWS) return false;
        for (int i = 0; i < ROWS; ++i) {
            if (matrix[i].length != COLUMNS) return false;
            for (int j = 0; j < COLUMNS; ++j) {
                if (legal_placement(i, j, matrix[i][j], matrix) == false) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Puts digit in the box row, col.
     *
     * @param row   The row
     * @param col   The column
     * @param digit The digit to insert in box row, col
     * @throws IllegalArgumentException if row, col or digit is outside the range
     *                                  [0..9]
     */
    @Override
    public void set(int row, int col, int digit) throws IllegalArgumentException {
        set_element(row, col, digit);
    }

    /**
     * Removes the digit in the box row, col.
     *
     * @param row The row
     * @param col The column
     * @throws IllegalArgumentException if row, col or digit is outside the range
     *                                  [0..9]
     */
    @Override
    public void remove(int row, int col) throws IllegalArgumentException {
        set_element(row, col, 0);
    }

    /**
     * Empties the grid.
     */
    @Override
    public void clear() {
        m_board = new int[COLUMNS][ROWS];
    }

    /**
     * Fills the grid with the digits in m. The digit 0 represents an empty box.
     *
     * @param matrix the matrix with the digits to insert
     * @throws IllegalArgumentException if m has the wrong dimension or contains
     *                                  values outside the range [0..9]
     */
    @Override
    public void setMatrix(int[][] matrix) throws IllegalArgumentException {
        if (!legal(matrix)) throw new IllegalArgumentException("Argument 'int[][] matrix' is not legal");
        m_board = matrix;
    }

    /**
     * Returns a matrix with the current values.
     *
     * @return integer matrix with current values
     */
    @Override
    public int[][] getMatrix() {
        return m_board;
    }

    public String toString() {
        var sb = new StringBuilder();
        for(int i = 0; i < ROWS; ++i) {
            sb.append('|');
            for(int j = 0; j < COLUMNS; ++j) {
                sb.append(" " + m_board[i][j] + " ");
                if(j == 2 || j == 5) sb.append("| ");
            }
            sb.append("|\n");
            if(i == 2 || i == 5) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }
}