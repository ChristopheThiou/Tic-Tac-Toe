public class Vue {
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    protected void display(Cell[][] board, int size) {
        StringBuilder representation = new StringBuilder();
        for (int i = 0; i < size - 1; i++) {
            representation.append("---".repeat(size)).append("-\n");
            for (int j = 0; j < size - 1; j++) {
                representation.append(board[i][j].getRepresentation());
            }
            representation.append("|\n");
        }
        representation.append("---".repeat(size)).append("-\n");

        afficherMessage(representation.toString());
    }
}