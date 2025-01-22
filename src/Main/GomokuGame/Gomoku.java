package Main.GomokuGame;

import Main.BoardGame;
import Main.Cell;
import Main.InteractionUtilisateur;
import Main.Player;
import Main.Vue;
import java.util.InputMismatchException;
import java.util.Random;



public class Gomoku extends BoardGame {


    public final int row = 15;
    public final int col = 15;
    public Cell[][] board;
    Player player1;
    Player player2;

    public Gomoku(Vue vue, InteractionUtilisateur interactionUtilisateur) {
        super(vue, interactionUtilisateur);
        board = new Cell[row][col];
        interactionUtilisateur = new InteractionUtilisateur();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = new Cell();
            }
        }
        player1 = new Player("| ⚪ ", "Joueur 1", false);
        player2 = new Player("| 🟤 ", "Joueur 2", false);
    }

    @Override
    public void play() {
        vue.afficherMessage("Bienvenue dans le jeu Gomoku! 🤗");
        vue.afficherMessage("Joueur 1 avec ⚪ et Joueur 2 avec 🟤");
        vue.afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");

        Player currentPlayer = player1;
        while (true) {

            vue.display(board);

            int[] move = currentPlayer.getMove(this);
            setOwner(move[0], move[1], currentPlayer);
            vue.afficherMessage(currentPlayer.getName() + " joue en position: (" + move[0] + ", " + move[1] + ")");

            if (isOver()) {
                vue.display(board);
                vue.afficherMessage("Le jeu est terminé! " + currentPlayer.getName() + " a gagné! 🔆👌");
                break;
            }

            if (isBoardFull()) {
                vue.display(board);
                vue.afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                break;
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }

    protected void setOwner(int row, int col, Player player) {
        if (isValidMove(row, col)) {
            board[row][col].setOwner(player);
        } 
    }

    protected boolean isBoardFull() {
        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < col ; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean isOver() {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j <= col - 5; j++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i][j + 1].getOwner() &&
                    board[i][j + 1].getOwner() == board[i][j + 2].getOwner() &&
                    board[i][j + 2].getOwner() == board[i][j + 3].getOwner() &&
                    board[i][j + 3].getOwner() == board[i][j + 4].getOwner()) {
                    return true;
                }
            }
        }

        for (int j = 0; j < col; j++) {
            for (int i = 0; i <= row - 5; i++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i + 1][j].getOwner() &&
                    board[i + 1][j].getOwner() == board[i + 2][j].getOwner() &&
                    board[i + 2][j].getOwner() == board[i + 3][j].getOwner() &&
                    board[i + 3][j].getOwner() == board[i + 4][j].getOwner()) {
                    return true;
                }
            }
        }

        for (int i = 0; i <= row - 5; i++) {
            for (int j = 0; j <= col - 5; j++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i + 1][j + 1].getOwner() &&
                    board[i + 1][j + 1].getOwner() == board[i + 2][j + 2].getOwner() &&
                    board[i + 2][j + 2].getOwner() == board[i + 3][j + 3].getOwner() &&
                    board[i + 3][j + 3].getOwner() == board[i + 4][j + 4].getOwner()) {
                    return true;
                }
            }
        }

        for (int i = 0; i <= row - 5; i++) {
            for (int j = 4; j < col; j++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i + 1][j - 1].getOwner() &&
                    board[i + 1][j - 1].getOwner() == board[i + 2][j - 2].getOwner() &&
                    board[i + 2][j - 2].getOwner() == board[i + 3][j - 3].getOwner() &&
                    board[i + 3][j - 3].getOwner() == board[i + 4][j - 4].getOwner()) {
                    return true;
                }
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j].getOwner() == null) {
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
                player2 = new Player("| 🟤 ", "AI", true);
                play();
                break;
            case 3:
                player1 = new Player("| ⚪ ", "AI 1", true);
                player2 = new Player("| 🟤 ", "AI 2", true);
                play();
                break;
            default:
                vue.afficherMessage("Choix invalide. Veuillez réessayer. 👺");
                gameMode();
        }
    }

    @Override
    public boolean isValidMove(int row, int col) {
        if (row < 0 || row >= this.row || col < 0 || col >= this.col) {
            return false;
        }
        return board[row][col].isEmpty();
    }

    @Override
    public int[] generateRandomPosition() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(15);
            col = random.nextInt(15);
        } while (!isValidMove(row, col));
        return new int[]{row, col};
    }

    @Override
    public int[] getMoveFromPlayer(Player player) {
        int row = 0, col = 0;
        while (true) {
            try {
                vue.afficherMessage(player.getName() + ", entrez le numéro de ligne (0 à 14) ou 404 pour quitter: ");
                row = interactionUtilisateur.scanner.nextInt();
                if (row == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }
                vue.afficherMessage(player.getName() + ", entrez le numéro de colonne (0 à 14) ou 404 pour quitter: ");
                col = interactionUtilisateur.scanner.nextInt();
                if (col == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }

                if (isValidMove(row, col)) {
                    break;
                } else {
                    vue.afficherMessage("Mouvement invalide. La case est déjà occupée ou hors des limites. Veuillez réessayer. 💩");
                }
            } catch (InputMismatchException e) {
                vue.afficherMessage("Entrée invalide. Veuillez entrer un nombre.");
                interactionUtilisateur.scanner.next();
            }
        }
        return new int[]{row, col};
    }
}
