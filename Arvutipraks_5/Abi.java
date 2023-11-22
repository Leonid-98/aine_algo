package Arvutipraks_5;

import ee.ut.dendroloj.Dendrologist;

import java.util.concurrent.ThreadLocalRandom;

public class Abi {

    public static void joonista(Tipp tipp) {
        Dendrologist.drawBinaryTree(tipp, t -> t.info, t -> t.v, t -> t.p);
    }

    public static Tipp juhuslikPuu(int n) {
        if (n == 0) return null;
        ThreadLocalRandom juhus = ThreadLocalRandom.current();
        String juhuslikTäht = Character.toString(juhus.nextInt('A', 'Z' + 1));
        int vasakule = juhus.nextInt(n);
        return new Tipp(juhuslikTäht, juhuslikPuu(vasakule), juhuslikPuu(n - 1 - vasakule));
    }

    public static Tipp juhuslikAvaldis(int n) {
        ThreadLocalRandom juhus = ThreadLocalRandom.current();
        if (n == 0) {
            return new Tipp(Integer.toString((juhus.nextBoolean() ? 1 : -1) * juhus.nextInt(1, 40)));
        }
        String tehe = switch (juhus.nextInt(3)) {
            case 0 -> "+";
            case 1 -> "-";
            case 2 -> "*";
            default -> throw new AssertionError("Võimatu juhuarv");
        };
        int vasakule = juhus.nextInt(n);
        return new Tipp(tehe, juhuslikAvaldis(vasakule), juhuslikAvaldis(n - 1 - vasakule));
    }

    public static Tipp juhuSümmeetriline(int n) {
        if (n == 0) return null;
        if (n == 1) return new Tipp(juhutäht());
        String juhutäht = juhutäht();
        Tipp vasem = new Tipp(juhutäht);
        Tipp parem = new Tipp(juhutäht);
        Tipp tipp = new Tipp(juhutäht(), vasem, parem);
        ehitaSümmeetrilist(n - 2, vasem, parem);
        return tipp;
    }

    private static void ehitaSümmeetrilist(int n, Tipp vasem, Tipp parem) {
        if (n <= 0) return;
        int juhuarv = (int) (Math.random() * 4);
        if (juhuarv <= 2) {
            String juhutäht = juhutäht();
            vasem.v = new Tipp(juhutäht);
            parem.p = new Tipp(juhutäht);
            ehitaSümmeetrilist(juhuarv == 2 ? (int) (Math.random() * n) : n - 1, vasem.v, parem.p);
        }
        if (juhuarv >= 1) {
            String juhutäht = juhutäht();
            vasem.p = new Tipp(juhutäht);
            parem.v = new Tipp(juhutäht);
            ehitaSümmeetrilist(juhuarv == 1 ? (int) (Math.random() * n) : n - 1, vasem.p, parem.v);
        }
    }

    private static String juhutäht() {
        ThreadLocalRandom juhus = ThreadLocalRandom.current();
        return Character.toString(juhus.nextInt('A', 'Z' + 1));
    }
}
