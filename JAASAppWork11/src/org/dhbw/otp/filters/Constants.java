package org.dhbw.otp.filters;

import org.dhbw.otp.Helpers;

/**
 * Beinhaltet die notwendige Konfiguration
 * 
 * @author Roman Scharton
 * 
 */
public class Constants {

	public final static String AUTH_UNAME_PASS = Helpers.getProperty("AUTH_UNAME_PASS");
	public final static String AUTH_UNAME_PASS_SUCCEDED = Helpers.getProperty("AUTH_UNAME_PASS_SUCCEDED");
	public final static String AUTH_TAN = Helpers.getProperty("AUTH_TAN");
	public final static String AUTH_TAN_SUCCEDED = Helpers.getProperty("AUTH_TAN_SUCCEDED");
	public final static String TAN_NUMBER = Helpers.getProperty("TAN_NUMBER");
	public final static String AUTH_UNAME_FIELD = Helpers.getProperty("AUTH_UNAME_FIELD");
	public final static String AUTH_PASS_FIELD = Helpers.getProperty("AUTH_PASS_FIELD");
	public final static String AUTH_TAN_FIELD = Helpers.getProperty("AUTH_TAN_FIELD");
	public static String ERR_MSG = null;
	public static boolean NEW_SECRET = false;
	public static String USER = null;
	

	private Constants() {
		super();
	}

}
