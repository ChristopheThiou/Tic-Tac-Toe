package main.vue;

public class Vue {
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void display(Cell[][] board) {
        StringBuilder representation = new StringBuilder();
        int rows = board.length;
        int cols = board[0].length;

        representation.append("    ");
        for (int j = 0; j < cols; j++) {
            representation.append(String.format("%-3d", j));
        }
        representation.append("\n");

        for (int i = 0; i < rows; i++) {
            representation.append("   ").append("---".repeat(cols)).append("-\n");

            representation.append(String.format("%-3d", i));

            for (int j = 0; j < cols; j++) {
                representation.append(board[i][j].getRepresentation());
            }
            representation.append("|\n");
        }

        representation.append("   ").append("---".repeat(cols)).append("-\n");

        afficherMessage(representation.toString());
    }
}