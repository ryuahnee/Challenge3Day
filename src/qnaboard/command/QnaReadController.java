package qnaboard.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import qnaboard.model.Qna_boardDTO;
import qnaboard.service.QnaBoardNotFoundException;
import qnaboard.service.Qna_boardService;

public class QnaReadController implements CommandController{
	
	private Qna_boardService qna_boardService = new Qna_boardService();
	

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("get")) {
			return processForm(request,response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		
	// 세션가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = user.getId();					//id값을 가져옴
		session.setAttribute("id", id);
				
			String qna_a_con = request.getParameter("qna_a_con");
			
			String field_ = request.getParameter("f");
			String query_ = request.getParameter("q");
			String p = request.getParameter("p");	
			
			String field = "qna_title";
			if(field_ != null && !field_.equals("")) {
				field = field_;
			}
			
			String query = "";
			if (query_ != null && !query_.equals("")) {
			    query = query_;
			}

			int page = 1;		//현재 페이지 
			if(p != null && !p.equals("")) {
				page =Integer.parseInt(p);
			}
			
			//페이징처리
			int lastpage = qna_boardService.lastpage(id);
			request.setAttribute("lastpage", lastpage);	//마지막페이지
			
			
		try {
			
			List<Qna_boardDTO> list = qna_boardService.qnaRead(field, query, id,page);
			request.setAttribute("id", id);
			request.setAttribute("list", list);
			request.setAttribute("field", field);
			request.setAttribute("query", query);
			request.setAttribute("p", page);
			// 로그인 정보를 request에 저장
			request.setAttribute("user", user);
			return request.getContextPath() +"/view/qna/qna_list.jsp";
		} catch (QnaBoardNotFoundException e) {
			return null;
		}
		
	}


}
