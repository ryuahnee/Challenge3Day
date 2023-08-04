package qnaboard.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
import qnaboard.service.QnaBoardNotFoundException;
import qnaboard.service.Qna_boardService;

@MultipartConfig(
fileSizeThreshold = 1024 * 1024,  	//	=>1메가 바이트가 넘으면 location사용 	
maxFileSize = 1024*1024*50,			// 	=> 50메가 바이트 , 클라이언트가 보내는 파일 하나당 사이즈 지정 
maxRequestSize = 1024*1024*50*5		//	=> 파일을 여러개 보낼때 최대 사이즈 50메가 / 50메가*5개 파일 까지 가능 
)

public class QnaDetailController implements CommandController{
	
	private Qna_boardService qna_boardService = new Qna_boardService();
	private Qna_answerService qna_answerService = new Qna_answerService();
	
	private static final String FORM_VIEW = "/view/qna/qna_list.jsp";	//리스트로 돌아감

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
	
	protected String processSubmit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Content-Type", "image/png");
		// 세션가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = user.getId();	//id값을 가져옴
		session.setAttribute("id", id);
		int qna_no = Integer.parseInt(request.getParameter("qna_no"));
		request.setAttribute("qna_no", qna_no);
		
		String p = request.getParameter("p");	
		if(p == null) {
			p="1";
		}
		int page = Integer.parseInt(p);		//현재 페이지 
		request.setAttribute("p", page);
		
		try {
			Qna_boardDTO qna_boardDTO = qna_boardService.qnaDetail(qna_no);	
			Qna_answer_boardDTO qna_answer_boardDTO = qna_answerService.qna_answerDetail(qna_no);
			
			///qnaAnModifyCon.do에서 받아온값 저장 
			String qna_a_con = request.getParameter("qna_a_con");
			request.setAttribute("qna_a_con", qna_a_con);
			request.setAttribute("an", qna_answer_boardDTO);			
			request.setAttribute("n",qna_boardDTO);
			
			session.setAttribute("qna_no",qna_no);
		
			return request.getContextPath() +"/view/qna/qna_Detail.jsp";
		} catch (QnaBoardNotFoundException e) {
			return  request.getContextPath() + FORM_VIEW;	
		}
	}
	
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("QnaDetailController의 doget");
		// 세션가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = user.getId();	//id값을 가져옴
		session.setAttribute("id", id);
		
		int qna_no = Integer.parseInt(request.getParameter("qna_no"));
		request.setAttribute("qna_no", qna_no);
		
		String p = request.getParameter("p");	
		if(p == null) {
			p="1";
		}
		int page = Integer.parseInt(p);		//현재 페이지 
		request.setAttribute("p", page);
		
		try {
			Qna_boardDTO qna_boardDTO = qna_boardService.qnaDetail(qna_no);	
			Qna_answer_boardDTO qna_answer_boardDTO = qna_answerService.qna_answerDetail(qna_no);
			
			///qnaAnModifyCon.do에서 받아온값 저장 
			String qna_a_con = request.getParameter("qna_a_con");
			request.setAttribute("qna_a_con", qna_a_con);
			request.setAttribute("an", qna_answer_boardDTO);			
			request.setAttribute("n",qna_boardDTO);
			// 로그인 정보를 request에 저장
			request.setAttribute("user", user);
			session.setAttribute("qna_no",qna_no);
			return  request.getContextPath() + "/view/qna/qna_Detail.jsp";
		} catch (QnaBoardNotFoundException e) {
			return  request.getContextPath() + FORM_VIEW;	
		}
	}
	
	

}
