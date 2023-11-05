/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 4
 * Teema: Paisktabelid
 *
 * Autor: Leonid Tšigrinski
 *
 * Kasutatud allikad:
 * 1) Läbimänguslaidid, Järjestamise positsioonimeetod
 * 2) https://et.wikipedia.org/wiki/Isikukood
 *****************************************************************************/

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Kodu4 {
    public static void main(String[] args) {
        long[] m1 = {
                36407023873L, 63410274305L, 62502252124L,
                24109263796L, 45901189756L, 50905191577L,
                44208225581L, 51104267139L, 54606268973L,
        };
        sort(m1);
    }

    /**
     * Sorteerib isikukoodid sünniaja järgi:
     * <ul style="list-style-type:none">
     *     <li> a) järjestuse aluseks on sünniaeg, vanemad inimesed on eespool; </li>
     *     <li> b) kui sünniajad on võrdsed, määrab järjestuse isikukoodi järjekorranumber (kohad 8-10); </li>
     *     <li> c) kui ka järjekorranumber on võrdne, siis määrab järjestuse esimene number. </li>
     * </ul>
     * <p>
     * Algoritm on selline:
     * Kuna sorteerida tuleb sünniaja järgi, iga isikukood teisendame kujuks AAAAKKPPJJJEK
     * (A - aasta, K - kuu, P - päev, J - järjekoranumber, E - esimene, K - kontrollnumber),
     * just sellel järjestusel, nagu ülalpool maininud.
     * Seda massiivi saab sorteerime kasutades positsioonimeetodit.
     * Kui massiiv on sorteeritud, isikukoodi saab teisendada tagasi "normaal" kujuks.
     * <p>
     * Tähelepanek, et sorteerimiseks endal kontrollnumber ei ole oluline. Kuigi tema tasub salvestada selleks, et
     * sooritada teisendus tagasi.
     * <p> <br>
     * Algoritmi keerukuse hinnang on lineaarne, kuna sammud on:
     * <ul style="list-style-type:none">
     *     <li> 1. Käime läbi iga element antud massiivis ning teisendame: O(n) * O(1) = O(n) </li>
     *     <li> 2. Sorteerime positsioonimeetodiga: O(13*n) = O(n) - sest sisend numbri pikkus on 13 (AAAAKKPPJJJEK), s.t. jägrguühiku suurename 13 korda (keskmiselt) </li>
     *     <li> 3. Käime läbi iga element sorteeritud massiivis ning teisendame tagasi: O(n) * O(1) = O(n) </li>
     * </ul>
     * Kokku on O(13*3*n) = O(n).
     *
     * @param isikukoodid sorteeritav isikukoodide massiiv
     */
    public static void sort(long[] isikukoodid) {
        // 1. Samm
        long[] isikukoodidAastaKuju = new long[isikukoodid.length];
        for (int i = 0; i < isikukoodid.length; i++)
            isikukoodidAastaKuju[i] = teisendaIsikukoodAastakujuks(isikukoodid[i]);

        // 2. Samm
        positsioonimeetod(isikukoodidAastaKuju);

        // 3. Samm
        for (int i = 0; i < isikukoodid.length; i++)
            isikukoodid[i] = teisendaAastakujuIsikukoodiks(isikukoodidAastaKuju[i]);
    }

    /**
     * Meetod, mis sorteerib antud massiivi positsiooni meetodiga.
     * Sammud on järgmised:
     * <ul style="list-style-type:none">
     *     <li> 1. Loome paisktabeli, mille ridade arv võrdub arvusüsteemi alusega (meie puhul 10)
     *     <li> 2. Alustame madalaima järguühikuga. Kui järguühik ületab suurima võtmete vahe, siis lõpetame tsükli</li>
     *     <ul style="list-style-type:none">
     *         <i>Tsükkli keha:<i/>
     *         <li> 3. Paiskame massiivi elemendid järguühiku kordaja järgi tabelisse. </li>
     *         <li> 4. Loeme tabelist järjest elemendid tagasi massiivi </li>
     *         <li> 5. Suurendame järguühikut 10 korda (kuna arvusüsteemi aluseks on 10) </li>
     *     </ul>
     *     <li> 6. Kontrollime järjestust. Leiame koha, kus järjestus on rikutud </li>
     * </ul>
     * <p>
     * Pööran tähelepanu, et paisktabeli konteineriks on järjekord. <br>
     * Põhjuseks on see, et 3. sammul me paiskame elemendid konteineri lõppu, aga 4. võtame elemendid juba algusest.
     * Täpselt selle printsibiiga töötab ka järjekord.
     *
     * @param massiiv sorteerime tema
     */
    public static void positsioonimeetod(long[] massiiv) {
        // 1. Samm
        HashMap<Integer, Queue<Long>> paisktabel = new HashMap<>();
        for (int võti = 0; võti < 10; võti++) {
            paisktabel.put(võti, new ArrayDeque<>());
        }

        // 2. Samm
        long jaarguühik = 1;
        long suurimVahe = suurimVahe(massiiv);
        System.out.println(suurimVahe);
        while (jaarguühik < suurimVahe) {
            // 3. Samm
            for (long elem : massiiv) {
                int h = (int) (elem / jaarguühik % 10); // Paiskfunktsioon h(k) = floor(k / jaarguühik) % 10
                Queue<Long> järjekord = paisktabel.get(h);
                järjekord.add(elem);
            }

            // 4. Samm
            int i = 0;
            for (Queue<Long> järjekord : paisktabel.values())
                while (!järjekord.isEmpty())
                    massiiv[i++] = järjekord.remove();

            // 5. Samm
            jaarguühik *= 10; // Arvusüsteemi aluseks on 10
        }

        // 6. Samm
        kontrolliJärjestust(massiiv);
    }

    /* Abi. Teisendab isikukood kujuks AAAAKKPPJJJEK (aasta, kuu, päev, järjekoranumber, esimene, kontrollnumber) */
    public static long teisendaIsikukoodAastakujuks(long id) {
        long esimene = id / 10000000000L;
        long sajand = 18 + (esimene - 1) / 2; // {18, 19, 20, 21}
        long lõik = (id / 10) % 1000000000; // arvud "indeksiga" [1-9], i.e "AAKKPPJJJ"
        long konrollnumber = id % 10;

        id = (sajand * 100000000000L) + (lõik * 100) + (esimene * 10) + konrollnumber;
        return id;
    }

    /* Abi. Teisendab kuju AAAAKKPPJJJEK tagasi Isikukoodiks*/
    public static long teisendaAastakujuIsikukoodiks(long kood) {
        long konrollnumber = kood % 10;
        long esimene = (kood / 10) % 10;
        long lõik = (kood / 100) % 1000000000;

        kood = (esimene * 10000000000L) + (lõik * 10) + konrollnumber;
        return kood;

    }


    /* Abi. Juhul, kui massiivi järjestus on rikutud, vahetab osad */
    public static void kontrolliJärjestust(long[] massiiv) {
        int rikutudIndeks = -1;

        // Otsime rikutud osad
        long eelmine = massiiv[0];
        for (int i = 1; i < massiiv.length; i++) {
            if (massiiv[i] < eelmine) {
                rikutudIndeks = i;
                break;
            }
            eelmine = massiiv[i];
        }

        // Vahetame osad (ainult vajadusel)
        if (rikutudIndeks != -1) {
            long[] uus = new long[massiiv.length];

            // Kopeerime uue massiivi elemendid õigepidi
            System.arraycopy(massiiv, rikutudIndeks, uus, 0, massiiv.length - rikutudIndeks);
            System.arraycopy(massiiv, 0, uus, massiiv.length - rikutudIndeks, rikutudIndeks);

            // Muudame algse massiivi
            System.arraycopy(uus, 0, massiiv, 0, massiiv.length);
        }
    }

    /* Abi. Tagastab vahe suurima ja vähima elemntide vahel */
    public static long suurimVahe(long[] massiiv) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        long vahe = -1;

        for (long elem : massiiv) {
            if (elem > max)
                max = elem;
            if (elem < min)
                min = elem;
        }
        return max - min;
    }


    /**
     * Genereerib isikukoodi lähtudes reeglitest püstitatud <a href=https://et.wikipedia.org/wiki/Isikukood>siin.</a>
     * <br>
     * Numbrite tähendused:
     * <ul style="list-style-type:none">
     *      <li> 1 - sugu ja sünniaasta esimesed kaks numbrit, (1...8) </li>
     *      <li> 2-3 - sünniaasta 3. ja 4. numbrid, (00...99) </li>
     *      <li> 4-5 - sünnikuu, (01...12) </li>
     *      <li> 6-7 - sünnikuupäev (01...31) </li>
     *      <li> 8-10 - järjekorranumber samal päeval sündinute eristamiseks (000...999) </li>
     *      <li> 11 - kontrollnumber (0...9) </li>
     * </ul>
     *
     * @return Eesti isikukoodi reeglitele vastav isikukood
     */
    static long genereeriIsikukood() {
        ThreadLocalRandom juhus = ThreadLocalRandom.current();
        LocalDate sünnikuupäev = LocalDate.ofEpochDay(juhus.nextLong(-62091, 84006)); // Suvaline kuupäeva a 1800-2199
        long kood = ((sünnikuupäev.getYear() - 1700) / 100 * 2 - juhus.nextInt(2)) * (long) Math.pow(10, 9) +
                sünnikuupäev.getYear() % 100 * (long) Math.pow(10, 7) +
                sünnikuupäev.getMonthValue() * (long) Math.pow(10, 5) +
                sünnikuupäev.getDayOfMonth() * (long) Math.pow(10, 3) +
                juhus.nextInt(1000);
        int korrutisteSumma = 0;
        int[] iAstmeKaalud = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        for (int i = 0; i < 10; i++) {
            korrutisteSumma += (int) (kood / (long) Math.pow(10, i) % 10 * iAstmeKaalud[9 - i]);
        }
        int kontroll = korrutisteSumma % 11;
        if (kontroll == 10) {
            int[] iiAstmeKaalud = {3, 4, 5, 6, 7, 8, 9, 1, 2, 3};
            korrutisteSumma = 0;
            for (int i = 0; i < 10; i++) {
                korrutisteSumma += (int) (kood / (long) Math.pow(10, i) % 10 * iiAstmeKaalud[9 - i]);
            }
            kontroll = korrutisteSumma % 11;
            kontroll = kontroll < 10 ? kontroll : 0;
        }
        return kood * 10 + kontroll;
    }


}
