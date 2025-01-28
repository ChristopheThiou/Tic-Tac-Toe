package main.games;

import java.util.InputMismatchException;
import java.util.Random;
import main.vue.InteractionUtilisateur;
import main.vue.Vue;




public class PuissanceQuatre extends BoardGame {
    private Cell[][] board;

    public PuissanceQuatre(Vue vue, InteractionUtilisateur interactionUtilisateur) {
        super(vue, interactionUtilisateur, 7);
        board = new Cell[6][size];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
        player1 = new Player("| 🔴 ", "Joueur 1", false);
        player2 = new Player("| 🟡 ", "Joueur 2", false);
    }

    @Override
    public void play() {
        vue.afficherMessage("Bienvenue dans le jeu Puissance 4! 🤗");
        vue.afficherMessage("Joueur 1 avec 🔴 et Joueur 2 avec 🟡");
        vue.afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");

        boolean gameOver = false;

        Player currentPlayer = Player.randomizeFirstPlayer(player1, player2, vue);

        while (!gameOver) {
            vue.display(board);
            int col = currentPlayer.isArtificial() ? getMoveFromAI(currentPlayer)[1] : getMoveFromPlayer(currentPlayer)[1];

            if (placerJeton(currentPlayer, col)) {
                if (verifierVictoire()) {
                    vue.display(board);
                    vue.afficherMessage(currentPlayer.getName() + " a gagné!");
                    gameOver = true;
                } else if (isBoardFull()) {
                    vue.display(board);
                    vue.afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                    gameOver = true;
                } else {
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                }
            }
        }
    }

    @Override
    public boolean isValidMove(int row, int col) {
        return col >= 0 && col < size && board[0][col].isEmpty();
    }

    private boolean placerJeton(Player player, int col) {
        if (col < 0 || col >= size) {
            vue.afficherMessage("Colonne hors limites : " + col);
            return false;
        }

        for (int i = 5; i >= 0; i--) {
            if (board[i][col].isEmpty()) {
                board[i][col].setOwner(player);
                vue.afficherMessage("Jeton placé en [" + i + "][" + col + "]");
                return true;
            }
        }
        return false;
    }

    private boolean verifierVictoire() {

        for (int i = 0; i < 6; i++) {
            int count = 1;
            for (int j = 1; j < size; j++) {
                if (board[i][j].getOwner() != null && board[i][j].getOwner() == board[i][j - 1].getOwner()) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 1;
                }
            }
        }

        for (int j = 0; j < size; j++) {
            int count = 1;
            for (int i = 1; i < 6; i++) {
                if (board[i][j].getOwner() != null && board[i][j].getOwner() == board[i - 1][j].getOwner()) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 1;
                }
            }
        }

        for (int i = 0; i <= 6 - 4; i++) {
            for (int j = 0; j <= size - 4; j++) {
                int count = 1;
                for (int k = 1; k < 4; k++) {
                    if (board[i + k][j + k].getOwner() != null && board[i + k][j + k].getOwner() == board[i + k - 1][j + k - 1].getOwner()) {
                        count++;
                        if (count == 4) {
                            return true;
                        }
                    } else {
                        count = 1;
                    }
                }
            }
        }

        for (int i = 3; i < 6; i++) {
            for (int j = 0; j <= size - 4; j++) {
                int count = 1;
                for (int k = 1; k < 4; k++) {
                    if (board[i - k][j + k].getOwner() != null && board[i - k][j + k].getOwner() == board[i - k + 1][j + k - 1].getOwner()) {
                        count++;
                        if (count == 4) {
                            return true;
                        }
                    } else {
                        count = 1;
                    }
                }
            }
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            if (board[0][i].isEmpty()) {
                return false;
            }
        }
        return true;
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
                player2 = new Player("| 🟡 ", "AI", true, difficulty);
                play();
                break;
            case 3:
                difficulty = interactionUtilisateur.getDifficultyLevel();
                player1 = new Player("| 🔴 ", "AI 1", true, difficulty);
                player2 = new Player("| 🟡 ", "AI 2", true, difficulty);
                play();
                break;
            default:
                vue.afficherMessage("Choix de mode de jeu invalide. Veuillez réessayer. 👺");
                gameMode();
        }
    }

    @Override
    public int[] generateRandomPosition() {
        Random random = new Random();
        int col;
        do {
            col = random.nextInt(size);
        } while (!isValidMove(0,col));
        return new int[]{0, col};
    }

    @Override
    public int[] getMoveFromPlayer(Player player) {
        int col = 0;
        while (true) {
            try {
                vue.afficherMessage(player.getName() + ", entrez le numéro de colonne (0 à 6) ou 404 pour quitter: ");
                col = interactionUtilisateur.scanner.nextInt();
                if (col == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }

                if (isValidMove(0, col)) {
                    break;
                } else {
                    vue.afficherMessage("Mouvement invalide. La colonne est pleine ou hors des limites. Veuillez réessayer. 💩");
                }
            } catch (InputMismatchException e) {
                vue.afficherMessage("Entrée invalide. Veuillez entrer un nombre.");
                interactionUtilisateur.scanner.next();
            }
        }
        return new int[]{0, col};
    }

    private int[] getMoveFromAI(Player player) {
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
        for (int j = 0; j < size; j++) {
            if (isValidMove(0, j)) {
                if (canWin(player1, j)) {
                    return new int[]{0, j};
                }
            }
        }
        return null;
    }

    private int[] getWinningMove() {
        for (int j = 0; j < size; j++) {
            if (isValidMove(0, j)) {
                if (canWin(player2, j)) {
                    return new int[]{0, j};
                }
            }
        }
        return null;
    }

    private boolean canWin(Player player, int col) {
        for (int i = 5; i >= 0; i--) {
            if (board[i][col].isEmpty()) {
                board[i][col].setOwner(player);
                boolean win = checkVictory(i, col, player);
                board[i][col].setOwner(null);
                if (win) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVictory(int row, int col, Player player) {
        return checkDirection(row, col, player, 1, 0) ||
               checkDirection(row, col, player, 0, 1) ||
               checkDirection(row, col, player, 1, 1) ||
               checkDirection(row, col, player, 1, -1); 
    }

    private boolean checkDirection(int row, int col, Player player, int dRow, int dCol) {
        int count = 0;
        for (int i = -3; i <= 3; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow >= 0 && newRow < 6 && newCol >= 0 && newCol < size && board[newRow][newCol].getOwner() == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    private int[] getStrategicMove() {
        for (int j = 0; j < size; j++) {
            if (isValidMove(0, j)) {
                if (hasAdjacentToken(5, j)) {
                    return new int[]{0, j};
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
                if (newRow >= 0 && newRow < 6 && newCol >= 0 && newCol < size) {
                    if (board[newRow][newCol].getOwner() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}