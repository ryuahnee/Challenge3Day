package member.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.MemberChallStatusService;
import mvc.command.CommandController;

public class MemberChallDeleteController implements CommandController {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberChallStatusService memberCallStatusSevice = new MemberChallStatusService();
		//1.파라미터받기
		int mem_no = Integer.parseInt(request.getParameter("memNo"));
		int no = Integer.parseInt(request.getParameter("no"));//삭제하고 싶은 글번호
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));//새고해서 갈 글 페이지
		int oldPageNo=Integer.parseInt(request.getParameter("oldPageNo"));//새고해서 갈 글 페이지
		//2.비즈니스로직<->Service<->DAO<->DB
		User user = (User) request.getSession().getAttribute("AUTH_USER");
		if(!canModify(user.getMem_no())) {
			response.setContentType("text/html; charset=UTF-8");
	         PrintWriter writer = response.getWriter();
	         String msg = "<script>alert('비정상적인 접근입니다.');"
	               +"location.href="+request.getContextPath()+"'/chall/list.do'</script>";
	         writer.print(msg);
	          return null;
		}
		memberCallStatusSevice.delete(no);
		
		//3.Model
		//4.View
		response.sendRedirect(request.getContextPath()+"/member/challList.do?no="+mem_no+"&pageNo="+pageNo+"&oldPageNo="+oldPageNo);
		return null;
	}
	//수정가능여부체크
	private boolean canModify(int modifyingUserNo) {
		if(modifyingUserNo==1) {
			return true;
		}
		return false;

	}
}
