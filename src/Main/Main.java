package main;

import main.controller.GamesController;
import main.controller.GomokuController;
import main.controller.PuissanceQuatreController;
import main.controller.TicTacToeController;
import main.vue.InteractionUtilisateur;
import main.vue.Vue;

public class Main {
    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        InteractionUtilisateur interactionUtilisateur = new InteractionUtilisateur();
        Vue vue = new Vue();
        int gameChoice = interactionUtilisateur.getGameChoice();
        GamesController gameInstance;

        switch (gameChoice) {
            case 1:
                gameInstance = new TicTacToeController();
                break;
            case 2:
                gameInstance = new PuissanceQuatreController();
                break;
            case 3:
                gameInstance = new GomokuController();
                break;
            default:
                vue.afficherMessage("Choix invalide. Veuillez réessayer.");
                return;
        }
        gameInstance.gameMode();
    }
}
