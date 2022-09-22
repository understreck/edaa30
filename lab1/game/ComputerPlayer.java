package lab1.game;

import java.util.Random;

public class ComputerPlayer extends Player {
    protected static Random random = new Random();

    ComputerPlayer(String name) {
        super(name);
    }

    public int takePins(Board b) {
        var i = random.nextInt(1, 3);
        UserInterface.printMessage(getUserId() + " takes: " + i + " pins");
        b.takePins(i);
        return b.getNrPins();
    }
}
