package main.games;

import java.util.Random;
import main.vue.Vue;


public class Player {
    private final String symbol;
    private final String name;
    public final boolean isArtificial;
    private final int difficultyLevel;

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

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public static Player randomizeFirstPlayer(Player player1, Player player2, Vue vue) {
        Random random = new Random();
        Player currentPlayer = random.nextBoolean() ? player1 : player2;
        vue.afficherMessage(currentPlayer.getName() + " commence en premier!");
        return currentPlayer;
    }
}