package myPage.command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import myPage.model.MemberBadge;
import myPage.model.MypageDTO;
import myPage.service.BadgeService;
import myPage.service.MypageNotFoundException;
import myPage.service.MypageService;

public class MypageStateListController implements CommandController{
	
	private static final String FORM_VIEW = "/view/member/tmp.jsp";
	private MypageService mypageService = new MypageService();
	private BadgeService badgeService = new BadgeService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MypageStateListController의 process() 진입");
		System.out.println("request.getMethod()"+request.getMethod());
		
		HttpSession session = request.getSession(); // 세션에 id값 저장 
		User user = (User)session.getAttribute("AUTH_USER");
		System.out.println("user="+user);
		
		if (user == null) {
		        return request.getContextPath() + FORM_VIEW;
		 }
		
		int memNo = user.getMem_no();
		String nickname = user.getNickname(); //Nickname값을 가져옴
		session.setAttribute("nickname", nickname);
		String id = user.getId();	//id값을 가져옴
		session.setAttribute("id", id);
		
		try {
			String chall_result = request.getParameter("chall_result");
			
			System.out.println("chall_result:"+chall_result);
			
			if(chall_result == null || chall_result.equals("")) {
				chall_result ="I";
			}
			List<MemberBadge> memberBdgList = badgeService.selectBadge(memNo);
			List<MemberBadge> monoBadgeList = badgeService.selectAllBadge();
			
			//서비스 호출
			List<MypageDTO> list = mypageService.mychallList(id, chall_result);
			request.setAttribute("list", list);
			request.setAttribute("chall_result", chall_result);
			request.setAttribute("memberBdgList", memberBdgList); //회원이 획득한 뱃지 정보
			request.setAttribute("monoBadgeList", monoBadgeList); //기본으로 뿌려지는 뱃지 정보
			
			if(chall_result.equals("I")) {
				return request.getContextPath() + "/view/myPage/Mypage_challList_i.jsp";	
			}else if(chall_result.equals("F")) {
				return request.getContextPath() + "/view/myPage/Mypage_challList_f.jsp";	
			}else if(chall_result.equals("my")) {
				return request.getContextPath() + "/view/myPage/Mypage_challList_my.jsp";
			}else {
				return request.getContextPath()+"main.jsp";
			}
			
			//return null;
		} catch (MypageNotFoundException e) {
			return request.getContextPath() + FORM_VIEW;	//임시페이지로 이동
		}
		
	}
	
}//class
