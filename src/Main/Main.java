package main;

import main.games.BoardGame;
import main.games.Gomoku;
import main.games.PuissanceQuatre;
import main.games.TicTacToe;
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
