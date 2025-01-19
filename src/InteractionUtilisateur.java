import java.util.InputMismatchException;
import java.util.Scanner;

public class InteractionUtilisateur {
    protected Scanner scanner;
    protected Vue vue;

    protected InteractionUtilisateur() {
        scanner = new Scanner(System.in);
        vue = new Vue();
    }

    protected int[] getMoveFromPlayer(Player player, TicTacToe game) {
        int row = -1, col = -1;
        while (true) {
            try {
                vue.afficherMessage(player.getName() + ", entrez le numéro de ligne (0 à 2) ou 404 pour quitter: ");
                row = scanner.nextInt();
                if (row == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }
                vue.afficherMessage(player.getName() + ", entrez le numéro de colonne (0 à 2) ou 404 pour quitter: ");
                col = scanner.nextInt();
                if (col == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }

                if (game.isValidMove(row, col)) {
                    break;
                } else {
                    vue.afficherMessage("Mouvement invalide. La case est déjà occupée ou hors des limites. Veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                vue.afficherMessage("Entrée invalide. Veuillez entrer un nombre.");
                scanner.next();
            }
        }
        return new int[]{row, col};
    }

    protected int getGameMode() {
        int choice = -1;
        while (true) {
            try {
                vue.afficherMessage("Choisissez le mode de jeu: ");
                vue.afficherMessage("1. Joueur vs Joueur");
                vue.afficherMessage("2. Joueur vs IA");
                vue.afficherMessage("3. IA vs IA");
                vue.afficherMessage("404. Pour quitter");
                choice = scanner.nextInt();
                if (choice == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }

                if (choice >= 1 && choice <= 3) {
                    break;
                } else {
                    vue.afficherMessage("Choix invalide. Veuillez entrer un nombre entre 1 et 3.");
                }
            } catch (InputMismatchException e) {
                vue.afficherMessage("Entrée invalide. Veuillez entrer un nombre.");
                scanner.next();
            }
        }
        return choice;
    }

    protected void playWithAI(TicTacToe game) {
        vue.afficherMessage("Bienvenue dans le jeu Tic Tac Toe!");
        vue.afficherMessage("Joueur 1 avec X et IA avec O");

        Player currentPlayer = game.player1;
        while (true) {
            vue.display(game.board, TicTacToe.size);
            if (game.isBoardFull()) {
                vue.afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                break;
            }
            int[] move = currentPlayer.getMove(game);
            game.setOwner(move[0], move[1], currentPlayer);
            vue.afficherMessage(currentPlayer.getName() + " joue en position: (" + move[0] + ", " + move[1] + ")");
            if (game.isOver()) {
                vue.display(game.board, TicTacToe.size);
                vue.afficherMessage("Le jeu est terminé! " + currentPlayer.getName() + " a gagné!");
                break;
            }
            currentPlayer = (currentPlayer == game.player1) ? game.player2 : game.player1;
        }
    }

    protected void playAIvsAI(TicTacToe game) {
        vue.afficherMessage("Bienvenue dans le jeu Tic Tac Toe!");
        vue.afficherMessage("IA 1 avec X et IA 2 avec O");

        Player currentPlayer = game.player1;
        while (true) {
            vue.display(game.board, TicTacToe.size);
            if (game.isBoardFull()) {
                vue.afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                break;
            }
            int[] move = currentPlayer.getMove(game);
            game.setOwner(move[0], move[1], currentPlayer);
            vue.afficherMessage(currentPlayer.getName() + " joue en position: (" + move[0] + ", " + move[1] + ")");
            if (game.isOver()) {
                vue.display(game.board, TicTacToe.size);
                vue.afficherMessage("Le jeu est terminé! " + currentPlayer.getName() + " a gagné!");
                break;
            }
            currentPlayer = (currentPlayer == game.player1) ? game.player2 : game.player1;
        }
    }
}