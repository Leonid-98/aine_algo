/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 7
 * Teema: Kaugusalgoritmid
 *
 * Autor: Leonid Tšigrinski, Jaan Janno (praktikumis loodud klassid)
 *
 *****************************************************************************/

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


public class Kodu7b {
    public static void main(String[] args) throws IOException {
        NimedegaNaabrusmaatriks m = NimedegaNaabrusmaatriks.loeNaabrusmaatriks(new File("täht.tsv"));
        kaugused("A", 5, m.nimed, m.M);
    }

    /**
     * Olgu meil elektriauto, mis võimaldab laadimiseta sõita x kilomeetrit ja laadida saab vaid tabelis nimetatud asulates.
     * Eesmärgiks on koostada programm, mis vastavalt etteantud lähtelinna ning
     * x-i järgi väljastab ekraanile reakaupa teepikkused igasse linna realiseerituna Dijkstra algoritmiga.
     * Dijktra algoritmi realiseeringus peab tööfrondi kujutamiseks kasutama kahendkuhja
     * <p>
     * Alguses loome vajalikud andmestruktuurid: paisktabeli (kaugused) ja kuhja (tööfrond).
     * Paisktabelisse salvestame Dijkstra algoritmi abil leitud kaugused. Alguses on kõik kaugused lõpmata suured
     * (või meie puhul MAX_INT).
     * Kuhja salvestame töötlemata tippud. Kasutame 6. kodutöö kuhja klassi uuesti.
     * Oleme selle muutnud pöördkuhjaks (min heap) ja selle elemendid on klassi Tipp objektid.
     * Selleks, et kuhja sees tippe võrrelda, salvestame ka kaugused Tipp.x väärtusele.
     * <p>
     * Seejärel lisame kuhja algtipu (lähtelinn) ja alustame Dijkstra algoritmiga:
     * Võtame juurtipu kuhjast, vaatleme tema naabreid. Kui leiame lühema tee naabrini, lisame tee pikkuse paisktabelisse
     * ja lisame naabri kuhja, eelnevalt uuendades tema x väärtust.
     * Vastasel juhul ignoreerime ja vaatame järgmist tippu kuhjast. Teeme seda seni, kuni kogu kuhi saab tühjaks.
     * <p>
     * Üks eripära on see, et lubatud teepikkus on piiratud argumendiga x. See tähendab, et kui kaare kaal on sellest väärtusest suurem,
     * siis "eemaldame" (==ignoreerime) selle kaare.
     * <p>
     * Lõpuks sorteerime tulemused ja väljastame ekraanile.
     */
    public static void kaugused(String lähtelinn, int x, String[] linnad, int[][] M) {
        // Loome graafi ja paisktabeli tulemuse salvestamiseks
        List<Tipp> graaf = maatriksGraafideks(linnad, M);
        Map<Tipp, Integer> kaugused = new HashMap<>();
        for (Tipp tipp : graaf)
            kaugused.put(tipp, Integer.MAX_VALUE);

        // Loome kuhi tööfrondi kujutamiseks, lisame algpunkt
        Kuhi tööfrond = new Kuhi();
        Tipp algtipp = leiaTipp(lähtelinn, graaf);
        kaugused.put(algtipp, 0);
        algtipp.x = 0;
        tööfrond.lisa(algtipp);

        // Alustame Dijkstra algoritmiga
        while (tööfrond.pikkus() > 0) {
            Tipp vaadeldav = tööfrond.kustutaJuurtipp();

            for (Kaar kaar : vaadeldav.kaared) {
                if (kaar.kaal > x) // Tee on pikkem, kui võimaldab aku
                    continue;

                Tipp naaber = kaar.lõpp;
                int uusKaugus = kaugused.get(vaadeldav) + kaar.kaal;
                if (uusKaugus < 0) // Ületäitmise kaitse
                    uusKaugus = Integer.MAX_VALUE;

                if (uusKaugus < kaugused.get(naaber)) { // Leidsime lühimad teed
                    kaugused.put(naaber, uusKaugus);
                    naaber.x = uusKaugus;
                    tööfrond.lisa(naaber); // Saame sinna sõida "järgmine kord"
                }
            }
        }

        Collections.sort(graaf); // Sorteerime x (kauguse) järgi. Kui on võrdsed, siis tähestikuliselt
        for (Tipp tipp : graaf)
            if (tipp.x != 0) // Kui me kunagi ei vaatlenud Tippu, tema x väärtus on 0, ehk pole saadaval
                System.out.println(tipp.info + " " + kaugused.get(tipp));
    }


