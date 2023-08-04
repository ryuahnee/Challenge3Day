package event.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.service.EventPage;
import event.service.EventService;
import mvc.command.CommandController;

//검색요청을 처리하는 컨트롤러.
//요청주소: localhost/event/search.do
public class SearchEventController implements CommandController {
	//private String Search_VIEW="/view/event/searchListEvent.jsp";
	private String Search_VIEW="/view/event/listEvent.jsp";
	private EventService eventService = new EventService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("SearchEventController-process()진입");
		//1. 파라미터 받기
		String pageNoVal = request.getParameter("pageNo");
		int pageNo=1; //기본적으로는 1페이지가 보여짐. (특정 페이지 선택 전까지)
		if(pageNoVal!=null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		String searchField = request.getParameter("searchField"); //select - option: 제목, 내용
		String searchText = request.getParameter("searchText");
		
		
		//2. 비즈니스 로직 수행
		EventPage eventPage = eventService.getEventPage(pageNo, searchField, searchText);
		//3. 모델
		request.setAttribute("eventPage", eventPage);
		request.setAttribute("nowPage", pageNo); //현재페이지
		request.setAttribute("searchField", searchField); //검색 옵션
		request.setAttribute("searchText", searchText); // 검색어
		//4. 뷰
		return request.getContextPath()+Search_VIEW;
			
	}

}
