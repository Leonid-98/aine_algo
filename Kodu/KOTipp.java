package Kodu;

public class KOTipp {
    int väärtus;
    KOTipp v;
    KOTipp p;
    int x; // abiväli

    KOTipp(int väärtus, KOTipp v, KOTipp p) {
        this.väärtus = väärtus;
        this.v = v;
        this.p = p;
    }

    KOTipp(int väärtus) {
        this.väärtus = väärtus;
    }
}