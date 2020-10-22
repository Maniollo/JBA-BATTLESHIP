package masmar.home.jba.battleship;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        Board board = Board.create();
        fleetSetup(board);
        play(board);
    }

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

    private static void play(Board board) {
        System.out.println("The game starts!\n");
        System.out.println(board.displayMasked());
        System.out.println("\nTake a shot!");
        boolean invalidAttempt = true;
        do {
            try {
                String next = SCANNER.next();
                ShotResult shotResult = board.shot(new Coordinate(next));
                System.out.println("\n" + board.displayMasked() + "\n");
                String msg = shotResult == ShotResult.HIT ? "You hit a ship!" : "You missed!";
                System.out.println(msg);
                System.out.println("\n" + board.display() + "\n");
                invalidAttempt = false;
            } catch (Exception e) {
                System.out.println("Error!: " + e.getMessage() + " Try again:");
            }
        } while (invalidAttempt);
    }
}
