package main.controller;

import main.games.PuissanceQuatre;

public class PuissanceQuatreController extends GamesController {

    public PuissanceQuatreController() {
        super();
        this.setBoardGame(new PuissanceQuatre(this.getVue(), this.getInteractionUtilisateur()));
    }
}
