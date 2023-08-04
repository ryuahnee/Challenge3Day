package event.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import event.model.Event;
import event.service.WriteRequest;
import jdbc.JdbcUtil;

//event테이블에 데이터를 삽입하는 기능 제공.

public class EventDAO {
	
	//날짜 없이 제목과 글만 입력하는 경우 
	public void insert(Connection conn, WriteRequest writeReq) throws SQLException {
		PreparedStatement stmt = null;
		String sql = "INSERT INTO event_board "
				+ "(event_title, event_con, has_btn) "
				+ "VALUES (?, ?, ?)";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, writeReq.getTitle());
			stmt.setString(2, writeReq.getContent());
			stmt.setString(3, writeReq.getHas_btn());
			
			int insertCnt= stmt.executeUpdate();
			System.out.println("EventDAO-insert(날짜자동입력)결과="+insertCnt+"행 입력"); //나중에 지울거임
			
		} finally {
			JdbcUtil.close(stmt);
		}
	}
	
	//총 게시물 수 조회
	public int selectCount(Connection conn) throws SQLException {
		//select count(*) from challenge3.event_board;
		String sql = "select count(*) from event_board";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			int totalCnt = 0;
			if(rs.next()) {
				totalCnt = rs.getInt(1);
			}
			return totalCnt;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	//목록 조회
	public List<Event> select(Connection conn, int startRow, int size) throws SQLException{
		String sql = "SELECT event_no, event_title, event_regdate, has_btn "
				+ "FROM challenge3.event_board "
				+ "order by event_no desc limit ?, ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			//쿼리문 안에 ?? 2개 넣으면 set데이터타입 할 예정.
			stmt.setInt(1, startRow);
			stmt.setInt(2, size);
			rs = stmt.executeQuery();
		
			List<Event> result = new ArrayList<>();
			while(rs.next()) {
				//데이터타입 변수명 = rs.get데이터타입("컬럼명");
				//데이터타입 변수명 = rs.get데이터타입(int 컬럼순서); //컬럼명이 없는 경우, (누적참여수 같은거
				result.add( convertEvent(rs) );//end of list.add();
			} //end of while(){}
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt); //커넥션은 여기서 끊으면 안됨 전달 해야 되니까.
		}
	}

	private Event convertEvent(ResultSet rs) throws SQLException { //rs에 담긴 쿼리 실행 결과를 Event타입으로 저장.
		return new Event(rs.getInt("event_no"), 
						 rs.getString("event_title"), 
						 rs.getDate("event_regdate"));
	}
	
	//상세조회
	public Event getDetail(Connection conn, int no) throws SQLException {
		System.out.println("EventDAO-getDetail() 진입");
		String sql = "SELECT event_no, event_title, event_con, event_regdate, has_btn "
				+ "FROM challenge3.event_board "
				+ "where event_no=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, no);
			rs = stmt.executeQuery();
			Event event = null;
			if(rs.next()) {
				event = new Event();
				event.setEvent_no(rs.getInt("event_no"));
				event.setEvent_title(rs.getString("event_title"));
				event.setEvent_con(rs.getString("event_con"));
				event.setEvent_regdate(rs.getDate("event_regdate"));
				event.setHas_btn(rs.getString("has_btn"));
				System.out.println("EventDAO에서 getDetail() Event event="+event);
			}
			return event;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		
	}
	
	//수정
	public int update(Connection conn, String event_title, String event_con, int no, String has_btn) throws SQLException {
		System.out.println("EventDAO-update()진입");
		String sql = "update event_board " + 
					 "set event_title=?, event_con=?, has_btn=? " + 
					 "where event_no=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, event_title);
			stmt.setString(2, event_con);
			stmt.setString(3, has_btn);
			stmt.setInt(4, no);
			return stmt.executeUpdate();
		}finally {
			JdbcUtil.close(stmt);
		}
	}

	//삭제
	public int delete(Connection conn, int no) throws SQLException {
		System.out.println("EventDAO-delete()진입");
		String sql = "delete from event_board where event_no=?";
		PreparedStatement stmt = null;
		try {
			stmt =  conn.prepareStatement(sql);
			stmt.setInt(1, no);
			return stmt.executeUpdate();
			
		} finally {
			JdbcUtil.close(stmt);
		}
		
	}

