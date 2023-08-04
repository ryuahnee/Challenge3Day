package event.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import event.dao.EventDAO;
import event.dao.EventMemListDAO;
import event.model.EventMemList;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

//eventmem_list와 관련된 요청을 담당하는 서비스클래스이다.
public class EventMemListService {
	
	private EventDAO eventDAO = new EventDAO();
	private EventMemListDAO emlDAO =  new EventMemListDAO();
	private int size = 10; //1페이지당 출력할 회원 수 
	
//	//eventmem_list 테이블에 insert요청을 처리(원본)
//	public int applyEvent(int mem_no, int event_no) {
//		Connection conn = null;
//		int badgeCnt = 0;
//		try {
//			conn = ConnectionProvider.getConnection();
//			conn.setAutoCommit(false);
//			badgeCnt = eventDAO.confirmBadgeCnt(conn, mem_no);
//			if(badgeCnt<3) { //뱃지 개수가 3개보다 모자르면
//				JdbcUtil.rollback(conn);
//				return badgeCnt;
//			} else {
//				eventDAO.applyEvent(conn, mem_no, event_no);
//				
//				conn.commit();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JdbcUtil.rollback(conn);
//		} finally {
//			JdbcUtil.close(conn);
//		}
//		return badgeCnt;
//	}
	
	//eventmem_list 테이블에 insert요청을 처리(사본)
	public void applyEvent(int mem_no, int event_no) {
		System.out.println("이벤트 신청  insert하러 옴");
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			eventDAO.applyEvent(conn, mem_no, event_no);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public int confirmBadgeCnt(int mem_no) throws SQLException {
		System.out.println("뱃지개수 확인하러 옴");
		Connection conn = null;
		conn = ConnectionProvider.getConnection();
		int badgeCnt = 0;
		
		badgeCnt = eventDAO.confirmBadgeCnt(conn, mem_no);
		
		return badgeCnt; //뱃지개수를 리턴
	}
	
	public int confirmEventHistory(int mem_no, int event_no) throws SQLException {
		System.out.println("이벤트 신청한 적 있는지 확인하러 옴");
		Connection conn = null;
		conn = ConnectionProvider.getConnection();
		int history = 0;
		history = eventDAO.confirmEventHistory(conn, mem_no, event_no);
		
		return history; //조회결과가 있으면 1, 없으면 0을 리턴
	}
	
	
	//회원목록 조회 요청을 처리
	public MemberPage getMemberPage(int pageNo, int event_no) {
		System.out.println("EventMemListService-getMemberPage()진입");
		Connection conn;
		try {
			conn = ConnectionProvider.getConnection();
			int totalCount = emlDAO.selecCount(conn, event_no); //신청한 총 회원수
			List<EventMemList> content = emlDAO.select(conn, (pageNo-1)*size, size, event_no); //목록조회
			MemberPage mp = new MemberPage(totalCount, pageNo, size, content);
			return mp;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
}
