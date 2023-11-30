/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 6
 * Teema: Kahendkuhjad
 *
 * Autor: Leonid Tšigrinski, Jaan Janno (Kuhi klass täisarvude jaoks, muutsin Tippude jaoks)
 *
 *****************************************************************************/

import ee.ut.dendroloj.Dendrologist;

import java.util.ArrayList;
import java.util.List;

/**
 * Kuhi modifikatsioon, kus elemnedideks on Tipp. Tippu väli x on väärtsus, mille järgi sorteerime
 */
public class TKuhi {
    private List<Tipp> kuhi; // Järjend, milles hoiame kuhja elemente.

    /**
     * Konstruktor, mis loob täiesti tühja kuhja.
     */
    public TKuhi() {
        this.kuhi = new ArrayList<>();
    }

    /**
     * Konstruktor, mis loob uue kuhja, lisades argumendiks antud elemendid uude kuhja.
     *
     * @param algsedElemendid
     */
    public TKuhi(List<Tipp> algsedElemendid) {
        this.kuhi = new ArrayList<>(algsedElemendid);
    }

    /**
     * Kuhja sisu sõnelisele kujule viimise meetod.
     *
     * @return Sõneline kuju sisemisest "kuhi" isendiväljast.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < kuhi.size(); i++) {
            Tipp t = kuhi.get(i);
            s.append(t.info).append("(").append(t.x).append(")");

            if (i == kuhi.size() - 1)
                s.append("]");
            else
                s.append(", ");
        }
        return s.toString();
    }

    /**
     * Tagastab kuhi elementide arv
     */
    public int pikkus() {
        return kuhi.size();
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return indeks käesoleva elemendi vasemale harule.
     */
    public int vasemIndeks(int i) {
        int indeks = (i * 2) + 1;
        if (indeks >= kuhi.size()) return -1;
        else return indeks;
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return indeks käesoleva elemendi paremale harule.
     */
    public int paremIndeks(int i) {
        int indeks = (i * 2) + 2;
        if (indeks >= kuhi.size()) return -1;
        else return indeks;
    }

    /**
     * @param i Käesoleva elemendi indeks kuhja järjendis.
     * @return indeks käesoleva elemendi ülemale.
     */
    public int ülemIndeks(int i) {
        if (i <= 0) return -1;
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
    public Tipp teePuukuju(int i) {
        if (i <= -1 || i >= kuhi.size()) return null; // Kui indeks mõõtmetelt väljas, saame tühja puukuju.
        Tipp vasemAlluv = teePuukuju(vasemIndeks(i)); // Ehitame puu i-nda elemendi vasema haru järgi.
        Tipp paremAlluv = teePuukuju(paremIndeks(i)); // Ehitame puu i-nda elemendi parema haru järgi.

        Tipp tipp = kuhi.get(i);
        tipp.v = vasemAlluv;
        tipp.p = paremAlluv;
        return tipp;
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

        // Tuleb kõik alluvad lisada
        if (element.v != null)
            lisa(element.v);
        if (element.p != null)
            lisa(element.p);
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
