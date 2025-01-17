import java.util.Scanner;

public class InteractionUtilisateur {
    private Scanner scanner;
    private Vue vue;

    public InteractionUtilisateur() {
        scanner = new Scanner(System.in);
        vue = new Vue();
    }

    public int[] getMoveFromPlayer(Player player) {
        int row, col;
        while (true) {
            vue.afficherMessage(player.getName() + ", entrez le numéro de ligne (0 à 2): ");
            row = scanner.nextInt();
            vue.afficherMessage(player.getName() + ", entrez le numéro de colonne (0 à 2): ");
            col = scanner.nextInt();

            if (row >= 0 && row <= 2 && col >= 0 && col <= 2) {
                break;
            } else {
                vue.afficherMessage("Entrée invalide. Les numéros de ligne et de colonne doivent être entre 0 et 2. Veuillez réessayer.");
            }
        }
        return new int[]{row, col};
    }

    public int getGameMode() {
        vue.afficherMessage("Choisissez le mode de jeu: ");
        vue.afficherMessage("1. Joueur vs Joueur");
        vue.afficherMessage("2. Joueur vs IA");
        vue.afficherMessage("3. IA vs IA");
        return scanner.nextInt();
    }

    public void playWithAI(TicTacToe game) {
        vue.afficherMessage("Bienvenue dans le jeu Tic Tac Toe!");
        vue.afficherMessage("Joueur 1 avec X et IA avec O");

        Object currentPlayer = game.player1;
        while (true) {
            vue.afficherPlateau(game.board, TicTacToe.size);
            if (game.isBoardFull()) {
                vue.afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                break;
            }
            if (currentPlayer instanceof Player) {
                int[] move = getMoveFromPlayer((Player) currentPlayer);
                game.setOwner(move[0], move[1], (Player) currentPlayer);
            } else if (currentPlayer instanceof ArtificialPlayer) {
                int row, col;
                do {
                    ((ArtificialPlayer) currentPlayer).generateRandomPosition();
                    row = ((ArtificialPlayer) currentPlayer).getRow();
                    col = ((ArtificialPlayer) currentPlayer).getCol();
                } while (!game.isValidMove(row, col));
                vue.afficherMessage(((ArtificialPlayer) currentPlayer).getName() + " joue en (" + row + ", " + col + ")");
                game.setOwnerAi(row, col, (ArtificialPlayer) currentPlayer);
            }
            if (game.isOver()) {
                vue.afficherPlateau(game.board, TicTacToe.size);
                vue.afficherMessage("Le jeu est terminé! " + ((Player) currentPlayer).getName() + " a gagné!");
                break;
            }
            currentPlayer = (currentPlayer == game.player1) ? game.ai2 : game.player1;
        }
    }

    public void playAIvsAI(TicTacToe game) {
        vue.afficherMessage("Bienvenue dans le jeu Tic Tac Toe!");
        vue.afficherMessage("IA 1 avec X et IA 2 avec O");

        ArtificialPlayer currentPlayer = game.ai1;
        while (true) {
            vue.afficherPlateau(game.board, TicTacToe.size);
            if (game.isBoardFull()) {
                vue.afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                break;
            }
            int row, col;
            do {
                currentPlayer.generateRandomPosition();
                row = currentPlayer.getRow();
                col = currentPlayer.getCol();
            } while (!game.isValidMove(row, col));
            vue.afficherMessage(currentPlayer.getName() + " joue en (" + row + ", " + col + ")");
            game.setOwnerAi(row, col, currentPlayer);
            if (game.isOver()) {
                vue.afficherPlateau(game.board, TicTacToe.size);
                vue.afficherMessage("Le jeu est terminé! " + currentPlayer.getName() + " a gagné!");
                break;
            }
            currentPlayer = (currentPlayer == game.ai1) ? game.ai2 : game.ai1;
        }
    }
}