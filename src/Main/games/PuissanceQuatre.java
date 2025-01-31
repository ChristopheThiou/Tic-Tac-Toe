package main.games;

import main.vue.Cell;
import main.vue.InteractionUtilisateur;
import main.vue.Vue;

public class PuissanceQuatre extends BoardGame {

    public PuissanceQuatre(Vue vue, InteractionUtilisateur interactionUtilisateur) {
        super(vue, interactionUtilisateur, 7, 4, "Puissance Quatre");
        board = new Cell[6][size];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < size; j++) {
                    this.board[i][j] = new Cell();
            }
        }
    }

    @Override
    protected boolean checkDirection(int row, int col, Player player, int dRow, int dCol) {
        int count = 0;
        String playerSymbol = player.getSymbol();
        for (int i = 0; i < 4; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow >= 0 && newRow < 6 && newCol >= 0 && newCol < size) {
                String owner = board[newRow][newCol].getOwner();
                if (owner != null && owner.equals(playerSymbol)) {
                    count++;
                    if (count == winCondition) {
                        return true;
                    }
                } else {
                    break;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isValidMove(int row, int col) {
        return col >= 0 && col < size && board[0][col].isEmpty();
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
        for (int col = 0; col < size; col++) {
            if (isValidMove(0, col)) {
                for (int row = 5; row >= 0; row--) {
                    if (board[row][col].isEmpty()) {
                        board[row][col].setOwner(player1.getSymbol());
                        boolean canBlock = checkVictory(player1);
                        board[row][col].setOwner(null);
                        if (canBlock) {
                            return new int[]{row, col};
                        }
                        break;
                    }
                }
            }
        }
        return generateRandomPosition();
    }

    private int[] getWinningMove() {
        for (int col = 0; col < size; col++) {
            if (isValidMove(0, col)) {
                for (int row = 5; row >= 0; row--) {
                    if (board[row][col].isEmpty()) {
                        board[row][col].setOwner(player2.getSymbol());
                        boolean canWin = checkVictory(player2);
                        board[row][col].setOwner(null);
                        if (canWin) {
                            return new int[]{row, col};
                        }
                        break;
                    }
                }
            }
        }
        return null;
    }

    private int[] getStrategicMove() {
        for (int col = 0; col < size; col++) {
            if (isValidMove(0, col)) {
                for (int row = 5; row >= 0; row--) {
                    if (board[row][col].isEmpty()) {
                        if (hasAdjacentToken(row, col)) {
                            return new int[]{row, col};
                        }
                        break;
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
                if (newRow >= 0 && newRow < 6 && newCol >= 0 && newCol < size) {
                    if (board[newRow][newCol].getOwner() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean placerJeton(Player player, int col) {
        if (col < 0 || col >= size) {
            vue.afficherMessage("Colonne hors limites : " + col);
            return false;
        }

        for (int i = 5; i >= 0; i--) {
            if (board[i][col].isEmpty()) {
                board[i][col].setOwner(player.getSymbol());
                vue.afficherMessage("Jeton placé en [" + i + "][" + col + "]");
                return true;
            }
        }
        return false;
    }
}