package Arvutipraks_6;

import ee.ut.dendroloj.Dendrologist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kuhi {
    private List<Integer> kuhi;


    public void lisa(int element) {
        kuhi.add(element);
        mullinaÜles(kuhi.size() - 1);
    }

    public int eemaldaJuurtipp() {
        if (kuhi.size() == 0)
            throw new RuntimeException();
        if (kuhi.size() == 1)
            return kuhi.remove(0);

        int juur = kuhi.get(0);
        int viimane = kuhi.remove(kuhi.size() - 1);
        kuhi.set(0, viimane);
        mullinaAlla(0);

        return juur;
    }

    public int vasemIndeks(int i) {
        int indeks = (i * 2) + 1;
        if (indeks >= kuhi.size())
            indeks = -1;
        return indeks;
    }

    public int vasem(int i) {
        return kuhi.get(vasemIndeks(i));
    }

    public int paremIndeks(int i) {
        int indeks = (i * 2) + 2;
        if (indeks >= kuhi.size())
            indeks = -1;
        return indeks;
    }

    public int parem(int i) {
        return kuhi.get(paremIndeks(i));
    }


    public int ülemIndeks(int i) {
        int indeks = (i - 1) / 2;
        if (indeks < 0)
            return -1;
        return indeks;
    }

    public int ülem(int i) {
        return kuhi.get(ülemIndeks(i));
    }

    public void mullinaAlla(int i) {
        if (i > ülemIndeks(kuhi.size() - 1) || kuhi.size() <= 1)
            return;

        int element = kuhi.get(i);

        int alumineIndeks = vasemIndeks(i);
        if (paremIndeks(i) != -1 && vasem(i) < parem(i))
            alumineIndeks = paremIndeks(i);

        if (element < kuhi.get(alumineIndeks)) {
            Collections.swap(kuhi, alumineIndeks, i);
            mullinaAlla(alumineIndeks);
        }

    }

    public void mullinaÜles(int i) {
        if (i <= 0)
            return;

        int element = kuhi.get(i);

        int ülemineIndeks = ülemIndeks(i);
        int ülemineElement = ülem(i);

        if (ülemineElement < element) {
            Collections.swap(kuhi, ülemineIndeks, i);
            mullinaÜles(ülemineIndeks);
        }

    }

    public void kuva() {
        Tipp tipp = teePuukujule(0, kuhi);
        Dendrologist.drawBinaryTree(tipp, t -> ("[" + t.väärtus + "], k=" + t.x), t -> t.v, t -> t.p);
    }

    private Tipp teePuukujule(int i, List<Integer> kuhi) {
        if (i <= -1 || i >= kuhi.size())
            return null;

        Tipp vasemharu = teePuukujule(vasemIndeks(i), kuhi);
        Tipp paremharu = teePuukujule(paremIndeks(i), kuhi);
        return new Tipp(kuhi.get(i), vasemharu, paremharu);
    }

    @Override
    public String toString() {
        return kuhi.toString();
    }

    public Kuhi(List<Integer> kuhi) {
        this.kuhi = kuhi;
    }

    public Kuhi() {
        kuhi = new ArrayList<>();
    }
}
