package qnaboard.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import auth.service.User;
import mvc.command.CommandController;
import qnaboard.model.Qna_boardDTO;
import qnaboard.service.QnaBoardNotFoundException;
import qnaboard.service.Qna_boardService;

public class QnaWriteController implements CommandController{

	private Qna_boardService qna_boardService = new Qna_boardService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("get")) {
			return processForm(request,response);
		} else if(request.getMethod().equalsIgnoreCase("post")) {
			return processSubmit(request,response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("문의하기page");
		// 세션가져오기
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("AUTH_USER");
			String id = user.getId();					//id값을 가져옴
			session.setAttribute("id", id);
			
			
			String qna_title = request.getParameter("qna_title");
			System.out.println("qna_title:"+qna_title);
			String qna_con = request.getParameter("qna_con");
			System.out.println("qna_con:"+qna_con);
			String qna_orifile_name = "";
			
			return request.getContextPath() + "/view/qna/qna_Write.jsp";
	}
	
protected String processSubmit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html;charset=UTF-8");
		
	// 세션가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = user.getId();					//id값을 가져옴
		session.setAttribute("id", id);
		// 로그인 정보를 request에 저장
				request.setAttribute("user", user);

		String qna_title = request.getParameter("qna_title");
		System.out.println("qna_title:"+qna_title);
		String qna_con = request.getParameter("qna_con");
		System.out.println("qna_con:"+qna_con);
		String qna_orifile_name = "";
		
		if(qna_title ==null || qna_title == "") {
			qna_title="오류";
		}
		if(qna_con ==null) {
			qna_con="오류";
		}
		
//파일업로드 
			Collection<Part> parts  = request.getParts();
			StringBuilder builder= new StringBuilder();
			
			for(Part p : parts) {
				if(!p.getName().equals("qna_orifile_name")) continue;	// 파일이 아니면 패스 
				if(p.getSize() == 0 ) continue;	// 파일을 1개만 올려도 ok
				
				Part filePart = p;
				qna_orifile_name = filePart.getSubmittedFileName();	//추가한것
				builder.append(qna_orifile_name);
				builder.append(",");
				
				InputStream fis = filePart.getInputStream();
				 System.out.println(fis);
				 
				// 2.저장 경로(임시 톰켓서버내.) 
				String realPath = request.getServletContext().getRealPath("/upload/qna");
				System.out.println(realPath);
				// 임시가 아닌 저장경로를 지정해준다 .파일이 없을 경우 생성 해줌.
				File path  = new File(realPath);
				if(!path.exists())
					path.mkdirs();
				// File.separator / import : java.io.file=> 원도우, 리눅스는 파일경로 안내시 슬래시(/)값이 다르기에 자바에서 제공하는 함수를 사용한다. 
				String filePate = realPath + File.separator + qna_orifile_name;	//추가함
				FileOutputStream fos = new FileOutputStream(filePate);
				
				//3. fis.read(); // byt씩 읽어옴. 다 읽으면 정수 -1값을 반환해줌
				byte[] buf = new byte[1024];
				int size = 0 ;
				while((size = fis.read(buf)) != -1) {
					fos.write(buf, 0, size);
				}
				fos.close();
				fis.close();
			}
			// start =>3 , end => 5일때 "abcdef"값의	de값을 지운다. 지금은 ","지우는 작업
			if (builder.length() > 0) {
			    builder.delete(builder.length()-1, builder.length());
			}
//파일업로드문 끝 
			
		//	DB저장
			try {
				if(qna_orifile_name != null) {
					qna_boardService.qnaWrite(qna_title, qna_con, id, qna_orifile_name);
				}else {
					qna_boardService.qnaWrite(qna_title, qna_con, id);
				}
			
			return request.getContextPath() +"/qna/List.do";
		} catch (QnaBoardNotFoundException e) {
			return null;
		}
	}
	
}
	
