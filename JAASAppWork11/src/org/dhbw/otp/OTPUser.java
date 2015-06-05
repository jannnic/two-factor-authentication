package org.dhbw.otp;

import java.io.Serializable;
import java.security.Principal;

/**
 * This class represents a user in the OTP JAAS Module. It just stores a name as String.
 * @author Amir Hadi
 *
 */
public class OTPUser implements Principal, Serializable {

	private static final long serialVersionUID = 2250206081274086435L;
	
	private final String name;
	
	/**
	 * Creates a OTPUser object with a given name
	 * @param name the name of the user
	 */
	public OTPUser(String name) {
		this.name = name;
	}

	/**
	 * @return the name of the user
	 */
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OTPUser other = (OTPUser) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
