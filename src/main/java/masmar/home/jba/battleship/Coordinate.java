package masmar.home.jba.battleship;

import java.util.Objects;

class Coordinate {
    private final char row;
    private final int column;

    public Coordinate(String coordinates) {
        if (invalidLength(coordinates) || invalidRow(coordinates.charAt(0)) || invalidColumn(coordinates.substring(1))) {
            throw new IllegalArgumentException("Incorrect input");
        }
        this.row = coordinates.charAt(0);
        this.column = Integer.parseInt(coordinates.substring(1));
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
