package qnaboard.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import auth.service.User;
import mvc.command.CommandController;
import qnaAnswerboard.service.Qna_answerService;
import qnaboard.model.Qna_answer_boardDTO;
import qnaboard.model.Qna_boardDTO;
import qnaboard.service.QnaBoardNotFoundException;
import qnaboard.service.Qna_boardService;

public class QnaTestModifyController2 implements CommandController{

	private Qna_boardService qna_boardService = new Qna_boardService();//추가함
	private Qna_answerService qna_answerService = new Qna_answerService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("QnaModifyController의 process() 진입");
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
	// 수정하기 창을 보여줌 
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Modify의 get");
		Qna_boardDTO qna_boardDTO;
		
		//	로그인값 세션으로 가져옴
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = user.getId();	//id값을 가져옴
		session.setAttribute("id", id);
		
		//	문의사항 번호를 가져온다.
		int qna_no = Integer.parseInt(request.getParameter("qna_no"));
		session.setAttribute("qna_no",qna_no);
		request.setAttribute("qna_no",qna_no);
		
		qna_boardDTO = qna_boardService.qnaDetail(qna_no);
		request.setAttribute("n",qna_boardDTO);
		
		//추가함
		Qna_answer_boardDTO qna_answer_boardDTO = qna_answerService.qna_answerDetail(qna_no);
		request.setAttribute("an", qna_answer_boardDTO);
		
		return  request.getContextPath() + "/view/qna/qna_Modify.jsp";
	}
	
	protected String processSubmit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Qna_boardDTO qna_boardDTO;
		System.out.println("Modify의 post");
		
		// 세션가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = user.getId();					//id값을 가져옴
		session.setAttribute("id", id);
		
//		고객이 수정한 값 (수정시 qna_orifile_name값이 없는 경우 null로 저장)
		String qna_title = request.getParameter("qna_title");
		String qna_con = request.getParameter("qna_con");
		System.out.println("qna_title: " +qna_title);
		System.out.println("qna_con: " +qna_con);
		
		//기존파일
		String qna_orifile_name = request.getParameter("qna_orifile_name");
		System.out.println("qna_orifile_name : " + qna_orifile_name);
		
		
//		글번호
		int qna_no = Integer.parseInt(request.getParameter("qna_no"));
		System.out.println("qna_no:"+qna_no);
		
//		페이징처리
		String page_ = request.getParameter("p");	
		if(page_ == null) {
			page_="1";
		}
		int page = Integer.parseInt(page_);		//현재 페이지 
		request.setAttribute("p", page);
		
		try {
			
			
			qna_boardService.qnaModify(qna_title, qna_con, id, qna_no);
			
			qna_boardDTO = qna_boardService.qnaDetail(qna_no);	
			
			request.setAttribute("n",qna_boardDTO);
			request.setAttribute("qna_no", qna_no);
			
			String deletefile = request.getParameter("deletefile");
			System.out.println("deletefile : " +deletefile);
			
			// 기존 파일 삭제 버튼을 누른경우 기존 파일을 삭제한다. (새로 업로드한 파일은 없음)
			/*
			 * if(deletefileBtn.equals("a") && qna_orifile_name !=null){ File delFile = new
			 * File(request.getServletContext().getRealPath("/upload/qna") +
			 * qna_orifile_name); }
			 */
		    
			response.sendRedirect(request.getContextPath() + "/qna/List.do");
			return null;
		}catch (QnaBoardNotFoundException e) {
			return null;
		}
	}

	
}
