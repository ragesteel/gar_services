package ru.gt2.rusref.fias;

/**
 * Все файлы ФИАС.
 */
public enum Fias {
    STRSTAT(StructureStatuses.class),
    ACTSTAT(ActualStatuses.class),
    INTVSTAT(IntervalStatuses.class),
    ESTSTAT(EstateStatuses.class),
    CENTERST(CenterStatuses.class),
    OPERSTAT(OperationStatuses.class),
    HSTSTAT(HouseStateStatuses.class),
    CURENTST(CurrentStatuses.class),
    SOCRBASE(AddressObjectTypes.class),
    LANDMARK(Landmarks.class);

    /** Класс обёртки. */
    public final Class<?> wrapper;
    
    private Fias(Class<?> wrapper) {
        this.wrapper = wrapper;
    }
}
