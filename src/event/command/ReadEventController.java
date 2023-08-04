package event.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.model.Event;
import event.service.EventNotFoundException;
import event.service.EventService;
import mvc.command.CommandController;

//상세보기 요청을 처리하는 담당 컨트롤러
//요청주소 http://localhost/event/read.do?no=글번호
public class ReadEventController implements CommandController {
	
	private EventService eventService = new EventService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1. 파라미터 받기
		//db로 날릴 땐 int로 바꿔줘야함 
		int no = Integer.parseInt( request.getParameter("no") );
		//2. 비즈니스 로직 처리
		try {
			Event event= eventService.getDetail(no);
			
			//3. 모델
			request.setAttribute("event", event);
			
			//4. 뷰
			return request.getContextPath()+"/view/event/readEvent.jsp";
			
		} catch (EventNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		
		
	}

}
