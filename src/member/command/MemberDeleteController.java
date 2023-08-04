package member.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.MemberService;
import mvc.command.CommandController;

public class MemberDeleteController implements CommandController {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberService memberService = new MemberService();
		//1.파라미터받기
		int no = Integer.parseInt(request.getParameter("no"));//삭제하고 싶은 글번호
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));//새고해서 갈 글 페이지
		
		//2.비즈니스로직<->Service<->DAO<->DB
		memberService.delete(no);
		
		//3.Model
		//4.View
		response.sendRedirect(request.getContextPath()+"/member/list.do?pageNo="+pageNo);
		return null;
	}
}
