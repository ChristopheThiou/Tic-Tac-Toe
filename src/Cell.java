public class Cell {
    private Player owner;

    protected Cell() {
        this.owner = null;
    }

    protected boolean isEmpty() {
        return owner == null;
    }

    protected void setOwner(Player owner) {
        this.owner = owner;
    }

    protected Object getOwner() {
        return owner;
    }

    public String getRepresentation() {
        return (owner == null) ? "|    " : owner.getSymbol();
    }
}