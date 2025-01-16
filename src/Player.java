public class Player {
    protected String symbol;
    protected String name;

    public Player(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public static class Player1 extends Player {
        public Player1() {
            super("| X ", "Joueur 1");
        }
    }

    public static class Player2 extends Player {
        public Player2() {
            super("| O ", "Joueur 2");
        }
    }
}