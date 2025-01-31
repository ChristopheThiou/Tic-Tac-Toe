package main.controller;

import main.games.Player;
import main.games.TicTacToe;

public class TicTacToeController extends GamesController {

    public TicTacToeController() {
        super();
        this.setBoardGame(new TicTacToe(this.getVue(), this.getInteractionUtilisateur()));

        getBoardGame().setPlayer1(new Player("❌", "Joueur 1", false, 0));
        getBoardGame().setPlayer2(new Player("⭕", "Joueur 2", false, 0));
    }

    @Override
    public void play() {
        getVue().messageAccueil(getBoardGame());
        getVue().symbolJoueurs(getBoardGame());
        getVue().endGameMessage(getBoardGame());

        getBoardGame().initFirstPlayer();

        while (true) {
            getVue().display((getBoardGame()).getBoard());

            int[] move = getBoardGame().move();
            (getBoardGame()).occupy(move);
            getVue().afficherMessage(getBoardGame().getCurrentPlayer().getName() + " joue en position: (" + move[0] + ", " + move[1] + ")");

            if (getBoardGame().isOver()) {
                getVue().display((getBoardGame()).getBoard());
                getVue().afficherMessage("Le jeu est terminé! " + getBoardGame().getCurrentPlayer().getName() + " a gagné! 🔆👌");
                break;
            }

            if (getBoardGame().isBoardFull()) {
                getVue().display((getBoardGame()).getBoard());
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
                getBoardGame().setPlayer2(new Player("⭕", "AI", true, difficulty));
                play();
                break;
            case 3:
                difficulty = getInteractionUtilisateur().getDifficultyLevel();
                getBoardGame().setPlayer1(new Player("❌", "AI 1", true, difficulty));
                getBoardGame().setPlayer2(new Player("⭕", "AI 2", true, difficulty));
                play();
                break;
            default:
                getVue().afficherMessage("Choix invalide. Veuillez réessayer.");
                gameMode();
        }
    }
}
