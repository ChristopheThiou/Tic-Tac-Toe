import java.util.Random;

public class Player {
    private String symbol;
    private String name;
    private boolean isArtificial;

    public Player(String symbol, String name, boolean isArtificial) {
        this.symbol = symbol;
        this.name = name;
        this.isArtificial = isArtificial;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public boolean isArtificial() {
        return isArtificial;
    }

    protected int[] getMove(BoardGame game) {
        if (isArtificial) {
            return generateRandomPosition(game);
        } else {
            return game.interactionUtilisateur.getMoveFromPlayer(this, game);
        }
    }

    private int[] generateRandomPosition(BoardGame game) {
        Random random = new Random();
        int row, col;
        if (game instanceof TicTacToe) {
            TicTacToe ticTacToe = (TicTacToe) game;
            do {
                row = random.nextInt(ticTacToe.row);
                col = random.nextInt(ticTacToe.col);
            } while (!ticTacToe.isValidMove(row, col));
            return new int[]{row, col};
        } else if (game instanceof PuissanceQuatre) {
            PuissanceQuatre puissanceQuatre = (PuissanceQuatre) game;
            do {
                col = random.nextInt(puissanceQuatre.col);
            } while (!puissanceQuatre.isValidMove(0, col));
            return new int[]{0, col}; // Ajout de 0 pour la ligne
        } else if (game instanceof Gomoku) {
            Gomoku gomoku = (Gomoku) game;
            do {
                row = random.nextInt(gomoku.row);
                col = random.nextInt(gomoku.col);
            } while (!gomoku.isValidMove(row, col));
            return new int[]{row, col};
        }
        return null;
    }
}