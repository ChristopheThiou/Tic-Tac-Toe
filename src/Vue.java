public class Vue {
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void display(Cell[][] board, int size) {
        Cell cell = new Cell();
        System.out.print(cell.getBoardRepresentation(board, size));
    }
}