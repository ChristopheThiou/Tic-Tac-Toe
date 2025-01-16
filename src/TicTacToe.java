import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.play();
    }

    private static final int size = 4;
    private Cell[][] board;
    private Scanner scanner;
    private Player player1;
    private Player player2;

    public TicTacToe() {
        board = new Cell[size][size];
        scanner = new Scanner(System.in);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
        player1 = new Player.Player1();
        player2 = new Player.Player2();
    }

    public void play() {
        System.out.println("Bienvenue dans le jeu Tic Tac Toe!");
        System.out.println("Joueur 1 avec X et Joueur 2 avec O");

        Player currentPlayer = player1;
        while (true) {
            display();
            if (isBoardFull()) {
                System.out.println("Le jeu est terminé! Toutes les cases sont remplies.");
                break;
            }
            int[] move = getMoveFromPlayer(currentPlayer);
            setOwner(move[0], move[1], currentPlayer);
            if (isOver()) {
                display();
                System.out.println("Le jeu est terminé! " + currentPlayer.getName() + " a gagné!");
                break;
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }

    public void display() {

        for (int i = 0; i < size - 1; i++) {
            if (i < size) {
                System.out.println("----".repeat(size - 1) + "-");
            }
            for (int j = 0; j < size - 1; j++) {
                if (board[i][j].getOwner() == null) {
                    System.out.print(Cell.getRepresentation());
                } else {
                    System.out.print(board[i][j].getOwner().getSymbol());
                }
            }
            System.out.println("|");
        }
        System.out.println("----".repeat(size - 1) + "-");
    }

    public int[] getMoveFromPlayer(Player player) {
        int row, col;
        while (true) {
            System.out.println(player.getName() + ", entrez le numéro de ligne (0 à 2): ");
            row = scanner.nextInt();
            System.out.println(player.getName() + ", entrez le numéro de colonne (0 à 2): ");
            col = scanner.nextInt();
    
            if (row >= 0 && row <= 2 && col >= 0 && col <= 2) {
                if (isValidMove(row, col)) {
                    break;
                } else {
                    System.out.println("Mouvement invalide. La case est déjà occupée. Veuillez réessayer.");
                }
            } else {
                System.out.println("Entrée invalide. Les numéros de ligne et de colonne doivent être entre 0 et 2. Veuillez réessayer.");
            }
        }
        return new int[]{row, col};
    }

    private boolean isValidMove(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        if (!board[row][col].isEmpty()) {
            return false;
        }
        return true;
    }

    public void setOwner(int row, int col, Player player) {
        if (isValidMove(row, col)) {
            board[row][col].setOwner(player);
            board[row][col].setValue(player.getSymbol());
        } else {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isOver() {
        for (int i = 0; i < size - 1; i++) {
            if (board[i][0].getOwner() != null &&
                board[i][0].getOwner() == board[i][1].getOwner() &&
                board[i][1].getOwner() == board[i][2].getOwner()) {
                return true;
            }
        }

        for (int j = 0; j < size - 1; j++) {
            if (board[0][j].getOwner() != null &&
                board[0][j].getOwner() == board[1][j].getOwner() &&
                board[1][j].getOwner() == board[2][j].getOwner()) {
                return true;
            }
        }

        return false;
    }  
}