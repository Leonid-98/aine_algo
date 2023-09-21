package Arvutipraks_1;

public class Y2b {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (algus)
        dna_mol_gen(11, "");
        // PROGRAMMIOSA, MILLE TÄITMISAEGA MÕÕDAME (lõpp)
        long stop = System.currentTimeMillis();
        System.out.println("Aega kulus " + (stop - start) + " milliskundit");
    }

    private static void dna_mol_gen(int n, String genoomi_lõik) {
        if (genoomi_lõik.length() == n)
            System.out.println(genoomi_lõik);
        else
            for (String nukleotiid : new String[]{"A", "T", "G", "C"}) {
                dna_mol_gen(n, genoomi_lõik + nukleotiid);
            }
    }
}