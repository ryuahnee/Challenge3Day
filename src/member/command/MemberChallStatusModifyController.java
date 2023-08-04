package member.command;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.model.Member;
import member.model.MemberChallStatus;
import member.service.MemberChallStatusService;
import member.service.MemberService;
import mvc.command.CommandController;

public class MemberChallStatusModifyController implements CommandController{
	private static final String FORM_VIEW = "/view/member/memberChallStatusModify.jsp";
	private MemberChallStatusService memberChallStatusService = new MemberChallStatusService();
	private MemberService memberService = new MemberService();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date start_date;
	Date final_date;

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ModifyController의 process() 진입");
		
		if(request.getMethod().equalsIgnoreCase("get")) {//요청방식이 get방식이면 processForm
			return processForm(request,response);
		} else if(request.getMethod().equalsIgnoreCase("post")) {//요청방식이 post방식이면 processSubmit
			return processSubmit(request,response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); //405허용되지 않은 메소드
			return null;
		}
	}
	//수정 폼 보여주기
	public String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//파라미터
		int chall_manual_no = Integer.parseInt(request.getParameter("no"));//수정할 회원번호
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int oldPageNo = Integer.parseInt(request.getParameter("oldPageNo"));
		int mem_no = Integer.parseInt(request.getParameter("memNo"));
		
		//비지니스 로직처리
		MemberChallStatus memberChallStatus = memberChallStatusService.getMemberChallStatusDetail(chall_manual_no);
		User user = (User) request.getSession().getAttribute("AUTH_USER");
		if(!canModify(user.getMem_no())) {
			response.setContentType("text/html; charset=UTF-8");
	         PrintWriter writer = response.getWriter();
	         String msg = "<script>alert('비정상적인 접근입니다.');"
	               +"location.href="+request.getContextPath()+"'/myPage/list.do'</script>";
	         writer.print(msg);
	          return null;
		}
		Member member = memberService.getMemberDetail(mem_no);
		//수정을 위해 세션에서 가져온 회원정보와 디비정보
		MemberChallStatus mcs = new MemberChallStatus(chall_manual_no, memberChallStatus.getChall_no(), memberChallStatus.getMem_no(), memberChallStatus.getChall_ing_status(), memberChallStatus.getStart_date(), memberChallStatus.getFinal_date(), memberChallStatus.getChall_result());
		
		//모델
		request.setAttribute("mcs", mcs);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("oldPageNo", oldPageNo);
		request.setAttribute("mem_no", mem_no);
		request.setAttribute("member", member);
		//뷰	
		return request.getContextPath()+FORM_VIEW ;
	}
	
	//수정가능여부체크
	private boolean canModify(int modifyingUserNo) {
		if(modifyingUserNo==1) {
			return true;
		}
		return false;

	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("submit진입");
		//파라미터 받기 chall_manual_no,chall_no,mem_no,chall_ing_status,start_date,final_date,chall_result);
		int chall_manual_no = Integer.parseInt(request.getParameter("chall_manual_no"));
		int chall_no = Integer.parseInt(request.getParameter("chall_no"));
		int mem_no = Integer.parseInt(request.getParameter("mem_no"));//수정할 회원번호
		int chall_ing_status = Integer.parseInt(request.getParameter("chall_ing_status"));
		String strStart_date=request.getParameter("start_date");
		String strFinal_date=request.getParameter("final_date");
		String chall_result=request.getParameter("chall_result");
		User user = (User)request.getSession().getAttribute("AUTH_USER");
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int oldPageNo = Integer.parseInt(request.getParameter("oldPageNo"));
		
		//날짜형변환
		try {
			start_date = dateFormat.parse(strStart_date);
			final_date = dateFormat.parse(strFinal_date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		//수정처리를 위한 데이터를 객체로 생성
		MemberChallStatus mcs = new MemberChallStatus(chall_manual_no, chall_no, mem_no, chall_ing_status, start_date, final_date, chall_result);
		System.out.println(mcs);
		request.setAttribute("mcs", mcs);
		
		//유효성검사
		//에러정보가 담기는 Map- p670 77라인
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		request.setAttribute("errors",errors);
		if(!errors.isEmpty()) { //에러가 있으면  수정폼페이지로 이동
			return request.getContextPath()+FORM_VIEW;
		}
		
		if(!canModify(user.getMem_no())) {
			if(!canModify(user.getMem_no())) {
				response.setContentType("text/html; charset=UTF-8");
		         PrintWriter writer = response.getWriter();
		         String msg = "<script>alert('비정상적인 접근입니다.');"
		               +"location.href="+request.getContextPath()+"'/myPage/list.do'</script>";
		         writer.print(msg);
		          return null;
			}
		}

		//비지니스로직처리
		memberChallStatusService.Modify(mcs);
		
		//모델
		
		//뷰
		response.sendRedirect(request.getContextPath()+"/member/challList.do?no="+mem_no+"&pageNo="+pageNo+"&oldPageNo="+oldPageNo);
		return null;
	}
}
