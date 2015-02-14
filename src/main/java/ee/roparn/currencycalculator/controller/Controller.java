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
import ee.roparn.currencycalculator.handler.CurrencyCalculationHandler;
import ee.roparn.currencycalculator.model.CurrencyModel;
import ee.roparn.currencycalculator.util.Configuration;
import org.json.JSONObject;

import static ee.roparn.currencycalculator.util.Configuration.*;

public class Controller extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private List<CurrencyModel> initialCurrencyList = new ArrayList<>();

  public void init() throws ServletException {
    try {
      Configuration.init();
      initialCurrencyList = getInitialCurrenciesList(getConfiguration().getEstonianBankCurrenciesXMLURL());
    } catch (Exception e) {
      getServletContext().log("An exception occurred", e);
      throw new ServletException("An exception occurred" + e.getMessage());
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    request.setAttribute("currenciesList", initialCurrencyList);
    response.setStatus(200);
    getServletContext().getRequestDispatcher(getConfiguration().getMainPageJSPFileName()).forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      handlePostRequestAndSendResponse(request, response);

    } catch (NumberFormatException e) {
      sendErrorPostResponse(response, "Invalid amount input parameter");
    } catch (NullPointerException e) {
      sendErrorPostResponse(response, "Could not parse parameters");
    } catch (Exception e) {
      e.printStackTrace();
      sendErrorPostResponse(response, e.getMessage());
    }
  }

  private void handlePostRequestAndSendResponse(HttpServletRequest request, HttpServletResponse response) throws Exception {
    double amount = Double.parseDouble(request.getParameter("amount"));
    String inCurrency = request.getParameter("inCurrency");
    String outCurrency = request.getParameter("outCurrency");
    Date date = parseStringInputToDate(request.getParameter("date"));

    JSONObject responseJSON = new CurrencyCalculationHandler().calculateResultAndParseToJSON(inCurrency, outCurrency, date, amount);
    sendPostResponse(response, responseJSON.toString(), 200);
  }

  private Date parseStringInputToDate(String inputDateString) throws ParseException {
    if (inputDateString == null || inputDateString.equals("")) {
      return new Date();
    } else {
      return new SimpleDateFormat("dd.MM.yyyy").parse(inputDateString);
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

  protected List<CurrencyModel> getInitialCurrenciesList(String currenciesXMLURL) throws Exception {

    return new EECurrencyXMLDAO(new Date()).saveAndParseCurrenciesXML(currenciesXMLURL);
  }

}
