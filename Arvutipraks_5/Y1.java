package Arvutipraks_5;

import ee.ut.dendroloj.Dendrologist;

public class Y1 {

    public static void main(String[] args) {
        Tipp tipp = Abi.juhuslikPuu(12);
        Dendrologist.setUIScale(2);
        Abi.joonista(tipp);

        int mituTippu = loendaTipud(tipp);
        System.out.println("Tippe on kokku " + mituTippu + ".");

        int kõrgus = leiaKõrgus(tipp);
        System.out.println("Kõrgus on " + kõrgus + ".");

        System.out.println("keskjärjestus:");
        keskjärjestus(tipp);

        System.out.println("lehttipud:");
        lehttipud(tipp);
    }

    private static void lehttipud(Tipp t) {
        if (t != null && t.v == null && t.p == null)
            System.out.println(t.info);
        else if (t != null) {
            lehttipud(t.v);
            lehttipud(t.p);
        }
    }

    private static void keskjärjestus(Tipp t) {
        if (t == null) return;
        keskjärjestus(t.v);
        System.out.println(t.info);
        keskjärjestus(t.p);
    }

    private static int leiaKõrgus(Tipp tipp) {
        if (tipp == null) return 0;
        int vasemaHaruKõrgus = leiaKõrgus(tipp.v);
        int paremaHaruKõrgus = leiaKõrgus(tipp.p);
        return 1 + Math.max(vasemaHaruKõrgus, paremaHaruKõrgus);
    }

    private static int loendaTipud(Tipp tipp) {
        if (tipp == null) return 0;
        return 1 + loendaTipud(tipp.v) + loendaTipud(tipp.p);
    }
}
