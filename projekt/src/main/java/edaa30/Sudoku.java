package main.java.edaa30;

public final class Sudoku implements SudokuSolver {
    public static final int ROWS = 9;
    public static final int COLUMNS = ROWS;

    private int[][] m_board = new int[ROWS][COLUMNS]; //collumn major (x, y)

    /**
     * Solves the loaded sudoku
     * @return True if the sudoku could be solved
     */
    @Override
    public boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int row, int col) {
        if(!legal(m_board)) return false;

        return solve(row, col, m_board);
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

    /**
     * Check if placing digit in square row, col is legal
     * @param digit The digit to insert in square row, col
     * @param row   The row
     * @param col   The column
     * @throws IllegalArgumentException if row, col or digit is outside the range
     *                                  [0..9]
     * @return True if the placement of digit at [row][col] is legal
     */
    @Override
    public boolean legal(int digit, int row, int col) throws IllegalArgumentException {
        if(!valid_args(digit, 0, row, col)) {
            throw new IllegalArgumentException("An argument was out of bounds.");
        }

        return legal_placement(row - 1, col - 1, digit, m_board);
    }

    private static boolean valid_args(int digit, int lDigitBound, int row, int col) {
        if (col < 1 || col > COLUMNS) return false;
        if (row < 1 || row > ROWS) return false;
        return digit >= lDigitBound && digit <= 9;
    }

    private static boolean valid(int[][] matrix) {
        if (matrix.length != ROWS) return false;
        for (int i = 0; i < ROWS; ++i) {
            if (matrix[i].length != COLUMNS) return false;
            for (int j = 0; j < COLUMNS; ++j) {
                if (!valid_args(matrix[i][j], 0, i + 1, j + 1)) {
                    return false;
                }
            }
        }

        return true;
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
                if(!legal_placement(i, j, matrix[i][j], matrix)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Puts digit in the square row, col
     *
     * @param row   The row
     * @param col   The column
     * @param digit The digit to insert in box row, col
     * @throws IllegalArgumentException if row, col or digit is outside the range
     *                                  [0..9]
     */
    @Override
    public void set(int row, int col, int digit) throws IllegalArgumentException {
        if(!valid_args(digit, 0, row, col)) {
            throw new IllegalArgumentException("An argument where out of bounds");
        }

        m_board[row - 1][col - 1] = digit;
    }

    /**
     * Removes the digit in the square row, col.
     *
     * @param row The row
     * @param col The column
     * @throws IllegalArgumentException if row, col or digit is outside the range
     *                                  [0..9]
     */
    @Override
    public void remove(int row, int col) throws IllegalArgumentException {
        set(row, col, 0);
    }

    /**
     * Empties the grid.
     */
    @Override
    public void clear() {
        m_board = new int[COLUMNS][ROWS];
    }

    /**
     * Sets internal matrix to argument if no element is out of bounds
     * and it has the correct 9 * 9 dimensions
     * @param matrix the matrix with the digits to insert
     * @throws IllegalArgumentException if matrix has the wrong dimension or
     *                                  contains values outside the range [0..9]
     */
    @Override
    public void setMatrix(int[][] matrix) throws IllegalArgumentException {
        if (!valid(matrix)) throw new IllegalArgumentException("Argument 'int[][] matrix' is not valid");
        m_board = matrix;
    }

    /**
     * Returns the inner representation of the loaded sudoku
     *
     * @return integer matrix with current values
     */
    @Override
    public int[][] getMatrix() {
        return m_board;
    }

    @Override
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