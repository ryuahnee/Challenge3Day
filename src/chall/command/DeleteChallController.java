package chall.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chall.service.ChallService;
import mvc.command.CommandController;

public class DeleteChallController implements CommandController {

	private ChallService challService = new ChallService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.파라미터받기
		int no = Integer.parseInt(request.getParameter("no"));//삭제할 글 번호
		
		//2.비지니스로직<->Service<->DAO<->DB
		challService.delete(no);
		
		//3.Model
		//4.View
		response.sendRedirect(request.getContextPath()+"/chall/list.do");
		return null;
	}

}
