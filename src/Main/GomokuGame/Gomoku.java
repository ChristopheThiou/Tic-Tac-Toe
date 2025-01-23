package Main.GomokuGame;

import Main.BoardGame;
import Main.Cell;
import Main.InteractionUtilisateur;
import Main.Player;
import Main.Vue;
import java.util.InputMismatchException;
import java.util.Random;


public class Gomoku extends BoardGame {
    public Cell[][] board;

    public Gomoku(Vue vue, InteractionUtilisateur interactionUtilisateur) {
        super(vue, interactionUtilisateur, 15);
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
        player1 = new Player("| ⚪ ", "Joueur 1", false);
        player2 = new Player("| 🟤 ", "Joueur 2", false);
    }

    @Override
    public void play() {
        vue.afficherMessage("Bienvenue dans le jeu Gomoku! 🤗");
        vue.afficherMessage("Joueur 1 avec ⚪ et Joueur 2 avec 🟤");
        vue.afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");

        Random random = new Random();
        Player currentPlayer = random.nextBoolean() ? player1 : player2;
        vue.afficherMessage(currentPlayer.getName() + " commence en premier!");
        while (true) {
            vue.display(board);

            int[] move = currentPlayer.isArtificial() ? getMoveFromAI(currentPlayer) : getMoveFromPlayer(currentPlayer);
            setOwner(move[0], move[1], currentPlayer);
            vue.afficherMessage(currentPlayer.getName() + " joue en position: (" + move[0] + ", " + move[1] + ")");

            if (isOver()) {
                vue.display(board);
                vue.afficherMessage("Le jeu est terminé! " + currentPlayer.getName() + " a gagné! 🔆👌");
                break;
            }

            if (isBoardFull()) {
                vue.display(board);
                vue.afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                break;
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }

    @Override
    public boolean isValidMove(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        return board[row][col].isEmpty();
    }

    protected void setOwner(int row, int col, Player player) {
        if (isValidMove(row, col)) {
            board[row][col].setOwner(player);
        }
    }

    protected boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isOver() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= size - 5; j++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i][j + 1].getOwner() &&
                    board[i][j + 1].getOwner() == board[i][j + 2].getOwner() &&
                    board[i][j + 2].getOwner() == board[i][j + 3].getOwner() &&
                    board[i][j + 3].getOwner() == board[i][j + 4].getOwner()) {
                    return true;
                }
            }
        }

        for (int j = 0; j < size; j++) {
            for (int i = 0; i <= size - 5; i++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i + 1][j].getOwner() &&
                    board[i + 1][j].getOwner() == board[i + 2][j].getOwner() &&
                    board[i + 2][j].getOwner() == board[i + 3][j].getOwner() &&
                    board[i + 3][j].getOwner() == board[i + 4][j].getOwner()) {
                    return true;
                }
            }
        }

        for (int i = 0; i <= size - 5; i++) {
            for (int j = 0; j <= size - 5; j++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i + 1][j + 1].getOwner() &&
                    board[i + 1][j + 1].getOwner() == board[i + 2][j + 2].getOwner() &&
                    board[i + 2][j + 2].getOwner() == board[i + 3][j + 3].getOwner() &&
                    board[i + 3][j + 3].getOwner() == board[i + 4][j + 4].getOwner()) {
                    return true;
                }
            }
        }

        for (int i = 4; i < size; i++) {
            for (int j = 0; j <= size - 5; j++) {
                if (board[i][j].getOwner() != null &&
                    board[i][j].getOwner() == board[i - 1][j + 1].getOwner() &&
                    board[i - 1][j + 1].getOwner() == board[i - 2][j + 2].getOwner() &&
                    board[i - 2][j + 2].getOwner() == board[i - 3][j + 3].getOwner() &&
                    board[i - 3][j + 3].getOwner() == board[i - 4][j + 4].getOwner()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void gameMode() {
        int choice = interactionUtilisateur.getGameMode();
        int difficulty = 1;

        switch (choice) {
            case 1:
                play();
                break;
            case 2:
                difficulty = interactionUtilisateur.getDifficultyLevel();
                player2 = new Player("| 🟤 ", "AI", true, difficulty);
                play();
                break;
            case 3:
                difficulty = interactionUtilisateur.getDifficultyLevel();
                player1 = new Player("| ⚪ ", "AI 1", true, difficulty);
                player2 = new Player("| 🟤 ", "AI 2", true, difficulty);
                play();
                break;
            default:
                vue.afficherMessage("Choix invalide. Veuillez réessayer. 👺");
                gameMode();
        }
    }

    @Override
    public int[] generateRandomPosition() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(size);
            col = random.nextInt(size);
        } while (!isValidMove(row, col));
        return new int[]{row, col};
    }

    @Override
    public int[] getMoveFromPlayer(Player player) {
        int row = 0, col = 0;
        while (true) {
            try {
                vue.afficherMessage(player.getName() + ", entrez le numéro de ligne (0 à 14) ou 404 pour quitter: ");
                row = interactionUtilisateur.scanner.nextInt();
                if (row == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }
                vue.afficherMessage(player.getName() + ", entrez le numéro de colonne (0 à 14) ou 404 pour quitter: ");
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

    public int[] getMoveFromAI(Player player) {
        int difficulty = player.getDifficultyLevel();
        switch (difficulty) {
            case 1:
                return generateRandomPosition();
            case 2:
                return getBlockingMove();
            case 3:
                int[] winningMove = getWinningMove();
                if (winningMove != null) {
                    return winningMove;
                }
                int[] blockingMove = getBlockingMove();
                if (blockingMove != null) {
                    return blockingMove;
                }
                return getStrategicMove();
            default:
                return generateRandomPosition();
        }
    }

    private int[] getBlockingMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValidMove(i, j)) {
                    if (canWin(player1, i, j)) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    private int[] getWinningMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValidMove(i, j)) {
                    if (canWin(player2, i, j)) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    private boolean canWin(Player player, int row, int col) {
        board[row][col].setOwner(player);
        boolean win = checkVictory(row, col, player);
        board[row][col].setOwner(null);
        return win;
    }

    private boolean checkVictory(int row, int col, Player player) {
        return checkDirection(row, col, player, 1, 0) ||
               checkDirection(row, col, player, 0, 1) ||
               checkDirection(row, col, player, 1, 1) ||
               checkDirection(row, col, player, 1, -1);
    }

    private boolean checkDirection(int row, int col, Player player, int dRow, int dCol) {
        int count = 0;
        for (int i = -4; i <= 4; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size && board[newRow][newCol].getOwner() == player) {
                count++;
                if (count == 5) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    private int[] getStrategicMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValidMove(i, j)) {
                    if (i == size / 2 && j == size / 2) {
                        return new int[]{i, j};
                    }
                    if (hasAdjacentToken(i, j)) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return generateRandomPosition();
    }

    private boolean hasAdjacentToken(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                    if (board[newRow][newCol].getOwner() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}