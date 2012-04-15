package ru.gt2.rusref.fias;

public class SortScript {
    public static void main(String... args) {
        for (Fias fias : Fias.orderForLoading()) {
            String name = fias.item.getSimpleName();
            System.out.println("move " + name + ".csv " + name + "-unsorted.csv");
            System.out.println("sort.exe " + name + "-unsorted.csv /output " + name + ".csv");
            System.out.println("del " + name + "-unsorted.csv");
        }
    }
}
