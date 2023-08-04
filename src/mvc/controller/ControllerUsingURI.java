package mvc.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandController;
import mvc.command.NullController;

public class ControllerUsingURI extends HttpServlet {
	
	private Map<String, CommandController> CommandControllerMap = new HashMap<>();
	//최초 한번만 호출. 설정값 초기화, DB연동 등
	public void init() throws ServletException {
		String configFile = getInitParameter("configFile");
        System.out.println("configFile="+configFile);
        Properties prop = new Properties();//Properties객체
        System.out.println("prop="+prop); //{}
        String configFilePath = getServletContext().getRealPath(configFile);
        System.out.println("configFilePath="+configFilePath);
        
        //실행동작할 수 있는 파일로 만든다
        try (FileReader fis = new FileReader(configFilePath)) {
            prop.load(fis);
        } catch (IOException e) {
            throw new ServletException(e);
        }
        
        Iterator keyIter = prop.keySet().iterator();
        while (keyIter.hasNext()) {
            String command = (String) keyIter.next();
            String ControllerClassName = prop.getProperty(command);
            try {
                Class<?> controllerClass = Class.forName(ControllerClassName);
                CommandController controllerInstance = (CommandController) controllerClass.newInstance();
                CommandControllerMap.put(command, controllerInstance);
                //메모리 상의 Map에 put된  key:value출력
                System.out.println(command+":"+CommandControllerMap.get(command));
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new ServletException(e);
            }
        }
	}
	
	//get방식 요청시 호출, 요청을 처리하고 응답을 생성
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet()호출");
		process(request, response);
	}
	
	//한 번만 호출. DB연결 해제, 리소스 반환.
	public void destroy() {
		System.out.println("destroy()호출");
	}


	//post방식 요청시 호출, 요청을 처리하고 응답을 생성
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost()호출");
		process(request, response);
	}
	
	//get방식 요청, post방식 요청시 호출
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {       
		String command = request.getRequestURI(); // *.do
		if(command.indexOf(request.getContextPath())==0) {
			//System.out.println(request.getContextPath().length());
			command = command.substring(request.getContextPath().length());
		}
		CommandController controller = CommandControllerMap.get(command); 
		
		if(controller==null) {
			System.out.println("controller is null");
			controller = new NullController(); //담당 핸들러가 없으면 404 처리.
		}
		
		//Map에 값 가져오기:  Map참조변수명.get(key)
		String viewPage = null;
		try {
			viewPage = controller.process(request, response); 
			System.out.println("viewPage="+viewPage);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		if(viewPage!=null) { // 76line. 
			//RequestDispatcher의 forward()를 이용하여 view페이지로 강제이동
			//참고: <jsp:forward>액션태그
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
	}
	
}
