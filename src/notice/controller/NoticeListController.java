package notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import notice.model.Notice;
import notice.service.ListNoticeService;
import notice.service.NoticePage;

public class NoticeListController implements CommandController{
	
	private String FORM_VIEW = "/view/notice/noticeList.jsp";
	private ListNoticeService listNoticeService = new ListNoticeService();

	
	private int size=15;
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("NoticeListController-process()진입");
		
		// 로그인 정보 가져오기
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("AUTH_USER");
			
		//1.파라미터받기
		String strPageNo = request.getParameter("pageNo"); //user가 선택한 페이지번호
		int pageNo = 1; //user가 선택안했을 때 기본적으로 보여지는 페이지번호
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
		}
		
		//1-2. 검색을 위한 파라미터받기
		String option = request.getParameter("searchField");
		String searchNoti = request.getParameter("searchText");
		String searchText = null;
		
		 if (searchNoti != null && !searchNoti.isEmpty()) { // 검색입력값이 있으면
	            searchText = searchNoti;
	        }
		
		
		//2. 비즈니스 로직 처리 
		//총 게시글 수, 목록, 페이징 처리 정보 담는다
		/*파라미터  int pageNo : 보고싶은 페이지(=>현재 페이지)*/
		 List<Notice> noticeList;
	      NoticePage noticePage;
	      
		 if (searchText != null) {
	            // 새로운 검색 기능 사용
	            noticePage = listNoticeService.getSearchNotices(option, searchText, pageNo, size);
	            noticeList = noticePage.getContent();
	        } else {
	            // 기존 목록 조회 기능 사용 (페이징)
	            noticePage = listNoticeService.getNoticePage(pageNo);
	            noticeList = noticePage.getContent();
	        }
		//3. model : 요청한 값 받아서 저장 이동 전 요청사항 내용 담기
		//NoticePage 에  페이지 정보 넘겨줌.
		request.setAttribute("noticeList", noticeList);
		request.setAttribute("noticePage", noticePage); // notice 목록, 총페이지 수 등 전달
		request.setAttribute("nowPage", pageNo); //현재페이지
		// 로그인 정보를 request에 저장
		request.setAttribute("user", user);

		
		
		
		//4.VIEW	 
		return request.getContextPath()+FORM_VIEW;
	
	
	}


}
