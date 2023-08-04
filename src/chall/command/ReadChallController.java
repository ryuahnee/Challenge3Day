package chall.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import chall.model.Chall;
import chall.model.ChallOpin;
import chall.service.ChallNotFoundException;
import chall.service.ChallService;
import mvc.command.CommandController;

//상세보기 요청 컨트롤러
public class ReadChallController  implements CommandController {
	
	//읽기 폼을 보여줄때 상세 내용을 가져오기 위한 Service
	private static final String FORM_VIEW = "/view/chall/readChall.jsp";
	private ChallService challService = new ChallService();
   

	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인한 세션값 가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");

		
		//1.파라미터받기
		int no = Integer.parseInt(request.getParameter("no"));
		System.out.println("ReadChallController- process() no="+no);
		
		//2.비즈니스로직<->Service<->DAO<->DB
		try {
			//파라미터 int no : 상세조회할 글 번호
			Chall chall = challService.getDetail(no);  
			List<ChallOpin> challopin = challService.getOpin(no);
			int isChall = challService.isChall(no);
			
			//3.Model
			request.setAttribute("user",user);
			request.setAttribute("chall", chall);
			request.setAttribute("challopin", challopin);
			request.setAttribute("isChall", isChall);
			
			//4.View
			return request.getContextPath()+FORM_VIEW;
		}catch(ChallNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
	}
	

}
