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

public class ChallengeopinionController implements CommandController{
	
	private static final String FORM_VIEW = "/view/member/tmp.jsp";
	private MypageService mypageService = new MypageService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MypageService의 process() 진입");
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
		System.out.println("ChallengeopinionController컨트롤러 입장");
		
		// 세션에 id값 저장 
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = (String)session.getAttribute("id");//id값을 가져옴
				
		if (user == null) {
			 return request.getContextPath() + FORM_VIEW;
			}
		
		//정보를 가져옴  
		String chall_result = request.getParameter("chall_result");
		System.out.println("chall_result : " +chall_result);
		int chall_no = Integer.parseInt(request.getParameter("chall_no"));
		System.out.println("chall_no : " +chall_no);
		String opin_con = request.getParameter("opin_con");
		System.out.println("opin_con : " +opin_con);
		int cate_no = Integer.parseInt(request.getParameter("cate_no"));
		System.out.println("cate_no : " +cate_no);
		
		try {
			mypageService.challengeopinionInsert(id, chall_no, opin_con, cate_no);
		
			request.setAttribute("chall_result", chall_result);
			
			//챌린지의 상세페이지로 이동한다.
			return request.getContextPath() + "/myPage/list.do?congratulations=true";
		} catch (QnaBoardNotFoundException e) {
			return null;
		}
} 
	
	protected String processSubmit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return null;
	}

	
}//class
