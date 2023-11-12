import ee.ut.dendroloj.Dendrologist;

import java.util.Objects;

public class Kodu5 {
    public static void main(String[] args) {

        KOTipp juur = null;
//        juur = lisaKirje(juur, 700);
//        juur = lisaKirje(juur, 500);
//        juur = lisaKirje(juur, 900);
//        juur = lisaKirje(juur, 300);
//        juur = lisaKirje(juur, 1100);
//        juur = lisaKirje(juur, 600);
//        juur = lisaKirje(juur, 800);
//        juur = lisaKirje(juur, 250);
//        juur = lisaKirje(juur, 350);
//        juur = lisaKirje(juur, 550);
//        juur = lisaKirje(juur, 650);
//
//        juur = lisaKirje(juur, 275);
//        juur = lisaKirje(juur, 270);
//        juur = lisaKirje(juur, 280);
//
//        kuvaKahendpuu(juur);
//        System.out.println(tagastaVähimKirje(juur));
//        juur = eemaldaVähimKirje(juur);
//        kuvaKahendpuu(juur);

        juur = lisaKirje(juur, 1);
        juur = lisaKirje(juur, 2);
        juur = lisaKirje(juur, 3);
        juur = lisaKirje(juur, 4);
        kuvaKahendpuu(juur);
        juur = eemaldaJuur(juur);
        kuvaKahendpuu(juur);

    }

    static void kuvaKahendpuu(KOTipp juurTipp) {
        Dendrologist.drawBinaryTree(juurTipp, t -> ("[" + t.väärtus + "] " + t.x), t -> t.v, t -> t.p);
    }

    public static KOTipp lisaKirje(KOTipp juur, int väärtus) {
        if (juur == null) { // Baasjuht: koht, kuhu tippu lisada, on tühi
            juur = new KOTipp(väärtus, null, null);
            juur.x = 1; // Tippul ei ole alluvat, sest ta on uus
            return juur;
        }

        if (väärtus < juur.väärtus) // Sammud: kas vasakule või paremale
            juur.v = lisaKirje(juur.v, väärtus);
        else
            juur.p = lisaKirje(juur.p, väärtus);

        juur.x = 1 + maksimaalneX(juur.v, juur.p); // Salvestame iga tippu kõrgus
        return juur;
    }

    /**
     * Abi. Otsib kõige sügavam vasak tipp ja tagastab tema.
     *
     * @note Eeldame, et algne sisend != null
     */
    public static int tagastaVähimKirje(KOTipp juur) {
        if (juur.v == null)
            return juur.väärtus;

        return tagastaVähimKirje(juur.v);
    }

    /**
     * Abi. Otsib kõige sügavam vasak tipp ja eemaldab tema. Asendab parema (suurema) tippuga, kui ta eksisteerib
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

    public static KOTipp eemaldaJuur(KOTipp juur) {
        return null;

    }

    public static KOTipp liidaAVLpuud(KOTipp avl1, KOTipp avl2) {
        throw new UnsupportedOperationException();
    }

    /**
     * Abi. Tagastab kahe juure maksimaalne x-väärtus, arvestades olukokrra, kui üks nendest on {@code null}
     */
    public static int maksimaalneX(KOTipp vasak, KOTipp parem) {
        if (vasak != null && parem != null)
            return Math.max(vasak.x, parem.x);
        else
            return Objects.requireNonNullElseGet(vasak, () -> parem).x;
    }

}