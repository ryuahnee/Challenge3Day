package event.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import event.service.EventMemListService;
import mvc.command.CommandController;

//이벤트 상세조회시, 신청하기 버튼을 클릭하면 회원의 정보를 eventmem_list에 추가를 처리하는 컨트롤러
//요청주소: http://localhost/event/apply.do
public class ApplyEventController implements CommandController {
	private EventMemListService ems = new EventMemListService();
	
	
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1. 파라미터 받기
		//뷰단에서 현재 글번호를 받아와야함.
		//2. 비즈니스 로직처리
		//세션에서 가지온 정보(멤버 번호, 이벤트 번호)들고 디비에 insert요청 하러 가야함.
		//세션에 있는 AUTH_USER 속성값을 가져와가지고 User타입의 user에다가 저장하겠다.
		int event_no = Integer.parseInt( request.getParameter("no") ); //뷰단에서 no파라미터를 받아오기.
		User user = (User) request.getSession().getAttribute("AUTH_USER");
		if (user != null) {
			int mem_no = user.getMem_no();
			
			//우선 뱃지개수 확인
			int badgeCnt = ems.confirmBadgeCnt(mem_no);
			int history = ems.confirmEventHistory(mem_no, event_no);
			if(user.getMem_no() != 1) {
				if(badgeCnt<3) {
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter writer = response.getWriter();
					String msg = "<script>alert('이벤트 신청은 뱃지개수 3개 이상부터 가능합니다. "
							+user.getNickname()+"님의 획득한 뱃지개수는 "+badgeCnt+"개입니다.');"
							+"location.href="+request.getContextPath()+"'/event/list.do'</script>";
					writer.print(msg);
					return null;
				} else if(history==1) { //신청한 적있는지 확인
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter writer = response.getWriter();
					String msg = "<script>alert('이미 참여한 이벤트입니다.');"
							+"location.href="+request.getContextPath()+"'/event/list.do'</script>";
					writer.print(msg);
					return null;
				} else { //다 안걸리면 이벤트 신청처리
					ems.applyEvent(mem_no, event_no);
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter writer = response.getWriter();
					String msg = "<script>alert('신청이 완료되었습니다.');"
							+"location.href="+request.getContextPath()+"'/event/list.do'</script>";
					writer.print(msg);
					return null;
				}
			} else if (user.getMem_no()==1) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = response.getWriter();
				String msg = "<script>alert('관리자는 신청할 수 없습니다.');"
						+"location.href="+request.getContextPath()+"'/event/list.do'</script>";
				writer.print(msg);
			}
		} else if (user == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			String msg = "<script>alert('로그인한 회원만 이용 가능합니다.');"
					+"location.href="+request.getContextPath()+"'/event/list.do'</script>";
			writer.print(msg);
		}
		return null;
		//3. 모델
		//4. 뷰
		
	}

}
