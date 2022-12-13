package main.java.edaa30;

public class SudokuGUI {
    private final SudokuSolver m_solver;

    public static String serialize(SudokuSolver solver) {
        var sb = new StringBuilder();
        for (var row : solver.getMatrix()) {
            for (var element : row) {
                sb.append(element + " ");
            }
            sb.setCharAt(sb.length() - 1, '\n');
        }

        return sb.toString();
    }

    public void deserialize(String sudokuString) throws IllegalArgumentException {
        var rows = sudokuString.split("\n");
        if (rows.length != 9) {
            throw new IllegalArgumentException("Wrong number rows in string");
        }
        var board = new int[9][9];
        for (int i = 0; i < 9; ++i) {
            var column = rows[i].split(" ");
            if (column.length != 9) {
                throw new IllegalArgumentException("Wrong number columns in a row");
            }

            for (int j = 0; j < 9; ++j) {
                try {
                    board[i][j] = Integer.parseInt(column[j]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("An element in the given string was not able to be parsed into an int");
                }
            }
        }

        m_solver.setMatrix(board);
    }

    public SudokuGUI(SudokuSolver solver) {
        m_solver = solver;
    }
}
