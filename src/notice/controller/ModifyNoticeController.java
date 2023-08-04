package notice.controller;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import mvc.command.CommandController;
import notice.model.Notice;
import notice.service.ListNoticeService;
import notice.service.ModifyRequest;

public class ModifyNoticeController implements CommandController{

	//필드
		private static final String FORM_VIEW ="/view/notice/modifyNoticeForm.jsp";
		
		private ListNoticeService listNoticeService = new ListNoticeService();
		
		@Override
		public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
			System.out.println("listNoticeService의 process()진입");
			
			if( request.getMethod().equalsIgnoreCase("GET") ) {//요청방식이 get방식이면  FORM_VIEW 보여주기
				return processForm(request,response);
			}else if(request.getMethod().equalsIgnoreCase("POST")) {//요청방식이 post방식이면 수정처리
				return processSubmit(request,response); 
			}else {
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);//405
				return null;
			}
		}//process
		
		//글수정폼을 보여주기
		private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			//1. 파라미터 받기= 어떤 no게시글 수정?
			//int no 수정하고 싶은 글번호
			int no = Integer.parseInt( request.getParameter("notiNo") ); //뷰에서 notiNo파라미터 받아
			
			//2.비즈니스로직 처리. 서비스에서 정보 가져온다
			Notice notice = listNoticeService.getDetail(no);
			// 로그인 정보 가져오기
			HttpSession session = request.getSession();
			User user = (User)request.getSession().getAttribute("AUTH_USER"); //세션로그인유저정보
			
			if(!user.getId().equals("admin")) { //로그인회원이 관리자가 아니면 403에러
				response.sendError(HttpServletResponse.SC_FORBIDDEN); // 403
				return null;
			}
			
			//수정을 위해 세션에서 가져온 글 번호, db에서 가져온 작성 내용
			ModifyRequest modReq = 
					new ModifyRequest(no,notice.getNotiTitle(),notice.getNotiContent());
				
			
			request.setAttribute("modReq", modReq); //modReq로 넘겨주기
			request.setAttribute("user", user); // 로그인 정보를 request에 저장
			return request.getContextPath()+FORM_VIEW;
		}
		
		
		
		
		//글수정처리
	    private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      //1. 파라미터 받기
	    	int no = Integer.parseInt(request.getParameter("no"));
	        String title = request.getParameter("title");
	        String content = request.getParameter("content");
	        User user = (User) request.getSession().getAttribute("AUTH_USER");

	        // 수정 요청 생성
	        Notice notice = new Notice();
	        notice.setNotiNo(no);
	        notice.setNotiTitle(title);
	        notice.setNotiContent(content);
	        
	        request.setAttribute("notice", notice);
	     
	        //유효성검사
			//에러정보가 담기는 Map- p670 77라인
			Map<String, Boolean> errors = new HashMap<String, Boolean>();
			request.setAttribute("errors",errors);
			if(!errors.isEmpty()) { //에러가 있으면  수정폼페이지로 이동
				return request.getContextPath()+FORM_VIEW;
			}
	        
			//2. 비즈니스로직 : 수정처리를 위해 세션에서 가져온 수정 정보
	        listNoticeService.modify(notice);
	        
	        //3.모델
	        //4.뷰
	        response.setContentType("text/html; charset=UTF-8");
	        PrintWriter writer = response.getWriter();
	        String msg = "<script>alert('게시글이 수정되었습니다!');location.href='" 
	        			+ request.getContextPath() + "/notice/read.do?notiNo="
	        			+ no + "';</script>";
	        writer.print(msg);

	        return null;
	    }

	}

	
