package masmar.home.jba.battleship;

import java.util.List;
import java.util.Scanner;

class PvP {
    private static final Scanner SCANNER = new Scanner(System.in);
    private final Board playerOne = Board.create();
    private final Board playerTwo = Board.create();
    private boolean firstActive = true;

    private static void fleetSetup(Board board) {
        List<Ship> fleet = List.of(
                new Ship(Type.AIRCRAFT_CARRIER),
                new Ship(Type.BATTLESHIP),
                new Ship(Type.SUBMARINE),
                new Ship(Type.CRUISER),
                new Ship(Type.DESTROYER));
        System.out.println(board.display());

        for (Ship ship : fleet) {
            boolean notFill = true;
            System.out.println("\nEnter the coordinates of the " + ship.getName() + " (" + ship.getLength() + " cells):");
            do {
                try {
                    String[] pointsInput = SCANNER.nextLine().trim().split(" ");
                    ship.setCoordinates(new Coordinate(pointsInput[0]), new Coordinate(pointsInput[1]));
                    board.addShip(ship);
                    notFill = false;
                } catch (Exception e) {
                    System.out.println("Error!: " + e.getMessage() + " Try again:");
                }
            } while (notFill);

            System.out.println("\n" + board.display());
        }
    }

    void play() {
        System.out.println("Player 1, place your ships on the game field");
        fleetSetup(playerOne);
        System.out.println("Press Enter and pass the move to another player");
        String command = SCANNER.nextLine();
        if (command.isEmpty()) {
            firstActive = !firstActive;
            System.out.println("Player 2, place your ships on the game field");
            fleetSetup(playerTwo);
        }
        System.out.println("Press Enter and pass the move to another player");
        GameResult gameResult = GameResult.ON_GOING;
        do {
            command = SCANNER.nextLine();
            if (command.isEmpty()) {
                firstActive = !firstActive;
                gameResult = firstActive
                        ? shot(playerOne, playerTwo, firstActive)
                        : shot(playerTwo, playerOne, firstActive);
            }
        } while (gameResult == GameResult.ON_GOING);

    }

    private GameResult shot(Board firstPlayer, Board opponent, boolean firstActive) {
        System.out.println(opponent.displayMasked());
        System.out.println("---------------------");
        System.out.println(firstPlayer.display());
        String prompt = firstActive ? "\nPlayer 1, it's your turn:" : "\nPlayer 2, it's your turn:";
        System.out.println(prompt);
        boolean invalidAttempt = true;
        GameResult gameResult = GameResult.ON_GOING;
        do {
            try {
                String next = SCANNER.nextLine();
                ShotResult shotResult = opponent.shot(new Coordinate(next));
                System.out.println(getMessage(shotResult));
                gameResult = shotResult == ShotResult.ALL_SANK ? GameResult.FINISHED : GameResult.ON_GOING;
                invalidAttempt = false;
                if (gameResult == GameResult.ON_GOING) {
                    System.out.println("Press Enter and pass the move to another player");
                }
            } catch (Exception e) {
                System.out.println("Error!: " + e.getMessage() + " Try again:");
            }
        } while (invalidAttempt);
        return gameResult;
    }

    private String getMessage(ShotResult shotResult) {
        switch (shotResult) {
            case HIT:
                return "You hit a ship!";
            case MISSED:
                return "You missed!";
            case SANK:
                return "You sank a ship! Specify a new target:";
            case ALL_SANK:
            default:
                return "You sank the last ship. You won. Congratulations!";
        }
    }
}
