package main.controller;

import main.games.Player;
import main.games.PuissanceQuatre;

public class PuissanceQuatreController extends GamesController {

    public PuissanceQuatreController() {
        super();
        this.setBoardGame(new PuissanceQuatre(this.getVue(), this.getInteractionUtilisateur()));

        getBoardGame().setPlayer1(new Player("🔴", "Joueur 1", false, 0));
        getBoardGame().setPlayer2(new Player("🟡", "Joueur 2", false, 0));
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
            int[] move = getBoardGame().move();
            int col = move[1];

            if (((PuissanceQuatre)getBoardGame()).placerJeton(getBoardGame().getCurrentPlayer(), col)) {
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

    @Override
    public void gameMode() {
        int choice = getInteractionUtilisateur().getGameMode();
        int difficulty = 1;

        switch (choice) {
            case 1:
                play();
                break;
            case 2:
                difficulty = getInteractionUtilisateur().getDifficultyLevel();
                getBoardGame().setPlayer2(new Player("🟡", "AI", true, difficulty));
                play();
                break;
            case 3:
                difficulty = getInteractionUtilisateur().getDifficultyLevel();
                getBoardGame().setPlayer1(new Player("🔴", "AI 1", true, difficulty));
                getBoardGame().setPlayer2(new Player("🟡", "AI 2", true, difficulty));
                play();
                break;
            default:
                getVue().afficherMessage("Choix invalide. Veuillez réessayer.");
                gameMode();
        }
    }
}
