package main.vue;

import main.Player;

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

    public Player getOwner() {
        return owner;
    }

    public String getRepresentation() {
        return (owner == null) ? "|    " : owner.getSymbol();
    }
}