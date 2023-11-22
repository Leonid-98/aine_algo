package Arvutipraks_6;

public class Tipp {
    int väärtus;
    Tipp v;
    Tipp p;
    int x; // abiväli

    Tipp(int väärtus, Tipp v, Tipp p) {
        this.väärtus = väärtus;
        this.v = v;
        this.p = p;
    }

    Tipp(int väärtus) {
        this.väärtus = väärtus;
    }
}