//	//회원의 이벤트 신청 (원본)
//	//insert into eventmem_list ~
//	public void applyEvent(Connection conn, int mem_no, int event_no) {
//		System.out.println("EventDAO-applyEvent()진입");
//		//insert하기 전에 먼저 select해서 해당 이벤트에 신청한 적이 있는지 확인
//		String sql = "insert into eventmem_list (event_no, mem_no) " + 
//				 	 "values(?, ?)";
//		String sql2 = "select mem_no, event_no "+
//					  "from eventmem_list "+
//					  "where mem_no = ? and event_no = ?";
//		PreparedStatement stmt = null;
//		PreparedStatement stmt2 = null;
//		ResultSet rs = null;
//		try {
//			stmt2 = conn.prepareStatement(sql2);
//			stmt2.setInt(1, mem_no);
//			stmt2.setInt(2, event_no);
//			rs = stmt2.executeQuery();
//			if(rs.next()) { //결과가 있으면
//				//만약에 select 조회결과, 이미 신청한 적이 있으면 alert 창 띄우게끔. 
//				//알러트창을 어떻게 띄울까아
//				System.out.println("이미신청한적 있는 회원임");
//				//~~PrintWriter write = response.getWriter();
//			} else if(!rs.next()) { //검색 결과가 없으면  insert 처리 진행
//				try {
//					stmt = conn.prepareStatement(sql);
//					stmt.setInt(1, event_no);
//					stmt.setInt(2, mem_no);
//					stmt.executeUpdate();
//					
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} finally {
//					JdbcUtil.close(stmt);
//				}
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		} finally {
//			JdbcUtil.close(rs);
//			JdbcUtil.close(stmt2);
//		}
//	}
	
	//분리작업 insert into eventmem_list ~
	public void applyEvent(Connection conn, int mem_no, int event_no) {
		System.out.println("EventDAO-applyEvent()진입");
		//insert하기 전에 먼저 select해서 해당 이벤트에 신청한 적이 있는지 확인
		String sql = "insert into eventmem_list (event_no, mem_no) " + 
				"values(?, ?)";
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, event_no);
			stmt.setInt(2, mem_no);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stmt);
		}
		
	}
	
	//분리작업 select
	public int confirmEventHistory(Connection conn, int mem_no, int event_no) {
		int history = 0;
		String sql2 = "select mem_no, event_no "+
				"from eventmem_list "+
				"where mem_no = ? and event_no = ?";
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		try {
			stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, mem_no);
			stmt2.setInt(2, event_no);
			rs = stmt2.executeQuery();
			if(rs.next()) { //검색 결과가 있으면 1을 리턴.
				history = 1;
			} 
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt2);
		}
		return history;
	}
	
	//회원이 이벤트 신청하기 전에!..뱃지가 3개 이상인지 확인하기
	public int confirmBadgeCnt(Connection conn, int mem_no) {
		String sql = "select count(ach_badge_no) " + 
					 "from member_badge " + 
					 "where mem_no=?";
		ResultSet rs = null;
		PreparedStatement stmt = null;
		int cnt = 0;
		try {
			stmt = conn.prepareStatement(sql);
		
			stmt.setInt(1, mem_no);
			rs=stmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
			
			System.out.println(mem_no+"의 뱃지개수="+cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		return cnt;
	}
	
	//검색
	public List<Event> search(Connection conn, String searchField, String searchText, int startRow, int size) throws SQLException{
		/*String sql = "select event_title
						from event_board
						where event_title like concat('%','모','%')";*/
					 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String search = "";
		if(searchText != null && !searchText.contentEquals("")) {
			search = "where "+searchField+" like concat('%', '"+searchText.trim()+"', '%') ";
		}
		String sql = "select event_no, event_title, event_regdate, has_btn " +
					 "from event_board " +
					 search +
					 "order by event_no desc limit ?, ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, startRow);
			stmt.setInt(2, size);
			rs = stmt.executeQuery();
			
			List<Event> result = new ArrayList<>();
			while(rs.next()) {
				//데이터타입 변수명 = rs.get데이터타입("컬럼명");
				//데이터타입 변수명 = rs.get데이터타입(int 컬럼순서); //컬럼명이 없는 경우, (누적참여수 같은거
				result.add( convertEvent(rs) );//end of list.add();
			} //end of while(){}
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt); //커넥션은 여기서 끊으면 안됨 전달 해야 되니까.
		}
	}
	
	//검색한 총 게시물 수 조회
	public int selectCount(Connection conn, String searchField, String searchText) throws SQLException {
		//select count(*) from challenge3.event_board;
		String search = "";
		if(searchText != null && !searchText.contentEquals("")) {
			search = "where "+searchField+" like concat('%', '"+searchText.trim()+"', '%') ";
		}
		String sql = "select count(*) "
				   + "from event_board "
				   + search;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			int totalCnt = 0;
			if(rs.next()) {
				System.out.println("(검색)EventDAO의 selectCount()메소드결과="+totalCnt); 
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	
	//선택한 여러 게시물 삭제
	//끄응..
//	public int deleteMulti(Connection conn, int no) {
//		System.out.println("EventDAO-deleteMulti() 진입");
//		String sql = "delete from event_board where event_no in (?)";
//		PreparedStatement stmt = null;
//		
//		stmt = conn.prepareStatement(sql);
//		stmt.setInt(parameterIndex, x);
//	}

}
