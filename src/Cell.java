public class Cell {
    private String value;
    private Player owner;
    private Player owner2;

    public Cell() {
        this.value = "-";
        this.owner = null;
        this.owner2 = null;
    }

    public boolean isEmpty() {
        return this.value == "-";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setOwner2(Player owner2) {
        this.owner2 = owner2;
    }

    public Player getOwner() {
        return owner;
    }

    public Player getOwner2() {
        return owner2;
    }

    public static String getRepresentation() {
        return "|   ";
    }
}