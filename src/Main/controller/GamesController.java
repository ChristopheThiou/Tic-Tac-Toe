package main.controller;

import main.games.BoardGame;
import main.vue.InteractionUtilisateur;
import main.vue.Vue;

public abstract class GamesController {
    private Vue vue;
    private InteractionUtilisateur interactionUtilisateur;

    protected BoardGame getBoardGame() {
        return boardGame;
    }

    protected void setBoardGame(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    private BoardGame boardGame;

    protected Vue getVue() {
        return vue;
    }

    protected InteractionUtilisateur getInteractionUtilisateur() {
        return interactionUtilisateur;
    }

    public GamesController() {
        this.vue = new Vue();
        this.interactionUtilisateur = new InteractionUtilisateur();
    }

    public abstract void play();
}