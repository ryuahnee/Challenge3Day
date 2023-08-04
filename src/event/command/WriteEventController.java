package event.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.service.EventService;
import event.service.WriteRequest;
import mvc.command.CommandController;

//게시글 쓰기 요청을 처리.
//get방식으로 오면 폼을 리턴, post방식으로 오면 입력처리.
//요청주소: http://localhost/event/write.do

//로그인한 계정이 관리자인지 확인 해야함 <<<<아직 안함!!!!!!!!
public class WriteEventController implements CommandController {
	
	private static final String FORM_VIEW = "/view/event/writeForm.jsp";
	private EventService es= new EventService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("WriteEventController-process()진입");
		//if() { //로그인한 계정이 관리자인지 아닌지 확인. AUTH_USER
			if(request.getMethod().equalsIgnoreCase("get")) {
				return processForm(request, response);
				
			} else if(request.getMethod().equalsIgnoreCase("post")) { //post
				return processSubmit(request, response); //p.622 line25
				
			} else {
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);//405 허용하지않은
				return null;
			}
	}
	
	//get방식이면 폼을 보여줌
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return request.getContextPath()+FORM_VIEW;
	}
	
	//post방식이면 글을 등록해줌.
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		WriteRequest wr= new WriteRequest();
		wr.setTitle(request.getParameter("title"));
		wr.setContent(request.getParameter("con"));
		
		
		//쿼리문도 살짝씩 손보면 되지뭐. 
		String[] values = request.getParameterValues("participation");
		String createBtn = "";
		if(values!=null) {
			for (int i=0; i<values.length; i++) {
				createBtn= values[i];
			}
		}
		wr.setHas_btn(createBtn);
	
		es.writeEvent(wr);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			String msg = "<script>alert('이벤트 게시물이 작성되었습니다.');"
					+"location.href="+request.getContextPath()+"'/event/list.do'</script>";
			writer.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
			return request.getContextPath()+"event/list.do";
		}
		return null;
		
	}

}
