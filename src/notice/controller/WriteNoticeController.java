package notice.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//글 등록처리 한다
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import notice.model.Notice;
import notice.service.ListNoticeService;

public class WriteNoticeController implements CommandController{

	private static final String FORM_VIEW = "/view/notice/writeNoticeForm.jsp";
	private ListNoticeService listNoticeService = new ListNoticeService();
	
		
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("WriteNoticeController의 process()진입");
		
		if( request.getMethod().equalsIgnoreCase("GET") ) {//요청방식이 get방식이면  FORM_VIEW 보여주기
			return processForm(request,response);
		}else if(request.getMethod().equalsIgnoreCase("POST")) {//요청방식이 post방식이면 로그인처리
			return processSubmit(request,response); 
		}else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);//405
			return null;
		}
	}//process



	// 글 등록폼 보여주기 
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
	
		//로그인정보 받기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		
		 // 로그인 정보를 request 속성에 저장
	    request.setAttribute("user", user);
		return request.getContextPath()+FORM_VIEW;
	}
	
	//글 등록처리 
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
			//에러정보 저장
			Map<String, Boolean> errors = new HashMap<String, Boolean>();
			request.setAttribute("errors",errors);

			//1. 파라미터 받기
			User user = (User)request.getSession(false).getAttribute("AUTH_USER");
			 
			//2.Request로 작성한 제목, 내용 가져온다 
			String notiTitle = request.getParameter("title");
			String notiContent = request.getParameter("content");
			
			//3. Notice 객체 셍성 및 필수입력 검사
			Notice notice = new Notice();
		    notice.setNotiTitle(notiTitle);
		    notice.setNotiContent(notiContent);
		    notice.validate(errors); // 필수 입력 검사
			
			//4. 입력완료되면 글번호를 리턴 -> jsp에서 글등록 성공 시 글 상세보기 기능으로 사용
		    int newArticleNo = listNoticeService.write(notice);
			
			//5. VIEW
			if(!errors.isEmpty()) { //에러가 있으면  글등록폼으로 재이동
				return request.getContextPath()+FORM_VIEW;
			}
			//3. MODEL
			request.setAttribute("newArticleNo", newArticleNo);
			
			//4. View - 성공시 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter(); // 브라우저 출력
			 
			 String msg = "<script>alert('공지사항이 등록되었습니다');location.href='" 
					    + request.getContextPath() + "/notice/list.do';</script>";

			    writer.print(msg);

			    return null;
			}


	}
		
		
		

	
	


