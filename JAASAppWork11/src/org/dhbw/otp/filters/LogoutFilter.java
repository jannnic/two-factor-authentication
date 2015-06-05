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

/**
 * Löscht die Sitzungsinhalte.
 * 
 * @author Roman Scharton
 * 
 */
public class LogoutFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {

	}

	public void destroy() {
	}

	public void doFilter(ServletRequest sreq, ServletResponse sres,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("Starting LogoutFilter.doFilter");

		HttpServletResponse response = (HttpServletResponse) sres;
		HttpServletRequest request = (HttpServletRequest) sreq;
		HttpSession session = request.getSession(false);
		session.invalidate();
		chain.doFilter(request, response);

	}
}
