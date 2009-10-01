package authdb.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import authdb.db.*;

/**
 * This action finds the user whose id is given as a request parameter
 * and populates the outging userForm bean with the user's information.
 */

public class FindAction extends DbAction implements Constants {

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm userForm,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		// obtain arguments from request
		String id = request.getParameter(PARAMETER_ID);

		// interact with model and populate form bean
    User entry = getUserManager().find(id);
		BeanUtils.setProperty(userForm, PROPERTY_ID, id);
		BeanUtils.setProperty(userForm, PROPERTY_PASSWORD, entry.getPassword());
		BeanUtils.setProperty(userForm, PROPERTY_ROLES, 
				Util.setAsString(entry.getRoles()));

		return mapping.findForward(FORWARD_SUCCESS);
	}
}