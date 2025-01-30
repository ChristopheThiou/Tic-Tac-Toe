package main.controller;

import main.games.Gomoku;

public class GomokuController extends GamesController {

    public GomokuController() {
        super();
        this.setBoardGame(new Gomoku(this.getVue(), this.getInteractionUtilisateur()));
    }

    @Override
    public void play() {
        getVue().afficherMessage("Bienvenue dans le jeu Gomoku! 🤗");
        getVue().afficherMessage("Joueur 1 avec ⚪ et Joueur 2 avec 🟤");
        getVue().afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");

        getBoardGame().initFirstPlayer();

        while (true) {
            getVue().display(((Gomoku) getBoardGame()).getBoard());

            int[] move = getBoardGame().move(new int[2]);
            ((Gomoku) getBoardGame()).getBoardGame().occupy(move);
            getVue().afficherMessage(getBoardGame().getCurrentPlayer().getName() + " joue en position: (" + move[0] + ", " + move[1] + ")");

            if (getBoardGame().isOver()) {
                getVue().display(((Gomoku) getBoardGame()).getBoard());
                getVue().afficherMessage("Le jeu est terminé! " + getBoardGame().getCurrentPlayer().getName() + " a gagné! 🔆👌");
                break;
            }

            if (getBoardGame().isBoardFull()) {
                getVue().display(((Gomoku) getBoardGame()).getBoard());
                getVue().afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                break;
            }
            getBoardGame().switchPlayer();
        }
    }
}