package qnaboard.command;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import qnaboard.model.Qna_boardDTO;
import qnaboard.service.QnaBoardNotFoundException;
import qnaboard.service.Qna_boardService;

//고객문의사항 삭제 페이지 
public class QnaDeleteController implements CommandController{
	
	private Qna_boardService qna_boardService = new Qna_boardService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("QnaDeleteController의 process() 진입");
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
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
	
	protected String processSubmit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("QnaDelete의 post진입");
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html;charset=UTF-8");
		int result = 0;
		
		//세션아이디 저장
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = (String)session.getAttribute("id");//id값을 가져옴
		System.out.println("id:"+id);
		
		try {
			int qna_no = Integer.parseInt(request.getParameter("qna_no"));
			String qna_fin_check = request.getParameter("qna_fin_check");	//답변여부
			String qna_orifile_name = request.getParameter("qna_orifile_name");	
			System.out.println("qna_orifile_name:"+ qna_orifile_name);
				
			//	파일삭제 
				File delFile = new File(request.getServletContext().getRealPath("/upload/qna/")+qna_orifile_name);
				System.out.println("delFileexists :" + delFile.exists());
				System.out.println("delFile:"+delFile);
				
			//	DB에서 삭제
			result = qna_boardService.qnadelete(qna_no,qna_fin_check);
			System.out.println("result:"+result);
			
			//	db에서 삭제 후  파일을 삭제 
			if(result == 1) {
				delFile.delete();
			}
			
			// 로그인 정보를 request에 저장
			request.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/qna/List.do");
			return null;
		} catch (QnaBoardNotFoundException e) {
			return null;
		}
	}


}
