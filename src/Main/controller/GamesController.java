package main.controller;

import main.games.BoardGame;
import main.games.Gomoku;
import main.games.PuissanceQuatre;
import main.games.TicTacToe;
import main.vue.InteractionUtilisateur;
import main.vue.Vue;

public class GamesController {
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

    public void startGame() {
        int gameChoice = interactionUtilisateur.getGameChoice();
        BoardGame gameInstance;

        switch (gameChoice) {
            case 1:
                gameInstance = new TicTacToe(vue, interactionUtilisateur);
                break;
            case 2:
                gameInstance = new PuissanceQuatre(vue, interactionUtilisateur);
                break;
            case 3:
                gameInstance = new Gomoku(vue, interactionUtilisateur);
                break;
            default:
                vue.afficherMessage("Choix invalide. Veuillez réessayer.");
                return;
        }
        gameInstance.gameMode();
    }
}