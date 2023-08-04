package member.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.exception.MemberNotFoundException;
import member.model.Member;
import member.service.MemberService;
import mvc.command.CommandController;

public class MemDetailController implements CommandController{

	private MemberService memberService = new MemberService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.파라미터받기
		String strNo = request.getParameter("no");
		String oldPage = request.getParameter("pageNo");
		int mem_no = Integer.parseInt(strNo);
		System.out.println("MemDetailController- process() mem_no="+mem_no);
		
		//2.비즈니스로직<->Service<->DAO<->DB
		User user = (User) request.getSession().getAttribute("AUTH_USER");
		if(!canModify(user.getMem_no(),mem_no)) {
			response.setContentType("text/html; charset=UTF-8");
	         PrintWriter writer = response.getWriter();
	         String msg = "<script>alert('비정상적인 접근입니다.');"
	               +"location.href="+request.getContextPath()+"'/chall/list.do'</script>";
	         writer.print(msg);
	          return null;
		}
		try {
			//파라미터 int no : 상세조회할 글 번호
			//리턴 OurArticleData ora : 글번호,작성자id,작성자명,제목,작성일,수정일,조회수,내용
			Member member = memberService.getMemberDetail(mem_no);  
			
			//3.Model
			request.setAttribute("member", member);
			request.setAttribute("oldPage", oldPage);
			
			//4.View
			return request.getContextPath()+"/view/member/memberDetail.jsp";
		}catch(MemberNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);//404
			return null;
		}
		
	}
	//수정가능여부체크
	private boolean canModify(int modifyingUserNo, int memNo) {
		if(modifyingUserNo==1|| modifyingUserNo==memNo) {
			return true;
		}
		return false;

	}
}
