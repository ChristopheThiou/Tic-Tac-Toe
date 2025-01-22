public class TicTacToe extends BoardGame  {
    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.vue.afficherMessage("Bienvenue dans le jeu TicTacToe!");
        ticTacToe.gameMode();
    }

    protected final int col = 3;
    protected final int row = 3;
    protected Cell[][] board;
    protected InteractionUtilisateur interactionUtilisateur;
    Player player1;
    Player player2;

    protected TicTacToe() {
        board = new Cell[row][col];
        interactionUtilisateur = new InteractionUtilisateur();
        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < col ; j++) {
                board[i][j] = new Cell();
            }
        }
        player1 = new Player("| ❌ ", "Joueur 1", false);
        player2 = new Player("| ⭕ ", "Joueur 2", false);
    }

    @Override
    public void play() {
        vue.afficherMessage("Bienvenue dans le jeu Tic Tac Toe! 🤗");
        vue.afficherMessage("Joueur 1 avec X et Joueur 2 avec O");
        vue.afficherMessage("Vous pouvez quitter le jeu à tout moment en tapant 404 💀");

        Player currentPlayer = player1;
        while (true) {

            vue.display(board);

            int[] move = currentPlayer.getMove(this);
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
        if (row < 0 || row >= this.row || col < 0 || col >= this.col) {
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
        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < col ; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean isOver() {
        for (int i = 0; i < row ; i++) {
            if (board[i][0].getOwner() != null &&
                board[i][0].getOwner() == board[i][1].getOwner() &&
                board[i][1].getOwner() == board[i][2].getOwner()) {
                return true;
            }
        }

        for (int j = 0; j < col ; j++) {
            if (board[0][j].getOwner() != null &&
                board[0][j].getOwner() == board[1][j].getOwner() &&
                board[1][j].getOwner() == board[2][j].getOwner()) {
                return true;
            }
        }

        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < col ; j++) {
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

    @Override
    public void gameMode() {
        int choice = interactionUtilisateur.getGameMode();
        switch (choice) {
            case 1:
                play();
                break;
            case 2:
                player2 = new Player("| ⭕ ", "AI", true);
                play();
                break;
            case 3:
                player1 = new Player("| ❌ ", "AI 1", true);
                player2 = new Player("| ⭕ ", "AI 2", true);
                play();
                break;
            default:
                vue.afficherMessage("Choix invalide. Veuillez réessayer. 👺");
                gameMode();
        }
    }
}