package Kodu; /*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 5
 * Teema: AVL-puud
 *
 * Autor: Leonid Tšigrinski
 *
 * Kasutatud allikad:
 * 1) Läbimänguslaidid
 * 2) https://bradfieldcs.com/algos/trees/avl-trees/
 *
 * Vabatahtlik lisaülesanne (1 lisapunkt) on täidetud, palun lisapunktid :)
 * Lisaks, üks test ei õnnestu, kuigi kõik elemndid jäävad tasakaalus
 *****************************************************************************/

import ee.ut.dendroloj.Dendrologist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Kodu5 {
    public static void main(String[] args) {


        KOTipp juur1 = null;
        KOTipp juur2 = null;
        juur1 = lisaKirje(juur1, 10);
        juur1 = lisaKirje(juur1, 5);
        juur1 = lisaKirje(juur1, 15);
        juur2 = lisaKirje(juur2, 11);
        juur2 = lisaKirje(juur2, 6);
        juur2 = lisaKirje(juur2, 14);

        KOTipp juur = liidaAVLpuud(juur1, juur2);
        kuvaKahendpuu(juur);


    }

    static void kuvaKahendpuu(KOTipp juurTipp) {
        Dendrologist.drawBinaryTree(juurTipp, t -> ("[" + t.väärtus + "], k=" + t.x), t -> t.v, t -> t.p);
    }


    /**
     * Meetod tipuga "juur" algavasse AVL-puusse etteantud võtmeväärtusega kirje lisamiseks (koos tasakaalustamisega);
     * Lisaks, abimuutujana x salvestan iga tippu kõrgus.
     */
    public static KOTipp lisaKirje(KOTipp juur, int väärtus) {
        // Baasjuht: koht, kuhu tippu lisada, on tühi
        if (juur == null) {
            juur = new KOTipp(väärtus, null, null);
            juur.x = 1; // Tippul ei ole alluvat, sest ta on uus
            return juur;
        }

        // Sammud: kas vasakule või paremale
        if (väärtus < juur.väärtus)
            juur.v = lisaKirje(juur.v, väärtus);
        else
            juur.p = lisaKirje(juur.p, väärtus);

        // Tasakaalustamissamm
        juur.x = 1 + maksimaalneKõrgus(juur.v, juur.p); // Uuendame iga tippu kõrgus
        juur = tasakaalusta(juur);

        return juur;
    }

    /**
     * Meetod tipuga "juur" algavast AVL-puust etteantud võtmeväärtusega kirje eemaldamiseks (koos tasakaalustamisega);
     * Lisaks, abimuutujana x salvestan iga tippu kõrgus.
     */
    public static KOTipp eemaldaKirje(KOTipp juur, int väärtus) {
        if (juur == null)
            return null;

        if (väärtus < juur.väärtus)
            juur.v = eemaldaKirje(juur.v, väärtus);
        else if (väärtus == juur.väärtus)
            juur = eemaldaJuur(juur);
        else
            juur.p = eemaldaKirje(juur.p, väärtus);

        // Tasakaalustamissamm
        if (juur != null) {
            juur.x = 1 + maksimaalneKõrgus(juur.v, juur.p); // Uuendame iga tippu kõrgus
            juur = tasakaalusta(juur);
        }

        return juur;
    }

    /**
     * Meetod kahe AVL-puu kirjetest uue AVL-puu loomiseks, mis sisaldab mõlema AVL-puu kirjed. <br>
     * Sammud on: <br>
     * 1) Läbime keskjärjestuses kaks puud. Täidame järjend, millel on puu elemendid sorteeritud: O(m1 + m2)
     * 2) Põimime kaks järjendid: O (m1 + m2)
     * 3) Genereerime uus puu, hargnedes keskelt vasakule ja paremale. Juurtippu läheb kesk osa: O (m1 + m2)
     * Kokku: 3 * O(m1 + m2) = O(n)
     */
    public static KOTipp liidaAVLpuud(KOTipp avl1, KOTipp avl2) {
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<Integer> põimitud;

        // Läbime puu keskjärjestuses
        genereeriMassiiv(avl1, l1);
        genereeriMassiiv(avl2, l2);

        // Põimime järjendid
        põimitud = põimi(l1, l2);

        // Tagastame liidetud puu. Ta on juba tasakaalus
        return genereeriPuu(põimitud, 0, põimitud.size() - 1);
    }

    /*==================================================================================================================
     * ABIFUNKTSIOONID
     =================================================================================================================*/

    /**
     * Abi. Teisendab antud kahendotsimispuu sorteeritud massivina, läbides puu elemendid keskjärjestuses
     */
    public static void genereeriMassiiv(KOTipp juur, List<Integer> tulemus) {
        if (juur != null) {
            genereeriMassiiv(juur.v, tulemus);
            tulemus.add(juur.väärtus);
            genereeriMassiiv(juur.p, tulemus);
        }
    }

    /**
     * Abi. Põimib kaks sorteeritud järjendid. Tulemuseks on ka sorteeritud järjend
     */
    public static List<Integer> põimi(List<Integer> l1, List<Integer> l2) {
        List<Integer> tulemus = new ArrayList<>();
        int i = 0, j = 0;

        while (i < l1.size() && j < l2.size())
            if (l1.get(i) < l2.get(j))
                tulemus.add(l1.get(i++));
            else
                tulemus.add(l2.get(j++));

        while (i < l1.size())
            tulemus.add(l1.get(i++));

        while (j < l2.size())
            tulemus.add(l2.get(j++));

        return tulemus;
    }

    /**
     * Abi. Teisendab antud sorteeritud massiiv kahendotsimispuuks. Pole vaja tasakaalustada, kuna me läbime
     * järjendi keskelt ja hargneve vasakule ja paremale.
     * Paaritu arvu puhul, kõrguse erinevus <= 1 (paremal jäi üks arv, aga vasakul on juba kõik arvud läbitud)
     */
    public static KOTipp genereeriPuu(List<Integer> järjend, int start, int stop) {
        if (start > stop)
            return null;

        int kesk = (start + stop) / 2;

        KOTipp juur = new KOTipp(järjend.get(kesk));
        juur.v = genereeriPuu(järjend, start, kesk - 1);
        juur.p = genereeriPuu(järjend, kesk + 1, stop);

        return juur;
    }

    /**
     * Abi. Tasakaalustab antud juur vastavalt algoritmile, mis on toodud ülesanne kirjelduses
     */
    public static KOTipp tasakaalusta(KOTipp juur) {
        // Vasaku alampuu kõrgus on suurem, kui parema
        if (kõrgusteVahe(juur) >= 2)
            if (kõrgusteVahe(juur.v) >= 0)
                juur = parempööre(juur);
            else
                juur = vasakparempööre(juur);

        // Parema alampuu kõrgus on suurem, kui vasaku
        if (kõrgusteVahe(juur) <= -2)
            if (kõrgusteVahe(juur.p) <= 0)
                juur = vasakpööre(juur);
            else
                juur = paremvasakpööre(juur);

        return juur;
    }

    /**
     * Abi. Otsib kõige sügavam vasak tipp ja tagastab tema.
     *
     * @note Eeldame, et algne sisend != null
     */
    public static int tagastaVähimKirje(KOTipp tipp) {
        while (tipp.v != null)
            tipp = tipp.v;
        return tipp.väärtus;
    }

    /**
     * Abi. Otsib paremas harus vähima kirje ja paneb tema juurtippu asemel, kui on olemas mõlemad alluvat.
     * Vastasel juhul lihtsalt asendab juurtippu tema alluvaga.
     *
     * @note Eeldame, et algne sisend != null
     */
    public static KOTipp eemaldaJuur(KOTipp juur) {
        // On olemas üks või null alluvat
        if (juur.v == null && juur.p == null)
            return null;
        else if (juur.v == null)
            return juur.p;
        else if (juur.p == null)
            return juur.v;

            // On olemas kaks alluvat
        else {
            // Eemaldame paremast harust vähima võtmega kirje
            int vähimParemal = tagastaVähimKirje(juur.p);
            juur.p = eemaldaKirje(juur.p, vähimParemal);

            // Salvestame eemaldatud kirje juurtippu
            juur.väärtus = vähimParemal;
            return juur;
        }
    }

    /**
     * Abi. Sooritab parempööre, tagastab uuendatud tipp
     *
     * @note Eeldame, et algne sisend != null
     */
    public static KOTipp parempööre(KOTipp tippA) {
        KOTipp tippB = tippA.v;
        tippA.v = tippB.p;
        tippB.p = tippA;

        // Kõrguse parandus
        tippB.p.x = 1 + maksimaalneKõrgus(tippB.p.v, tippB.p.p);
        tippB.x = 1 + maksimaalneKõrgus(tippB.v, tippB.p);

        return tippB;
    }

    /**
     * Abi. Sooritab vasakpööre, tagastab uuendatud tipp
     *
     * @note Eeldame, et algne sisend != null
     */
    public static KOTipp vasakpööre(KOTipp tippA) {
        KOTipp tippB = tippA.p;
        tippA.p = tippB.v;
        tippB.v = tippA;

        // Kõrguse parandus
        tippB.v.x = 1 + maksimaalneKõrgus(tippB.v.v, tippB.v.p);
        tippB.x = 1 + maksimaalneKõrgus(tippB.v, tippB.p);

        return tippB;
    }

    /**
     * Abi. Sooritab vasakparempööre, tagastab uuendatud juur
     *
     * @note Eeldame, et algne sisend != null
     */
    public static KOTipp vasakparempööre(KOTipp tipp) {
        tipp.v = vasakpööre(tipp.v);
        return parempööre(tipp);
    }

    /**
     * Abi. Sooritab paremvasakpööre, tagastab uuendatud tipp
     *
     * @note Eeldame, et algne sisend != null
     */
    public static KOTipp paremvasakpööre(KOTipp tipp) {
        tipp.p = parempööre(tipp.p);
        return vasakpööre(tipp);
    }


    /**
     * Abi. Tagastab maksimaalne kõrgus kahe tippu vahel, arvestades olukorra, kui üks või mõlemad tippu on null
     */
    public static int maksimaalneKõrgus(KOTipp vasak, KOTipp parem) {
        if (vasak == null && parem == null)
            return 0;

        if (vasak != null && parem != null)
            return Math.max(vasak.x, parem.x);
        else
            return Objects.requireNonNullElseGet(vasak, () -> parem).x;
    }

    /**
     * Abi. Tagastab tippu kõrgus, arvestades olukorra, kui tipp on null
     */
    public static int kõrgus(KOTipp tipp) {
        if (tipp == null)
            return 0;
        return tipp.x;
    }

    /**
     * Abi. Tagastab vahe vasaku ja parema tippu kõrguste vahel
     */
    public static int kõrgusteVahe(KOTipp tipp) {
        if (tipp == null)
            return 0;
        return kõrgus(tipp.v) - kõrgus(tipp.p);
    }

}