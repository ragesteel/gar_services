package ru.gt2.rusref.fias;

/**
 * Все файлы ФИАС.
 */
public enum Fias {
    STRSTAT(StructureStatuses.class, StructureStatus.class),
    ACTSTAT(ActualStatuses.class, ActualStatus.class),
    INTVSTAT(IntervalStatuses.class, IntervalStatus.class),
    ESTSTAT(EstateStatuses.class, EstateStatus.class),
    CENTERST(CenterStatuses.class, CenterStatus.class),
    OPERSTAT(OperationStatuses.class, OperationStatus.class),
    HSTSTAT(HouseStateStatuses.class, HouseStateStatus.class),
    CURENTST(CurrentStatuses.class, CurrentStatus.class),
    SOCRBASE(AddressObjectTypes.class, AddressObjectType.class),
    LANDMARK(Landmarks.class, Landmark.class),
    HOUSEINT(HouseIntervals.class, HouseInterval.class),
    NORMDOC(NormativeDocumentes.class, NormativeDocument.class),
    ADDROBJ(AddressObjects.class, AddressObject.class),
    HOUSE(Houses.class, House.class);

    /** Класс обёртки. */
    public final Class<?> wrapper;
    /** Внутренний класс. */
    public final Class<?> item;
    
    private Fias(Class<?> wrapper, Class<?> item) {
        this.wrapper = wrapper;
        this.item = item;
    }
}
