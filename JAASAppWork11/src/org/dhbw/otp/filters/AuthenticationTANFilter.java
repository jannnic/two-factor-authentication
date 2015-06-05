package org.dhbw.otp.filters;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dhbw.otp.Helpers;
import org.dhbw.otp.LDAPAuth;

/**
 * Filter. Führt den zweiten Authentifizierungsschritt durch.
 * 
 * @author Roman Scharton
 * 
 */
public class AuthenticationTANFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {

	}

	public void destroy() {
	}

	public void doFilter(ServletRequest sreq, ServletResponse sres,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("Starting AuthenticationTANFilter.doFilter");
		boolean indiz1 = false;
		boolean indiz2 = false;
		HttpServletResponse response = (HttpServletResponse) sres;
		HttpServletRequest request = (HttpServletRequest) sreq;
		HttpSession session = request.getSession(false);
		String tan = request.getParameter(Constants.AUTH_TAN_FIELD);
		if (tan == null) {
			indiz1 = true;
		} else if (tan.isEmpty()) {
			indiz2 = true;
		}

		Object second_authentication = session.getAttribute(Constants.AUTH_TAN);

		if (indiz1 && second_authentication != null
				&& second_authentication.equals(Constants.AUTH_TAN_SUCCEDED)) {
			/* second authentication already succeeded */
			chain.doFilter(request, response);
		} else if (indiz1 && second_authentication == null) {
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/"
					+ Helpers.getProperty("LOGOUT_PAGE"));

		} else if (indiz2) {
			request.getSession().setAttribute("message",
					"TAN darf nicht leer sein");
			System.out.println(session.getAttribute("message"));
			RequestDispatcher rd = request
					.getRequestDispatcher(Helpers.getProperty("TAN_LOGIN_PAGE"));
			rd.forward(request, response);
		} else {
			long keyLong = Long.parseLong(tan);
			long t = new Date().getTime() / TimeUnit.SECONDS.toMillis(30);
			// Getting secret for current user

			// getting LDAP-information from the config-file
			String ldapServer = Helpers.getProperty("ldapServer");
			String baseAdmin = Helpers.getProperty("baseAdmin");
			String baseUser = Helpers.getProperty("baseUser");
			String adminUser = Helpers.getProperty("adminUser");
			String adminPassword = Helpers.getProperty("adminPassword");
			String userID = Helpers.getProperty("userID");

			String user = Constants.USER;

			LDAPAuth la = new LDAPAuth(ldapServer, adminUser, baseAdmin,
					adminPassword);

			try {
				if (!GoogleAuthenticator.check_code(
						la.getSecret(userID + user, baseUser), keyLong, t)) {
					// If tan and GooglTan is wrong
					session.setAttribute("message", "Invalid TAN");
					System.out.println(session.getAttribute("message"));
					RequestDispatcher rd = request
							.getRequestDispatcher(Helpers.getProperty("TAN_LOGIN_PAGE"));
					rd.forward(request, response);
				} else {
					// If Google is right
					session.setAttribute(Constants.AUTH_TAN,
							Constants.AUTH_TAN_SUCCEDED);
					chain.doFilter(request, response);
				}
			} catch (InvalidKeyException e1) {
				System.out.println("Invalid key");
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				System.out.println("Error during algorithm");
				e1.printStackTrace();
			}

		}

	}

}
