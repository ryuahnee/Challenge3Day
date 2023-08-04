package qnaboard.command;

import java.io.IOException;
import java.util.Date;
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

public class Qna_ListController implements CommandController{
	
	private static final String FORM_VIEW = "/view/member/tmp.jsp";
	private Qna_boardService qna_boardService = new Qna_boardService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Qna_ListControlle의 process() 진입");
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
		return mit(request, response);
	} 
	
	
	protected String processSubmit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return mit(request, response);
	}

	
	protected String mit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션에 id값 저장 
		System.out.println("Qna_ListControlle의 post() 진입");
		Date date = new Date();
		System.out.println(date.getTime());
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		
		 if (user == null) {
		        return request.getContextPath() + FORM_VIEW;
		    }
		 
		String id = user.getId();	//id값을 가져옴
		session.setAttribute("id", id);
		
		String qna_a_con = request.getParameter("qna_a_con");
		String field_ = request.getParameter("field");
		String query_ = request.getParameter("query");
		
		String field = "qna_title";
		if(field_ != null && field_.equals("")) {
			field = field_;
		}
		
		String query = "";
		if(query_ != null && query_.equals("")) {
			query = query_;
		}
	
		//페이징처리
		int lastpage = qna_boardService.lastpage(id);
		request.setAttribute("lastpage", lastpage);	//마지막페이지
		
		String p = request.getParameter("p");	
		System.out.println("String페이지:"+p);
		if(p == null || p.equals("0")) {
			p="1";
		}
		int page = Integer.parseInt(p);		//현재 페이지 
		System.out.println("페이지:"+page);
		
		
		try {
			List<Qna_boardDTO> list = qna_boardService.qnaList(id, page, field, query);
			request.setAttribute("qna_a_con", qna_a_con);
			request.setAttribute("list", list);
			request.setAttribute("p", page);
			request.setAttribute("field", field);
			request.setAttribute("query", query);
			// 로그인 정보를 request에 저장
			request.setAttribute("user", user);
			
			return request.getContextPath() + "/view/qna/qna_list.jsp";
		} catch (QnaBoardNotFoundException e) {
			return request.getContextPath() + FORM_VIEW;	//임시페이지로 이동
		}
		
	}
	
	
}//class
