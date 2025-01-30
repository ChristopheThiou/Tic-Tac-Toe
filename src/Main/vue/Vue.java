package main.vue;

public class Vue {
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void display(Cell[][] board) {
        StringBuilder representation = new StringBuilder();
        int rows = board.length;
        int cols = board[0].length;

        // Ajouter les indices des colonnes
        representation.append("    ");
        for (int j = 0; j < cols; j++) {
            representation.append(String.format("%-3d", j));
        }
        representation.append("\n");

        for (int i = 0; i < rows; i++) {
            // Ajouter une ligne de séparation
            representation.append("   ").append("---".repeat(cols)).append("-\n");

            // Ajouter l'indice de la ligne
            representation.append(String.format("%-3d", i));

            for (int j = 0; j < cols; j++) {
                representation.append(board[i][j].getRepresentation());
            }
            representation.append("|\n");
        }

        // Ajouter la dernière ligne de séparation
        representation.append("   ").append("---".repeat(cols)).append("-\n");

        afficherMessage(representation.toString());
    }
}