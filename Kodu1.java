/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 1
 * Teema: Sorteerimismeetodite ajalise keerukuse võrdlus
 *
 * Autor: Leonid Tšigrinski
 *
 * Kasutatud allikad: 1) https://et.wikipedia.org/wiki/Mullsortimine
 *                    2) Aine Programmeerimine 2; Praktikum 9 (kiirmeetod); Autor: Athi Põder
 *****************************************************************************/

import java.util.Arrays;

public class Kodu1 {
    public static void main(String[] args) {
        int[] a = massiivJuhuslikult(10, 0, 10);
        System.out.println(Arrays.toString(a));
        a = kiirmeetod(a);
        System.out.println(Arrays.toString(a));
    }



    /**
     * Meetod, mis võtab argumendina antud massiivi ja sorteerib tema kasutades mullimeetodi algoritmi.
     * Printsiib on selline, et me käime vasakult paremale ning võrdleme vaadeldav element järgmisega.
     * Kui vaadeldav element suurem, kui järgmine, siis vahetame neid (ehk nihutame paremale) nii kaua, kui saab.
     * Järgmise elemndi puhul sammude arv on ühe (i) võrra väiksem, sest eelmine vaadeldav element on juba lõppus.
     *
     * @param m - massiiv, mida sorteeritakse
     * @return sorteeritud massiiv
     */
    public static int[] mullimeetod(int[] m) {
        if (m.length <= 1)
            return m;

        int suurus = m.length;
        int[] uus = kopeeriAlgus(m, suurus);

        for (int i = 0; i < suurus - 1; i++)
            for (int j = 0; j < suurus - 1 - i; j++)
                if (uus[j] > uus[j + 1]) {
                    vaheta(uus, j, j + 1);
                }

        return uus;
    }

    /**
     * Meetod, mis võtab argumendina antud massiivi ja sorteerib tema kasutades kiirmeetodi algoritmi.
     * Printsiib on selline, et me võtame suvaline väärtus, mille järgi me võrleme elemendid (ingl. *pivot*).
     * Olgu see on m[0]. Nüüd me jagame kõik elemendid kolmeks massiiviks: vasakule lähevad elemndid x[0]-st väiksem,
     * paremale suuremad, ning keskele võrdsed.
     * Teeme sama asja rekursiivlest, kasutades vasak ja parempoolne massiiv. Ühildame kõik osasid vasakult paremale,
     * sest neid on juba sorteeritud. See on binaarne rekursioon.
     *
     * @param m
     * @return sorteeritud massiiv
     */
    public static int[] kiirmeetod(int[] m) {
        // Rekursiooni baas
        if (m.length <= 1)
            return m;

        int x = m[0]; // Mille järgi me jagame pooleks, "pivot"
        int[] vasak = new int[m.length];
        int[] kesk = new int[m.length];
        int[] parem = new int[m.length];
        int suurusVasak = 0, suurusKesk = 0, suurusParem = 0;

        // Jaotan kolmeks alam massiiviks. Täidan
        for (int i = 0; i < m.length; i++) {
            if (m[i] < x)
                vasak[suurusVasak++] = m[i];
            else if (m[i] == x)
                kesk[suurusKesk++] = m[i];
            else
                parem[suurusParem++] = m[i];
        }

        // Juba täidetud. Nüüd muudan suurus
        vasak = kopeeriAlgus(vasak, suurusVasak);
        kesk = kopeeriAlgus(kesk, suurusKesk); // See on juba sorteeritud
        parem = kopeeriAlgus(parem, suurusParem);

        // Rekursiooni samm
        vasak = kiirmeetod(vasak);
        parem = kiirmeetod(parem);

        // Osade kokkupanek
        int[] uus = new int[m.length];
        int i = 0;
        for (int k : vasak)
            uus[i++] = k;
        for (int k : kesk)
            uus[i++] = k;
        for (int k : parem)
            uus[i++] = k;

        return uus;
    }

    /* Abi. Tagastab massiivi koopiat, kus on kõik elemndid indeksist 0 kuni (k-1)-ni */
    public static int[] kopeeriAlgus(int[] massiiv, int k) {
        int[] uus = new int[k];
        for (int i = 0; i < k; i++)
            uus[i] = massiiv[i];

        return uus;
    }

    /* Abi. Vahetab massiivi elemendid indeksiga i ja j omavahel */
    public static void vaheta(int[] massiiv, int i, int j) {
        int aju = massiiv[i];
        massiiv[i] = massiiv[j];
        massiiv[j] = aju;
    }

    /* Abi. Tagastab massiivi suurusega n, mis on kasvavalt sorteeritud. Alati 0-st N-ni */
    public static int[] massiivKasvavalt(int n) {
        int[] massiiv = new int[n];
        for (int i = 0; i < n; i++)
            massiiv[i] = i;

        return massiiv;
    }

    /* Abi. Tagastab massiivi suurusega n, mis on kahenevalt sorteeritud. Aalti (N-1)-st 0-ni */
    public static int[] massiivKahenevalt(int n) {
        int[] massiiv = new int[n];
        for (int i = 0, j = n - 1; i < massiiv.length; i++, j--)
            massiiv[i] = j;

        return massiiv;
    }

    /* Abi. Tagastab massiivi suurusega n, mille elemendid on suvalised arvud lõigust [min; max] (mõlemad k.a) */
    public static int[] massiivJuhuslikult(int n, int min, int max) {
        int[] m = new int[n];
        for (int i = 0; i < n; i++)
            m[i] = (int) ((Math.random() * (max - min + 1)) + min); // + 1 selleks, et ülemine piir oleks kaasarvatud

        return m;
    }
}
