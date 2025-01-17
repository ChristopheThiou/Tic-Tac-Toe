public class Vue {
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void afficherPlateau(Cell[][] board, int size) {
        for (int i = 0; i < size - 1; i++) {
            if (i < size) {
                System.out.println("----".repeat(size - 1) + "-");
            }
            for (int j = 0; j < size - 1; j++) {
                if (board[i][j].getOwner() == null) {
                    System.out.print(Cell.getRepresentation());
                } else {
                    Object owner = board[i][j].getOwner();
                    if (owner instanceof Player) {
                        System.out.print(((Player) owner).getSymbol());
                    } else if (owner instanceof ArtificialPlayer) {
                        System.out.print(((ArtificialPlayer) owner).getSymbol());
                    }
                }
            }
            System.out.println("|");
        }
        System.out.println("----".repeat(size - 1) + "-");
    }
}