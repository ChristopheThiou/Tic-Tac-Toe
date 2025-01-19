public class Vue {
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void display(Cell[][] board, int size) {
        System.out.print(Cell.getBoardRepresentation(board, size));
    }
}