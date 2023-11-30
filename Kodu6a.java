import ee.ut.dendroloj.Dendrologist;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class Kodu6a {
    public static void main(String[] args) throws IOException {
        Dendrologist.setUIScale(2);

        // Näide meetodite rakendamisest

        // Tekstifailist algse teksti lugemine
//        String sisu = Files.readString(new File("fail.txt").toPath());
        String sisu = "aaabbbbbc c";

        // Koodipuu koostamine, kodeerimine ja dekodeerimine
        Tipp koodipuu = koostaKoodipuu(sisu);
//        boolean[] bitid = kodeeri(koodipuu, sisu);
//        String dekodeeritud = dekodeeri(koodipuu, bitid);
//        System.out.println(dekodeeritud);

        // Näide abiklassi FailiSisu kasutamisest

        // Andmete faili kirjutamine
        // Tipp koodipuu = koostaKoodipuu(sisu);
        // boolean[] bitid = kodeeri(koodipuu, sisu);
        // FailiSisu.kirjutaFaili(new File("kodeeritud.dat"), koodipuu, bitid);

        // Andmete failist lugemine
        // FailiSisu failiSisu = FailiSisu.loeFailist(new File("kodeeritud.dat"));
        // String dekodeeritud = dekodeeri(failiSisu.koodipuu, failiSisu.bitid);
        // System.out.println(dekodeeritud);
    }

    public static Tipp koostaKoodipuu(String sisu) {
        /* a) teksti põhjal luua sümbolite sagedustabel; */
        int[][] sagedustabel = genereeriSagedustabel(sisu);
        System.out.println(Arrays.deepToString(sagedustabel));

        /* b) sagedustabeli põhjal luua loetelu ühetipulistest puudest (igas tipus on siis info kujul sümbol + selle esinemiste arv) */

        Tipp[] tippud = new Tipp[sagedustabel.length];


        /* c) selliste puude metsast Huffmani koodipuu loomine (koodipuu lehttippudes peaks olema .info välja väärtuseks vastav sümbol) */
        return null;
    }

    public static void kuva(Tipp tipp) {
        Dendrologist.drawBinaryTree(tipp, t -> (t.info + " (" + t.x + ")"), t -> t.v, t -> t.p);
    }

    /**
     * Abi. Tagastab sagedustabel kujul {{sümbol_1, sagedus_1}, ..., {sümbol_n, sagedus_n}}
     * Kuna teame, et UTF-8 tabelis on 256 elemndi,
     * realiseerime enda paisktabeli, kus sümboli arvuline väärtus vastab tema asukoha indeksile. Ehk h(k) = k
     * <p>
     * Teisendan sõne "byte" massiviks. Kuna Java "byte" on märgiga, tuleb liita +128, et vältiga negatiivsed indeksid.
     * <b>NB!! Kui pärast teisendada sümbolid tagasi sõneks, tuleb tagasi -128 lahutada</b>
     *
     * @return Sagedustabel, <b>sorteeritud</b> sümboli esinemise järgi
     */
    public static int[][] genereeriSagedustabel(String sõne) {
        int[][] sagedustabel = new int[256][2];
        byte[] sõneBaitides = sõne.getBytes(StandardCharsets.UTF_8);
        for (byte sümbol : sõneBaitides) {
            int sümbolUTF8 = sümbol + 128;
            sagedustabel[sümbolUTF8][0] = sümbolUTF8;
            sagedustabel[sümbolUTF8][1]++;
        }

        Arrays.sort(sagedustabel, (a, b) -> Integer.compare(b[1], a[1])); // teise elemendi järgi kehenevas järjekorras
        int lõpp;
        for (lõpp = 0; lõpp < sagedustabel.length; lõpp++)
            if (sagedustabel[lõpp][1] == 0) // Kui sagedus == 0, sest see jä järgmine sümbol enam ei esine
                break;

        return Arrays.copyOfRange(sagedustabel, 0, lõpp);
    }

    /* Teisendab UTF-8 int (määrgideta) vastavaks sümboliks sõne kujul */
    public static String intSõneks(int bait) {
        byte[] b = {(byte) (bait - 128)}; // Eelmise +128 nüüd tuleb lahutada
        return new String(b, StandardCharsets.UTF_8);
    }

    public static boolean[] kodeeri(Tipp koodipuu, String sisu) {
        // d) leida koodipuust igale sümbolile vastav uus kood -> prefikskood
        // e) algtekst, prefikskoodid, koodipuu -> pakitud tekst (bittide massiiv, kus true on 1 ja false on 0)

        throw new UnsupportedOperationException();
    }

    public static String dekodeeri(Tipp koodipuu, boolean[] bitid) {
        // f) koodipuu, pakitud tekst -> lahtipakitud tekst

        throw new UnsupportedOperationException();
    }


}