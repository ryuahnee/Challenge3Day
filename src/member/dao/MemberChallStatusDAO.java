package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import member.model.MemberChallList;
import member.model.MemberChallStatus;

public class MemberChallStatusDAO {
	//수정
	public int modify(Connection conn, int chall_manual_no, int chall_no, int mem_no, int chall_ing_status, Date start_date, Date final_date, String chall_result) throws SQLException {
		String sql="update member_chall_status"
				+ " set chall_no=?,mem_no=?,chall_ing_status=?,start_date=?,final_date=?,chall_result=?"
				+ " where chall_manual_no=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		try {
			stmt.setInt(1, chall_no);
			stmt.setInt(2, mem_no);
			stmt.setInt(3, chall_ing_status);
			stmt.setTimestamp(4, new Timestamp(start_date.getTime()));
			stmt.setTimestamp(5, new Timestamp(final_date.getTime()));
			stmt.setString(6, chall_result);
			stmt.setInt(7, chall_manual_no);
			
			return stmt.executeUpdate();
		}finally {
			JdbcUtil.close(stmt);
		}
	}
	
	//페이지네이션 관련
	public List<MemberChallList> select(Connection conn, int mem_no, String option, String searchCon, int startRow, int size) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String search = "";
		if(searchCon!=null && !searchCon.equals("")) {
			search = " and "+option+" like '%"+searchCon.trim()+"%'";
		}
			String sql="SELECT s.chall_manual_no, b.chall_no, b.chall_title, s.mem_no, s.chall_ing_status, s.start_date, s.final_date, s.chall_result" 
					+" FROM challenge3.member_chall_status s, challenge3.challenge_board b"
					+" WHERE s.chall_no=b.chall_no and s.mem_no=?"+search
					+" order by chall_manual_no desc limit ?,?";
		try {
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1,mem_no);
			stmt.setInt(2,startRow);
			stmt.setInt(3,size);
			rs = stmt.executeQuery();
			
			List<MemberChallList> result = new ArrayList<>();
			while(rs.next()) {
				result.add(convertMemberChall(rs));
			}
			return result;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	//리스트 내용
	private MemberChallList convertMemberChall(ResultSet rs) throws SQLException {
		return new MemberChallList(
				rs.getInt("chall_manual_no"),
				rs.getInt("chall_no"),
				rs.getString("chall_title"),
				rs.getInt("mem_no"),
				rs.getInt("chall_ing_status"),
				rs.getDate("start_date"),
				rs.getDate("final_date"),
				rs.getString("chall_result")
				);
	}	
	//회원별 챌린지 수 조회
	public int memberChallCnt(Connection conn, int mem_no, String option, String searchCon) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String search = "";
		if(searchCon!=null && !searchCon.equals("")) {
			search = " and "+option+" like '%"+searchCon.trim()+"%'";
		}
		System.out.println("");
		try {
			String sql="SELECT * FROM challenge3.challenge_board b,challenge3.member_chall_status s WHERE s.chall_no=b.chall_no and s.mem_no=?"+search;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,mem_no);
			rs=stmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}	
	
	//회원별 챌린지 조회
	public MemberChallStatus selectByMemNo(Connection conn, int no) throws SQLException {
		System.out.println("MemberChallengeStatusDAO-selectByMemNo()진입");
		//확인용 출력
	
		//3.객체준비
		//mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing
		String sql = "select chall_manual_no,chall_no,mem_no,chall_ing_status,start_date,final_date,chall_result "
				+ "from member_chall_status "
				+ "where mem_no=?";
		MemberChallStatus memberChallStatus = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, no);
			rs = stmt.executeQuery();
			
			if(rs.next()) {//조회한 아이디와 일치하는 회원이 존재한다면
				int chall_manual_no = rs.getInt("chall_manual_no");
				int chall_no = rs.getInt("chall_no");
				int mem_no = rs.getInt("mem_no");
				int chall_ing_status = rs.getInt("chall_ing_status");
				Date start_date = toDate(rs.getTimestamp("start_date"));
				Date final_date = toDate(rs.getTimestamp("final_date"));
				String chall_result = rs.getString("chall_result");
				
				memberChallStatus = new MemberChallStatus(chall_manual_no,chall_no,mem_no,chall_ing_status,start_date,final_date,chall_result);
			}
			//4.쿼리실행
			return memberChallStatus;
		}finally {//5.자원반납
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);		
		}
	}
	
	//Timestamp타입을 Date타입으로 형변환
	private Date toDate(Timestamp date) {
		return (date==null)?null:new Date(date.getTime());
	}

	public MemberChallStatus selectByChallMenualNo(Connection conn, int chall_manual_no) throws SQLException {
		System.out.println("MemberChallengeStatusDAO-selectByMemNo()진입");
		//확인용 출력
	
		//3.객체준비
		//mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing
		String sql = "select chall_manual_no,chall_no,mem_no,chall_ing_status,start_date,final_date,chall_result "
				+ "from member_chall_status "
				+ "where chall_manual_no=?";
		MemberChallStatus memberChallStatus = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, chall_manual_no);
			rs = stmt.executeQuery();
			
			if(rs.next()) {//조회한 아이디와 일치하는 회원이 존재한다면
				int chall_manual_no2 = rs.getInt("chall_manual_no");
				int chall_no = rs.getInt("chall_no");
				int mem_no = rs.getInt("mem_no");
				int chall_ing_status = rs.getInt("chall_ing_status");
				Date start_date = toDate(rs.getTimestamp("start_date"));
				Date final_date = toDate(rs.getTimestamp("final_date"));
				String chall_result = rs.getString("chall_result");
				
				memberChallStatus = new MemberChallStatus(chall_manual_no2, chall_no, mem_no, chall_ing_status, start_date, final_date, chall_result);
			}
			//4.쿼리실행
			return memberChallStatus;
		}finally {//5.자원반납
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);		
		}
	}

	//delete
	public int delete(Connection conn, int chall_manual_no)  throws SQLException {
		String sql = "delete from member_chall_status where chall_manual_no=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,chall_manual_no);
			return stmt.executeUpdate();
			//delete가 성공되면 1리턴, 실패시 0리턴
		}finally {
			JdbcUtil.close(stmt);
		}
	}

	public String selectByChallName(Connection conn, int chall_no) throws SQLException
 {
		String sql="SELECT chall_title FROM challenge3.challenge_board where chall_no=?";
		PreparedStatement stmt =null;
		ResultSet rs = null;
		String chall_name = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, chall_no);
			rs = stmt.executeQuery();
			
			if(rs.next()) {//조회한 아이디와 일치하는 회원이 존재한다면
				chall_name = rs.getString("chall_title");
			}
			//4.쿼리실행
			return chall_name;
		}finally {//5.자원반납
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);		
		}
	}
}
