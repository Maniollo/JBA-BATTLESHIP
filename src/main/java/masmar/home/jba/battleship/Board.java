package masmar.home.jba.battleship;

import java.util.List;

class Board {
    private final Symbol[][] board;
    private final Symbol[][] maskedBoard;

    private Board(Symbol[][] board, Symbol[][] maskedBoard) {
        this.board = board;
        this.maskedBoard = maskedBoard;
    }

    public static Board create() {
        Symbol[][] board = new Symbol[10][10];
        Symbol[][] maskedBoard = new Symbol[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = Symbol.FOG;
                maskedBoard[i][j] = Symbol.FOG;
            }
        }
        return new Board(board, maskedBoard);
    }

    public String display() {
        return asString(board);
    }

    public String displayMasked() {
        return asString(maskedBoard);
    }

    public void addShip(Ship ship) {
        List<Coordinate> coordinates = ship.getCoordinates();
        if (failNeighbourhoodCheck(coordinates)) {
            throw new IllegalStateException("You placed it too close to another one.");
        }
        for (Coordinate coordinate : coordinates) {
            board[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] = Symbol.SHIP;
        }
    }

    public ShotResult shot(Coordinate coordinate) {
        if (board[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] == Symbol.SHIP) {
            board[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] = Symbol.HIT;
            maskedBoard[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] = Symbol.HIT;
            return ShotResult.HIT;
        } else {
            board[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] = Symbol.MISS;
            maskedBoard[coordinate.getRowAsNumber()][coordinate.getColumn() - 1] = Symbol.MISS;
            return ShotResult.MISSED;
        }
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
        for (Coordinate coordinate : coordinates) {
            int x = coordinate.getRowAsNumber();
            int y = coordinate.getColumn() - 1;

            if (board[x][y] != Symbol.FOG) {
                return true;
            }

            if (x > 0) { // UP
                if (board[x - 1][y] != Symbol.FOG) {
                    return true;
                }

                if (y < 9) { // UP LEFT
                    if (board[x - 1][y + 1] != Symbol.FOG) {
                        return true;
                    }
                }

                if (y > 0) { // UP LEFT
                    if (board[x - 1][y - 1] != Symbol.FOG) {
                        return true;
                    }
                }
            }

            if (y < 9) { // LEFT
                if (board[x][y + 1] != Symbol.FOG) {
                    return true;
                }
            }

            if (x < 9) { // DOWN
                if (board[x + 1][y] != Symbol.FOG) {
                    return true;
                }

                if (y < 9) { // DOWN LEFT
                    if (board[x + 1][y + 1] != Symbol.FOG) {
                        return true;
                    }
                }

                if (y > 0) { // DOWN LEFT
                    if (board[x + 1][y - 1] != Symbol.FOG) {
                        return true;
                    }
                }
            }

            if (y > 0) { // RIGHT
                if (board[x][y - 1] != Symbol.FOG) {
                    return true;
                }
            }
        }
        return false;
    }
}
