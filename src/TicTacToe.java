public class TicTacToe {
    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.gameMode();
    }

    protected final int size = 4;
    protected Cell[][] board;
    protected InteractionUtilisateur interactionUtilisateur;
    protected Vue vue;
    Player player1;
    Player player2;

    protected TicTacToe() {
        board = new Cell[size][size];
        interactionUtilisateur = new InteractionUtilisateur();
        vue = new Vue();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                board[i][j] = new Cell();
            }
        }
        player1 = new Player("| X ", "Joueur 1", false);
        player2 = new Player("| O ", "Joueur 2", false);
    }

    protected void play() {
        vue.afficherMessage("Bienvenue dans le jeu Tic Tac Toe! 🤗");
        vue.afficherMessage("Joueur 1 avec X et Joueur 2 avec O");
        vue.afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");

        Player currentPlayer = player1;
        while (true) {

            vue.display(board, size);

            int[] move = currentPlayer.getMove(this);
            setOwner(move[0], move[1], currentPlayer);
            vue.afficherMessage(currentPlayer.getName() + " joue en position: (" + move[0] + ", " + move[1] + ")");

            if (isBoardFull()) {
                vue.afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                break;
            }

            if (isOver()) {
                vue.display(board, size);
                vue.afficherMessage("Le jeu est terminé! " + currentPlayer.getName() + " a gagné! 🔆");
                break;
            }

            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }

    protected boolean isValidMove(int row, int col) {
        if (row < 0 || row >= size - 1 || col < 0 || col >= size - 1) {
            return false;
        }
        if (!board[row][col].isEmpty()) {
            return false;
        }
        return true;
    }

    protected void setOwner(int row, int col, Player player) {
        if (isValidMove(row, col)) {
            board[row][col].setOwner(player);
        } else {
            throw new IllegalArgumentException("Invalid cell coordinates 💩");
        }
    }

    protected boolean isBoardFull() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean isOver() {
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

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (board[0][0].getOwner() != null &&
                    board[0][0].getOwner() == board[1][1].getOwner() &&
                    board[1][1].getOwner() == board[2][2].getOwner()) {
                    return true;
                }
                if (board[0][2].getOwner() != null &&
                    board[0][2].getOwner() == board[1][1].getOwner() &&
                    board[1][1].getOwner() == board[2][0].getOwner()) {
                    return true;
                }
            }
        }

        return false;
}

    protected void gameMode() {
        int choice = interactionUtilisateur.getGameMode();
        switch (choice) {
            case 1:
                play();
                break;
            case 2:
                player2 = new Player("| O ", "AI", true);
                play();
                break;
            case 3:
                player1 = new Player("| X ", "AI 1", true);
                player2 = new Player("| O ", "AI 2", true);
                play();
                break;
            default:
                vue.afficherMessage("Choix invalide. Veuillez réessayer.");
                gameMode();
        }
    }
}