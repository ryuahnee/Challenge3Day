package chall.command;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import chall.model.Chall;
import chall.service.ChallService;
import chall.service.ModifyRequest;
import mvc.command.CommandController;
public class ModifyChallController  implements CommandController{

	private static final String FORM_VIEW ="/view/chall/modifyChall.jsp";
	
	private ChallService challService =new ChallService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ModifyChallController의 process()진입");

		if( request.getMethod().equalsIgnoreCase("GET") ) {
			return processForm(request,response);
		}else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request,response); 
		}else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);//405
			return null;
		}
	}
	
	// 글수정폼을 보여주기
	private String processForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("수정하기 폼 들어옴");
		
		//로그인 값 세션으로 가져옴.
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("AUTH_USER");
		String id = user.getId();	//id값을 가져옴
		request.setAttribute("user",user);
		
	    // 1. 파라미터 받기
	    int no = Integer.parseInt( request.getParameter("no") );//수정하고 싶은 글번호
	    session.setAttribute("no", no);
	    request.setAttribute("no", no);
	    
	    // 2. 비즈니스 로직<->Service<->DAO<->DB
	    Chall chall = challService.getDetail(no);
	    //수정하려는 사람이 글 쓴 사람과 일치하지 않을 경우 
	    
	    if(!user.getId().equals("admin") && user.getMem_no()!=chall.getMemNo()) {
	    	response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			String msg = "<script>alert('비정상적인 접근입니다.');"
					+"location.href="+request.getContextPath()+"'/myPage/list.do'</script>";
			writer.print(msg);
	    	return null;
	    }

	    // 수정을 위해 세션에서 가져온 회원id, 글번호, db에서 가져온 제목과 내용
	    ModifyRequest modReq =
	            new ModifyRequest( no, chall.getCateNo(), chall.getChallTitle(),chall.getChallCon(), chall.getCertifiWords() );

	    // 3. Model
	    request.setAttribute("modReq", modReq);
	    request.setAttribute("user", user); // 로그인 정보를 request에 저장
	    return request.getContextPath() + FORM_VIEW;
	}
	
	
	// 글수정처리
	  private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		 HttpSession session = request.getSession();
		 User user = (User)session.getAttribute("AUTH_USER");
			
		 request.setAttribute("user",user);
			
	      // 1. 파라미터 받기
	      int no = Integer.parseInt(request.getParameter("no"));
	      int cate_no = Integer.parseInt(request.getParameter("cate_no"));
	      String chall_title = request.getParameter("chall_title");
	      String chall_con = request.getParameter("chall_con");
	      String certifi_words = request.getParameter("certifi_words");
	      //User authUser = (User)request.getSession().getAttribute("AUTH_USER");
	      
	      // 수정 요청 생성
	      Chall chall = new Chall();
	      chall.setChallNo(no);
	      chall.setCateNo(cate_no);
	      chall.setChallTitle(chall_title);
	      chall.setChallCon(chall_con);
	      chall.setCertifiWords(certifi_words);
	        
	        request.setAttribute("chall", chall);
	      
	   /*     // 유효성 검사
	        Map<String, Boolean> errors = new HashMap<String, Boolean>();
	        request.setAttribute("errors", errors);
	        if (!errors.isEmpty()) { // 에러가 있으면 수정폼페이지로 이동
	        	return request.getContextPath() + FORM_VIEW;
	        }
*/
	      // 2. 비즈니스 로직<->Service<->DAO<->DB
		challService.modify(chall);

	      // 3. Model

	      // 4. View
	      response.setContentType("text/html; charset=UTF-8");
	      PrintWriter writer = response.getWriter();
	      String msg = "<script>alert('게시글이 수정되었습니다!');location.href='" 
        			+ request.getContextPath() + "/chall/read.do?no="
        			+ no + "';</script>";
	      writer.print(msg);

	      return  null;
	  }
	  
}
