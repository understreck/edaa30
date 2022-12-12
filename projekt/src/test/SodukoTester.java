package test;

import main.java.edaa30.Sudoku;
import main.java.edaa30.SudokuSolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SodukoTester {
    SudokuSolver solver = new Sudoku();

    @BeforeEach
    void setUp() {
        solver.clear();
    }

    @AfterEach
    void tearDown() {
        solver.clear();
    }

    @Test
    void test_solveEmpty() {
        assertTrue(solver.solve(), "Solver could not solve empty Sudoku.");
        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                assertTrue(
                        solver.legal(solver.getMatrix()[i][j], i + 1, j + 1),
                        "Solvers solution is illegal according to itself");
            }
        }
    }

//    @Test
//    void test_solveEmpty() {
//        assertEquals(solver.solve(), true, "Solver could not solve empty Sudoku.");
//        for(int i = 0; i < 9; ++i) {
//            for(int j = 0; j < 9; ++j) {
//                assertEquals(
//                        solver.legal(solver.getMatrix()[i][j], i + 1, j + 1), true,
//                        "Solvers solution is illegal according to itself");
//            }
//        }
//    }

//    boolean solve();
//    boolean legal(int digit, int row, int col);
//    /**
//     * Puts digit in the box row, col.
//     *
//     * @param row   The row
//     * @param col   The column
//     * @param digit The digit to insert in box row, col
//     * @throws IllegalArgumentException if row, col or digit is outside the range
//     *                                  [0..9]
//     */
//    void set(int row, int col, int digit) throws IllegalArgumentException;
//    /**
//     * Removes the digit in the box row, col.
//     *
//     * @param row   The row
//     * @param col   The column
//     * @throws IllegalArgumentException if row, col or digit is outside the range
//     *                                  [0..9]
//     */
//    void remove(int row, int col) throws IllegalArgumentException;
//
//    /**
//     * Empties the grid.
//     */
//    void clear();
//    /**
//     * Fills the grid with the digits in m. The digit 0 represents an empty box.
//     *
//     * @param matrix the matrix with the digits to insert
//     * @throws IllegalArgumentException if m has the wrong dimension or contains
//     *                                  values outside the range [0..9]
//     */
//    void setMatrix(int[][] matrix) throws IllegalArgumentException;
//
//    /**
//     * Returns a matrix with the current values.
//     *
//     * @return integer matrix with current values
//     */
//    int[][] getMatrix();
}
