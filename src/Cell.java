public class Cell {
    private Player owner;
    private Player player = null;

    protected Cell() {
        this.owner = player;
    }

    protected boolean isEmpty() {
        return owner == player;
    }

    protected void setOwner(Player owner) {
        this.owner = owner;
    }

    protected Object getOwner() {
        return owner;
    }

    public String getRepresentation() {
        if (owner == null) {
            return "|   ";
        } else {
            return owner.getSymbol();
        }
    }

    public static String getBoardRepresentation(Cell[][] board, int size) {
        StringBuilder representation = new StringBuilder();
        for (int i = 0; i < size - 1; i++) {
            representation.append("---".repeat(size)).append("-\n");
            for (int j = 0; j < size - 1; j++) {
                representation.append(board[i][j].getRepresentation());
            }
            representation.append("|\n");
        }
        representation.append("---".repeat(size)).append("-\n");
        return representation.toString();
    }
}