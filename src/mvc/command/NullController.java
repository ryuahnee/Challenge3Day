package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NullController implements CommandController {
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 response.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
}
