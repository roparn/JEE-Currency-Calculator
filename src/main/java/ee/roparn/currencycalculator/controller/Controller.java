package ee.roparn.currencycalculator.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.roparn.currencycalculator.dao.EECurrencyXMLDAO;
import ee.roparn.currencycalculator.model.CurrencyModel;
import org.json.JSONObject;

import static ee.roparn.currencycalculator.util.Common.calculate;
import static ee.roparn.currencycalculator.util.Common.findCurrencyFromListByText;

public class Controller extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static String MAINPAGE_JSP = "/MainPage.jsp";
  private List<CurrencyModel> currencyList = new ArrayList<>();

  public void init() throws ServletException {
    try {
      currencyList = getCurrencies("http://www.eestipank.ee/valuutakursid/export/xml/latest");
    } catch (Exception e) {
      getServletContext().log("An exception occurred", e);
      throw new ServletException("An exception occurred" + e.getMessage());
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    request.setAttribute("currenciesList", currencyList);
    response.setStatus(200);
    getServletContext().getRequestDispatcher(MAINPAGE_JSP).forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      double amount = Double.parseDouble(request.getParameter("amount"));
      String inCurrency = request.getParameter("inCurrency");
      String outCurrency = request.getParameter("outCurrency");
      determineCurrenciesTableToDownload(request.getParameter("date"));

      double result = findCurrenciesAndCalculate(inCurrency, outCurrency, amount);

      sendPostResponse(response, createResponseJSON(amount, inCurrency, outCurrency, result).toString(), 200);

    } catch (NumberFormatException e) {
      sendErrorPostResponse(response, "Invalid amount input parameter");
    } catch (NullPointerException e) {
      sendErrorPostResponse(response, "Could not parse parameters");
    } catch (Exception e) {
      e.printStackTrace();
      sendErrorPostResponse(response, e.getMessage());
    }
  }

  private void determineCurrenciesTableToDownload(String inputDateString) throws ParseException {
    if (inputDateString == null || inputDateString.equals("")) {
    } else {
      Date date = new SimpleDateFormat("dd.MM.YY").parse(inputDateString);
    }
  }

  private void sendErrorPostResponse(HttpServletResponse response, String errorMessage) throws IOException {
    sendPostResponse(response, new JSONObject().put("error", errorMessage).toString(), 400);
  }

  private void sendPostResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
    response.setContentType("application/json");
    response.setStatus(statusCode);
    PrintWriter writer = response.getWriter();
    writer.print(message);
    writer.flush();
  }

  private JSONObject createResponseJSON(double amount, String inCurrency, String outCurrency, double result) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.accumulate("inCurrency", inCurrency);
    jsonObject.accumulate("outCurrency", outCurrency);
    jsonObject.accumulate("amount", amount);
    jsonObject.accumulate("result", result);

    return jsonObject;
  }

  private double findCurrenciesAndCalculate(String inCurrency, String outCurrency, double amount) throws Exception {

    CurrencyModel fromCurrency = findCurrencyFromListByText(inCurrency, currencyList);
    CurrencyModel toCurrency = findCurrencyFromListByText(outCurrency, currencyList);

    return calculate(fromCurrency, toCurrency, amount);
  }

  protected List<CurrencyModel> getCurrencies(String currenciesXMLURL) throws Exception {
    List<CurrencyModel> currencies = new EECurrencyXMLDAO().saveAndParseCurrenciesXML(currenciesXMLURL);

    CurrencyModel c = new CurrencyModel();
    c.setName("EUR");
    c.setRate(1.0);
    currencies.add(c);

    return currencies;
  }

}
