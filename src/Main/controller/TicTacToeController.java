package main.controller;

import main.games.TicTacToe;

public class TicTacToeController extends GamesController {

    public TicTacToeController() {
        super();
        this.setBoardGame(new TicTacToe(this.getVue(), this.getInteractionUtilisateur()));
    }
}
