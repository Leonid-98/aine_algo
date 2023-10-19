package Arvutipraks_3;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Arvutipraks_3 {
    public static void main(String[] args) {
//        System.out.println(palindrom("abb;;;2a"));
//        for (int i = 0; i < 10; i++) {
//            System.out.println(fiboMag(i));
//        }
        bitivektorid(5);
    }

    /* Ylesanne 1 */
    public static boolean palindrom(String s) {
        s = puhasta(s);
        Deque<Character> mag = new ArrayDeque<>();
        for (char c : s.toCharArray())
            mag.push(c);

        for (char c : s.toCharArray())
            if (mag.pop() != c)
                return false;

        return true;
    }

    private static String puhasta(String s) {
        s = s.toLowerCase();
        String tulemus = "";

        for (char c : s.toCharArray())
            tulemus = Character.isAlphabetic(c) ? tulemus + c : tulemus;

        return tulemus;
    }

    /* Ylesanne 2a. Magasiniga */
    public static int fiboMag(int n) {
        int tulemus = 0;
        Deque<Integer> mag = new ArrayDeque<>();
        mag.push(n);
        while (!mag.isEmpty()) {
            int arv = mag.pop();
            if (arv <= 1)
                tulemus += arv;
            else {
                mag.push(arv - 1);
                mag.push(arv - 2);
            }
        }
        return tulemus;
    }

    /* Ylesanne 3 */
    private static void bitivektoridMassiiviga(int n) {
        int[] massiiv = new int[n];
        while (true) {
            System.out.println(Arrays.toString(massiiv));
            int i;
            for (i = 0; i < n && massiiv[i] == 1; i++)
                massiiv[i] = 0;
            if (i == n) return;
            massiiv[i] = 1;
        }
    }

    private static void bitivektorid(int n) {
        Deque<Integer> magasin = new ArrayDeque<>();
        while (magasin.size() < n)
            magasin.push(0);

        while (true) {
            System.out.println(magasin);
            while (magasin.pop() == 1)
                if (magasin.isEmpty())
                    return;
            magasin.push(1);
            while (magasin.size() < n)
                magasin.push(0);
        }
    }


}
