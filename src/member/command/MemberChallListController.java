package member.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.model.Member;
import member.service.MemberChallListPage;
import member.service.MemberChallStatusService;
import member.service.MemberService;
import mvc.command.CommandController;

public class MemberChallListController implements CommandController {

	private String FORM_VIEW = "/view/member/memberChallList.jsp";
	private MemberChallStatusService memberChallStatus = new MemberChallStatusService();
	private MemberService memberservice = new MemberService();
	
	//p652 15라인
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberChallListController-process()진입");
		//1.파라미터받기-p652 17라인
		String strPageNo = request.getParameter("pageNo"); //user가 선택한 페이지번호
		int pageNo = 1; //user가 선택안했을 때 기본적으로 보여지는 페이지번호
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
		}
		String strMem_no=request.getParameter("no");
		int mem_no = Integer.parseInt(strMem_no);
		int oldPageNo = Integer.parseInt(request.getParameter("oldPageNo"));
		String option = request.getParameter("option");
		
		String papramSearchCon = request.getParameter("searchCon");
		String searchCon=null;
		if(papramSearchCon!=null) {
			searchCon = papramSearchCon;
		}
		//2.비즈니스로직<->Service<->DAO<->DB
		/*파라미터  int pageNo : 보고싶은 페이지(=>현재 페이지)*/
		User user = (User) request.getSession().getAttribute("AUTH_USER");
		if(!canModify(user.getMem_no())) {
			response.setContentType("text/html; charset=UTF-8");
	         PrintWriter writer = response.getWriter();
	         String msg = "<script>alert('비정상적인 접근입니다.');"
	               +"location.href="+request.getContextPath()+"'/myPage/list.do'</script>";
	         writer.print(msg);
	          return null;
		}
		MemberChallListPage memberChallListPage = memberChallStatus.getMemberChallListPage(option,searchCon,pageNo,mem_no);//총 게시글수
		Member member = memberservice.getMemberDetail(mem_no);
		//3.Model
	    /*ArticlePage articlePage에는  
	      총 게시글수포함(getTotal()호출)
		  article목록포함(getContent()호출) 
		  int  totalPages;	//총페이지수   
		  int  startPage;	//시작페이지  
		  innt  endPage;	//끝페이지*/
		request.setAttribute("memberChallListPage", memberChallListPage);
		request.setAttribute("nowPage", pageNo);
		request.setAttribute("mem_no", mem_no);
		request.setAttribute("oldPageNo", oldPageNo); 
		request.setAttribute("option", option); 
		request.setAttribute("searchCon", searchCon); 
		request.setAttribute("member", member); 	
		
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
