package Arvutipraks_5;

import ee.ut.dendroloj.Dendrologist;

public class Y7ja8 {

    public static void main(String[] args) {
        Dendrologist.setUIScale(2);
        KOTipp tipp = Y5.juhupuu(5);
        Y6.täida(tipp, KOTipp.juhusisu(tipp));

        int väikseim = leiaVähim(tipp);
        System.out.println("Vähim: " + väikseim);

        KOTipp leitav = otsi(tipp, 10);
        if (leitav == null) System.out.println("Ei leidunud!");
        else System.out.println("Leiti " + leitav.väärtus);

        lisa(tipp, 20);

        KOTipp.kuvaKahendotsimispuu(tipp);

        tipp = kustuta(tipp, tipp.väärtus); // kustutame katseks juurtipu.

        KOTipp.kuvaKahendotsimispuu(tipp);
    }

    /**
     * @param tipp       Tipp, millest algavast puust soovime antud võtit eemaldada.
     * @param kustutatav Võti, mida sisaldava tipu soovime puust eemaldada.
     * @return Kustutamisoperatsiooni-järgne juurtipp. Kui kustub mõni sügavam element, siis jääb samaks, aga võib
     * muutuda kui juur ise kustub.
     */
    private static KOTipp kustuta(KOTipp tipp, int kustutatav) {
        if (tipp == null) return null; // Tühjast tipust jääb ka peale kustutamist tühi tipp.
        else if (kustutatav < tipp.väärtus) tipp.v = kustuta(tipp.v, kustutatav); // Väiksemat kustutame vasemalt.
        else if (kustutatav > tipp.väärtus) tipp.p = kustuta(tipp.p, kustutatav); // Suuremat kustutame paremalt.
            // Siit alumistes saame eeldada, et uuritavas tipus on kustutatav võti, sest polnud suurem ega väiksem.
        else if (tipp.v == null && tipp.p == null) return null; // Kui alluvaid pole, jääb kustutamisest tühjus alles.
        else if (tipp.v == null) return tipp.p; // Kui leidub vaid parem, jääb kustutades parem haru juurena alles.
        else if (tipp.p == null) return tipp.v; // Kui leidub vaid vasem, jääb kustutades vasem haru juurena alles.
        else { // Kui leiduvad aga mõlemad harud, siis ..
            int paremaVähim = leiaVähim(tipp.p); // .. leiame parem haru vähima elemendi ja jätame meelde.
            tipp.p = kustuta(tipp.p, paremaVähim); // Kustutame selle vähima elemendi paremalt.
            tipp.väärtus = paremaVähim; // Ja asendame käesoleva kustutatava juurtipu sisu paremalt leitud vähimaga.
        }
        return tipp; // Tagastame kustutusjärgse juurtipu. Kui siia jõuame, siis juurtipp jäi kustutamata või
        // asendati vaid sisu. Muul juhul tagastasime juba üleval, kui juurtipp kustutati.
    }

    /**
     * @param tipp    Tipp, millest algavasse puusse lisame uut võtit.
     * @param lisatav Võti, millele soovime uue tipu luua ja puusse lisada.
     */
    private static void lisa(KOTipp tipp, int lisatav) {
        if (lisatav < tipp.väärtus) {  // Kui lisatav on tipust väiksem, asume teda vasemasse harusse lisama.
            if (tipp.v == null) tipp.v = new KOTipp(lisatav); // Kui vasem haru tühi, lisame uue tipu vasemaks haruks.
            else lisa(tipp.v, lisatav); // Muul juhul lisame võtme rekursiooniga vasema haru külge.
        } else { // Kui aga lisatav on tipust suurem või on võrdne, asume teda paremasse harusse lisama.
            if (tipp.p == null) tipp.p = new KOTipp(lisatav); // Kui parem haru tühi, lisame uue tipu paremaks haruks.
            else lisa(tipp.p, lisatav); // Muul juhul lisame võtme rekursiooniga parema haru külge.
        }
    }

    /**
     * @param tipp Tipp, millest algavas puus otsime võtit.
     * @param võti Otsitav võti.
     * @return Tipp, mis sisaldav otsitavat võtit. Kui ei leidu, siis "null".
     */
    private static KOTipp otsi(KOTipp tipp, int võti) {
        if (tipp == null) return null; // Tühjas puus võtit kindlasti ei leidu.
        else if (tipp.väärtus == võti) return tipp; // Kui käesolev tipp sisaldab võtit, siis tagastame tipu.
        else { // Kui aga käesolev tipp võtit ei sisaldanud, siis ..
            KOTipp vasemalt = otsi(tipp.v, võti); // .. otsime võtit tipu vasemast harust.
            if (vasemalt != null) return vasemalt; // Kui leiame, siis tagastame.
            KOTipp paremalt = otsi(tipp.v, võti); // Muul juhul otsime võtit edasi paremast harust.
            return paremalt; // Tagastame leiu. (see leid võib olla ka "null", kui ka paremast harust ei leitud)
        }
    }

    /**
     * @param tipp Tipp, millest algavas puus otsime vähimat.
     * @return Tipust tipp algava alampuu vähima tipu väärtus.
     */
    private static int leiaVähim(KOTipp tipp) {
        if (tipp.v == null) return tipp.väärtus; // Kui vasem puudub, on käesolev kõige väiksem.
        return leiaVähim(tipp.v); // Muul juhul leidub vasemas harus veel väiksem, otsime sealt.
    }
}

