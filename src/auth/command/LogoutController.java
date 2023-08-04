package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandController;

//로그아웃요청 처리 클래스 - 세션종료(정보삭제)
public class LogoutController implements CommandController{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session =request.getSession(false);
		if(session!=null) {
			session.invalidate();
		}
		response.sendRedirect(request.getContextPath()+"/chall/list.do");
		return null;
	}

}
