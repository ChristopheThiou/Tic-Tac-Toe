package main.games;

import main.vue.InteractionUtilisateur;
import main.vue.Vue;

public abstract class BoardGame {
    protected Vue vue;
    protected InteractionUtilisateur interactionUtilisateur;
    protected Player player1;
    protected Player player2;
    protected int size;

    protected BoardGame(Vue vue, InteractionUtilisateur interactionUtilisateur, int size) {
        this.vue = vue;
        this.interactionUtilisateur = interactionUtilisateur;
        this.size = size;
    }

    public abstract boolean isValidMove(int row, int col);

    public abstract void play();

    public abstract void gameMode();

    public abstract int[] generateRandomPosition();
}