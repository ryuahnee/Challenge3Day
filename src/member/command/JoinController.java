package member.command;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.exception.EmailDuplicationException;
import member.exception.IdDuplicationException;
import member.exception.NicknameDuplicationException;
import member.service.JoinRequest;
import member.service.MemberService;
import mvc.command.CommandController;

public class JoinController implements CommandController {
	//필드
	private static final String FORM_VIEW = "/view/member/joinForm.jsp";
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date birthyear;
	
	@Override
	public java.lang.String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("JoinHandler의 process() 진입");
		System.out.println("request.getMethod()"+request.getMethod());
		
		if(request.getMethod().equalsIgnoreCase("get")) {//요청방식이 get방식이면 processForm
			return processForm(request,response);
		} else if(request.getMethod().equalsIgnoreCase("post")) {//요청방식이 post방식이면 processSubmit
			return processSubmit(request,response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); //405허용되지 않은 메소드
			return null;
		}
	}

	//회원가입화면
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return request.getContextPath()+FORM_VIEW;
	}
	
	//회원가입처리
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		JoinRequest joinReq = new JoinRequest();
		System.out.println("processSubmit진입");
		//1.파라미터받기
		String id = request.getParameter("id");//아이디
		String pwd = request.getParameter("pwd");//비번
		String confirmPassword = request.getParameter("confirmPassword");//비번확인용
		String mem_name = request.getParameter("mem_name");//이름
		String nickname = request.getParameter("nickname");//닉네임
		String strBirthyear = request.getParameter("birthyear");//생일
		String gender = request.getParameter("gender");//성별
		String mem_email = request.getParameter("mem_email");//이메일
		String isMarketing = request.getParameter("isMarketing");//마케팅
		//birthyear형변환
		try {
			birthyear = dateFormat.parse(strBirthyear);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		joinReq.setId(id);
		joinReq.setPwd(pwd);
		joinReq.setConfirmPassword(confirmPassword);
		joinReq.setMem_name(mem_name);
		joinReq.setNickname(nickname);
		joinReq.setBirthyear(birthyear);
		joinReq.setGender(gender);
		joinReq.setMem_email(mem_email);
		joinReq.setIsMarketing(isMarketing);
		
		System.out.println("joinHandler의 joinReq참조변수="+joinReq.toString());//콘솔확인용
		
		Map<String,Boolean>errors = new HashMap<>();//생략가능
		//request.set~~ 여기에 넣어도 되고 3.model에서 넣어도 된다
		request.setAttribute("errors",errors);
		
		// 필수입력(유효성검사)
		joinReq.validate(errors);//생략가능
		
		
		//에러가 있으면 회원가입페이지로 이동
		if(!errors.isEmpty()) {
			return request.getContextPath()+FORM_VIEW;
		}
		//2.비즈니스로직처리하기<->Service<->DAO<->DB
		try {
			MemberService memberService = new MemberService();
			memberService.join(joinReq);
			
			//3.Model
			//세션
			//request개체명.setAttribute("속성명",Object값);
			
			//view
			return request.getContextPath()+"/chall/list.do";
			
		}catch(IdDuplicationException e) {
			errors.put("duplicatedId",Boolean.TRUE);//아이디중복예외
			return request.getContextPath()+FORM_VIEW;
		}catch(NicknameDuplicationException e) {
			errors.put("duplicatedNickname",Boolean.TRUE);//아이디중복예외
			return request.getContextPath()+FORM_VIEW;
		}catch(EmailDuplicationException e) {
			errors.put("duplicatedEmail",Boolean.TRUE);//아이디중복예외
			return request.getContextPath()+FORM_VIEW;
		}
	}

}


