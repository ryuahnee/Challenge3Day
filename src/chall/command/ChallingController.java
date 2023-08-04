package chall.command;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import chall.model.Chall;
import chall.service.ChallNotFoundException;
import chall.service.ChallPage;
import chall.service.ChallService;
import mvc.command.CommandController;

public class ChallingController implements CommandController {

	private static final String FORM_VIEW =  "/chall/list.do"; //마이페이지로 수정하기
	private ChallService challService = new ChallService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ChallingController process진입");
		//로그인한 세션값 가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = (String)session.getAttribute("id");

		//1.파라미터받기
		int chall_no = Integer.parseInt(request.getParameter("chall_no")); 
		int mem_no = user.getMem_no();
		System.out.println("CategoryChallController- process() chall_no="+chall_no);
		System.out.println("CategoryChallController- process() mem_no="+mem_no);
		
		int history = challService.confirmChallHistory(chall_no, mem_no);
		if(history==1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			String msg = "<script>alert('이미 도전중인 챌린지입니다.');"
					+"location.href="+request.getContextPath()+"'/chall/list.do'</script>";
			writer.print(msg);
			return null;
		} else {
			try {
				
				Chall chall = challService.getDetail(chall_no);  
				int challing  = challService.challing(chall_no, mem_no);
				
				//3.Model
				request.setAttribute("user",user);
				request.setAttribute("challing",challing);
				request.setAttribute("chall", chall);
				
				//4.View
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = response.getWriter();
				String msg = "<script>alert('챌린지 도전이 시작되었습니다!');"
						+"location.href="+request.getContextPath()+"'/chall/list.do'</script>";
				writer.print(msg);
				return null;
			}catch(ChallNotFoundException e) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
		}
		
		
	}

}
