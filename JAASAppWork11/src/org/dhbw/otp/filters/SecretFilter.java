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
import org.dhbw.otp.LDAPAuth;

public class SecretFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest sreq, ServletResponse sres,
			FilterChain chain) throws IOException, ServletException {
		
		System.out.println("Starting SecretFilter.doFilter");

		HttpServletRequest request = (HttpServletRequest) sreq;
		HttpServletResponse response = (HttpServletResponse) sres;
		HttpSession session = request.getSession();

		String host = Helpers.getProperty("host");
		String secret = new GoogleSecretKey().getSecretKeyEncoded();
		session.setAttribute("secret", secret);

		// half-authentication
		String ldapServer = Helpers.getProperty("ldapServer");
		String baseAdmin = Helpers.getProperty("baseAdmin");
		String baseUser = Helpers.getProperty("baseUser");
		String adminUser = Helpers.getProperty("adminUser");
		String adminPassword = Helpers.getProperty("adminPassword");
		String userID = Helpers.getProperty("userID");

		LDAPAuth la = new LDAPAuth(ldapServer, adminUser, baseAdmin,
				adminPassword);
		
		session.setAttribute("user", userID + Constants.USER);
		session.setAttribute("baseUser", baseUser);
		session.setAttribute("la", la);

		String qrCode = GoogleSecretKey.getQRBarcodeURL(Constants.USER, host,
				secret);
		String url = java.net.URLDecoder.decode(qrCode, "UTF-8");

		System.out.println("Encoded Key: " + secret);
		System.out.println("QRCode for " + secret + " : " + url);
		session.setAttribute("qrCode", url);

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
