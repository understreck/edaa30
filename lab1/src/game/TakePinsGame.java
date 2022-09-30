package game;

class TakePinsGame {
    public static void main(String[] args) {
        var i = -1;
        while (i < 0 || i > 2) {
            System.out.println(i);
            i = UserInterface.askForInt("0, 1 or 2 human players?");
        }

        var players = new Player[2];
        switch (i) {
            case 0: {
                players[0] = new ComputerPlayer("Computer 1");
                players[1] = new ComputerPlayer("Computer 2");
                break;
            }
            case 1: {
                players[0] = new HumanPlayer("Player 1");
                players[1] = new ComputerPlayer2("Computer 1");
                break;
            }
            case 2: {
                players[0] = new HumanPlayer("Player 1");
                players[1] = new HumanPlayer("Player 2");
                break;
            }
        }

        var board = new Board();
        board.setUp(UserInterface.askForInt("How many pins to start?"));

        var currentPlayer = players[1];
        while (board.getNrPins() != 0) {
            currentPlayer = players[(currentPlayer == players[0]) ? 1 : 0];
            currentPlayer.takePins(board);

            UserInterface.printMessage(
                    "There are " + board.getNrPins() + " pins left");
        }

        UserInterface.printMessage(
                "Congratulations, " + currentPlayer.getUserId() + " won");
    }
}
