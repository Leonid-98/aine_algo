package Arvutipraks_3;

import java.util.ArrayDeque;
import java.util.Deque;

public class Arvutipraks_3 {
    public static void main(String[] args) {
//        System.out.println(palindrom("abb;;;2a"));
//        for (int i = 0; i < 10; i++) {
//            System.out.println(fiboMag(i));
//        }

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


}
