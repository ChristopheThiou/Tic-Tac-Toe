public class Cell {
    private String value;
    private Object owner;

    protected Cell() {
        this.value = "-";
        this.owner = null;
    }

    protected boolean isEmpty() {
        return this.value.equals("-");
    }

    protected String getValue() {
        return value;
    }

    protected void setValue(String value) {
        this.value = value;
    }

    protected void setOwner(Object owner) {
        this.owner = owner;
    }

    protected Object getOwner() {
        return owner;
    }

    protected static String getRepresentation() {
        return "|   ";
    }
}