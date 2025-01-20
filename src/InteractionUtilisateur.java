import java.util.InputMismatchException;
import java.util.Scanner;

public class InteractionUtilisateur {
    protected Scanner scanner;
    protected Vue vue;

    protected InteractionUtilisateur() {
        scanner = new Scanner(System.in);
        vue = new Vue();
    }

    protected int[] getMoveFromPlayer(Player player, TicTacToe game) {
        int row = 0, col = 0;
        while (true) {
            try {
                vue.afficherMessage(player.getName() + ", entrez le numéro de ligne (0 à 2) ou 404 pour quitter: ");
                row = scanner.nextInt();
                if (row == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }
                vue.afficherMessage(player.getName() + ", entrez le numéro de colonne (0 à 2) ou 404 pour quitter: ");
                col = scanner.nextInt();
                if (col == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur.");
                    System.exit(0);
                }

                if (game.isValidMove(row, col)) {
                    break;
                } else {
                    vue.afficherMessage("Mouvement invalide. La case est déjà occupée ou hors des limites. Veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                vue.afficherMessage("Entrée invalide. Veuillez entrer un nombre.");
                scanner.next();
            }
        }
        return new int[]{row, col};
    }

    protected int getGameMode() {
        int choice = 0;
        while (true) {
            try {
                vue.afficherMessage("Choisissez le mode de jeu 🥸 🥸 : ");
                vue.afficherMessage("1. Joueur vs Joueur 🧠");
                vue.afficherMessage("2. Joueur vs IA 🧠🤖");
                vue.afficherMessage("3. IA vs IA 🤖");
                vue.afficherMessage("404. Pour quitter 🪦");
                choice = scanner.nextInt();
                if (choice == 404) {
                    vue.afficherMessage("Partie terminée par l'utilisateur. 🫵");
                    System.exit(0);
                }

                if (choice >= 1 && choice <= 3) {
                    break;
                } else {
                    vue.afficherMessage("Choix invalide. Veuillez entrer un nombre entre 1 et 3.");
                }
            } catch (InputMismatchException e) {
                vue.afficherMessage("Entrée invalide. Veuillez entrer un nombre. 🤯");
                scanner.next();
            }
        }
        return choice;
    }
}