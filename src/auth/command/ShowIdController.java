package auth.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import auth.service.SearchIdPwdService;
import member.model.Member;
import mvc.command.CommandController;

public class ShowIdController implements CommandController {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchIdPwdService searchIdPwdService = new SearchIdPwdService();
		//파라미터
		String mem_email = request.getParameter("email");//수정할 회원번호
		String auth = request.getParameter("auth");
		
		//비지니스 로직처리
		Member member = searchIdPwdService.selectByEmail(mem_email);
		if(!auth.equals("authCodeMatch")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		//수정을 위해 세션에서 가져온 회원정보와 디비정보
		Member memReq = new Member(member.getMem_no(), member.getId(), member.getMem_name(), member.getNickname(), member.getBirthyear(), member.getGender(), member.getJoin_date(), member.getMem_email(), member.getIsMarketing());
		//모델
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONObject totalObj = new JSONObject();
		JSONArray memberArr = new JSONArray();
		
		memberArr.add(member.getMem_email());
		memberArr.add(member.getId());
		
		totalObj.put("members", memberArr);
		
		String jsonStr = totalObj.toJSONString();
		out.print(jsonStr);
		
		
		return null;
	}

}
