package org.dhbw.otp;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * OTPGroup represents a group of users in the OTP JAAS Module.
 * @author Amir Hadi
 *
 */
public class OTPGroup implements java.security.acl.Group, Serializable {

	private static final long serialVersionUID = -31910719379392366L;
	private final String name;
	private final Set<Principal> users = new HashSet<Principal>();

	
	/**
	 * Creates a group with a given name
	 * @param name the name of the group
	 */
	public OTPGroup(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the group
	 * @return the name of the group
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Adds a {@link Principal} to the group
	 */
	@Override
	public boolean addMember(Principal user) {
		return users.add(user);
	}

	/**
	 * Removes a {@link Principal} from the group
	 */
	@Override
	public boolean removeMember(Principal user) {
		return users.remove(user);
	}

	/**
	 * Returns true if member is part of this group
	 */
	@Override
	public boolean isMember(Principal member) {
		return users.contains(member);
	}

	/**
	 * Returns the members as enumeration.
	 */
	@Override
	public Enumeration<? extends Principal> members() {
		return Collections.enumeration(users);
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
		OTPGroup other = (OTPGroup) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
