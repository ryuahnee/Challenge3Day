package event.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import event.model.EventMemList;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class EventMemListDAO {

	//해당 이벤트 글 총 신청 회원수 조회
	public int selecCount(Connection conn, int event_no) throws SQLException {
		//select count() from eventmem_list where evnet_no=
		String sql = "select count(mem_no) " + 
					"from eventmem_list " + 
					"where event_no=? " +
					"order by mem_no asc";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, event_no);
			rs = stmt.executeQuery();
			int totalCnt = 0;
			if(rs.next()) {
				totalCnt = rs.getInt(1);
				System.out.println("EventMemListDAO의 selectCount()메소드결과="+totalCnt); 
			}
			return totalCnt;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		
	}
	
	//관리자의 이벤트 신청회원목록 조회
	public List<EventMemList> select(Connection conn, int startRow , int size, int event_no) throws SQLException{
		String sql = "select e.event_appli_no, e.event_no, e.mem_no, e.isSelector, m.id, m.mem_name, m.nickname, m.birthyear, m.gender, m.isMarketing " + 
					"from eventmem_list e inner join member m " + 
					"on e.mem_no = m.mem_no " + 
					"where event_no=? " + 
					"order by mem_no asc limit ?, ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, event_no);
			stmt.setInt(2, startRow);
			stmt.setInt(3, size);
			rs = stmt.executeQuery();
			
			List<EventMemList> result = new ArrayList<>();
			while(rs.next()) {
				result.add( convertEventMemList(rs));
			}
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	private EventMemList convertEventMemList(ResultSet rs) throws SQLException {
		return new EventMemList(rs.getInt("event_appli_no"),
								rs.getInt("event_no"),
								rs.getInt("mem_no"),
								rs.getString("id"),
								rs.getString("mem_name"),
								rs.getString("nickname"),
								rs.getDate("birthyear"),
								rs.getString("gender"),
								rs.getString("isMarketing"),
								rs.getString("isSelector")
								);
	}

	//관리자가 이벤트 신청회원목록 조회를 통해 당첨자 선정
	//Service 클래스 생략함
	//선택된 회원들의 isSelector 컬럼을 'Y'로 수정해주는 요청.
	public int changeY(int[] event_appli_no) {
		System.out.println("EventMemListService-changeY()진입");
		int result = 0;
		String params = "";
		for(int i=0; i<event_appli_no.length; i++) {
			params += event_appli_no[i];
			if(i<event_appli_no.length-1)
				params += ", ";
		}
		String sql = "update eventmem_list " + 
					 "set isselector  = 'Y' " + 
					 "where event_appli_no in ("+params+")";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			result = stmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stmt);
			JdbcUtil.close(conn);
		}
		return result;
	}
	
	//Service 클래스 생략함
	//선택된 회원들의 isSelector 컬럼을 'N'로 수정해주는 요청.
	public int changeN(int[] event_appli_no) {
		System.out.println("EventMemListService-changeN()진입");
		int result = 0;
		String params = "";
		for(int i=0; i<event_appli_no.length; i++) {
			params += event_appli_no[i];
			if(i<event_appli_no.length-1)
				params += ", ";
		}
		String sql = "update eventmem_list " + 
					 "set isselector  = 'N' " + 
					 "where event_appli_no in ("+params+")";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			result = stmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stmt);
			JdbcUtil.close(conn);
		}
		return result;
	}
	

}
