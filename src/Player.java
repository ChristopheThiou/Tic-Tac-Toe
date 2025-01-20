import java.util.Random;

public class Player {
    protected String symbol;
    protected String name;
    protected boolean isArtificial;

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

    public int[] getMove(TicTacToe game) {
        int[] move;
        if (isArtificial) {
            move = generateRandomPosition(game);
        } else {
            move = game.interactionUtilisateur.getMoveFromPlayer(this, game);
        }
        return move;
    }

    private int[] generateRandomPosition(TicTacToe game) {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(game.size - 1);
            col = random.nextInt(game.size - 1);
        } while (!game.isValidMove(row, col));
        return new int[]{row, col};
    }
}