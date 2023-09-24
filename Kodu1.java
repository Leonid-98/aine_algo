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
 *                    2) https://www.baeldung.com/java-quicksort
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
        Esiteks, võrdlen ajakulu erijuhtudel. Neid on 3:
        1) Väike sisend (0, 1, 2)
        2) Parim juht (sorteeritud)
        3) Halvim juht (kahenevalt sorteeritud)
        Pärast, võrdlen ajakulu keskmisel juhtudel, kui sisend on juhuslik massiiv.
        Antud koodis iga mõõtmise jaoks teen 10 sämplit, et keerukuse hindamisel arvestada keskmist.
         */
        int[] massiiv;
        int suurim_sisend = 10_000;

        salvestaPrintimisedFailina("väiksed.txt");
        /* Erijuht 1. Väiksed suurused */
        System.out.println("[Erijuht 1. n kuulub {0, 1, 2}]");
        System.out.println("[Sisendi suurus (n)\tAeg (ns)]"); // Legend
        for (int n = 0; n <= 2; n++) {
            massiiv = massiivKasvavalt(n); // Väiksema sisendi puhul vahet ei ole, kas sorteeritud või mitte
            testiKolmSortimist(massiiv);
        }


        salvestaPrintimisedFailina("parim.txt");
        /* Erijuht 2. Parim juht */
        System.out.println("[Erijuht 2. Sorteeritud]");
        System.out.println("[Sisendi suurus (n)\tAeg (ns)]"); // Legend
        for (int n = 0; n <= suurim_sisend; n += 100) {
            massiiv = massiivKasvavalt(n);
            testiKolmSortimist(massiiv);
        }

        salvestaPrintimisedFailina("halvim.txt");
        /* Erijuht 3. Halvim juht */
        System.out.println("[Erijuht 3. Sorteeritud kahenevalt]");
        System.out.println("[Sisendi suurus (n)\tAeg (ns)]"); // Legend
        for (int n = 0; n <= suurim_sisend; n += 100) {
            massiiv = massiivKahenevalt(n);
            testiKolmSortimist(massiiv);
        }

        salvestaPrintimisedFailina("keskmine.txt");
        /* Kesmine juht. Juhuslik massiiv lõigust 0st 100ni (k.a) */
        System.out.println("[Kesmine juht. Juhuslik massiiv]");
        System.out.println("[Sisendi suurus (n)\tAeg (ns)]"); // Legend
        for (int n = 0; n <= suurim_sisend; n += 100) {
            massiiv = massiivJuhuslikult(n, 0, 100);
            testiKolmSortimist(massiiv);
        }
    }

    /**
     * Meetod, mis võtab argumendina antud massiivi ja sorteerib tema kasutades mullimeetodi algoritmi.
     * Printsiib on selline, et me käime vasakult paremale ning võrdleme vaadeldav element järgmisega.
     * Kui vaadeldav element suurem, kui järgmine, siis vahetame neid (ehk nihutame paremale) nii kaua, kui saab.
     * Järgmise elemndi puhul sammude arv on ühe (i) võrra väiksem, sest eelmine vaadeldav element on juba lõppus.
     *
     * @param massiiv - antud, ei muuta
     * @return uus sorteeritud massiiv
     */
    public static int[] mullimeetod(int[] massiiv) {
        if (massiiv.length <= 1) return massiiv;

        int suurus = massiiv.length;
        int[] uus = kopeeriAlgus(massiiv, suurus);

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
     * Olgu see on viimane. Nüüd me jagame massiivi poleks: vasakule lähevad elemndid temast väiksem, paremale suuremad.
     * "pivot" jääb keskele, tema järgi rekursiivselt jagame massiivi.
     * Teeme sama asja rekursiivlest, kasutades vasak ja parem osad.
     *
     * @param massiiv    massiiv, mida sorteritakse
     * @param algindeks  massiivi algusosa
     * @param loppindeks massiivi lõppuosa
     */
    private static void kiirmeetod(int[] massiiv, int algindeks, int loppindeks) {
        if (algindeks < loppindeks) {
            int jagamispunkt = jagamine(massiiv, algindeks, loppindeks);
            kiirmeetod(massiiv, algindeks, jagamispunkt - 1);
            kiirmeetod(massiiv, jagamispunkt + 1, loppindeks);
        }
    }

    /**
     * Abi. Paneb esimesest elimendist väiksemad vasakule ning suuremad paremale
     */
    private static int jagamine(int[] massiiv, int algindeks, int loppindeks) {
        int pivot = massiiv[loppindeks];
        int i = algindeks - 1;

        for (int j = algindeks; j < loppindeks; j++) {
            if (massiiv[j] <= pivot) {
                i++;
                vaheta(massiiv, i, j);
            }
        }

        vaheta(massiiv, i + 1, loppindeks);
        return i + 1;
    }

    /**
     * Vaikeväärtustatud kutse. Ei muuta antud massiivi, vaid tagastab uue
     */
    public static int[] kiirmeetod(int[] massiiv) {
        int[] uus = kopeeriAlgus(massiiv, massiiv.length);
        kiirmeetod(uus, 0, massiiv.length - 1);

        return uus;
    }

    /**
     * <java.util.Arrays.sort> meetodi modifikatsioon, et ta ei sorteeriks etteantud, vaid tagastab uue.
     * Põhjuseks on see, et kuna ma tahan teha mitu sämplid, on vaja, et originaal massiiv jääb samaks.
     * Muidu üks mõõt on õige ja ülejaanud on parima juhu.
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
     * Abi. Tagastab massiivi koopiat, kus on kõik elemndid indeksist 0 kuni (k-1)-ni
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
