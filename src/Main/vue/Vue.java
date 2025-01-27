package main.vue;

import main.games.Cell;

public class Vue {
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void display(Cell[][] board) {
        StringBuilder representation = new StringBuilder();
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            representation.append("-----".repeat(cols)).append("-\n");
            for (int j = 0; j < cols; j++) {
                representation.append(board[i][j].getRepresentation());
            }
            representation.append("|\n");
        }
        representation.append("-----".repeat(cols)).append("-\n");

        afficherMessage(representation.toString());
    }
}