package ProduitMatriceStrassenConcurrent;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        int[][] donneesA = {
                {1, 2, 3, 4, 5, 6, 7, 8},
                {9, 10, 11, 12, 13, 14, 15, 16},
                {17, 18, 19, 20, 21, 22, 23, 24},
                {25, 26, 27, 28, 29, 30, 31, 32},
                {33, 34, 35, 36, 37, 38, 39, 40},
                {41, 42, 43, 44, 45, 46, 47, 48},
                {49, 50, 51, 52, 53, 54, 55, 56},
                {57, 58, 59, 60, 61, 62, 63, 64}
        };

        int[][] donneesB = {
                {2, 0, 0, 0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 2}
        };

        Matrice matriceA = new Matrice(donneesA);
        Matrice matriceB = new Matrice(donneesB);

        //
        ForkJoinPool pool = new ForkJoinPool();

        ProduitMatricesStrassen produitMatricesStrassen = new ProduitMatricesStrassen(matriceA, matriceB);
        Matrice resultat = pool.invoke(produitMatricesStrassen);



        System.out.println("Matrice A:");
        System.out.println(matriceA);

        System.out.println("Matrice B:");
        System.out.println(matriceB);

        System.out.println("Résultat de la multiplication matricielle:");
        System.out.println(resultat);
    }
}
