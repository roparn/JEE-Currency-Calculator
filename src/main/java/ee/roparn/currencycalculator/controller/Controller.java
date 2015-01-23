package ee.roparn.currencycalculator.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.roparn.currencycalculator.dao.EECurrencyXMLDAO;
import ee.roparn.currencycalculator.model.CurrencyModel;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static String MAINPAGE_JSP = "/MainPage.jsp";
  private List<CurrencyModel> currencyList = new ArrayList<>();

  public void init() throws ServletException {
    try {
      currencyList = getCurrencies();
    } catch (Exception e) {
      getServletContext().log("An exception occurred", e);
      throw new ServletException("An exception occurred" + e.getMessage());
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    request.setAttribute("currenciesList", currencyList);
    getServletContext().getRequestDispatcher(MAINPAGE_JSP).forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  }

  protected List<CurrencyModel> getCurrencies(){
    return new EECurrencyXMLDAO().saveAndParseCurrenciesXML();
  }

}
