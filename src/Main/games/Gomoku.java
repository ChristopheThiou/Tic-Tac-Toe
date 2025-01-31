package main.games;

import java.util.InputMismatchException;
import java.util.Random;
import main.vue.Cell;
import main.vue.InteractionUtilisateur;
import main.vue.Vue;

public class Gomoku extends BoardGame {

    public Gomoku(Vue vue, InteractionUtilisateur interactionUtilisateur) {
        super(vue, interactionUtilisateur, 15, 5);
    }

    public Cell[][] getBoard() {
        return board;
    }

    @Override
    public boolean isValidMove(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        return board[row][col].isEmpty();
    }

    @Override
    public void setOwner(int row, int col, Player player) {
        if (isValidMove(row, col)) {
            board[row][col].setOwner(player.getSymbol());
        }
    }

    @Override
    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isOver() {
        return checkVictory(getCurrentPlayer());
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

    @Override
    public int[] getMoveFromAI(Player player) {
        int difficulty = player.getDifficultyLevel();
        switch (difficulty) {
            case 1:
                return generateRandomPosition();
            case 2:
                return getBlockingMove();
            case 3:
                int[] winningMove = getWinningMove();
                if (winningMove != null) {
                    return winningMove;
                }
                int[] blockingMove = getBlockingMove();
                if (blockingMove != null) {
                    return blockingMove;
                }
                return getStrategicMove();
            default:
                return generateRandomPosition();
        }
    }

    private int[] getBlockingMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValidMove(i, j)) {
                    board[i][j].setOwner(player1.getSymbol());
                    boolean canBlock = checkVictory(player1);
                    board[i][j].setOwner(null);
                    if (canBlock) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return generateRandomPosition();
    }

    private int[] getWinningMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValidMove(i, j)) {
                    board[i][j].setOwner(player2.getSymbol());
                    boolean canWin = checkVictory(player2);
                    board[i][j].setOwner(null);
                    if (canWin) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    private int[] getStrategicMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValidMove(i, j)) {
                    if (hasAdjacentToken(i, j)) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return generateRandomPosition();
    }

    private boolean hasAdjacentToken(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                    if (board[newRow][newCol].getOwner() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}