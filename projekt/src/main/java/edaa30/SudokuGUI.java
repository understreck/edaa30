package main.java.edaa30;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.SwingConstants.CENTER;

public class SudokuGUI {
    private final SudokuSolver m_solver;
    private SudokuSquare m_selected = null;

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

    private static void wrong_input_warning() {
        System.out.println("Wrong input");
    }

    private static void warn_unsolvable() {
        JOptionPane.showMessageDialog(
                null, "Given Sudoku is unsolvable!!",
                "Warning", JOptionPane.ERROR_MESSAGE);
    }

    public SudokuGUI(SudokuSolver solver) {
        m_solver = solver;
    }

    private void construct_frame(int sideLength) {
        var frame = new JFrame("Sudoku");
        frame.setMinimumSize(new Dimension(sideLength, sideLength + 100));
        frame.setMaximumSize(new Dimension(sideLength, sideLength + 100));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        var sudokuPanel = new SudokuFullGrid(sideLength);
        frame.add(sudokuPanel);

        var solveButton = new JButton("Solve");
        solveButton.addActionListener((a) -> {
            if(!m_solver.solve()) {
                warn_unsolvable();
            }

            sudokuPanel.update();
        });

        var clearButton = new JButton("Clear");
        clearButton.addActionListener((a) -> {
            m_solver.clear();
            sudokuPanel.update();
        });

        var buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> construct_frame(400));
    }

    private class SudokuFullGrid extends JPanel {
        public final SudokuSubGrid[] subGrids;
        public SudokuFullGrid(int sideLength) {
            super();
            setLayout(new GridLayout(3, 3));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            setFocusable(true);

            subGrids = new SudokuSubGrid[9];
            for(int i = 0; i < 9; ++i) {
                subGrids[i] = new SudokuSubGrid(i);
                add(subGrids[i]);
            }
        }

        public void update() {
            for(var grid : subGrids) {
                grid.update();
            }
        }
    }

    private class SudokuSubGrid extends JPanel {
        public final int index;
        public final SudokuSquare[] sudokuSquares;

        public SudokuSubGrid(int index) {
            super();
            this.index = index;
            super.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            var background = index % 2 == 0 ? Color.orange : Color.white;

            super.setBackground(background);
//            setFocusable(true);

            setLayout(new GridLayout(3, 3));

            sudokuSquares = new SudokuSquare[9];
            for(int i = 0; i < 9; ++i) {
                sudokuSquares[i] = new SudokuSquare(index, i, background);
                add(sudokuSquares[i]);
            }
        }

        public void update() {
            for(var square : sudokuSquares) {
                square.update();
            }
        }
    }

    private class SudokuSquare extends JPanel implements MouseListener, KeyListener {
        private final JLabel label = new JLabel();
        public final int row;
        public final int column;

        public final Color background;

        public void update() {
            label.setText(Integer.toString(m_solver.getMatrix()[row][column]));
        }

        public SudokuSquare(int subGrid, int index, Color c) {
            super();
            row = (subGrid / 3) * 3 + index / 3;
            column = (subGrid % 3) * 3 + index % 3;

            background = c;
            setBackground(c);
            setBorder(BorderFactory.createLineBorder(Color.black));

            setAlignmentX(CENTER_ALIGNMENT);
            setAlignmentY(CENTER_ALIGNMENT);
            label.setText(Integer.toString(m_solver.getMatrix()[row][column]));
            label.setHorizontalAlignment(CENTER);
            label.setFont(getFont().deriveFont(20.0f));

            this.addMouseListener(this);
            this.addKeyListener(this);
            setFocusable(true);
            add(label);
        }

        private void deselect() {
            setBackground(background);
            m_selected = null;
        }

        private void select() {
            if(m_selected != null) {
                m_selected.deselect();
            }

            setBackground(Color.blue);
            m_selected = this;
            requestFocus();
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            select();
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            deselect();
        }

        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if(m_selected == this) {
                var key = keyEvent.getKeyChar() - '0';
                if(key < 0 || key > 9) {
//                    wrong_input_warning();
                }
                else {
                    m_solver.set(row + 1, column + 1, key);
                    label.setText(Integer.toString(key));
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }
}
