package chall.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import chall.service.ChallNotFoundException;
import chall.service.ChallPage;
import chall.service.ChallService;
import mvc.command.CommandController;

public class CategoryChallController implements CommandController {

	private static final String FORM_VIEW = "/view/chall/cateChall.jsp";
	private ChallService challService = new ChallService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인한 세션값 가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = (String)session.getAttribute("id");

		//1.파라미터받기
		int cate_no = Integer.parseInt(request.getParameter("cate_no")); //보고싶은 카테고리 번호 int로 변환
		System.out.println("CategoryChallController-process() cate_no="+cate_no);
		
		String strPageNo = request.getParameter("pageNo"); //user가 선택한 페이지번호
		int pageNo = 1; //user가 선택안했을 때 기본적으로 보여지는 페이지번호
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
			System.out.println("pageNo="+pageNo);
		}
		
		String option = request.getParameter("option");
		
		String paparamSearchCon = request.getParameter("searchCon");
		String searchCon = null;
		if(paparamSearchCon!=null) {
			searchCon = paparamSearchCon;
		}
		
		//2.비즈니스로직<->Service<->DAO<->DB
		try {
			ChallPage challPage = challService.getCateChallPage(cate_no,option,searchCon,pageNo);
			ChallPage chall = challService.getCate(cate_no,option,searchCon,pageNo);
			
			
			//3.Model
			request.setAttribute("user",user);
			request.setAttribute("chall", chall);
			request.setAttribute("challPage", challPage);
			request.setAttribute("nowPage", pageNo); //현재페이지
			request.setAttribute("option", option);
			request.setAttribute("searchCon", searchCon);
			request.setAttribute("cate_no", cate_no);
			
			//4.View
			return request.getContextPath()+FORM_VIEW;
		}catch(ChallNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		
	}

}
