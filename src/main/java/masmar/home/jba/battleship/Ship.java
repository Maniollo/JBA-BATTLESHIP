package masmar.home.jba.battleship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Ship {
    private final int length;
    private final String name;
    private List<Coordinate> coordinates;

    Ship(Type type) {
        this.length = type.getLength();
        this.name = type.getName();
        this.coordinates = new ArrayList<>();
    }

    String getName() {
        return name;
    }

    int getLength() {
        return length;
    }

    List<Coordinate> getCoordinates() {
        return Collections.unmodifiableList(coordinates);
    }

    void setCoordinates(Coordinate start, Coordinate end) {
        List<Coordinate> coordinates = new ArrayList<>();
        if (incorrectLength(start, end)) {
            throw new IllegalArgumentException("Wrong length of the " + name + "!");
        }
        for (int i = 0; i < length; i++) {
            if (start.getRowAsNumber() == end.getRowAsNumber()) {
                char row = start.getRow();
                int column = start.getColumn() < end.getColumn() ? start.getColumn() + i : end.getColumn() + i;
                coordinates.add(new Coordinate("" + row + column));
            } else if (start.getColumn() == end.getColumn()) {
                char row = (char) (start.getRowAsNumber() < end.getRowAsNumber() ? start.getRow() + i : end.getRow() + i);
                int column = start.getColumn();
                coordinates.add(new Coordinate("" + row + column));
            } else {
                throw new RuntimeException();
            }
        }
        this.coordinates = new ArrayList<>(coordinates);
    }

    private boolean incorrectLength(Coordinate start, Coordinate end) {
        return start.getRowAsNumber() != end.getRowAsNumber() && start.getColumn() != end.getColumn()
                || Math.abs(end.getRowAsNumber() - start.getRowAsNumber()) != length - 1 && Math.abs(end.getRowAsNumber() - start.getRowAsNumber()) != 0
                || Math.abs(end.getColumn() - start.getColumn()) != length - 1 && Math.abs(end.getColumn() - start.getColumn()) != 0;
    }
}
