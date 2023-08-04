package notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import notice.model.Notice;
import notice.service.ListNoticeService;
import notice.service.NoticeNotFoundException;

//상세보기 요청 담당 컨트롤러

public class NoticeReadController implements CommandController{
	
	private String FORM_VIEW = "/view/notice/noticeDetail.jsp";
	private ListNoticeService listNoticeService = new ListNoticeService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.파라미터받기
		int notiNo= Integer.parseInt(request.getParameter("notiNo")); //공지 상세 번호 int로 변환
		System.out.println("NoticeReadController- process() no="+notiNo);
		
		// 로그인 정보 가져오기
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("AUTH_USER");
			
		//2.비즈니스로직<->Service<->DAO<->DB
		try {
			//파라미터 int notiNo : 상세조회할 글 번호
			//리턴  게시글 데이터:(서비스 생성) : 글번호,제목,작성일, 내용
			Notice notiData = listNoticeService.getDetail(notiNo);  
			
			//3.Model
			request.setAttribute("notice", notiData);
			request.setAttribute("user", user);

		//3. View : forward해서 list뷰단으로 이동,
			return request.getContextPath()+FORM_VIEW;
		
		}catch(NoticeNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);//404
			return null;
			}
		}
	

}