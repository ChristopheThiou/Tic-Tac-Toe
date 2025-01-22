public abstract class BoardGame {
    protected Vue vue;
    protected InteractionUtilisateur interactionUtilisateur;

    public BoardGame() {
        this.vue = new Vue();
        this.interactionUtilisateur = new InteractionUtilisateur();
    }

    public abstract boolean isValidMove(int row, int col);

    public abstract void play();
    public abstract void gameMode();

    protected void game() {
        int gameChoice = interactionUtilisateur.getGameChoice();
        BoardGame gameInstance;

        switch (gameChoice) {
            case 1:
                gameInstance = new TicTacToe();
                break;
            case 2:
                gameInstance = new PuissanceQuatre();
                break;
            case 3:
                gameInstance = new Gomoku();
                break;
            default:
                vue.afficherMessage("Choix de jeu invalide. Veuillez réessayer. 👺");
                game();
                return;
        }
        gameInstance.gameMode();
    }

    public static void main(String[] args) {
        System.out.println("Bienvenue dans le jeu Gomoku!");
        BoardGame boardGame = new BoardGame() {
            @Override
            public boolean isValidMove(int row, int col) {
                return false;
            }

            @Override
            public void play() {

            }
            public void gameMode() {

            }
        };
        boardGame.game();
    }
}