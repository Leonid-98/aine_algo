package Arvutipraks_4;

import java.util.LinkedList;

public class Abi {
    public static int[] juhumassiiv(int n, int min, int max) {
        int[] massiiv = new int[n];
        for (int i = 0; i < n; i++)
            massiiv[i] = min + (int) (Math.random() * (max + 1));
        return massiiv;
    }

    public static LinkedList<Integer> juhulinkedlist(int n, int min, int max) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int arv : Abi.juhumassiiv(n, min, max))
            list.add(arv);
        return list;
    }
}

