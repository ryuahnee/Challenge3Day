package myPage.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import myPage.service.MypageService;
import qnaboard.service.QnaBoardNotFoundException;

public class MypageStateUpdateController implements CommandController{

	private MypageService mypageService = new MypageService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MypageStateUpdateController의 process() 진입");
		System.out.println("request.getMethod()"+request.getMethod());
		
		if(request.getMethod().equalsIgnoreCase("get")) {
			return processForm(request,response);
		} else if(request.getMethod().equalsIgnoreCase("post")) {
			return processSubmit(request,response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("processForm컨트롤러 입장");
		//세션아이디 저장
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("AUTH_USER");
			String id = (String)session.getAttribute("id");//id값을 가져옴
		//정보를 가져옴 
			String chall_result = request.getParameter("chall_result");
			System.out.println("chall_result : " +chall_result);
			int chall_no = Integer.parseInt(request.getParameter("chall_no"));
			System.out.println("chall_no : " +chall_no);
				
				try {
					
					mypageService.challstatusUpdate(id, chall_no, chall_result);
				
					request.setAttribute("chall_result", chall_result);
					
					return request.getContextPath() + "/myPage/list.do";
				} catch (QnaBoardNotFoundException e) {
					return null;
				}
		
	} 
	
	//post
	protected String processSubmit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//세션아이디 저장
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = (String)session.getAttribute("id");//id값을 가져옴
		
		//정보를 가져옴 
		String chall_result = request.getParameter("chall_result");
		System.out.println("chall_result : " +chall_result);
		int chall_no = Integer.parseInt(request.getParameter("chall_no"));
		System.out.println("chall_no : " +chall_no);

		try {
			mypageService.challstatusUpdate(id, chall_no, chall_result);
		
			request.setAttribute("chall_result", chall_result);
			
			return request.getContextPath() + "/myPage/list.do";
		} catch (QnaBoardNotFoundException e) {
			return null;
		}
	}

}
