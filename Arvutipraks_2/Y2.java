package Arvutipraks_2;

import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.Grow;

public class Y2 {

    public static void main(String[] args) {
        Dendrologist.setUIScale(3);
        Dendrologist.wakeUp();
        int n = 4;
        int tulemus = lahutused(n);
        System.out.println("Lahutusi on kokku: " + tulemus);
    }

    private static int lahutused(int n) {
        if (n > 0) return lahutusedRek(n, "");
        else return 0;
    }

    @Grow
    /**
     * Väljastame ekraanile kõik summad, mis algavad "liidetis" sõnega ja millele
     * on 1-sid ja 0-sid juurde liidetud kokku n võrra.
     * @param n Kui palju on summasse veel vaja juurde liita.
     * @param liidetis Liitmistehe.
     * @return Kui mitmel viisil on võimalik liidetisele ühetised ja nullisid
     * liita nii, et liidame juurde täpselt n.
     */
    private static int lahutusedRek(int n, String liidetis) {
        if (n == 0) {
            System.out.println(liidetis);
            return 1;
        } else {
            int loendur = 0;
            loendur += lahutusedRek(n - 1, liidetis + "+1");
            if (n >= 2)
                loendur += lahutusedRek(n - 2, liidetis + "+2");
            return loendur;
        }
    }
}
