package main.controller;

import main.games.Gomoku;
import main.games.Player;

public class GomokuController extends GamesController {

    public GomokuController() {
        super();
        this.setBoardGame(new Gomoku(this.getVue(), this.getInteractionUtilisateur()));

        getBoardGame().setPlayer1(new Player("⚪", "Joueur 1", false, 0));
        getBoardGame().setPlayer2(new Player("🟤", "Joueur 2", false, 0));
    }

    @Override
    public void play() {
        getVue().afficherMessage("Bienvenue dans le jeu Gomoku! 🤗");
        getVue().afficherMessage("Joueur 1 avec ⚪ et Joueur 2 avec 🟤");
        getVue().afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");

        getBoardGame().initFirstPlayer();

        while (true) {
            getVue().display(((Gomoku) getBoardGame()).getBoard());

            int[] move = getBoardGame().move();
            ((Gomoku) getBoardGame()).occupy(move);
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
                getBoardGame().setPlayer2(new Player("🟤", "AI", true, difficulty));
                play();
                break;
            case 3:
                difficulty = getInteractionUtilisateur().getDifficultyLevel();
                getBoardGame().setPlayer1(new Player("⚪", "AI 1", true, difficulty));
                getBoardGame().setPlayer2(new Player("🟤", "AI 2", true, difficulty));
                play();
                break;
            default:
                getVue().afficherMessage("Choix invalide. Veuillez réessayer.");
                gameMode();
        }
    }
}