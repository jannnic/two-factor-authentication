package org.dhbw.otp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.Properties;

import javax.security.auth.Subject;

public class Helpers {
	public static final String USERNAME = "org.dhbw.otp.username";
	
	private static Properties propertiesFile;
	
	/**
	 * reads the properties
	 */
	public static void readProperties() {
		try {
			propertiesFile.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		} catch (FileNotFoundException e) {
			System.err.println("Properties file not found!");
		} catch (IOException e) {
			System.err.println("Could not read properties file!");
		}
	}	
	
	/**
	 * Add a role to a subject (in JBoss: add it to a group called "Roles")
	 * @param s The Subject
	 * @param role The Role
	 * @return true on success - false on failure
	 */
	public static boolean addRoleToSubject(Subject s, Principal role) {
		boolean hasRoles = false;
		for (Principal p : s.getPrincipals()) {
			if (p.getName().equals("Roles")) {
				if (p instanceof OTPGroup) {
					hasRoles = true;
					if (!((OTPGroup) p).isMember(role)) {
						((OTPGroup) p).addMember(role);
						return true;
					}
				}
			}
		}
		if (!hasRoles) {
			OTPGroup tempGroup = new OTPGroup("Roles");
			tempGroup.addMember(role);
			s.getPrincipals().add(tempGroup);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a role from a subject (in JBoss: remove the role from the "Roles" group)
	 * @param s The Subject
	 * @param role The Role 
	 * @return true on success - false on failure
	 */
	public static boolean removeRoleFromSubject(Subject s, Principal role) {
		boolean success = false;
		for (Principal p : s.getPrincipals()) {
			if (p.getName().equals("Roles")) {
				if (p instanceof OTPGroup) {
					success = ((OTPGroup) p).removeMember(role);
				}
			}
		}
		return success;
	}	
	
	/**
	 * Returns the stored value matching to key
	 * @param key the key
	 * @return the value
	 */
	public static String getProperty(String key) {
		if (propertiesFile == null) {
			propertiesFile = new Properties();
			readProperties();
		}
		return propertiesFile.getProperty(key);
	}	
}
