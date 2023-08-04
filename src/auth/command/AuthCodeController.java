package auth.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.SearchIdPwdService;
import mvc.command.CommandController;

public class AuthCodeController implements CommandController {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AuthController진입");
		SearchIdPwdService searchIdPwdService = new SearchIdPwdService();
		PrintWriter writer = response.getWriter();
		//파라미터 받기
		String authCode=request.getParameter("authCode");
		String inputCode=request.getParameter("inputCode");
		System.out.println(authCode);
		System.out.println(inputCode);
		//비지니스로직처리
		boolean result = searchIdPwdService.authCodeCheck(authCode,inputCode);
		//모델
		//뷰
		if(result==true) {
			writer.print("authCodeMatch");
		}else {
			writer.print("authCodeNotMatch");
		}
		return null;
	}

}
