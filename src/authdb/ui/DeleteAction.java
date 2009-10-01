package authdb.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This action deletes the user whose id is given as a request parameter. 
 */

public class DeleteAction extends DbAction implements Constants {

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		// obtain arguments from request
		String id = request.getParameter(PARAMETER_ID);

		// interact with model
		getUserManager().remove(id);

		request.setAttribute(ATTRIBUTE_MESSAGE_KEY, "delete.message");
		return mapping.findForward(FORWARD_SUCCESS);
	}
}