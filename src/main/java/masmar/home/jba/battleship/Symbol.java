package masmar.home.jba.battleship;

public enum Symbol {
    FOG("~"), SHIP("O"), HIT("X"), MISS("M");

    private final String symbol;

    Symbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
