// Сгенерировано автоматически, с помощью StAX2RecordMapper
package ru.gt2.gar.parse.xml.stax2;

import org.codehaus.stax2.XMLInputFactory2;
import org.jspecify.annotations.NonNull;
import ru.gt2.gar.domain.AddressObject;
import ru.gt2.gar.domain.AddressObjectDivision;
import ru.gt2.gar.domain.AddressObjectType;
import ru.gt2.gar.domain.AdmHierarchy;
import ru.gt2.gar.domain.Apartment;
import ru.gt2.gar.domain.ApartmentType;
import ru.gt2.gar.domain.CarPlace;
import ru.gt2.gar.domain.ChangeHistory;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.House;
import ru.gt2.gar.domain.HouseType;
import ru.gt2.gar.domain.MunHierarchy;
import ru.gt2.gar.domain.NormativeDoc;
import ru.gt2.gar.domain.NormativeDocKind;
import ru.gt2.gar.domain.NormativeDocType;
import ru.gt2.gar.domain.ObjectLevel;
import ru.gt2.gar.domain.OperationType;
import ru.gt2.gar.domain.Param;
import ru.gt2.gar.domain.ParamType;
import ru.gt2.gar.domain.ReestrObject;
import ru.gt2.gar.domain.Room;
import ru.gt2.gar.domain.RoomType;
import ru.gt2.gar.domain.Stead;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;

