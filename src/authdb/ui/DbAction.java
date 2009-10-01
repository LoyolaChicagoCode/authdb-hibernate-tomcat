package authdb.ui;

import javax.servlet.ServletContext;
import org.apache.struts.action.Action;
import authdb.db.*;

/**
 * This abstract action provides a method for accessing the model
 * (the user manager object).
 */
public abstract class DbAction extends Action implements Constants {

	/**
	 * This method returns the user manager, creating it first 
	 * in the application scope if necessary. 
	 */	
	public UserManager getUserManager() {
		UserManager userManager = null;
		try {
			ServletContext application = getServlet().getServletContext();
			synchronized (application) {
				userManager = (UserManager) application.getAttribute(ATTRIBUTE_USER_MANAGER);
				if (userManager == null) {
					userManager = new HibernateUserManager();
					application.setAttribute(ATTRIBUTE_USER_MANAGER, userManager);
				}
			}
		} catch (Exception e) { }
		return userManager;
	}
}