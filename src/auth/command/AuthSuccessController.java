package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.Auth;
import mvc.command.CommandController;

public class AuthSuccessController implements CommandController {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isauth= request.getParameter("authSuccess");
		String id= request.getParameter("inputId");
		
		Auth auth = new Auth(isauth,id);
		HttpSession session = request.getSession();
		session.setAttribute("IS_AUTH",auth);
		return null;
	}

}
