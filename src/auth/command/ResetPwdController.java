package auth.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.ResetPwdService;
import member.exception.MemberNotFoundException;
import member.exception.PermissionDeniedException;
import member.model.Auth;
import mvc.command.CommandController;

public class ResetPwdController implements CommandController {

	//필드
	private static final String FORM_VIEW ="/view/member/resetPwd.jsp";
	private ResetPwdService resetPwdService = new ResetPwdService();

	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ResetPwdController의 process()진입");
		
		if( request.getMethod().equalsIgnoreCase("GET") ) {
			return processForm(request,response);
		}else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request,response); 
		}else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}//process

	//비번변경폼을 보여주기-p623 32라인
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ResetPwdController의 processForm()진입");
		return request.getContextPath()+FORM_VIEW;
	}
	
	//비번변경처리-p623 37라인
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.파라미터받기
		String newPwd = request.getParameter("newPwd");
		String pwdConfirm = request.getParameter("pwdConfirm");
		Auth auth = (Auth)request.getSession().getAttribute("IS_AUTH");
		
		//에러정보가 담기는 Map- p623 39라인
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		request.setAttribute("errors",errors); //Model

		//필수입력(유효성)검사- p623 47라인
		//에러가 있다면
		//Map참조변수 errors에  문자열타입으로 key로, True를 값으로 저장 
		if(newPwd==null || newPwd.isEmpty()) {
			errors.put( "newPwd", Boolean.TRUE );
		}
		if(!newPwd.equals(pwdConfirm)) {
			errors.put( "pwdConfirm", Boolean.TRUE );
		}
		
		//p623 53라인
		if(!errors.isEmpty()) { //에러가 있으면  비번변경폼페이지로 이동
			return request.getContextPath()+FORM_VIEW;
		}
		
		//p623 57라인
		try {
			if(auth==null || !auth.getIsAuth().equals("true")) {
				throw new PermissionDeniedException();
			}
			int rowCnt = resetPwdService.changePwd(auth.getId(),newPwd);
			System.out.println("ChangePwdHandler-비번변경 실행결과="+rowCnt);//콘솔 확인용
			//1이면 수정성공, 0이면 수정실패
			
			//3.Model
			request.setAttribute("rowCnt", rowCnt);
			
			//4.View- 성공시:imsi.jsp이동 
			response.sendRedirect(request.getContextPath()+"/reLogin.do");
		}catch(MemberNotFoundException e) {//암호를 변경할 회원 데이터가 존재하지 않는 경우
			response.sendError(HttpServletResponse.SC_BAD_REQUEST); //400
			return null;
		}catch(PermissionDeniedException e) {//현재 암호가 일치하지 않는 경우
			response.sendError(HttpServletResponse.SC_FORBIDDEN); //400
			return null;
		}
		return null;
	}
}
