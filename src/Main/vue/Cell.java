package main.vue;


public class Cell {
    private String ownerSymbol;

    public Cell() {
        this.ownerSymbol = null;
    }

    public boolean isEmpty() {
        return ownerSymbol == null;
    }

    public void setOwner(String ownerSymbol) {
        this.ownerSymbol = ownerSymbol;
    }

    public String getOwner() {
        return ownerSymbol;
    }

    public String getRepresentation() {
        return (ownerSymbol == null) ? "|    " : ownerSymbol;
    }
}