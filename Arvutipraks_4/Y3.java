package Arvutipraks_4;

import java.util.Arrays;
import java.util.HashMap;

public class Y3 {

    public static void main(String[] args) {
        int n = 500000;
        int[] arvud = Abi.juhumassiiv(n, 0, 100000000);
        System.out.println(Arrays.toString(arvud));

        /*int[] summa1 = summaTsükliga(n, arvud);
        if (summa1 != null) {
            System.out.println(Arrays.toString(summa1));
            System.out.println(arvud[summa1[0]]);
            System.out.println(arvud[summa1[1]]);
            System.out.println(arvud[summa1[0]] + arvud[summa1[1]]);
        }*/

        /*int[] summa2 = summaPaisktabeliga(n, arvud);
        if (summa2 != null) {
            System.out.println(Arrays.toString(summa2));
            System.out.println(arvud[summa2[0]]);
            System.out.println(arvud[summa2[1]]);
            System.out.println(arvud[summa2[0]] + arvud[summa2[1]]);
        }*/

        long start = System.currentTimeMillis();
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (algus)
        int[] summa1 = summaTsükliga(n, arvud);
        if (summa1 != null) {
            System.out.println(Arrays.toString(summa1));
            System.out.println(arvud[summa1[0]]);
            System.out.println(arvud[summa1[1]]);
            System.out.println(arvud[summa1[0]] + arvud[summa1[1]]);
        }
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (lõpp)
        long stop = System.currentTimeMillis();
        System.out.println("Aega kulus " + (stop - start) + " milliskundit");

        start = System.currentTimeMillis();
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (algus)
        int[] summa2 = summaPaisktabeliga(n, arvud);
        if (summa2 != null) {
            System.out.println(Arrays.toString(summa2));
            System.out.println(arvud[summa2[0]]);
            System.out.println(arvud[summa2[1]]);
            System.out.println(arvud[summa2[0]] + arvud[summa2[1]]);
        }
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (lõpp)
        stop = System.currentTimeMillis();
        System.out.println("Aega kulus " + (stop - start) + " milliskundit");
    }

    private static int[] summaPaisktabeliga(int n, int[] arvud) {
        HashMap<Integer, Integer> kohatud = new HashMap<>();
        for (int i = 0; i < arvud.length; i++) {
            int element = arvud[i];
            int vaste = n - element;
            if (kohatud.containsKey(vaste)) return new int[]{kohatud.get(vaste), i};
            else kohatud.put(element, i);
        }
        return null;
    }

    private static int[] summaTsükliga(int n, int[] arvud) {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++)
                if (arvud[i] + arvud[j] == n) return new int[]{i, j};
        return null;
    }
}