    /**
     * Abi. Loome tippude loetelu nime järgi ja seome neid kaarega vastavalt tabeli värtusele. Tulemuseks on graaf
     */
    public static List<Tipp> maatriksGraafideks(String[] nimed, int[][] M) {
        List<Tipp> graaf = new ArrayList<>();
        int N = M.length;

        for (int i = 0; i < N; i++)
            graaf.add(new Tipp(nimed[i]));

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {

                int kaal = M[i][j];
                String alg = nimed[i];
                String lõpp = nimed[j];

                if (kaal <= 0)
                    continue;

                Tipp algtipp = leiaTipp(alg, graaf);
                Tipp lõpptipp = leiaTipp(lõpp, graaf);
                Kaar kaar = new Kaar(algtipp, lõpptipp, kaal);
                algtipp.kaared.add(kaar);
            }

        return graaf;
    }

    /**
     * Abi. Otsime tippu "info" järgi ja tagastame tema. Eeldame, et tema "info" väärtus on unikaalne, sest neid
     * ei saa eristada.
     */
    public static Tipp leiaTipp(String sisu, List<Tipp> tipud) {
        for (Tipp tipp : tipud)
            if (tipp.info.equals(sisu))
                return tipp;
        return null;
    }

}


//======================================================================================================================
// Graafiga seotud andmestruktuurid. Enamasti praktikumist
//======================================================================================================================

class Tipp implements Comparable<Tipp> {
    final String info;
    final List<Kaar> kaared = new ArrayList<>();
    int x = 0; // kaugus lähttippust Dijkstra algoritmi jaoks

    public Tipp(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "'" + info + '\'';
    }

    public String getInfo() {
        return info;
    }

    @Override
    public int compareTo(Tipp other) {
        int tulemus = Integer.compare(this.x, other.x);

        if (tulemus == 0) // kaugused on võrdsed
            tulemus = this.info.compareTo(other.info);

        return tulemus;
    }
}

class Kaar {
    final Tipp alg; // kaare lähtetipp
    final Tipp lõpp; // kaare suubumistipp
    final int kaal; // kaare kaal (kui peaks vaja olema)

    public Kaar(Tipp alg, Tipp lõpp, int kaal) {
        this.alg = alg;
        this.lõpp = lõpp;
        this.kaal = kaal;
    }
}

class NimedegaNaabrusmaatriks {
    final int[][] M;
    final String[] nimed;

    NimedegaNaabrusmaatriks(int[][] M, String[] nimed) {
        this.M = M;
        this.nimed = nimed;
    }

    public NimedegaNaabrusmaatriks kopeeri() {
        String[] koopiaNimed = new String[nimed.length];
        for (int i = 0; i < nimed.length; i++) koopiaNimed[i] = nimed[i];
        int[][] koopiaM = new int[nimed.length][nimed.length];
        for (int rida = 0; rida < nimed.length; rida++)
            for (int veerg = 0; veerg < nimed.length; veerg++)
                koopiaM[rida][veerg] = M[rida][veerg];
        return new NimedegaNaabrusmaatriks(koopiaM, koopiaNimed);
    }

    public static NimedegaNaabrusmaatriks loeNaabrusmaatriks(File fail) throws IOException {
        List<String> read = Files.readAllLines(fail.toPath());
        // Loeme esimeselt realt nimed
        String[] nimed = read.get(0).split("\t");
        // Loeme naabrusmaatriksi
        int n = read.size() - 1;
        int[][] M = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] väärtused = read.get(i + 1).split("\t");
            for (int j = 0; j < väärtused.length; j++)
                M[i][j] = Integer.parseInt(väärtused[j]);
        }
        return new NimedegaNaabrusmaatriks(M, nimed);
    }
}

/**
 * Arvutipraks_6.Kuhi modifikatsioon, kus elemnedideks on Tipp. Tippu väli x on väärtsus, mille järgi sorteerime.
 * Veel üks muutus, mis ma tegin: max kuhi asemel kasutan min kuhi, ehk väiksem element on üles.
 */
class Kuhi {
    private List<Tipp> kuhi;

    /**
     * Konstruktor, mis loob täiesti tühja kuhja.
     */
    public Kuhi() {
        this.kuhi = new ArrayList<>();
    }

    /**
     * Konstruktor, mis loob uue kuhja, lisades argumendiks antud elemendid uude kuhja.
     *
     * @param algsedElemendid
     */
    public Kuhi(List<Tipp> algsedElemendid) {
        this.kuhi = new ArrayList<>(algsedElemendid);
    }

