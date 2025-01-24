package Main;

import Main.GomokuGame.Gomoku;
import Main.PuissanceQuatreGame.PuissanceQuatre;
import Main.TicTacToeGame.TicTacToe;

public class Main {
    public static void main(String[] args) {
        game();
    }

    private static void game() {
        Vue vue = new Vue();
        InteractionUtilisateur interactionUtilisateur = new InteractionUtilisateur();
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
                return;
        }
        gameInstance.gameMode();
    }
}
