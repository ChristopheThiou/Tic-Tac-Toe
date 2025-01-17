public class Player extends AbstractPlayer {
    public Player(String symbol, String name) {
        super(symbol, name);
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