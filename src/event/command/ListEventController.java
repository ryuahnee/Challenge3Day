package event.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.service.EventPage;
import event.service.EventService;
import mvc.command.CommandController;
//이벤트 목록을 조회 요청을 처리하는 컨트롤러.
public class ListEventController implements CommandController {
	
	private String LIST_VIEW="/view/event/listEvent.jsp";
	private EventService eventService = new EventService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ListEventController-process()진입");
		//1. 파라미터 받기
		String pageNoVal = request.getParameter("pageNo");
		int pageNo=1; //기본적으로는 1페이지가 보여짐. (특정 페이지 선택 전까지)
		if(pageNoVal!=null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		//2. 비즈니스로직 수행
		EventPage eventPage = eventService.getEventPage(pageNo);
		//3. 모델 - 잠깐 보여주고 말거 아니니까 리퀘스트로.
		request.setAttribute("eventPage", eventPage);
		request.setAttribute("nowPage", pageNo); //유저가 선택한 페이지 정보를 모델로 넘겨줄 수 있음.
		
		return request.getContextPath()+LIST_VIEW;
	}

}
