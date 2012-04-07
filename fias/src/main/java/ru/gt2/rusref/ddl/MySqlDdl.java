package ru.gt2.rusref.ddl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import ru.gt2.rusref.fias.Fias;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Запускатель «велосипеда» по генерации Ddl.
 */
public class MySqlDdl {
    public static void main(String... args) {
        for (Fias fias : orderByReferences()) {
            MySqlTable table = new MySqlTable(fias);
            table.generate();
        }
    }

    private static Set<Fias> orderByReferences() {
        // Нужно получить упорядоченный по использованию список
        // Чтобы сначала были все зависимости, а потом уже таблица, которая зависит.
        // Способ такой идём по списку необработанных, проверяем если все зависимости в обработанных
        // если есть (или зависимостей вообще нет) — перемещаем в список обработанных. Иначе — переходим к следующему.
        // Во внешнем цикле проверяем наличие элементов в списке необработанных.
        // Для того чтобы не скатиться в бесконечный цикл — проверяем наличие перемещения, если прошли по циклу
        // перемещения — то получаем циклическую зависимость.
        List<Fias> notProcessed = Lists.newArrayList(Fias.values());
        Set<Fias> result = Sets.newLinkedHashSet();

        while (!notProcessed.isEmpty()) {
            boolean moveHappens = false;
            for (Iterator<Fias> iterator = notProcessed.iterator(); iterator.hasNext(); ) {
                Fias fias = iterator.next();
                ImmutableSet<Fias> references = ImmutableSet.copyOf(Fias.getReferenceTargets(fias.itemFields));

                Sets.SetView<Fias> notProcessedReferences =
                        Sets.difference(references,
                                Sets.union(result, Collections.singleton(fias)));
                moveHappens = notProcessedReferences.isEmpty();
                if (moveHappens) {
                    iterator.remove();
                    result.add(fias);
                    break;
                }
            }
            if (!moveHappens) {
                throw new RuntimeException("Cycle dependencies, not processed: " + notProcessed.toString());
            }
        }
        return result;
    }
}
