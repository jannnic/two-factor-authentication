package org.dhbw.otp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dhbw.otp.Helpers;

/**
 * Filter. Überprüft, ob beide Authentifizierungsschritte erfolgt sind.
 * 
 * @author Roman Scharton
 * 
 */
public class SecurityFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {

	}

	public void destroy() {
	}

	public void doFilter(ServletRequest sreq, ServletResponse sres,
			FilterChain chain) throws IOException, ServletException {

		System.out.println("Starting SecurityFilter.doFilter");

		HttpServletResponse response = (HttpServletResponse) sres;
		HttpServletRequest request = (HttpServletRequest) sreq;
		HttpSession session = request.getSession(true);

		// tan authentication already succeeded
		if (session.getAttribute(Constants.AUTH_TAN) != null
				&& session.getAttribute(Constants.AUTH_TAN).equals(
						Constants.AUTH_TAN_SUCCEDED)) {
			chain.doFilter(request, response);
		} else {
			// Redirection to new secret page
			if (Constants.NEW_SECRET) {
				response.sendRedirect(request.getContextPath() + "/"
						+ Helpers.getProperty("NEW_SECRET_PAGE"));
			
			} else {
				// redirection to tan login page
				response.sendRedirect(request.getContextPath() + "/"
						+ Helpers.getProperty("TAN_LOGIN_PAGE"));
			}
		}
	}	
}
