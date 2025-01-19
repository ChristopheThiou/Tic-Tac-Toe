public class Cell {
    private Boolean value;
    private Player owner;

    protected Cell() {
        value = false;
        owner = null;
    }

    protected boolean isEmpty() {
        return !value;
    }

    protected Boolean getValue() {
        return value;
    }

    protected void setValue(Boolean value) {
        this.value = value;
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
        } else if (owner instanceof Player) {
            return (owner).getSymbol();
        } 
        return null;
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