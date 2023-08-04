package qnaAnswerboard.command;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import mvc.command.CommandController;
import qnaAnswerboard.service.Qna_answerService;
import qnaboard.dao.Qna_boardDAO;
import qnaboard.model.Qna_answer_boardDTO;
import qnaboard.model.Qna_boardDTO;
import qnaboard.service.QnaBoardNotFoundException;
import qnaboard.service.Qna_boardService;

public class QnaAnModifyController implements CommandController{

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
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html;charset=UTF-8");
		//세션아이디 저장
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = (String)session.getAttribute("id");//id값을 가져옴	
		
		//	페이지 값 
		int qna_no = Integer.parseInt(request.getParameter("qna_no"));
		request.setAttribute("qna_no", qna_no);
		
		try {
			String qna_a_con = request.getParameter("qna_a_con");
			qna_answerService.qna_answerModify(qna_a_con);
			request.setAttribute("qna_a_con", qna_a_con);
			
			return request.getContextPath() +"/qna/Detail.do";
		} catch (QnaBoardNotFoundException e) {
			return null;
		}
	}

	
	
	
}
