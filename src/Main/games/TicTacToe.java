package main.games;

import main.vue.Cell;
import main.vue.InteractionUtilisateur;
import main.vue.Vue;

public class TicTacToe extends BoardGame {

    public TicTacToe(Vue vue, InteractionUtilisateur interactionUtilisateur) {
        super(vue, interactionUtilisateur, 3, 3, "Tic Tac Toe");
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
                return getBestMove();
            default:
                return generateRandomPosition();
        }
    }

    private int[] getBlockingMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValidMove(i, j)) {
                    board[i][j].setOwner(player1.getSymbol());
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
                    board[i][j].setOwner(player2.getSymbol());
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
                        board[i][j].setOwner(player2.getSymbol());
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
                        board[i][j].setOwner(player1.getSymbol());
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