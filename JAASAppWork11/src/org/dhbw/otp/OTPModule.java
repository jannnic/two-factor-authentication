package org.dhbw.otp;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.dhbw.otp.filters.Constants;

public class OTPModule implements LoginModule {

	private String cbUser, cbPass;
	private Subject subject;
	private CallbackHandler cbh;

	private boolean full_authenticated = false, commitSuccess = false;
	private OTPUser userPrincipal, authenticatedPrincipal = new OTPUser(
			"Authenticated");

	/**
	 * Initializes the login module
	 */
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.cbh = callbackHandler;
	}

	/**
	 * Subject wants to login
	 */
	@Override
	public boolean login() throws LoginException {
		// Create callbacks
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("Name");
		callbacks[1] = new PasswordCallback("Password", false);
		if (this.cbh == null) {
			throw new LoginException("CallbackHandler is null");
		}

		// Let the callback handler give us the name, password and token
		try {
			this.cbh.handle(callbacks);
		} catch (IOException e) {
			throw new LoginException(e.getMessage());
		} catch (UnsupportedCallbackException e) {
			e.printStackTrace();
			throw new LoginException(e.getMessage());
		}

		// Retrieve the username
		this.cbUser = ((NameCallback) callbacks[0]).getName();
		if (this.cbUser == null || this.cbUser.trim().isEmpty()) {
			Constants.ERR_MSG = "Der Benutzername darf nicht leer sein.";
			throw new LoginException(Constants.ERR_MSG);
		}

		// Retrieve the password
		this.cbPass = String.valueOf(((PasswordCallback) callbacks[1])
				.getPassword());
		if (this.cbPass == null || this.cbPass.trim().isEmpty()) {
			Constants.ERR_MSG = "Das Passwort darf nicht leer sein!";
			throw new LoginException(Constants.ERR_MSG);
		}

		// half-authentication
		
		// getting LDAP-information from the config-file
		String ldapServer = Helpers.getProperty("ldapServer");
		String baseAdmin = Helpers.getProperty("baseAdmin");
		String baseUser = Helpers.getProperty("baseUser");
		String adminUser = Helpers.getProperty("adminUser");
		String adminPassword = Helpers.getProperty("adminPassword");
		String userID = Helpers.getProperty("userID");

		// Authentication via LDAP
		LDAPAuth la = new LDAPAuth(ldapServer, adminUser, baseAdmin,
				adminPassword);

		if (!la.isConnectedWithAdmin()) {
			Constants.ERR_MSG = la.getReturnValue();
			throw new LoginException(Constants.ERR_MSG);
		}
		if (!la.userExists(userID + cbUser, baseUser)) {
			Constants.ERR_MSG = la.getReturnValue();
			throw new LoginException(Constants.ERR_MSG);
		}
		if (!la.passwordCorrect(userID + cbUser, baseUser, cbPass)) {
			Constants.ERR_MSG = la.getReturnValue();
			throw new LoginException(Constants.ERR_MSG);
		} else {
			Constants.USER = cbUser;
			full_authenticated = true;
		}
		return this.full_authenticated;
	}

	/**
	 * Adds "Authenticated" role to the subject
	 */
	@Override
	public boolean commit() throws LoginException {
		if (this.full_authenticated) {
			this.userPrincipal = new OTPUser(cbUser);
			subject.getPrincipals().add(userPrincipal);
			Helpers.addRoleToSubject(subject, authenticatedPrincipal);
		}
		this.commitSuccess = true;
		return commitSuccess;
	}

	/**
	 * Is invoked when an error happens or login() or commit() failed
	 */
	@Override
	public boolean abort() throws LoginException {
		if (this.full_authenticated && this.commitSuccess == false) {
			cbPass = cbUser = null;
			throw new FailedLoginException(
					"Runtime exception while proccessing the full authentication");
		}
		return false;
	}

	/**
	 * Removes the "Authenticated" role from the subject
	 */
	@Override
	public boolean logout() throws LoginException {
		Helpers.removeRoleFromSubject(subject, authenticatedPrincipal);
		subject.getPrincipals().remove(userPrincipal);
		userPrincipal = null;
		this.commitSuccess = this.full_authenticated = false;
		cbUser = cbPass = null;
		authenticatedPrincipal = null;
		return true;
	}

}
