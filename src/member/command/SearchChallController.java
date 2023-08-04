package member.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import member.service.MemberChallStatusService;
import mvc.command.CommandController;

public class SearchChallController implements CommandController {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberChallStatusService memberChallStatusService = new MemberChallStatusService();
		
		//파라미터
		int chall_no = Integer.parseInt(request.getParameter("chall_no"));
		//로직
		String chall_name = memberChallStatusService.getChallName(chall_no);
		
		//모델앤뷰
	    response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
	    
	    JSONObject jasonObj= new JSONObject();
	    jasonObj.put("chall_name",chall_name);
	    String jsonResult = jasonObj.toJSONString();
		out.print(chall_name);
		return null;
	}

}
