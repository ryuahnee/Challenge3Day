package event.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import event.model.Event;
import event.service.ModifyEventRequest;
import event.service.EventService;
import mvc.command.CommandController;

//수정 요청을 담당하는 컨트롤러
//메소드 요청 방식에 따라 폼을 보여주거나 submit처리를 해줄거임.
public class ModifyEventController implements CommandController {
	
	//우선, Service객체를 생성. (상세조회랑 비슷하기 때문에, readService를 재활용.)
	private EventService eventService= new EventService();
	
	//수정처리를 위한 ModifyEventService
	
	//폼을 두고두고 써줄거니까 변수 선언을 해주는 것이 좋음.
	private static final String FORM_VIEW = "/view/event/modifyForm.jsp";
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ModifyEventController의 process()진입");
		//메소드 방식에 따라 다르게 처리.
		if(request.getMethod().equalsIgnoreCase("get")) { //get방식이면 수정 폼을 보여주는 메소드로, 
			return processForm(request, response);
			
		} else if(request.getMethod().equalsIgnoreCase("post")) { //post방식이면 제출해주는 메소드로,
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); //둘 다 아니면 이상하다고 알려줌.
			return null;
		}
	}

	//수정폼을 보여주는 메소드
	public String processForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//이제 응당 컨트롤러가 해야할 일.
		//1. 파라미터받기 - 상세 조회하고 싶은 글 번호. = 수정하고자 하는 글 번호.
		int no = Integer.parseInt( request.getParameter("no") ); //뷰단에서 no파라미터를 받아오기.
		//2. 비즈니스 로직처리
			//서비스에 있는 걸 가지고 와야함.
		Event event = eventService.getDetail(no);
		//세션에 저장된 정보를 저장할 클래스가 필요. 수업에서는 User 클래스. ModifyArticleHandler
		User user = (User) request.getSession().getAttribute("AUTH_USER");
		if(!user.getId().equals("admin")) {//로그인한 사람이 관리자가 아니라면
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			String msg = "<script>alert('비정상적인 접근입니다.');"
					+"location.href="+request.getContextPath()+"'/event/list.do'</script>";
			writer.print(msg);
	    	return null;
		}
		

		//수정을 위해 db에서 가져온 data와 세션에서 가져온 글번호 (닉네임은 챌린지에서 가져와야할듯)
		ModifyEventRequest mer = new ModifyEventRequest(no, event.getEvent_title(), event.getEvent_con(), event.getHas_btn());
		
		//3. 모델
		request.setAttribute("mer", mer); //이렇게 만들어서 던져주겠다.
		
		//4. 뷰
		return request.getContextPath()+FORM_VIEW;
	}
	
	
	//수정처리를 해주는 메소드
	public String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ModifyEventController-processSubmit()진입");
		//1. 파라미터 받기
			//글번호, 제목, 내용만.
		int no =  Integer.parseInt(request.getParameter("no")); //name에서 갖고 옴
		String  title = request.getParameter("title");
		String content = request.getParameter("content");
		String[] values = request.getParameterValues("participation");
		
		String createBtn = "";
		if(values!=null) {
			for (int i=0; i<values.length; i++) {
				createBtn= values[i];
			}
		}
			//수정처리를 위한 데이터를 ModifyEventRequest객체로 생성.
		ModifyEventRequest mer= new ModifyEventRequest(no, title, content, createBtn);
		request.setAttribute("mer", mer);
			//유효성 검사
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		if(!errors.isEmpty()) {
			return request.getContextPath()+FORM_VIEW;
		}
		
		//2. 비즈니스 로직처리
		eventService.modify(mer);
		//3. 모델
		//4. 뷰
		return request.getContextPath()+"/event/list.do";
	}
}
