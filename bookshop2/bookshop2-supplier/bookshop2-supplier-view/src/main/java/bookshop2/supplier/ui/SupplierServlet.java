package bookshop2.supplier.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet(name="bookshop2.supplier", urlPatterns={"/supplier"})
public class SupplierServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

//	@EJB
//	MessageSession session;

	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		super.service(request, response);
	}
	
}
