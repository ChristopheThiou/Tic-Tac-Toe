package main.games;

import main.vue.InteractionUtilisateur;
import main.vue.Vue;

public abstract class BoardGame {
    protected static Vue vue;
    protected InteractionUtilisateur interactionUtilisateur;
    protected Player player1;
    protected Player player2;
    protected static int size;
    private Player currentPlayer;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    protected void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public BoardGame getBoardGame() {
        return this;
    }

    public BoardGame(Vue vue, InteractionUtilisateur interactionUtilisateur, int size) {
        BoardGame.vue = vue;
        this.interactionUtilisateur = interactionUtilisateur;
        BoardGame.size = size;
    }

    public abstract boolean isValidMove(int row, int col);

    public abstract int[] generateRandomPosition();

    public abstract int[] getMoveFromAI(Player player);

    public abstract int[] getMoveFromPlayer(Player player);

    public abstract boolean isBoardFull();

    public abstract boolean isOver();

    protected abstract void setOwner(int row, int col, Player player);

    public InteractionUtilisateur getInteractionUtilisateur() {
        return interactionUtilisateur;
    }

    public  void initFirstPlayer() {
        setCurrentPlayer(Player.randomizeFirstPlayer(player1, player2, vue));
    }

    public void switchPlayer() {
        setCurrentPlayer(getCurrentPlayer().equals(player1) ? player2 : player1);
    }

    public int[] move() {
        return getCurrentPlayer().isArtificial ? getMoveFromAI(getCurrentPlayer()) : getMoveFromPlayer(getCurrentPlayer());
    }

    public void occupy(int [] move) {
        setOwner(move[0], move[1], getCurrentPlayer());
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}