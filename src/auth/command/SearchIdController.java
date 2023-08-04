package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandController;

public class SearchIdController implements CommandController {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return request.getContextPath()+"/view/member/searchId.jsp";
	}

}
