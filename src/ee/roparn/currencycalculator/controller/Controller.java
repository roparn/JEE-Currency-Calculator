package ee.roparn.currencycalculator.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.roparn.currencycalculator.dao.CurrencyModel;
import ee.roparn.currencycalculator.dao.CurrencyTableXMLParser;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String MAINPAGE_JSP = "/MainPage.jsp";
	private CurrencyTableXMLParser ctp;
	private List<CurrencyModel> currencyList;

	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void init() throws ServletException {

		ctp = new CurrencyTableXMLParser("http://statistika.eestipank.ee/Reports?type=curd&format=xml&date1=2010-12-30&lng=est&print=off");
		try {
			currencyList = ctp.parseXMLToDAO();
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
		    String date = request.getParameter("date");
		    CurrencyModel cm1 = null,cm2 = null;
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
	    }catch (NullPointerException e){
	    }catch (Exception e){e.printStackTrace();}
	    finally {
			request.setAttribute("result", currencyList);
			request.setAttribute("outPutSum", outPutAmount);
			getServletContext().getRequestDispatcher(MAINPAGE_JSP).forward(request, response);
	    }
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
