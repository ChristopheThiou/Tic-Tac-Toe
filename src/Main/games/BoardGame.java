package main.games;

import main.vue.Cell;
import main.vue.InteractionUtilisateur;
import main.vue.Vue;

public abstract class BoardGame {
    protected Vue vue;
    protected InteractionUtilisateur interactionUtilisateur;
    protected int size;
    protected int winCondition;
    protected Player player1;
    protected Player player2;
    protected Player currentPlayer;
    protected Cell[][] board;

    public BoardGame(Vue vue, InteractionUtilisateur interactionUtilisateur, int size, int winCondition) {
        this.vue = vue;
        this.interactionUtilisateur = interactionUtilisateur;
        this.size = size;
        this.winCondition = winCondition;
        this.board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public abstract boolean isValidMove(int row, int col);

    public abstract int[] generateRandomPosition();

    public abstract int[] getMoveFromAI(Player player);

    public abstract int[] getMoveFromPlayer(Player player);

    public abstract boolean isBoardFull();

    public abstract boolean isOver();

    public InteractionUtilisateur getInteractionUtilisateur() {
        return interactionUtilisateur;
    }

    public void initFirstPlayer() {
        setCurrentPlayer(Player.randomizeFirstPlayer(player1, player2, vue));
    }

    public void switchPlayer() {
        setCurrentPlayer(getCurrentPlayer().equals(player1) ? player2 : player1);
    }

    public int[] move() {
        return getCurrentPlayer().isArtificial ? getMoveFromAI(getCurrentPlayer()) : getMoveFromPlayer(getCurrentPlayer());
    }

    public void occupy(int[] move) {
        if (move != null && move.length == 2) {
            setOwner(move[0], move[1], getCurrentPlayer());
        } else {
            throw new IllegalArgumentException("Invalid move");
        }
    }

    protected abstract void setOwner(int row, int col, Player player);

    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    protected boolean checkVictory(Player player) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (checkDirection(i, j, player, 1, 0) ||
                    checkDirection(i, j, player, 0, 1) ||
                    checkDirection(i, j, player, 1, 1) ||
                    checkDirection(i, j, player, 1, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, Player player, int dRow, int dCol) {
        int count = 0;
        String playerSymbol = player.getSymbol();
        for (int i = 0; i < winCondition; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
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
}