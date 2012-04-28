<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en_US" lang="en_US">
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <title>Тестовая страница AddressObject'а ФИАС</title>
</head>
<body>
<c:choose>
<c:when test="${empty param['parent']}">
    <sql:query var="addressObjects" dataSource="jdbc/fias">
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
    <sql:query var="addressObjects" dataSource="jdbc/fias">
    SELECT *, HEX(`aoGuid`) AS hexAoGuid
    FROM `addressObject`
    WHERE
        `parentGuid` = UNHEX(?)
        AND `actStatus` = 1
        AND `currStatus` = 0
    ORDER BY
        `aoLevel` ASC,
        `formalName` ASC
<sql:param value="${param['parent']}" />
    </sql:query>
</c:otherwise>
</c:choose>
<table>
    <tr>
        <th>aoGuid</th>
        <th>formalName</th>
        <th>shortName</th>
        <th>code</th>
    </tr>
    <c:forEach var="_row" items="${addressObjects.rows}">
        <tr>
            <td>${_row.hexAoGuid}</td>
            <td><a href="?parent=${_row.hexAoGuid}">${_row.formalName}</a></td>
            <td>${_row.shortName}</td>
            <td>${_row.code}</td>
        </tr>
    </c:forEach>
</table>


</html>
