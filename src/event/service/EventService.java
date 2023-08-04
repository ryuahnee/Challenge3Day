package event.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import event.dao.EventDAO;
import event.model.Event;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

//여기서 커넥션이 필요함.
public class EventService {
	
	//필드
	private EventDAO eventDAO = new EventDAO();
	private int size = 15; //1페이지 당 출력할 게시글 수
	
	//ListEventService
	public EventPage getEventPage(int pageNum) {
		System.out.println("EventPage의 getEventPage()진입"); 
		Connection conn;
		try {
			conn = ConnectionProvider.getConnection();
		
			int totalCount = eventDAO.selectCount(conn); //총 게시글 수
		
			List<Event> content = eventDAO.select(conn, (pageNum-1)*size, size); //목록조회
		
			EventPage ep = new EventPage(totalCount, pageNum, size, content);
			return ep;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//ReadEventService
	//파라미터로 받은 no가 필요. 써먹어주자.
	public Event getDetail(int no) {
		Connection conn;
		Event event = null;
		try {
			conn = ConnectionProvider.getConnection();
			//우리는 조회수 따위 없음.
			event = eventDAO.getDetail(conn, no);
			if(event==null) {
				throw new EventNotFoundException();
			}
			return event;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(); //더 높은 계층의 익셉션에게 넘겨줌.
		}
	}
	
	//WriteEventService
	public void writeEvent(WriteRequest writeReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			eventDAO.insert(conn, writeReq);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	//ModifyEventService
	public void modify(ModifyEventRequest mer) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//event_board테이블에  업데이트하기 전에 해당 글 번호 가져오기.
			Event event= eventDAO.getDetail(conn, mer.getEvent_no());
			if(event==null) {
				throw new EventNotFoundException();
			}
			
			//event_board테이블에 업데이트하는 메소드 호출.
				//파라미터: 수정하고자 하는 글번호와 제목, 내용, 버튼생성여부
			eventDAO.update(conn, mer.getEvent_title(), mer.getEvent_con(), mer.getEvent_no(), mer.getHas_btn());
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		
	}
	
	//삭제처리 p.667 line17
	//파라미터 EventRequest modReq: 수정처리를 위한 세션에서 가져온 회원id, 글번호, 제목, 내용
	//modifyEventService.modify(modReq);
	public void delete(int no) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//1-1. Event테이블에 업데이트하기 전, 해당 글 번호 가져오기
			Event event = eventDAO.getDetail(conn, no);
			if(event==null) {
				throw new EventNotFoundException();
			}
			//파라미터: 수정하고자 하는 글번호
			eventDAO.delete(conn, no);

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException();
		}finally {
			JdbcUtil.close(conn);
		}
	}

	//이벤트 검색
	//메소드오버로딩
	public EventPage getEventPage(int pageNo, String searchField, String searchText) {
		System.out.println("(검색)EventPage의 getEventPage()진입"); 
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
		
			int totalCount = eventDAO.selectCount(conn, searchField, searchText); //총 게시글 수
			System.out.println("(검색)totalCount="+totalCount); 
			List<Event> searchResult = eventDAO.search(conn, searchField, searchText, (pageNo-1)*size, size);
			//System.out.println("searchResult="+searchResult);
			
			EventPage ep = new EventPage(totalCount, pageNo, size, searchResult);
			System.out.println("ep="+ep);
			return ep;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
//	//ListEventService
//	public EventPage getEventPage(int pageNum) {
//		System.out.println("EventPage의 getEventPage()진입"); 
//		Connection conn;
//		try {
//			conn = ConnectionProvider.getConnection();
//		
//			int totalCount = eventDAO.selectCount(conn); //총 게시글 수
//		
//			List<Event> content = eventDAO.select(conn, (pageNum-1)*size, size); //목록조회
//		
//			EventPage ep = new EventPage(totalCount, pageNum, size, content);
//			return ep;
//			
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
	
}
