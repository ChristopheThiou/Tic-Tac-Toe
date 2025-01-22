package Main;

public class Cell {
    private Player owner;

    public Cell() {
        this.owner = null;
    }

    public boolean isEmpty() {
        return owner == null;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Object getOwner() {
        return owner;
    }

    public String getRepresentation() {
        return (owner == null) ? "|    " : owner.getSymbol();
    }
}