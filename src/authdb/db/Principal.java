package authdb.db;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * A user object backed by Hibernate.
 */
public class Principal implements User {

	// instance variables corresponding to columns in the table
	
	private String id;
	private String password;
	private Set roles;

	public Principal() { }
	
	/**
	 * @param id the unique user id
	 * @param password the password
	 * @param roles a set of role name Strings 
	 */
	public Principal(String id, String password, Set roles) {
		this.id = id;
		this.password = password;
		this.roles = mapStringsToRoles(roles);
	}
	
	// accessors and mutators for the instance variables (also used by Hibernate)
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	Set getRealRoles() {
		return roles;
	}
	void setRealRoles(Set roles) {
		this.roles = roles;
	}

	// methods required by interface User (not used by Hibernate)
	
	public Set getRoles() {
		return mapRolesToStrings(roles);
	}
	public void setRoles(Set roles) {
		this.roles = mapStringsToRoles(roles);
	}
  public int compareTo(Object that) {
    return getId().compareTo(((User) that).getId());
  }
  public String toString() {
		return "{id=" + getId() + ",password=" + getPassword() + ",roles=" + getRoles() + "}";
  }

  /**
	 * This method converts a set of Roles to a set of Strings. 
	 */
	protected static Set mapRolesToStrings(Set roles) {
		Set result = new TreeSet();
		Iterator i = roles.iterator();
		while (i.hasNext()) {
			result.add(((Role) i.next()).getRole());
		}
		return result;
	}
	
	/**
	 * This method converts a set of Strings to a set of Roles. 
	 */
	protected static Set mapStringsToRoles(Set strings) {
		Set result = new HashSet();
		Iterator i = strings.iterator();
		while (i.hasNext()) {
			result.add(new Role((String) i.next()));
		}
		return result;
	}
}
