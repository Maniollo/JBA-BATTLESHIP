package masmar.home.jba.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Coordinate {
    private final char row;
    private final int column;

    public Coordinate(String coordinates) {
        if (invalidLength(coordinates) || invalidRow(coordinates.charAt(0)) || invalidColumn(coordinates.substring(1))) {
            throw new IllegalArgumentException("You entered the wrong coordinates!");
        }
        this.row = coordinates.charAt(0);
        this.column = Integer.parseInt(coordinates.substring(1));
    }

    private Coordinate(char row, int column) {
        this.row = row;
        this.column = column;
    }

    public char getRow() {
        return row;
    }

    public int getRowAsNumber() {
        return row - 65;
    }

    public int getColumn() {
        return column;
    }

    public List<Coordinate> getNeighbours() {
        List<Coordinate> neighbours = new ArrayList<>();
        if (row > 'A') {
            char upperRow = (char) (this.row - 1);
            neighbours.add(new Coordinate(upperRow, column));
            if (column < 10) {
                neighbours.add(new Coordinate(upperRow, column + 1));
            }

            if (column > 1) {
                neighbours.add(new Coordinate(upperRow, column - 1));
            }
        }

        if (column < 10) {
            neighbours.add(new Coordinate(row, column + 1));
        }
        if (row < 'J') {
            char lowerRow = (char) (this.row + 1);
            neighbours.add(new Coordinate(lowerRow, column));

            if (column < 10) {
                neighbours.add(new Coordinate(lowerRow, column + 1));
            }

            if (column > 1) {
                neighbours.add(new Coordinate(lowerRow, column - 1));
            }

        }

        if (column > 1) {
            neighbours.add(new Coordinate(row, column - 1));
        }
        return neighbours;
    }

    private boolean invalidLength(String coordinates) {
        return coordinates.length() < 2 || coordinates.length() > 3;
    }

    private boolean invalidColumn(String column) {
        int i = Integer.parseInt(column) - 1;
        return i < 0 || i > 10;
    }

    private boolean invalidRow(char row) {
        return row < 'A' || row > 'J';
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row &&
                column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
