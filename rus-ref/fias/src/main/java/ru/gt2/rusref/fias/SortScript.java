package ru.gt2.rusref.fias;

public class SortScript {
    public static void main(String... args) {
        cmd();
        shell();
    }

    private static void cmd() {
        for (Fias fias : Fias.orderForLoading()) {
            if (fias.intermediate) {
                continue;
            }
            String name = fias.item.getSimpleName();
            System.out.println("move " + name + ".csv " + name + "-unsorted.csv && "
                    + "sort.exe " + name + "-unsorted.csv /output " + name + ".csv && "
                    + "del " + name + "-unsorted.csv");
        }
    }

    private static void shell() {
        for (Fias fias : Fias.orderForLoading()) {
            if (fias.intermediate) {
                continue;
            }
            String name = fias.item.getSimpleName();
            System.out.println("sort " + name + ".csv --output=" + name + ".csv");
        }
    }

}
