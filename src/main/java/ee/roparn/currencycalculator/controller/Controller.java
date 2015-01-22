package ee.roparn.currencycalculator.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.roparn.currencycalculator.dao.CurrencyXMLDAO;
import ee.roparn.currencycalculator.dao.EECurrencyXMLDAO;
import ee.roparn.currencycalculator.model.CurrencyModel;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String MAINPAGE_JSP = "/MainPage.jsp";
	private CurrencyXMLDAO currencyXMLDAO;
	private List<CurrencyModel> currencyList = new ArrayList<>();

	public void init() throws ServletException {
		try {
			currencyXMLDAO = new EECurrencyXMLDAO();
			currencyList = currencyXMLDAO.saveAndParseCurrenciesXML();
		} catch (Exception e) {
			getServletContext().log("An exception occurred", e);
			throw new ServletException("An exception occurred" + e.getMessage());
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double outPutAmount = 0;
	    try {
		    double amount = Double.parseDouble(request.getParameter("amount"));
		    String inCurrency = request.getParameter("inCurrency");
		    String outCurrency = request.getParameter("outCurrency");
//		    String date = request.getParameter("date");
		    CurrencyModel cm1 = null,cm2 = null;
		    // EE currency list
		    for (CurrencyModel item : currencyList){
		    	if (item.getName().equals(inCurrency))
		    		cm1 = item;
		    	if (item.getName().equals(outCurrency))
		    		cm2 = item;
		    	if (cm1 != null && cm2 != null)
		    		break;
		    }
		    outPutAmount = amount * cm1.convertCurrency(cm2);
		    outPutAmount = Math.round(outPutAmount*100.00)/100.00;
			request.setAttribute("outPutSum", outPutAmount);
		    // LT currency list
		    CurrencyModel cmlt1 = null,cmlt2 = null;
		    outPutAmount = amount * cmlt1.convertCurrency(cmlt2);
		    outPutAmount = Math.round(outPutAmount*100.00)/100.00;
			request.setAttribute("outPutSum2", outPutAmount);
	    }catch (NullPointerException e){
	    }catch (Exception e){e.printStackTrace();}
	    finally {
			request.setAttribute("result", currencyList);
			getServletContext().getRequestDispatcher(MAINPAGE_JSP).forward(request, response);
	    }
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
