package main.java.edaa30;

public class Main {
    public static void main(String[] args) {
        var gui = new SudokuGUI(new Sudoku());
        gui.run();
    }
}