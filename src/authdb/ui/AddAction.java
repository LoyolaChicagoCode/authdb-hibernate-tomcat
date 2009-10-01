package authdb.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import authdb.db.User;

/**
 * This action adds a user to the model whose information is provided
 * by the incoming userForm bean.
 */

public class AddAction extends DbAction implements Constants {

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm userForm,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		// obtain arguments from form bean
		String id = BeanUtils.getProperty(userForm, PROPERTY_ID);
		String password = BeanUtils.getProperty(userForm, PROPERTY_PASSWORD);
		String roles = BeanUtils.getProperty(userForm, PROPERTY_ROLES);

		// interact with model
		User entry = getUserManager().create(id, password, Util.stringAsSet(roles));
		
		if (entry != null) {
			request.setAttribute(ATTRIBUTE_MESSAGE_KEY, "add.success");
		} else {
			request.setAttribute(ATTRIBUTE_MESSAGE_KEY, "add.failure");
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}
}