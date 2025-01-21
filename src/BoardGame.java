public abstract class BoardGame {
    protected Vue vue;
    protected InteractionUtilisateur interactionUtilisateur;

    public BoardGame() {
        vue = new Vue();
        this.interactionUtilisateur = new InteractionUtilisateur();
    }
    public abstract boolean isValidMove(int row, int col);
    
}
