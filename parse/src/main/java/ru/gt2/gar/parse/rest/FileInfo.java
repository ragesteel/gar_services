package ru.gt2.gar.parse.rest;

import java.time.LocalDateTime;

/**
 * Информация о выгрузке.
 * При обработке нужно учитывать, что сервис может отдавать GarXMLDeltaURL при пустой GarXMLFullURL.
 *
 * @param versionId Идентификатор версии (для прямых выгрузок — дата вида yyyyMMdd)
 * @param textVersion Описание версии файла в текстовом виде
 * @param FiasCompleteDbfUrl URL полной версии ФИАС в формате DBF
 * @param FiasCompleteXmlUrl URL полной версии ФИАС в формате XML
 * @param FiasDeltaDbfUrl URL дельта версии ФИАС в формате DBF
 * @param FiasDeltaXmlUrl URL дельта версии ФИАС в формате XML
 * @param Kladr4ArjUrl URL версии КЛАДР 4, сжатого в формате ARJ
 * @param Kladr47ZUrl URL версии КЛАДР 4, сжатого в формате 7Z
 * @param GarXMLFullURL URL полной версии ГАР в формате XML, сжатого в ZIP
 * @param GarXMLDeltaUrl URL дельта версии ГАР в формате XML, сжатого в ZIP
 * @param expDate Дата экспорта (yyyy-MM-ddTHH24:MI:SS)
 * @param date Дата выгрузки (dd.MM.yyyy)
 */
public record FileInfo(
        String versionId,
        String textVersion,
        String FiasCompleteDbfUrl,
        String FiasCompleteXmlUrl,
        String FiasDeltaDbfUrl,
        String FiasDeltaXmlUrl,
        String Kladr4ArjUrl,
        String Kladr47ZUrl,
        String GarXMLFullURL,
        String GarXMLDeltaUrl,
        LocalDateTime expDate,
        String date) {
}