    @Override
    public String toString() {
        return kuhi.toString();
    }

    public int pikkus() {
        return kuhi.size();
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return indeks käesoleva elemendi vasemale harule.
     */
    public int vasemIndeks(int i) {
        int indeks = (i * 2) + 1;
        if (indeks >= kuhi.size())
            return -1;
        else
            return indeks;
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return indeks käesoleva elemendi paremale harule.
     */
    public int paremIndeks(int i) {
        int indeks = (i * 2) + 2;
        if (indeks >= kuhi.size())
            return -1;
        else
            return indeks;
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return indeks käesoleva elemendi ülemale.
     */
    public int ülemIndeks(int i) {
        if (i <= 0)
            return -1;
        return (i - 1) / 2;
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return i-nda järjendi elemendi vasema haru *sisu*.
     */
    public Tipp vasem(int i) {
        return kuhi.get(vasemIndeks(i));
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return i-nda järjendi elemendi parema haru *sisu*.
     */
    public Tipp parem(int i) {
        return kuhi.get(paremIndeks(i));
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return i-nda järjendi elemendi ülema *sisu*.
     */
    public Tipp ülem(int i) {
        return kuhi.get(ülemIndeks(i));
    }


    /**
     * Vahetab ära kaks järjendi elementi, vastavalt elemendite väärtusele, kasutades nende indekseid vastupidi.
     *
     * @param i              Esimese vahetatava indeks.
     * @param element        Esimese vahetatava element.
     * @param ülemineIndeks  Teise vahetatava indeks.
     * @param ülemineElement Teise vahetatava element.
     */
    private void vaheta(int i, Tipp element, int ülemineIndeks, Tipp ülemineElement) {
        kuhi.set(i, ülemineElement);
        kuhi.set(ülemineIndeks, element);
    }

    /**
     * Meetod, mis viib käesoleval indeksil oleva elemendi kuhja järjendis/puus allapoole, kuni element ei ole enam oma
     * vahetutest alluvatest väiksem.
     *
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     */
    public void mullinaAlla(int i) {
        if (i > ülemIndeks(kuhi.size() - 1) || kuhi.size() <= 1)
            return;

        Tipp element = kuhi.get(i);
        int alumineIndeks = vasemIndeks(i);

        if (paremIndeks(i) != -1 && vasem(i).x > parem(i).x)
            alumineIndeks = paremIndeks(i);

        if (element.x > kuhi.get(alumineIndeks).x) {
            vaheta(i, element, alumineIndeks, kuhi.get(alumineIndeks));
            mullinaAlla(alumineIndeks);
        }
    }

    /**
     * Meetod, mis viib käesoleval indeksil oleva elemendi kuhja järjendis/puus ülespoole, kuni element ei ole enam oma
     * ülemast suurem.
     *
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     */
    public void mullinaÜles(int i) {
        if (i <= 0)
            return;

        Tipp element = kuhi.get(i);
        int ülemineIndeks = ülemIndeks(i);
        Tipp ülemineElement = ülem(i);

        if (ülemineElement.x > element.x) {
            vaheta(i, element, ülemineIndeks, ülemineElement);
            mullinaÜles(ülemineIndeks);
        }
    }

    /**
     * Lisab kuhja uue elemendi.
     *
     * @param element Kuhja lisatav element.
     */
    public void lisa(Tipp element) {
        kuhi.add(element);
        mullinaÜles(kuhi.size() - 1);
    }

    /**
     * @return Juurtipp, mis meetodi töö tulemusel kustutatakse kuhjast.
     */
    public Tipp kustutaJuurtipp() {
        if (kuhi.size() == 0)
            throw new RuntimeException();
        if (kuhi.size() == 1)
            return kuhi.remove(0);

        Tipp juur = kuhi.get(0);
        Tipp viimane = kuhi.remove(kuhi.size() - 1);
        kuhi.set(0, viimane);
        mullinaAlla(0);

        return juur;
    }

    /**
     * Muudame muutujas/järjendis "kuhi" asuvad elemendid kuhja reeglitele vastavasse järjestusse. Selle saavutamisek
     * tahame viia kõiki tippe mullina allapoole, alustades sügavamatest tippudest ja liikudes ülespoole.
     */
    public void kuhjasta() {
        int viimaneVahetipp = ülemIndeks(kuhi.size() - 1);
        for (int i = viimaneVahetipp; i >= 0; i--)
            mullinaAlla(i);
    }
}
