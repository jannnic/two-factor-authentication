package org.dhbw.otp.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dhbw.otp.Helpers;
import org.dhbw.otp.LDAPAuth;

/**
 * Servlet implementation class SecretServlet
 */
public class SecretServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SecretServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("save")!= null) {
			response.sendRedirect(request.getContextPath()+"/"+ Helpers.getProperty("SAVED_SECRET_PAGE"));
			//Saving the secret in LDAP
			LDAPAuth la =  (LDAPAuth) request.getSession().getAttribute("la");
			la.setSecret(request.getSession().getAttribute("user").toString(), 
							request.getSession().getAttribute("baseUser").toString(),
							request.getSession().getAttribute("secret").toString()    
							);
		}
		if(request.getParameter("cancel") != null) {
			response.sendRedirect(request.getContextPath()+"/"+ Helpers.getProperty("LOGOUT_PAGE"));
		}
	}

}