import javax.xml.stream.XMLStreamException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneratedRecordCreators {
    private GeneratedRecordCreators() {
    }

    @NonNull
    private static AddressObject createAddressObject(TypedAttrReader tar) throws XMLStreamException {
        return new AddressObject(
                tar.getLong("ID"),
                tar.getLong("OBJECTID"),
                tar.getUUID("OBJECTGUID"),
                tar.getLong("CHANGEID"),
                tar.getString("NAME"),
                tar.getString("TYPENAME"),
                tar.getString("LEVEL"),
                tar.getInt("OPERTYPEID"),
                tar.getNullableLong("PREVID"),
                tar.getNullableLong("NEXTID"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getIntAsBoolean("ISACTUAL"),
                tar.getIntAsBoolean("ISACTIVE"));
    }

    @NonNull
    private static AddressObjectDivision createAddressObjectDivision(TypedAttrReader tar) throws
            XMLStreamException {
        return new AddressObjectDivision(
                tar.getLong("ID"),
                tar.getLong("PARENTID"),
                tar.getLong("CHILDID"),
                tar.getLong("CHANGEID"));
    }

    @NonNull
    private static AddressObjectType createAddressObjectType(TypedAttrReader tar) throws
            XMLStreamException {
        return new AddressObjectType(
                tar.getInt("ID"),
                tar.getInt("LEVEL"),
                tar.getString("SHORTNAME"),
                tar.getString("NAME"),
                tar.getNullableString("DESC"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getBoolean("ISACTIVE"));
    }

    @NonNull
    private static AdmHierarchy createAdmHierarchy(TypedAttrReader tar) throws XMLStreamException {
        return new AdmHierarchy(
                tar.getLong("ID"),
                tar.getLong("OBJECTID"),
                tar.getNullableLong("PARENTOBJID"),
                tar.getLong("CHANGEID"),
                tar.getNullableString("REGIONCODE"),
                tar.getNullableString("AREACODE"),
                tar.getNullableString("CITYCODE"),
                tar.getNullableString("PLACECODE"),
                tar.getNullableString("PLANCODE"),
                tar.getNullableString("STREETCODE"),
                tar.getNullableLong("PREVID"),
                tar.getNullableLong("NEXTID"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getIntAsBoolean("ISACTIVE"),
                tar.getString("PATH"));
    }

    @NonNull
    private static ApartmentType createApartmentType(TypedAttrReader tar) throws XMLStreamException {
        return new ApartmentType(
                tar.getInt("ID"),
                tar.getString("NAME"),
                tar.getNullableString("SHORTNAME"),
                tar.getNullableString("DESC"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getBoolean("ISACTIVE"));
    }

    @NonNull
    private static Apartment createApartment(TypedAttrReader tar) throws XMLStreamException {
        return new Apartment(
                tar.getLong("ID"),
                tar.getLong("OBJECTID"),
                tar.getUUID("OBJECTGUID"),
                tar.getLong("CHANGEID"),
                tar.getString("NUMBER"),
                tar.getInt("APARTTYPE"),
                tar.getInt("OPERTYPEID"),
                tar.getNullableLong("PREVID"),
                tar.getNullableLong("NEXTID"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getIntAsBoolean("ISACTUAL"),
                tar.getIntAsBoolean("ISACTIVE"));
    }

    @NonNull
    private static CarPlace createCarPlace(TypedAttrReader tar) throws XMLStreamException {
        return new CarPlace(
                tar.getLong("ID"),
                tar.getLong("OBJECTID"),
                tar.getUUID("OBJECTGUID"),
                tar.getLong("CHANGEID"),
                tar.getString("NUMBER"),
                tar.getInt("OPERTYPEID"),
                tar.getNullableLong("PREVID"),
                tar.getNullableLong("NEXTID"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getIntAsBoolean("ISACTUAL"),
                tar.getIntAsBoolean("ISACTIVE"));
    }

    @NonNull
    private static ChangeHistory createChangeHistory(TypedAttrReader tar) throws XMLStreamException {
        return new ChangeHistory(
                tar.getLong("CHANGEID"),
                tar.getLong("OBJECTID"),
                tar.getUUID("ADROBJECTID"),
                tar.getInt("OPERTYPEID"),
                tar.getNullableLong("NDOCID"),
                tar.getLocalDate("CHANGEDATE"));
    }

    @NonNull
    private static HouseType createHouseType(TypedAttrReader tar) throws XMLStreamException {
        return new HouseType(
                tar.getInt("ID"),
                tar.getString("NAME"),
                tar.getNullableString("SHORTNAME"),
                tar.getNullableString("DESC"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getBoolean("ISACTIVE"));
    }

    @NonNull
    private static House createHouse(TypedAttrReader tar) throws XMLStreamException {
        return new House(
                tar.getLong("ID"),
                tar.getLong("OBJECTID"),
                tar.getUUID("OBJECTGUID"),
                tar.getLong("CHANGEID"),
                tar.getNullableString("HOUSENUM"),
                tar.getNullableString("ADDNUM1"),
                tar.getNullableString("ADDNUM2"),
                tar.getNullableInt("HOUSETYPE"),
                tar.getNullableInt("ADDTYPE1"),
                tar.getNullableInt("ADDTYPE2"),
                tar.getInt("OPERTYPEID"),
                tar.getNullableLong("PREVID"),
                tar.getNullableLong("NEXTID"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getIntAsBoolean("ISACTUAL"),
                tar.getIntAsBoolean("ISACTIVE"));
    }

    @NonNull
    private static MunHierarchy createMunHierarchy(TypedAttrReader tar) throws XMLStreamException {
        return new MunHierarchy(
                tar.getLong("ID"),
                tar.getLong("OBJECTID"),
                tar.getNullableLong("PARENTOBJID"),
                tar.getLong("CHANGEID"),
                tar.getNullableString("OKTMO"),
                tar.getNullableLong("PREVID"),
                tar.getNullableLong("NEXTID"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getIntAsBoolean("ISACTIVE"),
                tar.getString("PATH"));
    }

    @NonNull
    private static NormativeDoc createNormativeDoc(TypedAttrReader tar) throws XMLStreamException {
        return new NormativeDoc(
                tar.getLong("ID"),
                tar.getNullableString("NAME"),
                tar.getLocalDate("DATE"),
                tar.getString("NUMBER"),
                tar.getInt("TYPE"),
                tar.getInt("KIND"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getNullableString("ORGNAME"),
                tar.getNullableString("REGNUM"),
                tar.getNullableLocalDate("REGDATE"),
                tar.getNullableLocalDate("ACCDATE"),
                tar.getNullableString("COMMENT"));
    }

    @NonNull
    private static NormativeDocKind createNormativeDocKind(TypedAttrReader tar) throws
            XMLStreamException {
        return new NormativeDocKind(
                tar.getInt("ID"),
                tar.getString("NAME"));
    }

    @NonNull
    private static NormativeDocType createNormativeDocType(TypedAttrReader tar) throws
            XMLStreamException {
        return new NormativeDocType(
                tar.getInt("ID"),
                tar.getString("NAME"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"));
    }

    @NonNull
    private static ObjectLevel createObjectLevel(TypedAttrReader tar) throws XMLStreamException {
        return new ObjectLevel(
                tar.getInt("LEVEL"),
                tar.getString("NAME"),
                tar.getNullableString("SHORTNAME"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getBoolean("ISACTIVE"));
    }

    @NonNull
    private static OperationType createOperationType(TypedAttrReader tar) throws XMLStreamException {
        return new OperationType(
                tar.getInt("ID"),
                tar.getString("NAME"),
                tar.getNullableString("SHORTNAME"),
                tar.getNullableString("DESC"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getBoolean("ISACTIVE"));
    }

    @NonNull
    private static ParamType createParamType(TypedAttrReader tar) throws XMLStreamException {
        return new ParamType(
                tar.getInt("ID"),
                tar.getString("NAME"),
                tar.getString("CODE"),
                tar.getNullableString("DESC"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getBoolean("ISACTIVE"));
    }

    @NonNull
    private static ReestrObject createReestrObject(TypedAttrReader tar) throws XMLStreamException {
        return new ReestrObject(
                tar.getLong("OBJECTID"),
                tar.getUUID("OBJECTGUID"),
                tar.getLong("CHANGEID"),
                tar.getIntAsBoolean("ISACTIVE"),
                tar.getInt("LEVELID"),
                tar.getLocalDate("CREATEDATE"),
                tar.getLocalDate("UPDATEDATE"));
    }

    @NonNull
    private static RoomType createRoomType(TypedAttrReader tar) throws XMLStreamException {
        return new RoomType(
                tar.getInt("ID"),
                tar.getString("NAME"),
                tar.getNullableString("SHORTNAME"),
                tar.getNullableString("DESC"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getBoolean("ISACTIVE"));
    }

    @NonNull
    private static Room createRoom(TypedAttrReader tar) throws XMLStreamException {
        return new Room(
                tar.getLong("ID"),
                tar.getLong("OBJECTID"),
                tar.getUUID("OBJECTGUID"),
                tar.getLong("CHANGEID"),
                tar.getString("NUMBER"),
                tar.getInt("ROOMTYPE"),
                tar.getInt("OPERTYPEID"),
                tar.getNullableLong("PREVID"),
                tar.getNullableLong("NEXTID"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getIntAsBoolean("ISACTUAL"),
                tar.getIntAsBoolean("ISACTIVE"));
    }

    @NonNull
    private static Stead createStead(TypedAttrReader tar) throws XMLStreamException {
        return new Stead(
                tar.getLong("ID"),
                tar.getLong("OBJECTID"),
                tar.getUUID("OBJECTGUID"),
                tar.getLong("CHANGEID"),
                tar.getNullableString("NUMBER"),
                tar.getInt("OPERTYPEID"),
                tar.getNullableLong("PREVID"),
                tar.getNullableLong("NEXTID"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"),
                tar.getIntAsBoolean("ISACTUAL"),
                tar.getIntAsBoolean("ISACTIVE"));
    }

    @NonNull
    private static Param createParam(TypedAttrReader tar) throws XMLStreamException {
        return new Param(
                tar.getLong("ID"),
                tar.getLong("OBJECTID"),
                tar.getNullableLong("CHANGEID"),
                tar.getLong("CHANGEIDEND"),
                tar.getInt("TYPEID"),
                tar.getString("VALUE"),
                tar.getLocalDate("UPDATEDATE"),
                tar.getLocalDate("STARTDATE"),
                tar.getLocalDate("ENDDATE"));
    }

    public static Map<GarType, StAX2StreamReaderProcessor> createProcessors(
            XMLInputFactory2 xmlInputStream2, int batchSize) {
        return Stream.of(
                        new StAX2StreamReaderProcessor(GarType.ADDR_OBJ, xmlInputStream2, batchSize, GeneratedRecordCreators::createAddressObject),
                        new StAX2StreamReaderProcessor(GarType.ADDR_OBJ_DIVISION, xmlInputStream2, batchSize, GeneratedRecordCreators::createAddressObjectDivision),
                        new StAX2StreamReaderProcessor(GarType.ADDR_OBJ_TYPES, xmlInputStream2, batchSize, GeneratedRecordCreators::createAddressObjectType),
                        new StAX2StreamReaderProcessor(GarType.ADM_HIERARCHY, xmlInputStream2, batchSize, GeneratedRecordCreators::createAdmHierarchy),
                        new StAX2StreamReaderProcessor(GarType.APARTMENT_TYPES, xmlInputStream2, batchSize, GeneratedRecordCreators::createApartmentType),
                        new StAX2StreamReaderProcessor(GarType.APARTMENTS, xmlInputStream2, batchSize, GeneratedRecordCreators::createApartment),
                        new StAX2StreamReaderProcessor(GarType.CARPLACES, xmlInputStream2, batchSize, GeneratedRecordCreators::createCarPlace),
                        new StAX2StreamReaderProcessor(GarType.CHANGE_HISTORY, xmlInputStream2, batchSize, GeneratedRecordCreators::createChangeHistory),
                        new StAX2StreamReaderProcessor(GarType.HOUSE_TYPES, xmlInputStream2, batchSize, GeneratedRecordCreators::createHouseType),
                        new StAX2StreamReaderProcessor(GarType.HOUSES, xmlInputStream2, batchSize, GeneratedRecordCreators::createHouse),
                        new StAX2StreamReaderProcessor(GarType.MUN_HIERARCHY, xmlInputStream2, batchSize, GeneratedRecordCreators::createMunHierarchy),
                        new StAX2StreamReaderProcessor(GarType.NORMATIVE_DOCS, xmlInputStream2, batchSize, GeneratedRecordCreators::createNormativeDoc),
                        new StAX2StreamReaderProcessor(GarType.NORMATIVE_DOCS_KINDS, xmlInputStream2, batchSize, GeneratedRecordCreators::createNormativeDocKind),
                        new StAX2StreamReaderProcessor(GarType.NORMATIVE_DOCS_TYPES, xmlInputStream2, batchSize, GeneratedRecordCreators::createNormativeDocType),
                        new StAX2StreamReaderProcessor(GarType.OBJECT_LEVELS, xmlInputStream2, batchSize, GeneratedRecordCreators::createObjectLevel),
                        new StAX2StreamReaderProcessor(GarType.OPERATION_TYPES, xmlInputStream2, batchSize, GeneratedRecordCreators::createOperationType),
                        new StAX2StreamReaderProcessor(GarType.PARAM_TYPES, xmlInputStream2, batchSize, GeneratedRecordCreators::createParamType),
                        new StAX2StreamReaderProcessor(GarType.REESTR_OBJECTS, xmlInputStream2, batchSize, GeneratedRecordCreators::createReestrObject),
                        new StAX2StreamReaderProcessor(GarType.ROOM_TYPES, xmlInputStream2, batchSize, GeneratedRecordCreators::createRoomType),
                        new StAX2StreamReaderProcessor(GarType.ROOMS, xmlInputStream2, batchSize, GeneratedRecordCreators::createRoom),
                        new StAX2StreamReaderProcessor(GarType.STEADS, xmlInputStream2, batchSize, GeneratedRecordCreators::createStead),
                        new StAX2StreamReaderProcessor(GarType.ADDR_OBJ_PARAMS, xmlInputStream2, batchSize, GeneratedRecordCreators::createParam),
                        new StAX2StreamReaderProcessor(GarType.HOUSES_PARAMS, xmlInputStream2, batchSize, GeneratedRecordCreators::createParam),
                        new StAX2StreamReaderProcessor(GarType.APARTMENTS_PARAMS, xmlInputStream2, batchSize, GeneratedRecordCreators::createParam),
                        new StAX2StreamReaderProcessor(GarType.ROOMS_PARAMS, xmlInputStream2, batchSize, GeneratedRecordCreators::createParam),
                        new StAX2StreamReaderProcessor(GarType.STEADS_PARAMS, xmlInputStream2, batchSize, GeneratedRecordCreators::createParam),
                        new StAX2StreamReaderProcessor(GarType.CARPLACES_PARAMS, xmlInputStream2, batchSize, GeneratedRecordCreators::createParam),
                        new StAX2StreamReaderProcessor(GarType.ADDHOUSE_TYPES, xmlInputStream2, batchSize, GeneratedRecordCreators::createHouseType))
                .collect(Collectors.toMap(XMLStreamProcessor::getGarType, Function.identity()));
    }
}
