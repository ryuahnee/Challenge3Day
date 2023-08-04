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
import member.service.MemberService;
import mvc.command.CommandController;

public class MemberModifyController implements CommandController {
	private static final String FORM_VIEW = "/view/member/memberModify.jsp";
	private MemberService memberService = new MemberService();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date birthyear;

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
		int mem_no = Integer.parseInt(request.getParameter("no"));//수정할 회원번호
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));//수정할 회원번호
		
		//비지니스 로직처리
		Member member = memberService.getMemberDetail(mem_no);
		User user = (User) request.getSession().getAttribute("AUTH_USER");
		if(!canModify(user.getMem_no(),member)) {
			response.setContentType("text/html; charset=UTF-8");
	         PrintWriter writer = response.getWriter();
	         String msg = "<script>alert('비정상적인 접근입니다.');"
	               +"location.href="+request.getContextPath()+"'/myPage/list.do'</script>";
	         writer.print(msg);
	          return null;
		}
		//수정을 위해 세션에서 가져온 회원정보와 디비정보
		Member memReq = new Member(mem_no, member.getId(), member.getMem_name(), member.getNickname(), member.getBirthyear(), member.getGender(), member.getJoin_date(), member.getMem_email(), member.getIsMarketing());
		//모델
		request.setAttribute("memReq", memReq);
		request.setAttribute("pageNo", pageNo);
		//뷰	
		return request.getContextPath()+FORM_VIEW ;
	}
	
	//수정가능여부체크
	private boolean canModify(int modifyingUserNo, Member member) {
		if(modifyingUserNo==member.getMem_no()||modifyingUserNo==1) {
			return true;
		}
		return false;

	}
	
	
	
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("submit진입");
		//파라미터 받기
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));//수정할 회원번호
		int mem_no = Integer.parseInt(request.getParameter("mem_no"));//수정할 회원번호
		String id=request.getParameter("id");
		String mem_name=request.getParameter("mem_name");
		String nickname=request.getParameter("nickname");
		String strBirthyear=request.getParameter("birthyear");
		String gender=request.getParameter("gender");
		String mem_email=request.getParameter("mem_email");
		String isMarketing=request.getParameter("isMarketing");
		User authUser = (User)request.getSession().getAttribute("AUTH_USER");
		//birthyear형변환
		try {
			birthyear = dateFormat.parse(strBirthyear);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		//수정처리를 위한 데이터를 객체로 생성
		Member memReq = new Member(mem_no, id, mem_name, nickname, birthyear, gender, null, mem_email, isMarketing);
		System.out.println(memReq);
		request.setAttribute("memReq", memReq);
		
		//유효성검사
		//에러정보가 담기는 Map- p670 77라인
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		request.setAttribute("errors",errors);
		if(!errors.isEmpty()) { //에러가 있으면  수정폼페이지로 이동
			return request.getContextPath()+FORM_VIEW;
		}
		
		//비지니스로직처리
		User user = (User) request.getSession().getAttribute("AUTH_USER");
		if(!canModify(user.getMem_no(),memReq)) {
			response.setContentType("text/html; charset=UTF-8");
	         PrintWriter writer = response.getWriter();
	         String msg = "<script>alert('비정상적인 접근입니다.');"
	               +"location.href="+request.getContextPath()+"'/myPage/list.do'</script>";
	         writer.print(msg);
	          return null;
		}
		memberService.Modify(memReq);
		
		//모델
		
		//뷰
		response.sendRedirect(request.getContextPath()+"/member/detail.do?no="+mem_no+"&pageNo="+pageNo);
		return null;
	}
}