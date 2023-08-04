package auth.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.LoginFailException;
import auth.service.LoginService;
import auth.service.User;
import mvc.command.CommandController;

//로그인 폼 화면을 보여주는 담당 컨트롤러
//모든 핸들(담당 컨트롤러)러는 반드시 interface CommandHandler를 구현해야한다
//요청주소 http://localhost/login.do
//성공하면 메인페이지로 이동 + 세션에 회원 정보 저장
//실패하면 다시 로그인폼
public class LoginController implements CommandController{
	//필드 접근제한자 속성 타입 필드명[=초기값];
	private static final String FORM_VIEW = "/view/member/loginForm.jsp";
	private LoginService loginService = new LoginService();
	
	//담당컨트롤러 요청 메소드
	//리턴유형 String: client에게 보여주는 jsp문서의 경로와 파일명
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("LoginController의 process() 진입");
		System.out.println("request.getMethod()"+request.getMethod());
		
		if(request.getMethod().equalsIgnoreCase("get")) {//요청방식이 get방식이면 FORM_VIEW보여주기
			return processForm(request,response);
		} else if(request.getMethod().equalsIgnoreCase("post")) {//요청방식이 post방식이면 회원가입처리
			return processSubmit(request,response);//p598 24line
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); //405허용되지 않은 메소드 <- 사용자 측 문제
			return null;
		}
	}//process

	//로그인 폼 보여주기 p607 32라인
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return request.getContextPath()+FORM_VIEW;
	} 
	
	//로그인 처리 p607 36라인
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//1. 파라미터받기
		String id = request.getParameter("id");
		String pwd = trim(request.getParameter("pwd"));
		
		//에러정보가 담기는 map
		Map<String,Boolean>errors = new HashMap<>();//생략가능
		request.setAttribute("errors",errors);
		
		// 필수입력(유효성검사)
		if(id==null||id.isEmpty()) {
			errors.put("id",Boolean.TRUE);
		}
		if(pwd==null||pwd.isEmpty()) {
			errors.put("pwd",Boolean.TRUE);
		}
		if(!id.equals(pwd)) {
			
		}
		
		//에러가 있으면 회원가입페이지로 이동
		if(!errors.isEmpty()) {
			return request.getContextPath()+FORM_VIEW;
		}
		try {
			//2. 비지스로직 처리 <->Service<->DAO<->DB
			//파라미터(String id, String password) 유저가 입력한 아이디 비번
			//리턴 User-로그인에 성공한 회원정보(회원id,회원명)
			User user = loginService.login(id, pwd);
			//3. model-session.setAttribute("속성명", Object값); / request.sendRedirection
			HttpSession session = request.getSession();
			session.setAttribute("AUTH_USER",user);
			//4. view - 성공시: 챌린지 리스트, 실패시:loginForm.jsp
			return request.getContextPath() + "/chall/list.do";
		}catch(LoginFailException e) {
			errors.put("idOrPwNoMatch", Boolean.TRUE);
			return request.getContextPath()+FORM_VIEW;
		}
	}
	
	//좌우에 공백 제거 = p607 64line
	private String trim(String str) {
		return str==null?null:str.trim();
	}
}//class
