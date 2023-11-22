package Arvutipraks_5;

import ee.ut.dendroloj.Dendrologist;

import java.util.Arrays;

public class Y6 {

    public static void main(String[] args) {
        Dendrologist.setUIScale(3);
        KOTipp tipp = Y5.juhupuu(5);
        int[] täidis = KOTipp.juhusisu(tipp);
        System.out.println(Arrays.toString(täidis));

        täida(tipp, täidis);

        KOTipp.kuvaKahendotsimispuu(tipp);
    }

    public static void täida(KOTipp tipp, int[] täidis) {
        täidaRek(tipp, 0, täidis);
    }

    private static int täidaRek(KOTipp tipp, int i, int[] täidis) {
        if (tipp == null) return 0;
        int vasemal = täidaRek(tipp.v, i, täidis);
        tipp.väärtus = täidis[i + vasemal];
        int paremal = täidaRek(tipp.p, i + vasemal + 1, täidis);
        return 1 + vasemal + paremal;
    }
}
