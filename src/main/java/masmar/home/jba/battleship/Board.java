package masmar.home.jba.battleship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

class Board {
    private final Map<String, List<Coordinate>> ships;
    private final List<Coordinate> hits;
    private final List<Coordinate> misses;

    private Board() {
        this.ships = new HashMap<>();
        this.hits = new ArrayList<>();
        this.misses = new ArrayList<>();
    }

    public static Board create() {
        return new Board();
    }

    private static Symbol[][] emptyBoard() {
        Symbol[][] maskedBoard = new Symbol[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                maskedBoard[i][j] = Symbol.FOG;
            }
        }
        return maskedBoard;
    }

    public String display() {
        Symbol[][] board = emptyBoard();
        hits.forEach(coordinate -> board[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] = Symbol.HIT);
        misses.forEach(coordinate -> board[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] = Symbol.MISS);
        getAllShipsCoordinates().forEach(coordinate -> board[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] = Symbol.SHIP);

        return asString(board);
    }

    public String displayMasked() {
        Symbol[][] board = emptyBoard();
        hits.forEach(coordinate -> board[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] = Symbol.HIT);
        misses.forEach(coordinate -> board[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] = Symbol.MISS);
        return asString(board);
    }

    public void addShip(Ship ship) {
        List<Coordinate> coordinates = ship.getCoordinates();
        if (failNeighbourhoodCheck(coordinates)) {
            throw new IllegalStateException("You placed it too close to another one.");
        }
        ships.put(ship.getName(), coordinates);
    }

    public ShotResult shot(Coordinate coordinate) {
        List<Coordinate> ships = getAllShipsCoordinates();

        if (ships.contains(coordinate) || hits.contains(coordinate)) {
            hits.add(coordinate);

            Optional<String> maybeShipName = this.ships.entrySet().stream()
                    .filter(entry -> entry.getValue().stream().anyMatch(shipCoordinate -> shipCoordinate.equals(coordinate)))
                    .map(Map.Entry::getKey)
                    .findFirst();

            if (maybeShipName.isEmpty()) {
                return ShotResult.HIT;
            } else {
                String shipName = maybeShipName.get();
                List<Coordinate> updated = this.ships.get(shipName).stream()
                        .filter(coordinate1 -> !coordinate1.equals(coordinate))
                        .collect(toList());
                if (updated.isEmpty()) {
                    this.ships.remove(shipName);
                    return this.ships.isEmpty() ? ShotResult.ALL_SANK : ShotResult.SANK;
                } else {
                    this.ships.replace(shipName, updated);
                    return ShotResult.HIT;
                }
            }
        } else {
            misses.add(coordinate);
            return ShotResult.MISSED;
        }
    }

    private List<Coordinate> getAllShipsCoordinates() {
        return this.ships.values().stream()
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private String asString(Symbol[][] board) {
        StringBuilder asString = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < 10; i++) {
            asString.append((char) ('A' + i)).append(" ");
            for (int j = 0; j < 10; j++) {
                asString.append(board[i][j].getSymbol());
                if (j == 9) {
                    asString.append("\n");
                } else {
                    asString.append(" ");
                }
            }
        }
        asString.deleteCharAt(asString.length() - 1);
        return asString.toString();
    }

    private boolean failNeighbourhoodCheck(List<Coordinate> coordinates) {
        List<Coordinate> ships = getAllShipsCoordinates();
        return coordinates.stream()
                .anyMatch(coordinate -> ships.contains(coordinate) ||
                        coordinate.getNeighbours().stream().anyMatch(ships::contains));
    }
}
