package qnaAnswerboard.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import qnaAnswerboard.service.Qna_answerService;
import qnaboard.service.QnaBoardNotFoundException;

public class QnaAnWriteController implements CommandController{

	private Qna_answerService qna_answerService = new Qna_answerService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if(request.getMethod().equalsIgnoreCase("post")) {
			return processSubmit(request,response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	protected String processSubmit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//세션아이디 저장
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = (String)session.getAttribute("id");//id값을 가져옴

		// 페이지값 저장
		int qna_no = Integer.parseInt(request.getParameter("qna_no"));
		request.setAttribute("qna_no", qna_no);
		
		String mem_email = request.getParameter("mem_email");
		String userid = request.getParameter("userid");
		System.out.println("mem_email:"+mem_email);
		System.out.println("userid:"+userid);
		try {
		//id는 관리자인지 검사용으로 저장한다.
			String qna_a_con = request.getParameter("qna_a_con");
			qna_answerService.qna_answerWrite(qna_no, qna_a_con, id, mem_email, userid);
		
			return request.getContextPath() + "/qna/Detail.do";
		} catch (QnaBoardNotFoundException e) {
			return null;
		}
	}

	
}
	
