<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en_US" lang="en_US">
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <title>Тестовая страница AddressObject'а ФИАС</title>
</head>
<body>
<sql:setDataSource dataSource="jdbc/fias" />
<c:choose>
<c:when test="${empty param['parent']}">
    <sql:query var="addressObjects">
    SELECT *, HEX(`aoGuid`) AS hexAoGuid
    FROM `addressObject`
    WHERE
        `parentGuid` IS NULL
        AND `actStatus` = 1
        AND `currStatus` = 0
    ORDER BY
        `aoLevel` ASC,
        `formalName` ASC
    </sql:query>
</c:when>
<c:otherwise>
    <sql:query var="selectedAddressObject">
        <sql:param value="${param['parent']}" />
    SELECT *, HEX(`parentGuid`) AS hexParentGuid
    FROM `addressObject`
    WHERE
        `aoGuid` = UNHEX(?)
        AND `actStatus` = 1
        AND `currStatus` = 0
    ORDER BY
        `aoLevel` ASC,
        `formalName` ASC
    </sql:query>
    <sql:query var="addressObjects">
        <sql:param value="${param['parent']}" />
    SELECT *, HEX(`aoGuid`) AS hexAoGuid
    FROM `addressObject`
    WHERE
        `parentGuid` = UNHEX(?)
        AND `actStatus` = 1
        AND `currStatus` = 0
    ORDER BY
        `aoLevel` ASC,
        `formalName` ASC
    </sql:query>
    <sql:query var="houses">
        <sql:param value="${param['parent']}" />
    SELECT `house`.*,
        `estateStatus`.`name` AS `estateStatusName`,
        `structureStatus`.`name` AS `structureStateName`
    FROM `house`
    LEFT JOIN `estateStatus` ON `house`.`estStatus` = `estateStatus`.`estStatId`
    LEFT JOIN `structureStatus` ON `house`.`strStatus` = `structureStatus`.`strStatId`
    WHERE
        `aoGuid` = UNHEX(?)
    </sql:query>
</c:otherwise>
</c:choose>
<c:if test="${not empty param['parent']}">
<c:forEach var="_row" items="${selectedAddressObject.rows}">
    <h2><a href="?parent=${_row.hexParentGuid}">«</a> ${_row.formalName} ${_row.shortName}</h2>
<table>
    <tr>
        <th>СС</th>
        <th>А</th>
        <th>РРР</th>
        <th>ГГГ</th>
        <th>ВВВ</th>
        <th>ППП</th>
        <th>УУУУ</th>
        <th>ЭЭЭ</th>
        <th>ЦЦЦ</th>
    </tr>
    <tr>
        <td>${_row.regionCode}</td>
        <td>${_row.autoCode}</td>
        <td>${_row.areaCode}</td>
        <td>${_row.cityCode}</td>
        <td>${_row.ctarCode}</td>
        <td>${_row.placeCode}</td>
        <td>${_row.streetCode}</td>
        <td>${_row.extrCode}</td>
        <td>${_row.sextCode}</td>
    </tr>
</table>
Индекс: ${_row.postalCode}, ОКАТО ${_row.okato}, ОКТМО ${_row.oktmo}
</c:forEach>
</c:if>

<c:if test="${addressObjects.rowCount > 0}">
    <table>
        <tr>
            <th>Название</th>
            <th>КЛАДР 4.0</th>
        </tr>
        <c:set var="prevLevel" value="" />
        <c:forEach var="_row" items="${addressObjects.rows}">
            <c:if test="${prevLevel != _row.aoLevel}">
                <tr>
                    <th colspan="4">Уровень ${_row.aoLevel}</th>
                </tr>
            </c:if>
            <c:set var="prevLevel" value="${_row.aoLevel}" />
            <tr>
                <td><a href="?parent=${_row.hexAoGuid}">${_row.formalName} ${_row.shortName}</a></td>
                <td>${_row.code}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${not empty param['parent'] and houses.rowCount > 0}">
<table>
    <tr>
        <th>Признак</th>
        <th>№ дома</th>
        <th>№ корпуса</th>
        <th>№ строения</th>
        <th>Признак</th>
    </tr>
    <c:set var="prevLevel" value="" />
    <c:forEach var="_col" items="${houses.columnNames}">
        ${_col}
    </c:forEach>
    <c:forEach var="_row" items="${houses.rows}">
        <tr>
            <td>${_row.estateStatusName}</td>
            <td>${_row.houseNum}</td>
            <td>${_row.buildNum}</td>
            <td>${_row.structNum}</td>
            <td>${_row.structureStateName}</td>
        </tr>
    </c:forEach>
</table>
</c:if>

</html>
