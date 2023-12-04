package Kodu; /*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 6
 * Teema: Kahendkuhjad
 *
 * Autor: Leonid Tšigrinski
 *
 *****************************************************************************/

import ee.ut.dendroloj.Dendrologist;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Kodu6a {
    public static void main(String[] args) throws IOException {
        Dendrologist.setUIScale(1);

//        String sisu = Files.readString(new File("Kõrboja_peremees.txt").toPath());
        String sisu = "'Kõrboja_peremees.txt' puhul terminali väljund on liiga pikk (˵ ͡° ͜ʖ ͡°˵)";
        System.out.println("aglne sisend:\n" + sisu);

        Tipp koodipuu = koostaKoodipuu(sisu);
        Dendrologist.drawBinaryTree(koodipuu, t -> ("[" + t.info + "], k=" + t.x), t -> t.v, t -> t.p);

        boolean[] bitid = kodeeri(koodipuu, sisu);
        System.out.println("kodeeritud:");
        for (boolean bitt : bitid) {
            String b = bitt ? "1" : "0";
            System.out.printf("%s", b);
        }
        System.out.println();

        FailiSisu.kirjutaFaili(new File("Kodu/kodeeritud.dat"), koodipuu, bitid);
        FailiSisu failiSisu = FailiSisu.loeFailist(new File("Kodu/kodeeritud.dat"));

        String dekodeeritud = dekodeeri(failiSisu.koodipuu, failiSisu.bitid);
        System.out.println("dekodeeritud:\n" + dekodeeritud);
    }

    /**
     * Meetod, mis koostab Huffmani kodipuu:
     * <ol>
     *   <li>Loome sagedustabel iga sümboli jaoks, kasutades paisktabeli</li>
     *   <li>Sagedustabeli põhjal teeme Tippude loetelu, millel pole alluvat. Väljas "info" on sümbol, "x" on sagedus</li>
     *   <li>Loome min kuhi. Lisame kuhjasse kõik tippud. Kuhi võrdleb tippud sageduse põhjal. Kuna ta on minimaalne, väksemad lähevad ülesse.</li>
     *   <li>Initsialiseerime tühi Tippu, mis on Huffmani puu aluseks.</li>
     *   <li>Käime läbi kõik kuhja elemendid:
     *     <ul>
     *       <li>Võtame kaks esimesed kuhja elemendid (ehk väiksemad)</li>
     *       <li>Olgu need elemendid lehtedeks. Nende ülemuseks loome uue vahetippu, kus x on nende mõlema x summa, info on null</li>
     *       <li>Huffmani Tippu (mis on loodud 4-l sammul) juureks paneme seda vahetippu. (Ehk sümbolid on lehed)</li>
     *       <li>Lisame kuhjasse vahetippu (Ta ei pruugi olla minimaalne, võib olla läheb alla)</li>
     *       <li>Kui kuhi ei ole tühi, hüpame tagasi sammu (5)</li>
     *     <ul/>
     *   </li>
     * </ol>
     *
     * @return Huffmani puu juur
     */
    public static Tipp koostaKoodipuu(String sisu) {
        // a) teksti põhjal luua sümbolite sagedustabel

        HashMap<Character, Integer> sagedustabel = new HashMap<>();
        for (char sümbol : sisu.toCharArray())
            if (sagedustabel.containsKey(sümbol)) {
                int sagedus = sagedustabel.get(sümbol);
                sagedus++;
                sagedustabel.put(sümbol, sagedus);
            } else
                sagedustabel.put(sümbol, 1);

        // b) sagedustabeli põhjal luua loetelu ühetipulistest puudest
        // (igas tipus on siis info kujul sümbol + selle esinemiste arv)

        TKuhi kuhi = new TKuhi();
        for (Character sümbol : sagedustabel.keySet()) {
            Tipp t = new Tipp(sümbol.toString());
            t.x = sagedustabel.get(sümbol);
            kuhi.lisa(t);
        }

        // c) selliste puude metsast Huffmani koodipuu loomine
        // (koodipuu lehttippudes peaks olema .info välja väärtuseks vastav sümbol)

        Tipp juur = null;
        Tipp vahe, vasak, parem;
        while (kuhi.pikkus() > 1) {
            vasak = kuhi.kustutaJuurtipp();
            parem = kuhi.kustutaJuurtipp();

            vahe = new Tipp(null);
            vahe.x = vasak.x + parem.x;
            vahe.v = vasak;
            vahe.p = parem;

            juur = vahe;
            kuhi.lisa(vahe);
        }

        return juur;
    }

    /**
     * Meetod, mis kodeerib sõne:
     * <ol>
     *   <li>Loome paisktabel, kus võtiks on sümbol ja väärtuseks on tema binaarkood sõne kujul</li>
     *   <li>Paikstaabeli järgi kodeerime sõne binaararvuks (sõnena)</li>
     *   <li>Teisendame tulemus boolean massiiviks</li>
     * </ol>
     *
     * @return kodeeritud sõne bittid
     */
    public static boolean[] kodeeri(Tipp koodipuu, String sisu) {
        // d) leida koodipuust igale sümbolile vastav uus kood -> prefikskood

        HashMap<Character, String> koodid = kodeeriRek(koodipuu, "");

        // e) algtekst, prefikskoodid, koodipuu -> pakitud tekst (bittide massiiv, kus true on 1 ja false on 0)

        StringBuilder kodeeritudSõne = new StringBuilder();
        for (char sümbol : sisu.toCharArray()) {
            kodeeritudSõne.append(koodid.get(sümbol));
        }

        int pikkus = kodeeritudSõne.length();
        boolean[] tulemus = new boolean[pikkus];
        for (int i = 0; i < pikkus; i++)
            tulemus[i] = kodeeritudSõne.charAt(i) == '1';

        return tulemus;
    }

    /**
     * Abi. Käime läbi puu eestjärjestus, salvestame paisktabelis iga sümbol ja tee, kuidas sümbolini jõua.
     */
    public static HashMap<Character, String> kodeeriRek(Tipp juur, String kood) {
        HashMap<Character, String> tulemus = new HashMap<>();
        // Baas. Jõudsime sümbolini
        if (juur.v == null && juur.p == null && juur.info != null) {
            tulemus.put(juur.info.charAt(0), kood);
            return tulemus;
        }

        // Hargnemine. Vasakul minnes on 0, paremal on 1. Ühildame mõlema osa paisktabeli üheks
        tulemus.putAll(kodeeriRek(juur.v, kood + "0"));
        tulemus.putAll(kodeeriRek(juur.p, kood + "1"));

        return tulemus;
    }

    /**
     * Käime läbi kõik sümbolid sama tee kaudu, nagu bitide massiivi määratud.
     * Kui enam alluvat pole, üks sümbol on dekodeeritud. Alustame uuesti juurtippust.
     *
     * @return Aglne sõne
     */
    public static String dekodeeri(Tipp koodipuu, boolean[] bitid) {
        // f) koodipuu, pakitud tekst -> lahtipakitud tekst

        StringBuilder tulemus = new StringBuilder();
        Tipp juur = koodipuu; // Läbime tee koodi järgi
        for (boolean bitt : bitid) {
            if (bitt)
                juur = juur.p;
            else
                juur = juur.v;

            if (juur.v == null && juur.p == null) {
                tulemus.append(juur.info);
                juur = koodipuu; // Kood on leitud, liigutame algusesse
            }
        }

        return tulemus.toString();
    }
}
