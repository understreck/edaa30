package lab1.game;

import java.util.Random;

public class ComputerPlayer2 extends Player {
    protected static Random random = new Random();

    ComputerPlayer2(String name) {
        super(name);
    }

    public int takePins(Board b) {
        var i = b.getNrPins() % 3;
        UserInterface.printMessage(getUserId() + " takes: " +
                (i == 0 ? 1 : i) + " " +
                "pins");
        b.takePins(i);
        return b.getNrPins();
    }
}
