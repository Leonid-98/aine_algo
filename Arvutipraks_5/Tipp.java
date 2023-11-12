package Arvutipraks_5;
import ee.ut.dendroloj.Dendrologist;

import java.util.concurrent.ThreadLocalRandom;

public class Tipp {
    String info;
    Tipp v;
    Tipp p;
    int x; // abiväli

    Tipp(String info, Tipp v, Tipp p) {
        this.info = info;
        this.v = v;
        this.p = p;
    }

    Tipp(String info) {
        this.info = info;
    }

    static void kuvaKahendpuu(Tipp juurTipp) {
        Dendrologist.drawBinaryTree(juurTipp, t -> t.info, t -> t.v, t -> t.p);
    }

    static Tipp juhuslikPuu(int n) {
        if (n == 0) return null;
        ThreadLocalRandom juhus = ThreadLocalRandom.current();
        String juhuslikTäht = Character.toString(juhus.nextInt('A', 'Z' + 1));
        int vasakule = juhus.nextInt(n);
        return new Tipp(juhuslikTäht, juhuslikPuu(vasakule), juhuslikPuu(n - 1 - vasakule));
    }
}
