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
import qnaboard.model.Qna_answer_boardDTO;
import qnaboard.model.Qna_boardDTO;
import qnaboard.service.Qna_boardService;

public class QnaAnModifypage implements CommandController{
	
	private Qna_boardService qna_boardService = new Qna_boardService();
	private Qna_answerService qna_answerService = new Qna_answerService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("get")) {
			return processForm(request,response);
		} else if(request.getMethod().equalsIgnoreCase("post")) {
			return processSubmit(request,response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
		
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
	// 세션가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = user.getId();	//id값을 가져옴
		session.setAttribute("id", id);
		
		//	문의사항 번호를 가져온다.
		int qna_no = Integer.parseInt(request.getParameter("qna_no"));
		session.setAttribute("qna_no",qna_no);
		
			Qna_boardDTO qna_boardDTO = qna_boardService.qnaDetail(qna_no);	
			String qna_a_con = request.getParameter("qna_a_con");
			Qna_answer_boardDTO qna_answer_boardDTO = qna_answerService.qna_answerDetail(qna_no);
			
			request.setAttribute("qna_a_con", qna_a_con);
			request.setAttribute("qna_no", qna_no);
			request.setAttribute("an", qna_answer_boardDTO);
			request.setAttribute("n",qna_boardDTO);
			session.setAttribute("qna_no",qna_no);
			return request.getContextPath() + "/view/qna/qna_answerModify.jsp";
	}

	protected String processSubmit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return null;
	}
	
}
