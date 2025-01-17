import java.util.Random;

public class ArtificialPlayer {
    protected String symbol;
    protected String name;
    protected int row;
    protected int col;

    public ArtificialPlayer(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    void generateRandomPosition() {
        Random random = new Random();
        this.row = random.nextInt(3);
        this.col = random.nextInt(3);
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getRow() {
        generateRandomPosition();
        return row;
    }

    public int getCol() {
        generateRandomPosition();
        return col;
    }

    public static class Ai1 extends ArtificialPlayer {
        public Ai1() {
            super("| X ", "Ai 1");
        }
    }

    public static class Ai2 extends ArtificialPlayer {
        public Ai2() {
            super("| O ", "Ai 2");
        }
    }
}