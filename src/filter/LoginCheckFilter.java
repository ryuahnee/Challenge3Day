package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//로그인 여부 검사기능
//비번찾기 등과 같이 로그인 여부를 검사할때 필터링하는 역할을 하는 클래스
//요청주소 /checkPwd.do
public class LoginCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		//세션에 저장된 속성 유무를 검사한다
		//우리는 로그인에 성공한 회원정보(아이디,회원명)를 "AUTH_USER"이름으로 USER클래스에
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession();
		//세션이 비어있거나 이런놈이 없으면 로그인 페이지로 가라
		if(session==null||session.getAttribute("AUTH_USER")==null) {
			HttpServletResponse response= (HttpServletResponse)res;
			response.sendRedirect(request.getContextPath()+"/login.do");
		} else {
			chain.doFilter(req, res);
		}
	}

}
