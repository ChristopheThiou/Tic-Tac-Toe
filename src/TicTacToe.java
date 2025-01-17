public class TicTacToe {
    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.gameMode();
    }

    public static final int size = 4;
    public Cell[][] board;
    public InteractionUtilisateur interactionUtilisateur;
    public Vue vue;
    Player player1;
    Player player2;
    ArtificialPlayer ai1;
    ArtificialPlayer ai2;

    public TicTacToe() {
        board = new Cell[size][size];
        interactionUtilisateur = new InteractionUtilisateur();
        vue = new Vue();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
        player1 = new Player.Player1();
        player2 = new Player.Player2();
        ai1 = new ArtificialPlayer.Ai1();
        ai2 = new ArtificialPlayer.Ai2();
    }

    public void play() {
        vue.afficherMessage("Bienvenue dans le jeu Tic Tac Toe!");
        vue.afficherMessage("Joueur 1 avec X et Joueur 2 avec O");

        Player currentPlayer = player1;
        while (true) {
            vue.afficherPlateau(board, size);
            if (isBoardFull()) {
                vue.afficherMessage("Le jeu est terminé! Toutes les cases sont remplies.");
                break;
            }
            int[] move = interactionUtilisateur.getMoveFromPlayer(currentPlayer);
            setOwner(move[0], move[1], currentPlayer);
            if (isOver()) {
                vue.afficherPlateau(board, size);
                vue.afficherMessage("Le jeu est terminé! " + currentPlayer.getName() + " a gagné!");
                break;
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }

    public boolean isValidMove(int row, int col) {
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

    public void setOwnerAi(int row, int col, ArtificialPlayer ai) {
        if (isValidMove(row, col)) {
            board[row][col].setOwner(ai);
            board[row][col].setValue(ai.getSymbol());
        } else {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isOver() {
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

    public void gameMode() {
        int choice = interactionUtilisateur.getGameMode();
        switch (choice) {
            case 1:
                play();
                break;
            case 2:
                interactionUtilisateur.playWithAI(this);
                break;
            case 3:
                interactionUtilisateur.playAIvsAI(this);
                break;
            default:
                vue.afficherMessage("Choix invalide. Veuillez réessayer.");
                gameMode();
        }
    }
}