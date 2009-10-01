package authdb.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This action extracts a list of users from the model and
 * populates the outgoing listForm bean.
 */

public class ListAction extends DbAction implements Constants {

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm listForm,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		// interact with model
		List users = new ArrayList(getUserManager().findAll());

		// populate form bean
		BeanUtils.setProperty(listForm, PROPERTY_USERS, users);

		return mapping.findForward(FORWARD_SUCCESS);
	}
}