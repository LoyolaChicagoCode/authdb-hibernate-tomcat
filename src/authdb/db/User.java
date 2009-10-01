package authdb.db;

import java.util.Set;

/**
 * This interface represents a user entity.
 */

public interface User extends Comparable {
  String getId();
  String getPassword();
  void setPassword(String password);
  /**
   * @return a set of role names as Strings.
   */
  Set getRoles();
  /**
   * @param roles a set of role names as Strings.
   */
  void setRoles(Set roles);
}