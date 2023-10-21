/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 3
 * Teema: Magasin ja järjekord
 *
 * Autor: Leonid Tšigrinski
 *
 * NB! Test FAIL-ib järjestuse tõttu, kuid mainitud "Sama taseme kataloogid ja failid võib väljastada suvalises järjestuses"
 * Seega palun, vaadake tulemused üle :)
 *****************************************************************************/

import java.io.File;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class Kodu3a {
    public static void main(String[] args) {
        failipuu("Test");

        Queue<Integer> a = new ArrayDeque<>();
        minuPush(a, 1);
        minuPush(a, 2);
        minuPush(a, 3);
        System.out.println(a);
        System.out.println(minuPop(a));
        System.out.println(a);
    }

    /*
     Meetod, mis järjekorda (kui andmestruktuuri) kasutades läbib failisüsteemi tasemeti,
     alates etteantud kataloogist, ja väljastab ekraanile kõik alamkataloogide ning failide nimed.
     Tulemus peab rahuldama järgmiseid tingimusi:
     * Faili- ja katalooginimed väljastada sügavusest sõltuva taandega, 1 taande tähistamiseks olgu tabulaator "\t";
     * Faili nimi tuleb anda koos faili nime juurde kuuluva laiendiga, näiteks "tulemus.txt";
     * Katalooginime ümber peavad olema kandilised sulud "[" ja "]";
     * Sama taseme kataloogid ja failid võib väljastada suvalises järjestuses;
     * Väljastada ainult nende failide nimed, mille suurused ei ületa 500 KB (1 KB = 1024 B) (kataloogid võivad olla suvalise suurusega).
     * Soovituslik on väljastada faili nime järel sulgudes faili suurus ühikuga KB (vt. väljundi näidet allpool).


     Esmalt seletan, kuidas asendada rekursiooni magasinidega:

     public static void rek(arg1, arg2) {
          if (baasjuht 1)
              ...
          if (baasjuht 2)
              ...
          rek(arg1 - N, arg2 - M);
          rek(arg1 - N, arg2 - M);
     }

     Nüüd sama meetod kasutades magasini:

     public static void rek(arg1, arg2) {
          Deque<Integer> magasinArg1 = new ArrayDeque<>(); // Iga muudetava argumendi jaoks loon magasiini
          Deque<Integer> magasinArg2 = new ArrayDeque<>();
          magasinArg1.push(arg1);                          // N.ö. esimene väljakutse
          magasinArg2.push(arg2);

          while (!magasinArg1.isEmpty()) {
              int a1 = magasinArg1.pop();                  // N.ö. meetodi keha, pärast väljakutsumist
              int a2 = magasinArg2.pop();
              if (baasjuht 1)                              // Baasjuhud
                  ...
              else if (baasjuht 2)
                  ...
              else {
                  magasinArg1.push(a1 - N);                // N.ö. "rekursiooni" sammud
                  magasinArg2.push(a2 - M);
              }
          }
     }

    Algoritm on selline:
    Samm:
    Kui antud File objekt on kasut, n.ö. rekursiivselt lähen sügavale, jätan meeles File objekt ise ning sügavus
    suurendan ühe võrra. See oleks unaarne rekursioon (kui lahendada rekursiivselt)
    Baasjuht:
    Kui antud File objekt on fail, siis väljastan tema (siis, kui suurus ei ületa 500KB).
    Nüüd on väike trikk: on mainitud, kuidas asendada rekursiooni magasiniga. Kuna ülesanne kirjeldus nõuab kasutada
    järjekorra, ma implementeerin magasini loogikat kasutades järjekorra anmestruktuuri.
    */
    public static void failipuu(File fail, String sügavus) {
        /* Päis. Rekursiooni puhul see oleks vaikeväärtustatud väljakutse */
        Queue<String> sügavusJärjekor = new ArrayDeque<>(); // Kaks argumendi, mida tahaksin "rekursiivselt" muuta
        Queue<File> failJärjekord = new ArrayDeque<>();
//        sügavusMag.push(sügavus);
//        failMag.push(fail);
        minuPush(sügavusJärjekor, sügavus);
        minuPush(failJärjekord, fail);

        /* Keha */
        while (!sügavusJärjekor.isEmpty()) {
            sügavus = minuPop(sügavusJärjekor);
            fail = minuPop(failJärjekord);

            if (!fail.isDirectory()) { /* Baasjuht 1, ehk see on fail */
                float suurusKB = fail.length() / 1024.0f;
                if (suurusKB <= 500)
                    System.out.println(sügavus + fail.getName() + " (" + ümmarda(suurusKB) + " KB)");

            } else { /* Samm. Saab iga failiga ""hargneda" edasi */

                System.out.println(sügavus + "[" + fail.getName() + "]");
                for (File failAju : Objects.requireNonNull(fail.listFiles())) {
                    minuPush(sügavusJärjekor, sügavus + "\t"); // Rekursiivne parameter muutub
                    minuPush(failJärjekord, failAju);
                }
            }
        }
    }

    /* Vaikeväärtustatud kuste */
    public static void failipuu(String tee) {
        failipuu(new File(tee), "");
    }

    /* Abi. Ümmardab antud arv kahe komakohani */
    public static double ümmarda(double arv) {
        return (double) Math.round(arv * 100) / 100;
    }

    /* Abi. Magasini implementatsioon kasutades järjekord.
     * Kasutan abi järjekord, kuhu ajuliselt lisan kõik elemndid, et "nihutada" lisatud element järjekorra lõppu */
    public static <T> void minuPush(Queue<T> järjekord, T element) {
        Queue<T> aju = new ArrayDeque<>();
        while (!järjekord.isEmpty())
            aju.add(järjekord.remove());

        järjekord.add(element); // Läks esimesena, seega saab tema esimesena ka (FIFO)

        while (!aju.isEmpty()) {
            järjekord.add(aju.remove());
        }
    }

    /* Abi. Magasini implementatsioon kasutades järjekord.
     * Eeldame, et magasini tipp asub samas kohas, kus on järjekorra tipp */
    public static <T> T minuPop(Queue<T> järjekord) {
        return järjekord.remove();
    }


}