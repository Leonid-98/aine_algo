import ee.ut.dendroloj.Dendrologist;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class Kodu6a {
    public static void main(String[] args) throws IOException {
        Dendrologist.setUIScale(2);

        // Tekstifailist algse teksti lugemine
//        String sisu = Files.readString(new File("fail.txt").toPath());
        String sisu = "vanapaganavanaema";


        // Koodipuu koostamine, kodeerimine ja dekodeerimine
        Tipp koodipuu = koostaKoodipuu(sisu);
//        Dendrologist.drawBinaryTree(koodipuu, t -> ("[" + t.info + "], k=" + t.x), t -> t.v, t -> t.p);
        boolean[] bitid = kodeeri(koodipuu, sisu);
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


    public static boolean[] kodeeri(Tipp koodipuu, String sisu) {
        // d) leida koodipuust igale sümbolile vastav uus kood -> prefikskood

        printCode(koodipuu, "");

        // e) algtekst, prefikskoodid, koodipuu -> pakitud tekst (bittide massiiv, kus true on 1 ja false on 0)



        return null;
    }

    public static void printCode(Tipp root, String s) {
        if (root.v == null && root.p == null && root.info != null) {
            System.out.println(String.format("'%s'='%s'", root.info, s));
            return;
        }
        printCode(root.v, s + "0");
        printCode(root.p, s + "1");
    }

    public static String dekodeeri(Tipp koodipuu, boolean[] bitid) {
        // f) koodipuu, pakitud tekst -> lahtipakitud tekst
        return null;
    }
}
