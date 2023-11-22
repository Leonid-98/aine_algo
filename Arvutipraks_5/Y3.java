package Arvutipraks_5;

import ee.ut.dendroloj.Dendrologist;

public class Y3 {

    public static void main(String[] args) {
        Tipp t = Abi.juhuslikPuu(7);
        Dendrologist.setUIScale(3);
        Abi.joonista(t);

        kirjuta(t);
    }

    private static void kirjuta(Tipp t) {
        kirjutaRek(t, 0, "");
    }

    private static void kirjutaRek(Tipp t, int sügavus, String teekond) {
        if (t == null) return;
        kirjutaRek(t.p, sügavus + 1, " ".repeat(sügavus) + "┏");
        System.out.println(teekond + t.info);
        kirjutaRek(t.v, sügavus + 1, " ".repeat(sügavus) + "┗");
    }
}
