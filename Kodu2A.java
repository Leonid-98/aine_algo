/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 2
 * Teema: Rekursioon. Variantide läbivaatamine
 *
 * Autor: Leonid Tšigrinski
 *
 * Kasutatud allikad: -
 *
 *****************************************************************************/

public class Kodu2A {
    public static void main(String[] args) {
        int[] a = {2818, 1709, 90, 2610, 3077, 211, 3561, 1284, 888};
        System.out.println(valik(a));

    }

    /**
     * Meetod mis sellise etteantud järjendi korral kontrollib, kas sellest järjendist mingi elementide valik,
     * milles on mitte rohkem kui pooled elemendid, annab kogukaaluks 10 kg (10 kg = 10000 g).
     * <p>
     * Printsiib on selline:
     * Baasjuhul kontrollime erijuhud: massiiv on läbitud, valiku suurus on ületatud või kombinatsioon leitud.
     * Rekursioonis me hargneme kahele poole: siis, kui lisame vaadeldav element valikule või mitte.
     * <p>
     * Optimeerime rekursiooni niimodi:
     * 1) Hargneme vasakule ainult siis, kui lisades elementi me ei ületa valiku lubatud kaalu.
     * 2) Hargneme paremale ainult siis, kui kombinatsioon veel ei leitud vasakul harul.
     *
     * @param massiiv Antud elemendide järjend (iga element grammides)
     * @param i       Vaadeldava elemndi indeks
     * @param summa   Vaadeldava valiku koogukaal (summa)
     * @param mitu    Vaadeldava valiku elementide arv
     * @return Kas leidub valik, mille koogukaal on täpselt 10_000 g.
     */
    public static boolean valik(int[] massiiv, int i, int summa, int mitu) {
        // Baasjuht 1. Kõik elmendid on läbitud, aga soobiva kombinatsiooni ei leia
        if (i >= massiiv.length)
            return false;
        // Baasjuht 2. Valiku suurus on rohkem, kui pooled, aga soobiva kombinatsiooni ei leia
        if (mitu >= massiiv.length / 2)
            return false;
        // Baasjuht 3. Valiku summa on täpselt 10_000, soobiv kombinatsioon leitud
        if (massiiv[i] + summa == 10_000)
            return true;

        boolean vasak = false;
        boolean parem = false; // Vaikimisi, valik ei soobi

        // Rekursiooni samm
        if (massiiv[i] + summa < 10_000) // Hargneme, kui on "ruumi", ehk kaal ei ületa 10 kg.
            vasak = valik(massiiv, i + 1, summa + massiiv[i], mitu + 1);
        if (!vasak) // Hargneme, kui vasakul kombinatsioon ei leitud
            parem = valik(massiiv, i + 1, summa, mitu);

        return vasak || parem; // Tõsi, kui vähemalt ühes harus leiatakse soobiva kombinatsiooni (disjunktsioon)
    }

    /* Vaikeväärtustatud kutse */
    public static boolean valik(int[] massiiv) {
        return valik(massiiv, 0, 0, 0);
    }
}
