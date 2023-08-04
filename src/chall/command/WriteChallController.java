package chall.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import chall.service.ChallService;
import mvc.command.CommandController;


public class WriteChallController  implements CommandController{
	
	//필드
		private static final String FORM_VIEW ="/view/chall/writeChall.jsp";
		private ChallService challService = new ChallService();
		
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
		
		//글등록폼 보여주기
				private String processForm(HttpServletRequest request, HttpServletResponse response) {
					HttpSession session = request.getSession();
					User user = (User)session.getAttribute("AUTH_USER");
					session.setAttribute("AUTH_USER", user);
					return request.getContextPath()+FORM_VIEW;
				}
				
		//글등록처리
		private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("AUTH_USER");
			String id = user.getId();					//id값을 가져옴
			session.setAttribute("id", id);
			
			int cate_no = Integer.parseInt(request.getParameter("cate_no"));
			String chall_title = request.getParameter("chall_title");
			String chall_con = request.getParameter("chall_con");
			String certifi_words = request.getParameter("certifi_words");
			
			challService.writeChall(cate_no,chall_title,chall_con,certifi_words,id);
			return request.getContextPath()+"/chall/list.do";
			
	
		}

				
}
