import ee.ut.dendroloj.Dendrologist;

import java.util.ArrayList;
import java.util.List;

public class Kuhi {
    private List<Integer> kuhi; // Järjend, milles hoiame kuhja elemente.

    /**
     * Konstruktor, mis loob täiesti tühja kuhja.
     */
    public Kuhi() {
        this.kuhi = new ArrayList<>(); // Kuhjaks on uus täiesti uus ja tühi järjend.
    }

    /**
     * Konstruktor, mis loob uue kuhja, lisades argumendiks antud elemendid uude kuhja.
     *
     * @param algsedElemendid
     */
    public Kuhi(List<Integer> algsedElemendid) {
        this.kuhi = new ArrayList<>(algsedElemendid); // Loome uue kuhja, kus algseteks elementideks on argumendi sisu.
    }

    /**
     * Kuhja sisu sõnelisele kujule viimise meetod.
     *
     * @return Sõneline kuju sisemisest "kuhi" isendiväljast.
     */
    @Override
    public String toString() {
        return kuhi.toString();
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return indeks käesoleva elemendi vasemale harule.
     */
    public int vasemIndeks(int i) {
        int indeks = (i * 2) + 1; // Kasutame etteantud valemit.
        if (indeks >= kuhi.size()) return -1; // Kui valem viib järjendi mõõtmetest välja, siis -1.
        else return indeks; // Muul juhul tagastame valemi leitud tulemus.
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return indeks käesoleva elemendi paremale harule.
     */
    public int paremIndeks(int i) {
        int indeks = (i * 2) + 2; // Kasutame etteantud valemit.
        if (indeks >= kuhi.size()) return -1; // Kui valem viib järjendi mõõtmetest välja, siis -1.
        else return indeks; // Muul juhul tagastame valemi leitud tulemus.
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return indeks käesoleva elemendi ülemale.
     */
    public int ülemIndeks(int i) {
        if (i <= 0) return -1; // Kui indeks on 0, ehk juurtipp, siis ülemat ei leidu, tagastame -1.
        return (i - 1) / 2; // Muul juhul kasutame etteantud valemit ja tagastame tulemuse.
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return i-nda järjendi elemendi vasema haru *sisu*.
     */
    public int vasem(int i) {
        return kuhi.get(vasemIndeks(i));
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return i-nda järjendi elemendi parema haru *sisu*.
     */
    public int parem(int i) {
        return kuhi.get(paremIndeks(i));
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return i-nda järjendi elemendi ülema *sisu*.
     */
    public int ülem(int i) {
        return kuhi.get(ülemIndeks(i));
    }

    /**
     * Kuvab DendroloJ-d kasutades kuhja puukuju ekraanile.
     */
    public void kuva() {
        Tipp tipp = teePuukuju(0);
        Dendrologist.drawBinaryTree(tipp, t -> ("[" + t.info + "], k=" + t.x), t -> t.v, t -> t.p);
    }


    /**
     * @param i Elemendi indeks, mille Tippu parasjagu loomas oleme.
     * @return Tipp, mis sisaldab infoväljal kuhja i-ndat elementi, alluvad koostame rekursiooniga.
     */
    private Tipp teePuukuju(int i) {
        if (i <= -1 || i >= kuhi.size()) return null; // Kui indeks mõõtmetelt väljas, saame tühja puukuju.
        Tipp vasemAlluv = teePuukuju(vasemIndeks(i)); // Ehitame puu i-nda elemendi vasema haru järgi.
        Tipp paremAlluv = teePuukuju(paremIndeks(i)); // Ehitame puu i-nda elemendi parema haru järgi.
        return new Tipp("" + kuhi.get(i), vasemAlluv, paremAlluv); // Koostame tipu, infoväljal kuhja i-nda elemendi sisu ..
        // .. ning alluvateks määrame rekursiooniga koostatud vasema ja parema alampuu juurtipud.
    }

    /**
     * Vahetab ära kaks järjendi elementi, vastavalt elemendite väärtusele, kasutades nende indekseid vastupidi.
     *
     * @param i              Esimese vahetatava indeks.
     * @param element        Esimese vahetatava element.
     * @param ülemineIndeks  Teise vahetatava indeks.
     * @param ülemineElement Teise vahetatava element.
     */
    private void vaheta(int i, int element, int ülemineIndeks, int ülemineElement) {
        kuhi.set(i, ülemineElement); // Kirjutame esimese vahetatava indeksile teise vahetatava elemendi.
        kuhi.set(ülemineIndeks, element); // Kirjutame teise vahetatava indeksile esimese vahetatava elemendi.
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
        int element = kuhi.get(i);
        int alumineIndeks = vasemIndeks(i);
        if (paremIndeks(i) != -1 && vasem(i) > parem(i)) alumineIndeks = paremIndeks(i);

        if (element > kuhi.get(alumineIndeks)) {
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
        if (i <= 0) return; // Üles saame liikuda vaid siis, kui tegemist ei ole juurtipuga.
        int element = kuhi.get(i); // Leiame, mis element i-ndal asub.

        int ülemineIndeks = ülemIndeks(i); // Leiame ülema indeksi.
        int ülemineElement = ülem(i); // Leiame abimeetodiga ülemal indeksil asuva elemendi.

        if (ülemineElement > element) { // Kui ülevalpool asuv elementi on väiksem, siis on järjestus vale ja ..
            // Vahetame elemendi väärtused ülemaga, et nad oleksid õigetpidi järjestatud.
            vaheta(i, element, ülemineIndeks, ülemineElement);
            // Viime ülespoole viidud elementi vajadusel veel kõrgemale:
            mullinaÜles(ülemineIndeks);
        }
    }

    /**
     * Lisab kuhja uue elemendi.
     *
     * @param element Kuhja lisatav element.
     */
    public void lisa(int element) {
        kuhi.add(element); // Lisame uue elemendi kuhja lõppu.
        mullinaÜles(kuhi.size() - 1); // Viime teda mullina üles, et temast kõrgemal ei leiduks väikseimaid.
    }

    /**
     * @return Juurtipp, mis meetodi töö tulemusel kustutatakse kuhjast.
     */
    public int kustutaJuurtipp() {
        if (kuhi.size() == 0) throw new RuntimeException(); // Tühjast kuhjast ei saa kustutada ja tagastada.
        if (kuhi.size() == 1) return kuhi.remove(0); // Üheelemendilisest saab kustutamisel tühi kuhi.
        // Kui on aga rohkem elemente:
        int juur = kuhi.get(0); // Jätame juurtipu meelde.
        int viimane = kuhi.remove(kuhi.size() - 1); // Kustutame viimase tipu ja jätame meelde.
        kuhi.set(0, viimane); // Kustutame juurtipu, asendades tema sisu viimase elemendi sisuga.
        mullinaAlla(0); // Viime uut juurtippu mullina alla, et temast sügavamal ei leiduks suuremaid.
        return juur; // Tagastame varem meelde jäänud kustutatud juurtipu sisu.
    }

    /**
     * Muudame muutujas/järjendis "kuhi" asuvad elemendid kuhja reeglitele vastavasse järjestusse. Selle saavutamisek
     * tahame viia kõiki tippe mullina allapoole, alustades sügavamatest tippudest ja liikudes ülespoole.
     */
    public void kuhjasta() {
        // Leiame, mis on viimane tipp, mida on mõtet mullina alla viima asuda. Tipud, millel alluvaid ei ole, ei oleks
        // vaja vaevuda allapoole viia. Seega leiame, mis on viimase tipu ülem, tema ülem ongi viimane tipp, millel on
        // olemas alluv.
        int viimaneVahetipp = ülemIndeks(kuhi.size() - 1);
        for (int i = viimaneVahetipp; i >= 0; i--) // Käikme viimasest vahetipust kuni juurtipuni indeksid läbi.
            mullinaAlla(i); // Viime iga indeksi elemendid mulline allapoole.
        // Peale tsüklit on kuhja järjend kuhja reeglitele vastaval kujul.
    }
}
