package ProduitMatriceStrassenConcurrent;

import java.util.concurrent.RecursiveTask;

/**
 * Cette classe représente une tâche de calcul du produit de deux matrices en utilisant l'algorithme de Strassen.
 * Cet algorithme est mis en place avec Fork/Join, permettant une execution concurrente.
 */
public class ProduitMatricesStrassen extends RecursiveTask<Matrice> {

    // Taille minimale pour effectuer la multiplication directe sans diviser davantage
    private static final int tailleMin = 2;

    // Les matrices d'entrée
    private Matrice A;
    private Matrice B;

    // Taille des matrices (supposées carrées)
    private int taille;

    /**
     * Constructeur de la classe
     * @param A Matrice à gauche du produit
     * @param B Matrice à droite du produit
     */
    public ProduitMatricesStrassen(Matrice A, Matrice B) {
        this.A = A;
        this.B = B;
        this.taille = A.getTaille();
    }

    /**
     * Méthode principale de calcul du produit. Cette methode est surchargée depuis RecursiveTask.
     * @return Matrice résultante du produit des deux matrices d'entrées
     */
    @Override
    protected Matrice compute() {
        // Si la taille de la sous-matrice est suffisamment petite, on opère la multiplication directement
        if (taille <= tailleMin) {
            return A.multiplier(B);
        } else {
            // Diviser la taille de la matrice par 2 tant que la taille minimale n'est pas atteinte
            int tailleDivisee = taille / 2;

            // Diviser chaque matrice en quatre sous-matrices
            Matrice A11 = A.extraireSousMatrice(0, 0, tailleDivisee);
            Matrice A12 = A.extraireSousMatrice(0, tailleDivisee, tailleDivisee);
            Matrice A21 = A.extraireSousMatrice(tailleDivisee, 0, tailleDivisee);
            Matrice A22 = A.extraireSousMatrice(tailleDivisee, tailleDivisee, tailleDivisee);

            Matrice B11 = B.extraireSousMatrice(0, 0, tailleDivisee);
            Matrice B12 = B.extraireSousMatrice(0, tailleDivisee, tailleDivisee);
            Matrice B21 = B.extraireSousMatrice(tailleDivisee, 0, tailleDivisee);
            Matrice B22 = B.extraireSousMatrice(tailleDivisee, tailleDivisee, tailleDivisee);

            // Calculer les produits intermédiaires de l'algorithme de Strassen. C'est ici que s'opère la récursivité
            ProduitMatricesStrassen tache1 = new ProduitMatricesStrassen(A11.additionner(A22), B11.additionner(B22));
            ProduitMatricesStrassen tache2 = new ProduitMatricesStrassen(A21.additionner(A22), B11);
            ProduitMatricesStrassen tache3 = new ProduitMatricesStrassen(A11, B12.soustraire(B22));
            ProduitMatricesStrassen tache4 = new ProduitMatricesStrassen(A22, B21.soustraire(B11));
            ProduitMatricesStrassen tache5 = new ProduitMatricesStrassen(A11.additionner(A12), B22);
            ProduitMatricesStrassen tache6 = new ProduitMatricesStrassen(A21.soustraire(A11), B11.additionner(B12));
            ProduitMatricesStrassen tache7 = new ProduitMatricesStrassen(A12.soustraire(A22), B21.additionner(B22));

            // Soumettre les tâches à la file d'attente du pool de threads
            invokeAll(tache1, tache2, tache3, tache4, tache5, tache6, tache7);

            // Attendre la fin de l'exécution des tâches et récupérer les résultats sous forme de matrice pour continuer les opérations
            Matrice P1 = tache1.join();
            Matrice P2 = tache2.join();
            Matrice P3 = tache3.join();
            Matrice P4 = tache4.join();
            Matrice P5 = tache5.join();
            Matrice P6 = tache6.join();
            Matrice P7 = tache7.join();

            // Calculer les sous-matrices du résultat
            Matrice C11 = P1.additionner(P4).soustraire(P5).additionner(P7);
            Matrice C12 = P3.additionner(P5);
            Matrice C21 = P2.additionner(P4);
            Matrice C22 = P1.soustraire(P2).additionner(P3).additionner(P6);

            // Combiner les dernières sous-matrices pour former la matrice de résultat
            Matrice resultat = new Matrice(taille);
            resultat.combinerQuatreMatrices(C11, C12, C21, C22);

            // C'est une matrice est retournée. Soit vers le résultat final soit vers le résultat des produits P1 à P7 de chaque appel récursif
            return resultat;
        }
    }
}
