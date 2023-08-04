package chall.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chall.service.ChallPage;
import chall.service.ChallService;
import mvc.command.CommandController;

public class SearchChallController implements CommandController {

	private String Search_VIEW="/view/chall/listChall.jsp";
	private ChallService challService = new ChallService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1. 파라미터 받기
		String pageNoVal = request.getParameter("pageNo");
		int pageNo=1; //기본적으로는 1페이지가 보여짐. (특정 페이지 선택 전까지)
		if(pageNoVal!=null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		String option = request.getParameter("option"); //select - option: 제목, 내용
		String searchCon = request.getParameter("searchCon");
		
		
		//2. 비즈니스 로직 수행
		ChallPage challPage = challService.getChallPage(pageNo, option, searchCon);
		//3. 모델
		request.setAttribute("challPage", challPage);
		request.setAttribute("nowPage", pageNo);
		request.setAttribute("option", option);
		request.setAttribute("searchCon", searchCon);
		
		//4. 뷰
		return request.getContextPath()+Search_VIEW;
	}

}