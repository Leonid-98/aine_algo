package Kodu; /*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 1
 * Teema: Sorteerimismeetodite ajalise keerukuse võrdlus
 *
 * Autor: Leonid Tšigrinski
 *
 * Kasutatud allikad: 1) https://et.wikipedia.org/wiki/Mullsortimine
 *                    2) Kiirmeetod. Programmeerimine 2. Praktikum 9. Ahti Põder
 *                    3) https://stackoverflow.com/questions/1994255/how-to-write-console-output-to-a-txt-file
 *
 * NB! On vaja lisada -Xmx käsurea argument (VM Options).
 * Muidu toimub StackOverflow suurema sisendi puhul
 *****************************************************************************/

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class Kodu1 {
    public static void main(String[] args) {
        /*
        Esiteks võrdlen ajakulu erijuhtudel. Neid on 3:
        1) Väike sisend (0, 1, 2)
        2) Parim juht (sorteeritud)
        3) Halvim juht (kahenevalt sorteeritud)
        Pärast võrdlen ajakulu keskmisel juhtudel, kui sisend on juhuslik massiiv.
        Antud koodis teen iga mõõtmise jaoks 10 sämpli, et keerukuse hindamisel arvestada keskmist.
         */

//        int[] massiiv;
//        int suurimSuurus = 10_000;
//
//        /* Erijuht 1. Väiksed suurused */
//        salvestaPrintimisedFailina("väiksed.txt");
//        System.out.println("[Erijuht 1. n kuulub {0, 1, 2}]");
//        System.out.println("[Sisendi suurus (n)\tAeg (ns)]"); // Legend
//        for (int n = 0; n <= 2; n++) {
//            massiiv = massiivKasvavalt(n); // Väiksema sisendi puhul vahet ei ole, kas sorteeritud või mitte
//            testiKolmSortimist(massiiv);
//        }
//
//        /* Erijuht 2. Parim juht */
//        salvestaPrintimisedFailina("parim.txt");
//        System.out.println("[Erijuht 2. Sorteeritud]");
//        System.out.println("[Sisendi suurus (n)\tAeg (ns)]"); // Legend
//        for (int n = 0; n <= suurimSuurus; n += 100) {
//            massiiv = massiivKasvavalt(n);
//            testiKolmSortimist(massiiv);
//        }
//
//        /* Erijuht 3. Halvim juht */
//        salvestaPrintimisedFailina("halvim.txt");
//        System.out.println("[Erijuht 3. Sorteeritud kahenevalt]");
//        System.out.println("[Sisendi suurus (n)\tAeg (ns)]"); // Legend
//        for (int n = 0; n <= suurimSuurus / 2 ; n += 100) { // Tuleb sisend kaks korda vähendada, muidu StackOverflow
//            massiiv = massiivKahenevalt(n);
//            testiKolmSortimist(massiiv);
//        }
//
//        /* Kesmine juht. Juhuslik massiiv lõigust 0st 100ni (k.a) */
//        salvestaPrintimisedFailina("keskmine.txt");
//        System.out.println("[Kesmine juht. Juhuslik massiiv]");
//        System.out.println("[Sisendi suurus (n)\tAeg (ns)]"); // Legend
//        for (int n = 0; n <= suurimSuurus; n += 100) {
//            massiiv = massiivJuhuslikult(n, 0, 100);
//            testiKolmSortimist(massiiv);
//        }
        char[] x = {'M', 'U', 'L', 'L', 'I', 'M', 'E', 'E', 'T', 'O', 'D'};
        char[] z = {'E', 'E', 'E', 'I', 'M', 'P', 'S', 'T', 'T', 'O', 'D'};
//        int[] y = new int[z.length];
//        for (int i = 0; i < z.length; i++)
//            y[i] = z[i];
        mullimeetod(new int[]{1, 5, 4, 2, 8});
    }

    /**
     * Meetod, mis võtab argumendina antud massiivi ja sorteerib selle, kasutades mullimeetodi algoritmi.
     * Põhimõte on järgmine: me liigume vasakult paremale ja võrdleme hetkel vaadeldavat elementi järgmisega.
     * Kui hetkel vaadeldav element on suurem kui järgmine, siis vahetame neid (nihe paremale) nii kaua, kui võimalik.
     * Järgmise elemendi puhul on sammude arv ühe võrra väiksem, sest eelmine vaadeldav element on juba lõpus.
     *
     * @param massiiv - antud, ei muuta
     * @return uus sorteeritud massiiv
     */
    public static int[] mullimeetod(int[] massiiv) {
        if (massiiv.length <= 1) return massiiv;

        int suurus = massiiv.length;
        int[] uus = kopeeriAlgus(massiiv, suurus);
        int loe = 0;
        for (int i = 0; i < suurus - 1; i++) {
            System.out.println();
            for (int j = 0; j < suurus - 1 - i; j++) {
                System.out.println(uus[j] + " " + uus[j + 1]);
                if (uus[j] > uus[j + 1]) {
                    vaheta(uus, j, j + 1);
                    loe++;
                }
            }
        }
        System.out.println(loe);
        return uus;
    }

    public static char[] printi(int[] x) {
        char[] y = new char[x.length];
        for (int i = 0; i < x.length; i++)
            y[i] = (char) x[i];
        return y;
    }


    /**
     * <java.util.Arrays.sort> meetodi modifikatsioon, et see ei sorteeri antud massiivi, vaid tagastab uue.
     * Põhjus seisneb selles, et kuna ma tahan teha mitu sämpli, on oluline, et algne massiiv jääks muutumatuks.
     * Muidu on üks mõõtmine õige ja ülejäänud parimal juhul.
     *
     * @param massiiv antud, ei muuta
     * @return uus sorteeritud massiiv
     */
    public static int[] susteemimeetod(int[] massiiv) {
        int[] uus = kopeeriAlgus(massiiv, massiiv.length);
        Arrays.sort(uus);
        return uus;
    }

    /**
     * Meetod, mis võtab argumendina antud massiivi ja sorteerib selle, kasutades kiirmeetodi algoritmi.
     * Põhimõte on järgmine: me valime suvalise elemendi, mida nimetatakse "pöördepunktiks" (ingl. pivot),
     * mille järgi me võrdleme teisi elemente. Olgu antud juhul see on x[0]. Seejärel jagame massiivi kolmeks:
     * vasakule lähevad elemendid, mis on väiksemad kui "pöördepunkt", paremale lähevad suuremad elemendid.
     * "Pöördepunkt" ja temaga võrdsed elemendid jäävad keskele.
     * Teeme sama protseduuri rekursiivselt, kasutades vasakut ja paremat osasid.
     */
    public static int[] kiirmeetod(int[] massiiv) {
        // Rekursiooni baas. Massiiv on sorteeritud, kui tal on 1 element
        if (massiiv.length <= 1)
            return massiiv;

        // Allokeerin mälu. See on väga kallis osa, seega sisendi suurus on piiratud. Vastasel juhul toimub StackOvflw
        int x = massiiv[0];
        int[] vasak = new int[massiiv.length];
        int[] kesk = new int[massiiv.length];
        int[] parem = new int[massiiv.length];
        int vasakSuurus = 0, keskSuurus = 0, paremSuurus = 0;

        // Jaotan kolmeks alam massiiviks
        for (int element : massiiv) {
            if (element < x)
                vasak[vasakSuurus++] = element;
            else if (element == x)
                kesk[keskSuurus++] = element;
            else
                parem[paremSuurus++] = element;
        }

        // Elemendid on jaotatud, tuleb massiivi suurus muuta (ehk tagastada uue vastava suurusega)
        vasak = kopeeriAlgus(vasak, vasakSuurus);
        kesk = kopeeriAlgus(kesk, keskSuurus);
        parem = kopeeriAlgus(parem, paremSuurus);

        // Rekursiooni samm. Sorteerin samamoodi osad
        vasak = kiirmeetod(vasak);
        parem = kiirmeetod(parem);

        // Sorteeeritud osade kokkupanek
        int[] uus = new int[massiiv.length];
        int i = 0;
        for (int k : vasak) uus[i++] = k;
        for (int k : kesk) uus[i++] = k;
        for (int k : parem) uus[i++] = k;

        return uus;
    }

    /**
     * Abi. Iga algoritmi puhul sorteerib antud massiiv 10 korda ja kuvab ajamõõtmise tulemused
     */
    public static void testiKolmSortimist(int[] massiiv) {
        long start, stop;
        int n = massiiv.length;

        System.out.println("<Mullimeetod>");
        System.out.printf("%d: ", n);
        for (int i = 0; i < 10; i++) {
            start = System.nanoTime();
            mullimeetod(massiiv);
            stop = System.nanoTime();

            if (i == 9)
                System.out.printf("%d\n", (stop - start));
            else
                System.out.printf("%d, ", (stop - start));
        }

        System.out.println("<Kiirmeetod>");
        System.out.printf("%d: ", n);
        for (int i = 0; i < 10; i++) {
            start = System.nanoTime();
            kiirmeetod(massiiv);
            stop = System.nanoTime();

            if (i == 9)
                System.out.printf("%d\n", (stop - start));
            else
                System.out.printf("%d, ", (stop - start));
        }

        System.out.println("<java.util.Arrays.sort>");
        System.out.printf("%d: ", n);
        for (int i = 0; i < 10; i++) {
            start = System.nanoTime();
            susteemimeetod(massiiv);
            stop = System.nanoTime();

            if (i == 9)
                System.out.printf("%d\n", (stop - start));
            else
                System.out.printf("%d, ", (stop - start));
        }
    }

    /**
     * Abi. Tagastab massiivi koopiat, mis sisaldab elemendid indeksist 0 kuni (k-1)-ni
     */
    public static int[] kopeeriAlgus(int[] massiiv, int k) {
        int[] uus = new int[k];
        for (int i = 0; i < k; i++)
            uus[i] = massiiv[i];

        return uus;
    }

    /**
     * Abi. Vahetab massiivi elemendid indeksiga i ja j omavahel
     */
    public static void vaheta(int[] massiiv, int i, int j) {
        int aju = massiiv[i];
        massiiv[i] = massiiv[j];
        massiiv[j] = aju;
    }

    /**
     * Abi. Tagastab massiivi suurusega n, mis on kasvavalt sorteeritud. Alati 0-st N-ni
     */
    public static int[] massiivKasvavalt(int n) {
        int[] uus = new int[n];
        for (int i = 0; i < n; i++)
            uus[i] = i;

        return uus;
    }

    /**
     * Abi. Tagastab massiivi suurusega n, mis on kahenevalt sorteeritud. Aalti (N-1)-st 0-ni
     */
    public static int[] massiivKahenevalt(int n) {
        int[] uus = new int[n];
        for (int i = 0, j = n - 1; i < uus.length; i++, j--)
            uus[i] = j;

        return uus;
    }

    /**
     * Abi. Tagastab massiivi suurusega n, mille elemendid on suvalised arvud lõigust [min; max] (mõlemad k.a)
     */
    public static int[] massiivJuhuslikult(int n, int min, int max) {
        int[] uus = new int[n];
        for (int i = 0; i < n; i++)
            uus[i] = (int) ((Math.random() * (max - min + 1)) + min); // + 1 selleks, et ülemine piir oleks kaasarvatud

        return uus;
    }

    /**
     * Abi. Konfigureerib voog, et kõik printimised salvestakse failina
     */
    public static void salvestaPrintimisedFailina(String failinimi) {
        PrintStream out;
        try {
            out = new PrintStream(new FileOutputStream(failinimi));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.setOut(out);
    }
}
