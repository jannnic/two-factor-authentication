package org.dhbw.otp;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.dhbw.otp.filters.Constants;

public class LDAPAuth {

	private String returnValue = "";
	private boolean connectedWithAdmin;
	private DirContext ctx = null;
	private String ldapServer = null;

	/**
	 * Connect with adminUsername and adminPassword
	 * 
	 * @param ldapServer
	 *            - the LDAP-Server
	 * @param adminUser
	 *            - the admin-user
	 * @param baseAdmin
	 *            - the admin-base
	 * @param adminPassword
	 *            - the admin-password
	 */
	public LDAPAuth(String ldapServer, String adminUser, String baseAdmin,
			String adminPassword) {
		try {
			// Create initial context
			DirContext ctx = connectToLDAP(ldapServer, adminUser, baseAdmin,
					adminPassword);

			// Login correct
			this.ldapServer = ldapServer;
			this.ctx = ctx;
			this.returnValue = "Connected to LDAP (admin) \n";
			this.connectedWithAdmin = true;

		} catch (NamingException e) {
			// Login incorrect
			this.returnValue = "Connection to LDAP failed (admin) \n";
			this.connectedWithAdmin = false;
		}
	}

	/**
	 * Check if username exists
	 * 
	 * @return true - if the username exists, false if not
	 */
	public boolean userExists(String user, String baseUser) {
		try {
			NamingEnumeration<?> namingEnum = ctx.search(user + "," + baseUser,
					"(objectclass=simpleSecurityObject)",
					getSimpleSearchControls());
			// Close the context when we're done
			ctx.close();
			while (namingEnum != null && namingEnum.hasMore()) {
				SearchResult res = (SearchResult) namingEnum.next();
				Attributes attr = res.getAttributes();

				if (attr.get("secret") == null) {
					Constants.NEW_SECRET = true;
				} else {
					Constants.NEW_SECRET = false;
				}
				this.returnValue = "Username " + user + " does exist \n";
				return true;
			}

			namingEnum.close();
		} catch (NamingException e) {
			this.returnValue = "Wrong username. " + user + " does not exist \n";
			return false;
		}
		return false;
	}

	/**
	 * Check if password is correct
	 * 
	 * @return true if it is correct, false if not
	 */
	public boolean passwordCorrect(String user, String baseUser,
			String userPassword) {
		try {
			// Create initial context
			DirContext ctx = connectToLDAP(ldapServer, user, baseUser,
					userPassword);

			// Login correct
			this.returnValue = "Password for " + user + " is correct \n";

			// Close the context when we're done
			ctx.close();

			return true;

		} catch (NamingException e) {
			// Login incorrect
			this.returnValue = "Password for " + user + " is not correct \n";
			return false;
		}
	}

	/**
	 * Sets a new secret for the given user
	 * 
	 * @param user
	 *            - the user
	 * @param baseUser
	 *            - the base for the user
	 */
	public void setSecret(String user, String baseUser, String value) {
		try {
			NamingEnumeration<?> namingEnum = ctx.search(user + "," + baseUser,
					"(objectclass=simpleSecurityObject)",
					getSimpleSearchControls());
			
			while (namingEnum != null && namingEnum.hasMore()) {
				SearchResult res = (SearchResult) namingEnum.next();
				Attributes attr = res.getAttributes();
				attr.put("secret", value);
				this.returnValue = "Set new secret for user: " + user + " succesful \n";
				// Add to LDAP
				ctx.modifyAttributes(user + "," + baseUser,DirContext.REPLACE_ATTRIBUTE, attr);
				// Close the context when we're done
				ctx.close();
			}

			namingEnum.close();
		} catch (NamingException e) {
			this.returnValue = "Error while setting secret for user: " + user + "\n";
			System.out.println(returnValue);
		}
		
	}
	
	/**
	 * Gets the secret for given user
	 * @param user
	 * 			- the user
	 * @param baseUser
	 * 			-the base for the user
	 * @return secret as a String
	 */
	public String getSecret(String user, String baseUser) {
		try {
			String secret = "";
			NamingEnumeration<?> namingEnum = ctx.search(user + "," + baseUser,
					"(objectclass=simpleSecurityObject)",
					getSimpleSearchControls());
			
			while (namingEnum != null && namingEnum.hasMore()) {
				SearchResult res = (SearchResult) namingEnum.next();
				Attributes attr = res.getAttributes();
				secret = attr.get("secret").toString();
				String [] secretTemp = secret.split("secret: ");
				secret = secretTemp[1];
				ctx.close();
			}

			namingEnum.close();
			return secret;
		} catch (NamingException e) {
			this.returnValue = "Error while getting secret for user: " + user + "\n";
			return "";
		}
	}

	private SearchControls getSimpleSearchControls() {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setTimeLimit(30000);
		// String[] attrIDs = {"objectGUID"};
		// searchControls.setReturningAttributes(attrIDs);
		return searchControls;
	}

	private DirContext connectToLDAP(String ldapServer, String user,
			String base, String password) throws NamingException {
		// Set up environment for creating initial context
		Hashtable<String, String> env = new Hashtable<String, String>(11);
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapServer);

		// Authenticate
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, user + "," + base);
		env.put(Context.SECURITY_CREDENTIALS, password);

		try {
			// Create initial context
			DirContext ctx = new InitialDirContext(env);

			// Login correct
			return ctx;
		} catch (NamingException e) {
			// Login incorrect
			throw e;

		}
	}

	/**
	 * returns a string which indicates the current status
	 * 
	 * @return the current status
	 */
	public String getReturnValue() {
		return returnValue;
	}

	/**
	 * Checks if the LDAP-Connection with the admin-account was successful
	 * 
	 * @return true if it was successful, false otherwise
	 */
	public boolean isConnectedWithAdmin() {
		return connectedWithAdmin;
	}
	
}
