package main.controller;

import main.games.Gomoku;

public class GomokuController extends GamesController {

    public GomokuController() {
        super();
        this.setBoardGame(new Gomoku(this.getVue(), this.getInteractionUtilisateur()));
    }
}