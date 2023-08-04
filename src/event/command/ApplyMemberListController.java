package event.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.dao.EventMemListDAO;
import event.model.Event;
import event.service.EventMemListService;
import event.service.EventService;
import event.service.MemberPage;
import mvc.command.CommandController;

//관리자가 이벤트 상세조회 페이지에서 해당 이벤트를 신청한 회원 목록을 조회하는 요청을 처리해주는 컨트롤러이다.
//요청주소: http://localhost/event/memberlist.do
public class ApplyMemberListController implements CommandController {
	//필드
	private String LIST_VIEW="/view/event/listMemberApply.jsp";
	private EventMemListService emlService = new EventMemListService();
	private EventMemListDAO emlDAO = new EventMemListDAO();
	private EventService eventService =  new EventService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ApplyMemberListController-process()진입");
		if(request.getMethod().equalsIgnoreCase("get")) {
			return processList(request, response);
			
		} else if(request.getMethod().equalsIgnoreCase("post")) { //post
			return processSubmit(request, response); //p.622 line25
			
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);//405 허용하지않은
			return null;
		}
	}
	
	public String processList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ApplyMemberListController-processList()진입");
		
		//1. 요청 파라미터 받기
		String pageNoVal = request.getParameter("pageNo");
		
		//현재 보고 있는 event_no가 받아져야 함
		int event_no = Integer.parseInt(request.getParameter("no"));
		int pageNo=1; //기본적으로 1페이지가 보여짐
		if(pageNoVal!=null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		
		//2. 비즈니스로직 수행하기
		MemberPage memberPage = emlService.getMemberPage(pageNo, event_no);
		
		//3. 모델
		request.setAttribute("memberPage", memberPage);
		request.setAttribute("nowPage", pageNo);
		
		return request.getContextPath()+LIST_VIEW;
	}
	public String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ApplyMemberListController-processSubmit()진입");
		
		//3. 모델
		String[] rowcheck = request.getParameterValues("rowcheck");
		String btn = request.getParameter("btn");
		System.out.println("btn"+btn);
		if(rowcheck != null) {
			//체크박스에 선택된 회원들의 isSelector 컬럼 값 변경해주기
			if(btn.equals("해당 회원을 선정하기")) {
				int[] event_appli_no = new int[rowcheck.length];
				for(int i=0; i<rowcheck.length; i++) {
					event_appli_no[i] = Integer.parseInt(rowcheck[i]);
				}
				int result = emlDAO.changeY(event_appli_no);
				System.out.println("result"+result);
			} else if (btn.equals("해당 회원에 대한 선정을 취소하기")) {
				int[] event_appli_no = new int[rowcheck.length];
				for(int i=0; i<rowcheck.length; i++) {
					event_appli_no[i] = Integer.parseInt(rowcheck[i]);
				}
				int result = emlDAO.changeN(event_appli_no);
				System.out.println("result"+result);
			}
		} else if(rowcheck == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			String msg = "<script>alert('한명 이상 선택해야 합니다.');</script>";
			writer.print(msg);
		}
		return request.getContextPath()+"/event/list.do";
	}

}
