package chall.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import chall.model.Chall;
import chall.service.ChallPage;
import chall.service.ChallService;
import mvc.command.CommandController;

public class ListChallController implements CommandController {

	private String FORM_VIEW = "/view/chall/listChall.jsp"; 
	private ChallService challService = new ChallService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("listCallController-process()진입");
		
		//로그인 정보 가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = (String)session.getAttribute("id");
		
		//1.파라미터받기
		String strPageNo = request.getParameter("pageNo"); //user가 선택한 페이지번호
		int pageNo = 1; //user가 선택안했을 때 기본적으로 보여지는 페이지번호
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
		}
		
		String option = request.getParameter("option");
		
		String paparamSearchCon = request.getParameter("searchCon");
		String searchCon = null;
		if(paparamSearchCon!=null) {
			searchCon = paparamSearchCon;
		}
		
		//2.비즈니스로직<->Service<->DAO<->DB
		ChallPage challPage = challService.getChallPage(option,searchCon,pageNo);
	
		//3.Model
		request.setAttribute("challPage", challPage);
		request.setAttribute("nowPage", pageNo); //현재페이지
		request.setAttribute("option", option);
		request.setAttribute("searchCon", searchCon);
		
		//4.View
		return request.getContextPath()+FORM_VIEW;
	}

}