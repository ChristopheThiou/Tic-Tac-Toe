package Main;


public class Player {
    private String symbol;
    private String name;
    private boolean isArtificial;
    public int difficultyLevel;

    public Player(String symbol, String name, boolean isArtificial) {
        this(symbol, name, isArtificial, 1);
    }

    public Player(String symbol, String name, boolean isArtificial, int difficultyLevel) {
        this.symbol = symbol;
        this.name = name;
        this.isArtificial = isArtificial;
        this.difficultyLevel = difficultyLevel;
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

    public int[] getMove(BoardGame game) {
        if (isArtificial) {
            return generateRandomPosition(game);
        } else {
            return game.interactionUtilisateur.getMoveFromPlayer(this, game);
        }
    }

    private int[] generateRandomPosition(BoardGame game) {
        return game.generateRandomPosition();
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }
}