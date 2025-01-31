package main.games;

import java.util.InputMismatchException;
import java.util.Random;
import main.vue.Cell;
import main.vue.InteractionUtilisateur;
import main.vue.Vue;


public abstract class BoardGame {
    protected Vue vue;
    protected InteractionUtilisateur interactionUtilisateur;
    protected int size;
    protected int winCondition;
    protected Player player1;
    protected Player player2;
    protected Player currentPlayer;
    protected Cell[][] board;

    public BoardGame(Vue vue, InteractionUtilisateur interactionUtilisateur, int size, int winCondition) {
        this.vue = vue;
        this.interactionUtilisateur = interactionUtilisateur;
        this.size = size;
        this.winCondition = winCondition;
        this.board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public abstract int[] getMoveFromAI(Player player);

    public Cell[][] getBoard() {
        return board;
    }

    public boolean isOver() {
        return checkVictory(getCurrentPlayer());
    }

    public InteractionUtilisateur getInteractionUtilisateur() {
        return interactionUtilisateur;
    }

    public void initFirstPlayer() {
        setCurrentPlayer(Player.randomizeFirstPlayer(player1, player2, vue));
    }

    public void switchPlayer() {
        setCurrentPlayer(getCurrentPlayer().equals(player1) ? player2 : player1);
    }

    public int[] move() {
        return getCurrentPlayer().isArtificial ? getMoveFromAI(getCurrentPlayer()) : getMoveFromPlayer(getCurrentPlayer());
    }

    public void occupy(int[] move) {
        if (move != null && move.length == 2) {
            setOwner(move[0], move[1], getCurrentPlayer());
        } else {
            throw new IllegalArgumentException("Invalid move");
        }
    }

    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    protected boolean checkVictory(Player player) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (checkDirection(i, j, player, 1, 0) ||
                    checkDirection(i, j, player, 0, 1) ||
                    checkDirection(i, j, player, 1, 1) ||
                    checkDirection(i, j, player, 1, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean checkDirection(int row, int col, Player player, int dRow, int dCol) {
        int count = 0;
        String playerSymbol = player.getSymbol();
        for (int i = 0; i < winCondition; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                String owner = board[newRow][newCol].getOwner();
                if (owner != null && owner.equals(playerSymbol)) {
                    count++;
                    if (count == winCondition) {
                        return true;
                    }
                } else {
                    break;
                }
            }
        }
        return false;
    }

    protected boolean isValidMove(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        return board[row][col].isEmpty();
    }

    protected void setOwner(int row, int col, Player player) {
        if (isValidMove(row, col)) {
            board[row][col].setOwner(player.getSymbol());
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected int[] generateRandomPosition() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(size);
            col = random.nextInt(size);
        } while (!isValidMove(row, col));
        return new int[]{row, col};
    }

    protected int[] getMoveFromPlayer(Player player) {
        int row = 0, col = 0;
        while (true) {
            try {
                vue.afficherMessage(player.getName() + ", entrez le numéro de ligne (0 à 2) ou 404 pour quitter: ");
                row = interactionUtilisateur.scanner.nextInt();
                if (row == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }
                vue.afficherMessage(player.getName() + ", entrez le numéro de colonne (0 à 2) ou 404 pour quitter: ");
                col = interactionUtilisateur.scanner.nextInt();
                if (col == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }

                if (isValidMove(row, col)) {
                    break;
                } else {
                    vue.afficherMessage("Mouvement invalide. La case est déjà occupée ou hors des limites. Veuillez réessayer. 💩");
                }
            } catch (InputMismatchException e) {
                vue.afficherMessage("Entrée invalide. Veuillez entrer un nombre.");
                interactionUtilisateur.scanner.next();
            }
        }
        return new int[]{row, col};
    }
}