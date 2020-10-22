package masmar.home.jba.battleship;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        gameSetup();
    }

    private static void gameSetup() {
        Board board = Board.create();
        Scanner scanner = new Scanner(System.in);
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
                    String[] pointsInput = scanner.nextLine().trim().split(" ");
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
}
