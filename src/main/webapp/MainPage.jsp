<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="java.util.List" %>
<%@page import="ee.roparn.currencycalculator.model.CurrencyModel" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Main Page</title>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
    <script src="jquery-2.1.0.min.js"></script>
    <script src="jquery.validate.min.js"></script>
    <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
    <script src="currencycalculator.mainpage.js"></script>
</head>
<body>

<form id="mainform" method="POST" action='Controller' name="showall">
    <table>
        <tr>
            <td>Summa: <input type="text" name="amount"/></td>
        </tr>
        <tr>
            <td>Lähtevaluuta
                <select name="inCurrency" class="required">
                    <option>EUR</option>
                    <c:forEach items="${currenciesList}" var="item">
                        <option>${item.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Sihtvaluuta:
                <select name="outCurrency" class="required">
                    <option>EUR</option>
                    <c:forEach items="${currenciesList}" var="item">
                        <option>${item.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Kuupäev: <input type="text" name="date" id="datepicker"/></td>
        </tr>
    </table>

    <p>
        <input id="validate" type="submit" name="send" value="Arvuta"/>&nbsp;
        <input type="reset" value="Lähtesta"/>
    </p>
</form>

<p id="results"></p>

</body>
</html> 