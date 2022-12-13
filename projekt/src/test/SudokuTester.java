package test;

import main.java.edaa30.Sudoku;
import main.java.edaa30.SudokuSolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuTester {
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
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                assertTrue(
                        solver.legal(solver.getMatrix()[i][j], i + 1, j + 1),
                        "Solvers solution is illegal according to itself");
            }
        }
    }

    @Test
    void test_solveIllegalSquare() {
        solver.set(1, 1, 9);
        solver.set(3, 3, 9);
        assertFalse(solver.solve(), "Solver says it can solve an illegal sudoku");
    }

    @Test
    void test_solveIllegalRow() {
        solver.set(1, 1, 9);
        solver.set(1, 5, 9);
        assertFalse(solver.solve(), "Solver says it can solve an illegal sudoku");
    }

    @Test
    void test_solveIllegalColumn() {
        solver.set(1, 1, 9);
        solver.set(3, 1, 9);
        assertFalse(solver.solve(), "Solver says it can solve an illegal sudoku");
    }

    @Test
    void test_solveUnsolvable() {
        solver.set(1, 1, 1);
        solver.set(1, 2, 2);
        solver.set(1, 3, 3);
        solver.set(2, 5, 4);
        solver.set(3, 9, 4);
        assertFalse(solver.solve(), "Solver says it can solve an unsolvable sudoku");
    }

    @Test
    void test_legal() {
        solver.set(5, 5, 5);
        assertFalse(solver.legal(5, 6, 5), "Solver says an illegal column placement is legal.");
        assertFalse(solver.legal(5, 5, 6), "Solver says an illegal row placement is legal.");
        assertFalse(solver.legal(5, 6, 6), "Solver says an illegal square placement is legal.");

        solver.solve();
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                assertTrue(
                        solver.getMatrix()[i][j] != 0,
                        "Solvers solution includes empty squares.");
                assertTrue(
                        solver.legal(solver.getMatrix()[i][j], i + 1, j + 1),
                        "Solvers solution is illegal according to itself");
            }
        }

        assertThrows(IllegalArgumentException.class, () -> solver.legal(-1, 1, 1), "Digit below bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.legal(10, 1, 1), "Digit above bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.legal(1, 0, 1), "Row below bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.legal(1, 10, 1), "Row above bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.legal(1, 1, 0), "Column below bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.legal(1, 1, 10), "Column above bounds does not throw");
    }

    @Test
    void test_set() {
        for (int i = 1; i <= 9; ++i) {
            solver.set(i, i, i);
            assertEquals(solver.getMatrix()[i - 1][i - 1], i, "The correct digit did not get set");
        }

        solver.set(1, 2, 1);
        assertEquals(solver.getMatrix()[0][1], 1, "The correct digit did not get set");

        assertThrows(IllegalArgumentException.class, () -> solver.set(0, 1, 1), "Row below bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.set(10, 1, 1), "Row above bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.set(1, 0, 1), "Column below bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.set(1, 10, 1), "Column above bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.set(1, 1, -1), "Digit below bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.set(1, 1, 10), "Digit above bounds does not throw");
    }

    @Test
    void test_remove() {
        solver.set(1, 1, 1);
        solver.remove(1, 1);
        assertEquals(solver.getMatrix()[0][0], 0, "Remove did not erase the digit");

        assertThrows(IllegalArgumentException.class, () -> solver.remove(0, 1), "Row below bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.remove(10, 1), "Row above bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.remove(1, 0), "Column below bounds does not throw");
        assertThrows(IllegalArgumentException.class, () -> solver.remove(1, 10), "Column above bounds does not throw");
    }

    @Test
    void test_clear() {
        solver.solve();
        solver.clear();
        for (var row : solver.getMatrix()) {
            for (var element : row) {
                assertEquals(element, 0, "Clear does not leave all squares empty.");
            }
        }
    }

    @Test
    void test_setMatrix_valid() {
        int[][] validBoard = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 2, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 3, 0, 0, 0, 3, 0, 0},
                {0, 0, 0, 4, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 0, 0},
                {0, 0, 0, 6, 0, 6, 0, 0, 0},
                {0, 0, 7, 0, 0, 0, 7, 0, 0},
                {0, 8, 0, 0, 0, 0, 0, 8, 0},
                {9, 0, 0, 0, 0, 0, 0, 0, 9}
        };

        assertDoesNotThrow(() -> solver.setMatrix(validBoard), "Setting a valid matrix threw");

        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                assertEquals(validBoard[i][j], solver.getMatrix()[i][j], "Matrices where not equal");
            }
        }
    }

    @Test
    void test_setMatrix_argOOB() {
        int[][] oobBoard = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 2, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 3, 0, 0, 0, 3, 0, 0},
                {0, 0, 0, 4, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 10, 0, 0, 0, 0},
                {0, 0, 0, 6, 0, 6, 0, 0, 0},
                {0, 0, 7, 0, 0, 0, 7, 0, 0},
                {0, 8, 0, 0, 0, 0, 0, 8, 0},
                {9, 0, 0, 0, 0, 0, 0, 0, 9}
        };

        assertThrows(IllegalArgumentException.class, () -> solver.setMatrix(oobBoard),
                "Setting a matrix with an out of bounds element did not throw");
    }

    @Test
    void test_setMatrix_argWrongDim() {
        {
            int[][] wrongNumRowsBoard = {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {0, 2, 0, 0, 0, 0, 0, 2, 0},
                    {0, 0, 3, 0, 0, 0, 3, 0, 0},
                    {0, 0, 0, 4, 0, 4, 0, 0, 0},
                    {0, 0, 0, 6, 0, 6, 0, 0, 0},
                    {0, 0, 7, 0, 0, 0, 7, 0, 0},
                    {0, 8, 0, 0, 0, 0, 0, 8, 0},
                    {9, 0, 0, 0, 0, 0, 0, 0, 9}
            };

            assertThrows(IllegalArgumentException.class, () -> solver.setMatrix(wrongNumRowsBoard),
                    "Setting a matrix with wrong number of rows did not throw");
        }
        {
            int[][] wrongNumColsBoard = {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {0, 2, 0, 0, 0, 0, 0, 2, 0},
                    {0, 0, 3, 0, 0, 0, 3, 0, 0},
                    {0, 0, 0, 4, 0, 4, 0, 0, 0},
                    {0, 0, 0, 0, 5, 0, 0, 0, 0, 0},
                    {0, 0, 0, 6, 0, 6, 0, 0, 0},
                    {0, 0, 7, 0, 0, 0, 7, 0, 0},
                    {0, 8, 0, 0, 0, 0, 0, 8, 0},
                    {9, 0, 0, 0, 0, 0, 0, 0, 9}
            };

            assertThrows(IllegalArgumentException.class, () -> solver.setMatrix(wrongNumColsBoard),
                    "Setting a matrix with wrong number of columns in a row did not throw");
        }
    }
}
