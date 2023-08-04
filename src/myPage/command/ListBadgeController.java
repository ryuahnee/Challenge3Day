package myPage.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import mvc.command.CommandController;
import myPage.model.MemberBadge;
import myPage.service.BadgeService;

// 뱃지 리스트 조회 요청을 담당하는 컨트롤러

public class ListBadgeController implements CommandController {
	private String LIST_VIEW= "/view/myPage/myBadgeList.jsp";
	
	private BadgeService badgeService = new BadgeService();
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1. 파라미터 받기
		//세션에서 로그인한 회원의 번호를 가져오기
		User user = (User) request.getSession().getAttribute("AUTH_USER");
		System.out.println("user="+user);
		int memNo = user.getMem_no();
		
		//2. 비즈니스 로직 처리
		List<MemberBadge> memberBdgList = badgeService.selectBadge(memNo);
		List<MemberBadge> monoBadgeList = badgeService.selectAllBadge();
		
//		Map map = new HashMap();
//		String img = "";
//		map.put(memberBdgList.get(0), );
		
		//3. 모델
		request.setAttribute("memberBdgList", memberBdgList); //회원이 획득한 뱃지 정보
		request.setAttribute("monoBadgeList", monoBadgeList); //기본으로 뿌려지는 뱃지 정보
		
		System.out.println("ListBadgeController를 통한 memberBdgList="+memberBdgList);
		System.out.println("ListBadgeController를 통한 monoBadgeList="+monoBadgeList);
	
		//4. 뷰
		return request.getContextPath()+LIST_VIEW;
	}

}
