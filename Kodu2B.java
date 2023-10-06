/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 2
 * Teema: Rekursioon. Variantide läbivaatamine
 *
 * Autor: Leonid Tšigrinski
 *
 * Kasutatud allikad: -
 *
 *****************************************************************************/

import java.util.Arrays;

public class Kodu2B {
    public static void main(String[] args) {
        long[] a = {1, 2, 3, 4};
        int p = 4;
        System.out.println(tükeldused(a, 3));
        long c = Long.MAX_VALUE;
        System.out.println(c + c);
    }

    /**
     * Meetod leiab ja tagastab traadi kõikvõimalike selliste tükelduste arvu, mille puhul ülejääk
     * on väiksem kui vähim traadi lubatud pikkus (traadipikkused määratud massiiviga).
     * Ainult järjekorra poolest erinevaid tükeldusi loeme samaks.
     * <p>
     * Printsiib on selline:
     * Võtame ühte elemendi. Hargneme temaga vasakule niipalju, et hargnemise summa ei ületa traadi pikkust.
     * Siis, kui hagnemise (ehk tükeldamise) summa ületab traadi pikkust, toimub baasjuht, ning enam ei hargne.
     * Järgmine haru võtab juba teist elementi, samamoodi rekursiivselt vaatleme kõik tükeldamised.
     * Oluline on see, et massiiv peaks olema sorteeritud, nii et
     * 1) Me kindlalt teame vähim traadi lubatud pikkus, mille järgi me võrdleme jääk. See on esimene element.
     * 2) Kui tükeldamise summa ületab traadi pikkust, me kindlasti teame, et võib rekursiooni lõpetada,
     * sest järgmised elemendid on alati suuremad, ning me ei kaota ühtegi kombinatsiooni.
     * <p>
     * Oluline, et ei ole korduvaid elemente, muidu massiivi {a, a} puhul, esimese elemendi tükeldus "aa" ja teise
     * elemndi tükeldus "aa" loeme erinevaks
     *
     * @param massiiv Lubatud traadipikkuste väärtused
     * @param pikkus  Traadi kogu pikkus
     * @param start   Vaadeldava elemndi indeks, kust me alustame kombinatsioonid
     * @param summa   Vaadeldava tükelduste pikkus
     * @return Tükelduste arv
     */
    public static int tükeldused(long[] massiiv, long pikkus, int start, long summa) {
        int tulemus = 0;

        if (pikkus - summa < massiiv[0]) // Jääk < vähim traadi lubatud pikkus. Massiiv on sorteeritud, seega min = a[0]
            tulemus++;

        for (int i = start; i < massiiv.length; i++)
            // Suure sisendi puhul võib toimuda ületäitmine, et (summa + massiiv[i]) läheb negatiivseks. Kontrollin seda
            // Teiseks, kontrollin, et tükelduste pikkus oleks väiksem, kui traadi lubatud pikkus
            if (summa + massiiv[i] > 0 && summa + massiiv[i] <= pikkus)
                // Rekursiooni samm. Hargneme iga elemendiga nii paalju, kui "lubab" pikkus
                tulemus += tükeldused(massiiv, pikkus, i, summa + massiiv[i]);
        // else { Baasjuht } Enam ei hargne, sest tükelduste pikkus on suurem, kui kogu traadi pikkus

        return tulemus;
    }

    /* Vaikeväärtustatud kutse */
    public static int tükeldused(long[] massiiv, long pikkus) {
        Arrays.sort(massiiv);
        return tükeldused(massiiv, pikkus, 0, 0);
    }
}