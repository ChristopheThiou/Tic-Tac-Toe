package main.controller;

import main.games.PuissanceQuatre;

public class PuissanceQuatreController extends GamesController {

    public PuissanceQuatreController() {
        super();
        this.setBoardGame(new PuissanceQuatre(this.getVue(), this.getInteractionUtilisateur()));
    }

    @Override
    public void play() {
        getVue().afficherMessage("Bienvenue dans le jeu Puissance 4! 🤗");
        getVue().afficherMessage("Joueur 1 avec 🔴 et Joueur 2 avec 🟡");
        getVue().afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");

        boolean gameOver = false;

        getBoardGame().initFirstPlayer();

        while (!gameOver) {
            getVue().display(((PuissanceQuatre) getBoardGame()).getBoard());
            int col []= getBoardGame().move(new int[1]);

            if (PuissanceQuatre.placerJeton(getBoardGame().getCurrentPlayer(), col [1])) {
                if (getBoardGame().isOver()) {
                    getVue().display(((PuissanceQuatre) getBoardGame()).getBoard());
                    getVue().afficherMessage(getBoardGame().getCurrentPlayer().getName() + " a gagné!");
                    gameOver = true;
                } else if (getBoardGame().isBoardFull()) {
                    getVue().display(((PuissanceQuatre) getBoardGame()).getBoard());
                getVue().afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                    gameOver = true;
                } else {
                    getBoardGame().switchPlayer();
                }
            }
        }
    }
}
