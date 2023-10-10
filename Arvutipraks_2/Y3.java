package Arvutipraks_2;

import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.Grow;

public class Y3 {
    public static void main(String[] args) {
        Dendrologist.wakeUp();
        int n = 5;
        int k = 2;
        bitivektor(n, k);
    }

    private static void bitivektor(int n, int k) {
        System.out.println(bitivektorRek(1, n, k, ""));
    }

    /**
     * Väljastab ekraanile kõik bitivektorid, mis algavad sõnega "vektor"
     * ja millele on veel lisatud n sümbolit, millest k on ühed.
     *
     * @param n      Kui palju on veel vektorisse vaja sümboleid lisada.
     * @param k      Kui palju on veel vektorisse vaja ühetesid lisada.
     * @param vektor Jooksvalt koostatav bitivektor.
     */
    @Grow
    private static int bitivektorRek(int i, int n, int k, String vektor) {
        if (n == 0 && k == 0) {
            System.out.println(i + ": " + vektor);
            return 1;
        } else {
            int vasemal = 0;
            int paremal = 0;
            if (k > 0)
                vasemal = bitivektorRek(i, n - 1, k - 1, vektor + "1");
            if (n - k > 0)
                paremal = bitivektorRek(i + vasemal, n - 1, k, vektor + "0");
            return vasemal + paremal;
        }
    }
}
