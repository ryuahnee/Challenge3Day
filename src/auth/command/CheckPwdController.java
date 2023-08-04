package auth.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.ResetPwdService;
import mvc.command.CommandController;

public class CheckPwdController implements CommandController {

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
			
    //화면보여주기
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		int mem_no=Integer.parseInt(request.getParameter("memNo"));
		request.setAttribute("mem_no", mem_no);
		return request.getContextPath()+"/view/member/checkPwd.jsp";
	}	
			
	public String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		ResetPwdService resetPwdService = new ResetPwdService();
		String pwd=request.getParameter("pwd");
		int mem_no=Integer.parseInt(request.getParameter("memNo"));
		System.out.println(pwd);
		System.out.println(mem_no);
		String result=resetPwdService.checkPwd(pwd, mem_no);
		System.out.println(result);
		writer.print(result);
		
		return null;
	}

}
