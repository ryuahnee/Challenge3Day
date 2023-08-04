package qnaAnswerboard.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import qnaAnswerboard.service.Qna_answerService;
import qnaboard.service.QnaBoardNotFoundException;

public class QnaAnDeleteController implements CommandController{

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
	
	
	//post방식
	protected String processSubmit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("answerService진입");
	// 세션가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = user.getId();	//id값을 가져옴
		session.setAttribute("id", id);	
		
		int qna_no = Integer.parseInt(request.getParameter("qna_no"));
		
		try {
			qna_answerService.qna_answerDelete(qna_no);
			request.setAttribute("qna_no", qna_no);
			System.out.println("qna_no"+qna_no);
			
			request.setCharacterEncoding("UTF-8");
			return request.getContextPath() + "/qna/Detail.do";
		} catch (QnaBoardNotFoundException e) {
			return null;
		}
		
	}

}
	
