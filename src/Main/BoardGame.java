package Main;

public abstract class BoardGame {
    protected Vue vue;
    protected InteractionUtilisateur interactionUtilisateur;

    protected BoardGame(Vue vue, InteractionUtilisateur interactionUtilisateur) {
        this.vue = vue;
        this.interactionUtilisateur = interactionUtilisateur;
    }

    public abstract boolean isValidMove(int row, int col);

    public abstract void gameMode();
    
}