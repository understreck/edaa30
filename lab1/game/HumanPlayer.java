package lab1.game;

public class HumanPlayer extends Player {
    HumanPlayer(String name) {
        super(name);
    }

    public int takePins(Board b) {
        try {
            b.takePins(UserInterface.askForInt(getUserId() +
                    ", take one or two pins: "));
            return b.getNrPins();
        } catch (IllegalArgumentException i) {
            UserInterface.printMessage(i.getMessage());
            return takePins(b);
        }
    }
}
