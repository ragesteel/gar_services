package ru.gt2.gar.parse.xml;

import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;

// Просто считалка элементов в спиcке
public class ListCounter<T> implements Consumer<List<T>> {
    @Getter
    private int counter;

    @Override
    public void accept(List<T> objects) {
        counter += objects.size();
    }
}
