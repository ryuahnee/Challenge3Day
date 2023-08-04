package event.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.service.EventService;
import mvc.command.CommandController;

//삭제요청 처리를 담당하는 컨트롤러
//요청주소: localhost/event/delete.do
public class DeleteEventController implements CommandController {
	private EventService eventService = new EventService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1. 파라미터 받기
		int no = Integer.parseInt(request.getParameter("no"));
		
		//2. 비즈니스로직 수행
		eventService.delete(no);
		
		//3. 모델
		
		//4. 뷰
		response.sendRedirect(request.getContextPath()+"/event/list.do");
		return null;
	}

}
