package Arvutipraks_5;

import ee.ut.dendroloj.Dendrologist;

public class Y5 {

    public static void main(String[] args) {
        Dendrologist.setUIScale(3);
        int h = 5;
        KOTipp tipp = juhupuu(h);
        KOTipp.kuvaKahendotsimispuu(tipp);
    }

    public static KOTipp juhupuu(int h) {
        if (h == 0) return null;
        else if (h == 1) return new KOTipp(0);
        else if (Math.random() < 1.0 / 3.0)
            return new KOTipp(0, juhupuu(h - 1), juhupuu(h - 1));
        else if (Math.random() < 0.5)
            return new KOTipp(0, juhupuu(h - 1), juhupuu(h - 2));
        else
            return new KOTipp(0, juhupuu(h - 2), juhupuu(h - 1));
    }
}
