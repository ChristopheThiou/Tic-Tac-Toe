package Main.TicTacToeGame;

import Main.BoardGame;
import Main.Cell;
import Main.InteractionUtilisateur;
import Main.Player;
import Main.Vue;
import java.util.InputMismatchException;
import java.util.Random;


public class TicTacToe extends BoardGame {
    public Cell[][] board;

    public TicTacToe(Vue vue, InteractionUtilisateur interactionUtilisateur) {
        super(vue, interactionUtilisateur, 3);
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
        player1 = new Player("| ❌ ", "Joueur 1", false);
        player2 = new Player("| ⭕ ", "Joueur 2", false);
    }

    @Override
    public void play() {
        vue.afficherMessage("Bienvenue dans le jeu Tic Tac Toe! 🤗");
        vue.afficherMessage("Joueur 1 avec ❌ et Joueur 2 avec ⭕");
        vue.afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");

        Random random = new Random();
        Player currentPlayer = random.nextBoolean() ? player1 : player2;
        vue.afficherMessage(currentPlayer.getName() + " commence en premier!");

        while (true) {
            vue.display(board);

            int[] move = currentPlayer.isArtificial() ? getMoveFromAI(currentPlayer) : getMoveFromPlayer(currentPlayer);
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

    @Override
    public boolean isValidMove(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        return board[row][col].isEmpty();
    }

    protected void setOwner(int row, int col, Player player) {
        if (isValidMove(row, col)) {
            board[row][col].setOwner(player);
        }
    }

    protected boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean isOver() {
        for (int i = 0; i < size ; i++) {
            if (board[i][0].getOwner() != null &&
                board[i][0].getOwner() == board[i][1].getOwner() &&
                board[i][1].getOwner() == board[i][2].getOwner()) {
                return true;
            }
        }

        for (int j = 0; j < size ; j++) {
            if (board[0][j].getOwner() != null &&
                board[0][j].getOwner() == board[1][j].getOwner() &&
                board[1][j].getOwner() == board[2][j].getOwner()) {
                return true;
            }
        }

        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
                if (board[0][0].getOwner() != null &&
                    board[0][0].getOwner() == board[1][1].getOwner() &&
                    board[1][1].getOwner() == board[2][2].getOwner()) {
                    return true;
                }
                if (board[0][2].getOwner() != null &&
                    board[0][2].getOwner() == board[1][1].getOwner() &&
                    board[1][1].getOwner() == board[2][0].getOwner()) {
                    return true;
                }
            }
        }

        return false;
}

@Override
public void gameMode() {
    int choice = interactionUtilisateur.getGameMode();
    int difficulty = 1; 

    switch (choice) {
        case 1:
            play();
            break;
        case 2:
            difficulty = interactionUtilisateur.getDifficultyLevel();
            player2 = new Player("| ⭕ ", "AI", true, difficulty);
            play();
            break;
        case 3:
            difficulty = interactionUtilisateur.getDifficultyLevel();
            player1 = new Player("| ❌ ", "AI 1", true, difficulty);
            player2 = new Player("| ⭕ ", "AI 2", true, difficulty);
            play();
            break;
        default:
            vue.afficherMessage("Choix invalide. Veuillez réessayer. 👺");
            gameMode();
    }
}

    @Override
    public int[] generateRandomPosition() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(size);
            col = random.nextInt(size);
        } while (!isValidMove(row, col));
        return new int[]{row, col};
    }

    @Override
    public int[] getMoveFromPlayer(Player player) {
        int row = 0, col = 0;
        while (true) {
            try {
                vue.afficherMessage(player.getName() + ", entrez le numéro de ligne (0 à 2) ou 404 pour quitter: ");
                row = interactionUtilisateur.scanner.nextInt();
                if (row == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }
                vue.afficherMessage(player.getName() + ", entrez le numéro de colonne (0 à 2) ou 404 pour quitter: ");
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

    public int[] getMoveFromAI(Player player) {
        int difficulty = player.getDifficultyLevel();
        switch (difficulty) {
            case 1:
                return generateRandomPosition();
            case 2:
                return getBlockingMove();
            case 3:
                return getBestMove();
            default:
                return generateRandomPosition();
        }
    }

    private int[] getBlockingMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValidMove(i, j)) {
                    board[i][j].setOwner(player1);
                    if (isOver()) {
                        board[i][j].setOwner(null);
                        return new int[]{i, j};
                    }
                    board[i][j].setOwner(null);
                }
            }
        }
        return generateRandomPosition();
    }

    private int[] getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValidMove(i, j)) {
                    board[i][j].setOwner(player2);
                    int score = minimax(board, 0, false);
                    board[i][j].setOwner(null);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(Cell[][] board, int depth, boolean isMaximizing) {
        if (isOver()) {
            return isMaximizing ? -1 : 1;
        }
        if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (isValidMove(i, j)) {
                        board[i][j].setOwner(player2);
                        int score = minimax(board, depth + 1, false);
                        board[i][j].setOwner(null);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (isValidMove(i, j)) {
                        board[i][j].setOwner(player1);
                        int score = minimax(board, depth + 1, true);
                        board[i][j].setOwner(null);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
}