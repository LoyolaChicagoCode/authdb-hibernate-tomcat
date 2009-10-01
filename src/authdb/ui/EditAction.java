package authdb.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This action edits a user according to the information in the
 * incoming userForm bean.
 */

public class EditAction extends DbAction implements Constants {

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
    getUserManager().update(id, password, Util.stringAsSet(roles));

		request.setAttribute(ATTRIBUTE_MESSAGE_KEY, "edit.message");
		return mapping.findForward(FORWARD_SUCCESS);
	}
}