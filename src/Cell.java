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
}