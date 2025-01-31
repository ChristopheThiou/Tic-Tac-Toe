package main.vue;

import main.games.BoardGame;

public class Vue {
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void messageAccueil(BoardGame game) {
        afficherMessage("Bienvenue dans le jeu " + game.getGameName() + "! 🤗");
    }

    public void symbolJoueurs(BoardGame game) {
        afficherMessage(game.getPlayer1().getName() + " avec " + game.getPlayer1().getSymbol() + " et " + game.getPlayer2().getName() + " avec " + game.getPlayer2().getSymbol());
    }

    public void endGameMessage(BoardGame game) {
        afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");
    }

    public void display(Cell[][] board) {
        StringBuilder representation = new StringBuilder();
        int rows = board.length;
        int cols = board[0].length;

        representation.append("    ");
        for (int j = 0; j < cols; j++) {
            representation.append(String.format("%-3d", j));
        }
        representation.append("\n");

        for (int i = 0; i < rows; i++) {
            representation.append("   ").append("---".repeat(cols)).append("-\n");

            representation.append(String.format("%-3d", i));

            for (int j = 0; j < cols; j++) {
                representation.append(board[i][j].getRepresentation());
            }
            representation.append("|\n");
        }

        representation.append("   ").append("---".repeat(cols)).append("-\n");

        afficherMessage(representation.toString());
    }
}