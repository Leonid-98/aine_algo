/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö 6
 * Teema: Kahendkuhjad
 *
 * Autor: Leonid Tšigrinski
 *
 *****************************************************************************/

/**
 * Abiklass, mis sisaldub sümboli esinemise arv mingis tekstis
 */
public class Sümbol implements Comparable<Sümbol> {
    char väärtus;
    int sagedus;

    public Sümbol(char väärtus, int sagedus) {
        this.väärtus = väärtus;
        this.sagedus = sagedus;
    }

    @Override
    public int compareTo(Sümbol o) {
        // Selleks, et saaks sorteerida massiivi
        return this.sagedus - o.sagedus;
    }
    @Override
    public String toString() {
        return String.format("%c(%d)", väärtus, sagedus);
    }
}
