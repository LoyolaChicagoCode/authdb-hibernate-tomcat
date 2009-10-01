package authdb.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This action extracts a list of users from the model and
 * populates the outgoing listForm bean.
 */

public class InitAction extends DbAction implements Constants {

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm listForm,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		// interact with model
		getUserManager().init();

		return mapping.findForward(FORWARD_SUCCESS);
	}
}