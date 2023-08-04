package notice.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import notice.service.ListNoticeService;

public class DeleteNoticeController implements CommandController{

	private ListNoticeService listNoticeService = new ListNoticeService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
	System.out.println("DeleteNoticeController 진입");
		// 로그인 정보 가져오기
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("AUTH_USER");
				
		//1. 파라미터 받기
		String notiNoString = request.getParameter("notiNo");
		int no = Integer.parseInt(notiNoString.trim()); // 공백 제거 후 정수로 변환
		
		//2. 비지니스 로직 
		//파라미터 no - 삭제할 글 번호
		listNoticeService.deleteNotice(no); 
		
		//3.Model
		
		//4.View
		//삭제 성공시 js의 alert()이용해 삭제 성공 메세지 출력-> 목록보기
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter(); //브라우저에 출력
		  String msg = "<script>alert('게시글이 삭제되었습니다');location.href='" 
      			+ request.getContextPath() + "/notice/list.do"+ "';</script>";
      			writer.print(msg);
		
		
		return null;
	}

	
}
