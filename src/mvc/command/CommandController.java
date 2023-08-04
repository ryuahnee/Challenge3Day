package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandController {
	//모든 담당컨트롤러가 구현해야하는  메소드
	//리턴유형은 String, 클라이언트에게 보여주는 jsp문서의 풀경로.
	public String process(HttpServletRequest request, 
			HttpServletResponse response) throws Exception;
}
