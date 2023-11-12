package Arvutipraks_4;

import java.util.Arrays;
import java.util.HashSet;

public class Y1 {

    public static void main(String[] args) {
        int[] m1 = Abi.juhumassiiv(100000, 0, 1000000);
        int[] m2 = Abi.juhumassiiv(100000, 0, 1000000);

        //int ühiseid1 = ühiseidTsükliga(m1, m2);
        //System.out.println(ühiseid1);

        //int ühiseid2 = ühiseidHulgaga(m1, m2);
        //System.out.println(ühiseid2);

        long start = System.currentTimeMillis();
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (algus)
        int ühiseid1 = ühiseidTsükliga(m1, m2);
        System.out.println(ühiseid1);
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (lõpp)
        long stop = System.currentTimeMillis();
        System.out.println("Aega kulus " + (stop - start) + " milliskundit");

        start = System.currentTimeMillis();
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (algus)
        int ühiseid2 = ühiseidHulgaga(m1, m2);
        System.out.println(ühiseid2);
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (lõpp)
        stop = System.currentTimeMillis();
        System.out.println("Aega kulus " + (stop - start) + " milliskundit");
    }

    private static int ühiseidHulgaga(int[] m1, int[] m2) {
        HashSet<Integer> esimeses = new HashSet<>();
        HashSet<Integer> ühised = new HashSet<>();
        for (int element : m1)
            esimeses.add(element);
        for (int element : m2)
            if (esimeses.contains(element)) ühised.add(element);
        return ühised.size();
    }

    private static int ühiseidTsükliga(int[] m1, int[] m2) {
        int loendur = 0;
        for (int i = 0; i < m1.length; i++)
            if (esimeneÜhine(i, m1, m2)) loendur++;
        return loendur;
    }

    private static boolean esimeneÜhine(int i, int[] m1, int[] m2) {
        int element = m1[i];
        for (int j = 0; j < i; j++)
            if (m1[j] == element) return false;
        for (int j = 0; j < m2.length; j++)
            if (m2[j] == element) return true;
        return false;
    }
}