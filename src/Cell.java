public class Cell {
    private String value;
    private Object owner;

    public Cell() {
        this.value = "-";
        this.owner = null;
    }

    public boolean isEmpty() {
        return this.value.equals("-");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public Object getOwner() {
        return owner;
    }

    public static String getRepresentation() {
        return "|   ";
    }
}