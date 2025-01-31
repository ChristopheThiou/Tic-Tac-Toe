package main.games;

import main.vue.InteractionUtilisateur;
import main.vue.Vue;


public class Gomoku extends BoardGame {

    public Gomoku(Vue vue, InteractionUtilisateur interactionUtilisateur) {
        super(vue, interactionUtilisateur, 15, 5, "Gomoku");
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