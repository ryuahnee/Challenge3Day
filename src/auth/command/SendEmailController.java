package auth.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.SearchIdPwdService;
import member.model.Member;
import mvc.command.CommandController;

//이메일 보내주기
public class SendEmailController implements CommandController {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchIdPwdService searchIdPwdService = new SearchIdPwdService();
		PrintWriter writer = response.getWriter();
		
		//파라미터 받기
		String email = request.getParameter("email");
		String id = request.getParameter("id");
		Member member=null;
		
		if (id==null) {
			member = searchIdPwdService.selectByEmail(email);		
			if(member==null) {
				writer.print("EmailNotFound");
				return null;			
			}
		} else {
			member = searchIdPwdService.selectById(id);
			if(member==null) {
				writer.print("EmailNotFound");
				return null;			
			}
			email=member.getMem_email();
		}
		//난수발생
		String authCode = searchIdPwdService.authCodeMaker();
		//이메일 보내주기
		String subject="작심삼일 인증코드입니다";
		String body="작심삼일 인증코드 : "+authCode;
		searchIdPwdService.sendEmail(email,subject,body);
		writer.print(authCode);
		return null;			
	}
}