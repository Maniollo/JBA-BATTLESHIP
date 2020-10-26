package masmar.home.jba.battleship;

import java.util.List;
import java.util.Scanner;

class SinglePlayer {

    private static final Scanner SCANNER = new Scanner(System.in);
    private final Board board = Board.create();

    void play() {
        fleetSetup();
        playSingle();
    }

    private void fleetSetup() {
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

    private void playSingle() {
        System.out.println("The game starts!\n");
        System.out.println(board.displayMasked());
        System.out.println("\nTake a shot!");
        ShotResult shotResult = ShotResult.MISSED;
        do {
            boolean invalidAttempt = true;
            do {
                try {
                    String next = SCANNER.next();
                    shotResult = board.shot(new Coordinate(next));
                    System.out.println("\n" + board.displayMasked() + "\n");
                    System.out.println(getMessage(shotResult));
                    invalidAttempt = false;
                } catch (Exception e) {
                    System.out.println("Error!: " + e.getMessage() + " Try again:");
                }
            } while (invalidAttempt);
        } while (shotResult != ShotResult.ALL_SANK);
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
