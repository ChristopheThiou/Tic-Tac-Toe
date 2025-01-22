package Main.PuissanceQuatreGame;

import Main.BoardGame;
import Main.Cell;
import Main.InteractionUtilisateur;
import Main.Player;
import Main.Vue;
import java.util.InputMismatchException;
import java.util.Random;



public class PuissanceQuatre extends BoardGame {

    public final int row = 6;
    public final int col = 7;
    public Cell[][] board;

    Player player1;
    Player player2;

    public PuissanceQuatre(Vue vue, InteractionUtilisateur interactionUtilisateur) {
        super(vue, interactionUtilisateur);
        board = new Cell[row][col];
        interactionUtilisateur = new InteractionUtilisateur();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = new Cell();
            }
        }
        player1 = new Player("| 🔴 ", "Joueur 1", false);
        player2 = new Player("| 🟡 ", "Joueur 2", false);
    }

    @Override
    public void play() {
        vue.afficherMessage("Bienvenue dans le jeu Puissance 4! 🤗");
        vue.afficherMessage("Joueur 1 avec 🔴 et Joueur 2 avec 🟡");
        vue.afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");

        boolean gameOver = false;
        Player currentPlayer = player1;

        while (!gameOver) {
            vue.display(board);
            int col = currentPlayer.getMove(this)[1];

            if (placerJeton(currentPlayer, col)) {
                if (verifierVictoire()) {
                    vue.display(board);
                    vue.afficherMessage(currentPlayer.getName() + " a gagné!");
                    gameOver = true;
                } else if (isBoardFull()) {
                    vue.display(board);
                    vue.afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                    gameOver = true;
                } else {
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                }
            } 
        }
    }

    private boolean placerJeton(Player player, int col) {
        if (col < 0 || col >= this.col) {
            System.out.println("Colonne hors limites : " + col);
            return false;
        }

        for (int i = row - 1; i >= 0; i--) {
            if (board[i][col].isEmpty()) {
                board[i][col].setOwner(player);
                System.out.println("Jeton placé en [" + i + "][" + col + "]");
                return true;
            }
        }

        System.out.println("Colonne pleine : " + col + ". ou hors limites impossible de placer le jeton.");
        return false;
    }

    private boolean verifierVictoire() {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col - 3; j++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i][j + 1].getOwner() &&
                    board[i][j + 1].getOwner() == board[i][j + 2].getOwner() &&
                    board[i][j + 2].getOwner() == board[i][j + 3].getOwner()) {
                    return true;
                }
            }
        }

        for (int i = 0; i < row - 3; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i + 1][j].getOwner() &&
                    board[i + 1][j].getOwner() == board[i + 2][j].getOwner() &&
                    board[i + 2][j].getOwner() == board[i + 3][j].getOwner()) {
                    return true;
                }
            }
        }

        for (int i = 0; i < row - 3; i++) {
            for (int j = 0; j < col - 3; j++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i + 1][j + 1].getOwner() &&
                    board[i + 1][j + 1].getOwner() == board[i + 2][j + 2].getOwner() &&
                    board[i + 2][j + 2].getOwner() == board[i + 3][j + 3].getOwner()) {
                    return true;
                }
            }
        }

        for (int i = 3; i < row; i++) {
            for (int j = 0; j < col - 3; j++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i - 1][j + 1].getOwner() &&
                    board[i - 1][j + 1].getOwner() == board[i - 2][j + 2].getOwner() &&
                    board[i - 2][j + 2].getOwner() == board[i - 3][j + 3].getOwner()) {
                    return true;
                }
            }
        }
    
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void gameMode() {
        int choice = interactionUtilisateur.getGameMode();
        switch (choice) {
            case 1:
                play();
                break;
            case 2:
                player2 = new Player("| 🟡 ", "AI", true);
                play();
                break;
            case 3:
                player1 = new Player("| 🔴 ", "AI 1", true);
                player2 = new Player("| 🟡 ", "AI 2", true);
                play();
                break;
            default:
                vue.afficherMessage("Choix invalide. Veuillez réessayer. 👺");
                gameMode();
        }
    }

    @Override
    public boolean isValidMove(int row, int col) {
        if (col < 0 || col >= this.col) {
            return false;
        }
        return board[0][col].isEmpty();
    }

    @Override
    public int[] generateRandomPosition() {
        Random random = new Random();
        int col;
        do {
            col = random.nextInt(7);
        } while (!isValidMove(0, col));
        return new int[]{0, col};
    }

    @Override
    public int[] getMoveFromPlayer(Player player) {
        int col = 0;
        while (true) {
            try {
                vue.afficherMessage(player.getName() + ", entrez le numéro de colonne (0 à 6) ou 404 pour quitter: ");
                col = interactionUtilisateur.scanner.nextInt();
                if (col == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }

                if (isValidMove(0, col)) {
                    break;
                } else {
                    vue.afficherMessage("Mouvement invalide. La colonne est pleine ou hors des limites. Veuillez réessayer. 💩");
                }
            } catch (InputMismatchException e) {
                vue.afficherMessage("Entrée invalide. Veuillez entrer un nombre.");
                interactionUtilisateur.scanner.next();
            }
        }
        return new int[]{0, col};
    }
}