package ProduitMatriceStrassenConcurrent;

/**
 * Cette classe représente une matrice carrée d'une certaine taille. Elle permet des opérations de base sur les matrices.
 */
public class Matrice {
    // Attributs
    private int[][] tableau;  // Le tableau de valeurs représentant la matrice
    private int taille;        // La taille (nombre de lignes et de colonnes) de la matrice

    // Constructeurs

    /**
     * Constructeur qui crée une matrice carrée à partir de sa taille. Les valeurs sont initialisées à zéro.
     * @param taille Taille de la matrice (nombre de lignes et de colonnes).
     */
    public Matrice(int taille) {
        this.taille = taille;
        this.tableau = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                tableau[i][j] = 0;
            }
        }
    }

    /**
     * Constructeur qui crée une matrice à partir de données contenues dans un tableau de valeurs. La taille s'ajuste en conséquence.
     * @param tableau Tableau de valeurs représentant la matrice.
     */
    public Matrice(int[][] tableau) {
        this.tableau = tableau;
        this.taille = tableau.length;
    }

    // Getter

    /**
     * Retourne la taille de la matrice (nombre de lignes et de colonnes).
     * @return Taille de la matrice.
     */
    public int getTaille(){
        return taille;
    }


    // Autres méthodes

    /**
     * Retourne une nouvelle sous-matrice de la matrice actuelle.
     * @param debutLigne Ligne de départ de la sous-matrice.
     * @param debutColonne Colonne de départ de la sous-matrice.
     * @param nouvelleTaille Nouvelle taille de la sous-matrice.
     * @return Nouvelle matrice qui est une portion de la matrice actuelle.
     */
    public Matrice extraireSousMatrice(int debutLigne, int debutColonne, int nouvelleTaille) {
        int[][] nouvelleMatrice = new int[nouvelleTaille][nouvelleTaille];

        for (int i = 0; i < nouvelleTaille; i++) {
            for (int j = 0; j < nouvelleTaille; j++) {
                nouvelleMatrice[i][j] = this.tableau[debutLigne + i][debutColonne + j];
            }
        }

        return new Matrice(nouvelleMatrice);
    }

    /**
     * Retourne une nouvelle matrice resultant de la différence entre la matrice actuelle et une seconde matrice.
     * @param B Matrice à soustraire.
     * @return Nouvelle matrice résultant de la soustraction.
     */
    public Matrice soustraire(Matrice B) {
        int[][] resultat = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                resultat[i][j] = this.tableau[i][j] - B.tableau[i][j];
            }
        }
        return new Matrice(resultat);
    }

    /**
     * Retourne une nouvelle matrice resultant de la somme entre la matrice actuelle et une seconde matrice.
     * @param B Matrice à additionner.
     * @return Nouvelle matrice résultant de l'addition.
     */
    public Matrice additionner(Matrice B) {
        int[][] resultat = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                resultat[i][j] = this.tableau[i][j] + B.tableau[i][j];
            }
        }
        return new Matrice(resultat);
    }

    /**
     * Retourne une nouvelle matrice resultant du produit matriciel direct entre la matrice actuelle et une seconde matrice.
     * @param B Matrice à multiplier.
     * @return Nouvelle matrice résultant du produit matriciel direct.
     */
    public Matrice multiplier(Matrice B) {
        // Implémentation de la multiplication simple, à adapter selon vos besoins.
        int[][] resultat = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                for (int k = 0; k < taille; k++) {
                    resultat[i][j] += this.tableau[i][k] * B.tableau[k][j];
                }
            }
        }
        return new Matrice(resultat);
    }

    /**
     * Combine quatre sous-matrices pour insérer leurs valeurs dans la matrice actuelle de taille double.
     * @param C11 Sous-matrice en haut à gauche.
     * @param C12 Sous-matrice en haut à droite.
     * @param C21 Sous-matrice en bas à gauche.
     * @param C22 Sous-matrice en bas à droite.
     */
    public void combinerQuatreMatrices(Matrice C11, Matrice C12, Matrice C21, Matrice C22) {
        for (int i = 0; i < taille / 2; i++) {
            for (int j = 0; j < taille / 2; j++) {
                tableau[i][j] = C11.tableau[i][j];
                tableau[i][j + taille / 2] = C12.tableau[i][j];
                tableau[i + taille / 2][j] = C21.tableau[i][j];
                tableau[i + taille / 2][j + taille / 2] = C22.tableau[i][j];
            }
        }
    }

    /**
     * Retourne la matrice sous forme de chaîne de caractères.
     * @return Matrice sous forme de chaîne de caractères.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                sb.append(tableau[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}