import ee.ut.dendroloj.Dendrologist;

import java.util.Objects;

public class Kodu5 {
    public static void main(String[] args) {

        KOTipp juur = null;
        juur = lisaKirje(juur, 3);
        juur = lisaKirje(juur, 1);
        juur = lisaKirje(juur, 6);
        juur = lisaKirje(juur, 4);
        juur = lisaKirje(juur, 8);
        juur = lisaKirje(juur, 7);
        juur = lisaKirje(juur, 9);
        kuvaKahendpuu(juur);
        juur = vasakpööre(juur);
        kuvaKahendpuu(juur);


    }

    static void kuvaKahendpuu(KOTipp juurTipp) {
        Dendrologist.drawBinaryTree(juurTipp, t -> ("[" + t.väärtus + "] " + t.x), t -> t.v, t -> t.p);
    }

    public static KOTipp lisaKirje(KOTipp juur, int väärtus) {
        juur = lisaKirjeRek(juur, väärtus);

//        int vasakKõrgus = 0;
//        int paremKõrgus = 0;
//        if (juur.v != null)
//            vasakKõrgus = juur.v.x;
//        if (juur.p != null)
//            paremKõrgus = juur.p.x;
//
//        if (vasakKõrgus - paremKõrgus >= 2)
//            juur = parempööre(juur);
//        else if (paremKõrgus - vasakKõrgus >= 2)
//            juur = vasakpööre(juur);

        return juur;
    }

    public static KOTipp lisaKirjeRek(KOTipp juur, int väärtus) {
        if (juur == null) { // Baasjuht: koht, kuhu tippu lisada, on tühi
            juur = new KOTipp(väärtus, null, null);
            juur.x = 1; // Tippul ei ole alluvat, sest ta on uus
            return juur;
        }

        if (väärtus < juur.väärtus) // Sammud: kas vasakule või paremale
            juur.v = lisaKirjeRek(juur.v, väärtus);
        else
            juur.p = lisaKirjeRek(juur.p, väärtus);

        juur.x = 1 + maksimaalneX(juur.v, juur.p); // Salvestame iga tippu kõrgus
        return juur;
    }

    public static KOTipp eemaldaKirje(KOTipp juur, int väärtus) {
        juur = eemaldaKirjeRek(juur, väärtus);
        if (juur == null)
            return null;

//        int vasakKõrgus = 0;
//        int paremKõrgus = 0;
//        if (juur.v != null)
//            vasakKõrgus = juur.v.x;
//        if (juur.p != null)
//            paremKõrgus = juur.p.x;
//
//        if (vasakKõrgus - paremKõrgus >= 2)
//            juur = parempööre(juur);
//        else if (paremKõrgus - vasakKõrgus >= 2)
//            juur = vasakpööre(juur);

        return juur;
    }

    public static KOTipp eemaldaKirjeRek(KOTipp juur, int väärtus) {
        if (juur == null)
            return null;

        if (väärtus < juur.väärtus)
            juur.v = eemaldaKirjeRek(juur.v, väärtus);
        else if (väärtus == juur.väärtus)
            juur = eemaldaJuur(juur);
        else
            juur.p = eemaldaKirjeRek(juur.p, väärtus);

        if (juur != null)
            juur.x = 1 + maksimaalneX(juur.v, juur.p); // Salvestame iga tippu kõrgus
        return juur;
    }

    public static KOTipp liidaAVLpuud(KOTipp avl1, KOTipp avl2) {
        throw new UnsupportedOperationException();
    }

    /*==================================================================================================================
     * ABIFUNKTSIOONID
     =================================================================================================================*/

    /**
     * Abi.
     * Otsib kõige sügavam vasak tipp ja tagastab tema.
     *
     * @note Eeldame, et algne sisend != null
     */
    public static int tagastaVähimKirje(KOTipp juur) {
        while (juur.v != null)
            juur = juur.v;
        return juur.väärtus;
    }

    /**
     * Abi.
     * Otsib kõige sügavam vasak tipp ja eemaldab tema. Asendab parema (suurema) tippuga, kui ta eksisteerib.
     *
     * @note Eeldame, et algne sisend != null
     */
    public static KOTipp eemaldaVähimKirje(KOTipp juur) {
        if (juur.v == null && juur.p == null) // Baasjuht 1: leitud tipp, millel ei ole alluvat
            return null;
        else if (juur.v == null) // Baasjuht 2: leitud tipp, millel on üks alluv (parem, ta on suurem)
            return juur.p;

        juur.v = eemaldaVähimKirje(juur.v);
        return juur;
    }

    /**
     * Abi.
     * Otsib paremas harus vähima kirje ja paneb tema juurtippu asemel, kui on olemas mõlemad alluvat.
     * Vastasel juhul lihtsalt asendab juurtippu tema alluvaga.
     *
     * @note Eeldame, et algne sisend != null
     */
    public static KOTipp eemaldaJuur(KOTipp juur) {
        // Lihtsustatud juhud: on olemas üks või null alluvat
        if (juur.v == null && juur.p == null)
            return null;
        else if (juur.v == null)
            return juur.p;
        else if (juur.p == null)
            return juur.v;

        else { // "Tavaline" juht: on olemas mõlemad alluvat
            // Eemaldame paremast harust vähima võtmega kirje
            int vähimParemal = tagastaVähimKirje(juur.p);
            juur.p = eemaldaVähimKirje(juur.p);
            // Salvestame eemaldatud kirje juurtippu
            juur.väärtus = vähimParemal;
            return juur;
        }
    }

    /**
     * Abi. Sooritab parempööre, tagastab uuendatud juur
     *
     * @note Eeldame, et algne sisend != null
     */
    public static KOTipp parempööre(KOTipp juurA) {
        KOTipp juurB = juurA.v;
        juurA.v = juurB.p;
        juurB.p = juurA;

        return juurB;
    }

    /**
     * Abi. Sooritab vasakpööre, tagastab uuendatud juur
     *
     * @note Eeldame, et algne sisend != null
     */
    public static KOTipp vasakpööre(KOTipp juurA) {
        KOTipp juurB = juurA.p;
        juurA.p = juurB.v;
        juurB.v = juurA;

        return juurB;
    }

    /**
     * Abi.
     * Tagastab kahe juure maksimaalne x-väärtus
     *
     */
    public static int maksimaalneX(KOTipp vasak, KOTipp parem) {
        if (vasak == null && parem == null)
            return 0;

        if (vasak != null && parem != null)
            return Math.max(vasak.x, parem.x);
        else
            return Objects.requireNonNullElseGet(vasak, () -> parem).x;
    }
}