package member.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.MemberPage;
import member.service.MemberService;
import mvc.command.CommandController;

public class MemberListController  implements CommandController {

	private String FORM_VIEW = "/view/member/memberList.jsp";
	private MemberService memberService = new MemberService();
	
	//p652 15라인
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberListController-process()진입");
		String strPageNo = request.getParameter("pageNo"); //user가 선택한 페이지번호
		int pageNo = 1; //디폴트 페이지번호
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
		}
		String option = request.getParameter("option");
		
		String papramSearchCon = request.getParameter("searchCon");
		String searchCon=null;
		if(papramSearchCon!=null) {
			searchCon = papramSearchCon;
		}
		
		//2.비즈니스로직
		User user = (User) request.getSession().getAttribute("AUTH_USER");
		if(!canModify(user.getMem_no())) {
			response.setContentType("text/html; charset=UTF-8");
	         PrintWriter writer = response.getWriter();
	         String msg = "<script>alert('비정상적인 접근입니다.');"
	               +"location.href="+request.getContextPath()+"'/chall/list.do'</script>";
	         writer.print(msg);
	          return null;
		}
		MemberPage memberPage = memberService.getMemberPage(option,searchCon,pageNo);//총 게시글수
		System.out.println("memberPage"+memberPage);
		//3.Model
		request.setAttribute("memberPage", memberPage);
		request.setAttribute("nowPage", pageNo); //현재페이지
		request.setAttribute("option", option); //현재페이지
		request.setAttribute("searchCon", searchCon); //현재페이지		
		
		//4.View
		return request.getContextPath()+FORM_VIEW;
	}
	//수정가능여부체크
	private boolean canModify(int modifyingUserNo) {
		if(modifyingUserNo==1) {
			return true;
		}
		return false;

	}
}
