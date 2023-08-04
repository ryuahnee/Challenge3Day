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
import member.model.Member;

//교재 DAO-p592
//1.드라이버로딩->2.커넥션얻기->3.객체준비하기->4.쿼리실행->5.자원반납
//1.드라이버로딩은 환경설정단계에서 준비완료
//2.커넥션얻기는 service로부터 전달받는다
//5.자원반환 - (여기서는 대부분)connection자원반납은 service에서 / 그외 rs,stmt는 DAO에서 반납
public class MemberDAO {
	//아이디로 사람찾기
	public Member selectById(Connection conn, String id) throws SQLException {
		System.out.println("MemberDAO-selectById()진입");
		System.out.println(id);
		//확인용 출력
	
		//3.객체준비
		//mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing
		String sql = "select mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing" + 
				" from member" + 
				" where id=?";
		Member member = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {//조회한 아이디와 일치하는 회원이 존재한다면
				int mem_no = rs.getInt("mem_no");
				String id2 = rs.getString("id");
				String pwd = rs.getString("pwd");
				String mem_name = rs.getString("mem_name");
				String nickname= rs.getString("nickname");
				Date birthyear=rs.getDate("birthyear");
				String gender= rs.getString("gender");
				Date join_date=toDate(rs.getTimestamp("join_date"));
				String mem_email= rs.getString("mem_email");
				String isMarketing= rs.getString("isMarketing");
				//member테이블의 join_date컬럼의 값을  Timestamp타임으로 저장한 뒤
				//Date타입으로 형변환
				
				member = new Member(mem_no,id2,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing);
				System.out.println(member);
			}
			//4.쿼리실행
			return member;
		}finally {//5.자원반납
			System.out.println("DAO"+member);
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);			
		}
	}
	//닉네임으로 사람찾기
	public Member selectByNickName(Connection conn, String nickname) throws SQLException {
		//3.객체준비
		//mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing
		String sql = "select mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing" + 
				" from member" + 
				" where nickName=?";
		Member member = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, nickname);
			rs = stmt.executeQuery();
			
			if(rs.next()) {//조회한 아이디와 일치하는 회원이 존재한다면
				int mem_no = rs.getInt("mem_no");
				String id1 = rs.getString("id");
				String pwd = rs.getString("pwd");
				String mem_name = rs.getString("mem_name");
				String nickname2= rs.getString("nickname");
				String gender= rs.getString("gender");
				String mem_email= rs.getString("mem_email");
				String isMarketing= rs.getString("isMarketing");
				//member테이블의 join_date컬럼의 값을  Timestamp타임으로 저장한 뒤
				//Date타입으로 형변환
				Date birthyear=rs.getDate("birthyear");
				Date join_date=toDate(rs.getTimestamp("join_date"));
				
				member = new Member(mem_no,id1,pwd,mem_name,nickname2,birthyear,gender,join_date,mem_email,isMarketing);
			}
			//4.쿼리실행
			return member;
		}finally {//5.자원반납
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);			
		}
	}
	
	//이메알로 사람찾기
	public Member selectByMemEmail(Connection conn, String mem_email) throws SQLException {
		//3.객체준비
		//mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing
		String sql = "select mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing" + 
				" from member" + 
				" where mem_email=?";
		Member member = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, mem_email);
			rs = stmt.executeQuery();
			
			if(rs.next()) {//조회한 아이디와 일치하는 회원이 존재한다면
				int mem_no = rs.getInt("mem_no");
				String id1 = rs.getString("id");
				String pwd = rs.getString("pwd");
				String mem_name = rs.getString("mem_name");
				String nickname= rs.getString("nickname");
				String gender= rs.getString("gender");
				String mem_email2= rs.getString("mem_email");
				String isMarketing= rs.getString("isMarketing");
				//member테이블의 join_date컬럼의 값을  Timestamp타임으로 저장한 뒤
				//Date타입으로 형변환
				Date birthyear=rs.getDate("birthyear");
				Date join_date=toDate(rs.getTimestamp("join_date"));
				
				member = new Member(mem_no,id1,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email2,isMarketing);
			}
			//4.쿼리실행
			return member;
		}finally {//5.자원반납
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);			
		}
	}
	
	//번호로 사람찾기
	public Member selectByMemNo(Connection conn, int mem_no) throws SQLException {
		System.out.println("selectByMemNo 진입");
		//3.객체준비
		//mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing
		String sql = "select mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing" + 
				" from member" + 
				" where mem_no=?";
		Member member = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mem_no);
			rs = stmt.executeQuery();
			
			if(rs.next()) {//조회한 아이디와 일치하는 회원이 존재한다면
				int mem_no2 = rs.getInt("mem_no");
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String mem_name = rs.getString("mem_name");
				String nickname= rs.getString("nickname");
				String gender= rs.getString("gender");
				String mem_email= rs.getString("mem_email");
				String isMarketing= rs.getString("isMarketing");
				//member테이블의 join_date컬럼의 값을  Timestamp타임으로 저장한 뒤
				//Date타입으로 형변환
				Date birthyear=rs.getDate("birthyear");
				Date join_date=toDate(rs.getTimestamp("join_date"));
				
				member = new Member(mem_no2,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing);
			}
			//4.쿼리실행
			return member;
		}finally {//5.자원반납
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);			
		}
	}
	
	//Timestamp타입을 Date타입으로 현변환p593 38라인
	private Date toDate(Timestamp date) {
		//삼항연산자 : (조건)?조건참일경우:조건거짓일결우;
		return (date==null)?null:new Date(date.getTime());
	}
	
	//insert메서드	
	public void insert(Connection conn, Member member) throws SQLException {
		//확인용 출력
		System.out.println("MemberDAO-insert()진입");

		PreparedStatement stmt =null;
		
		//3.객체준비하기
		String sql="insert into member(mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing) " + 
				"values(0,?,?,?,?,?,?,?,?,?)";
		stmt = conn.prepareStatement(sql);
		
		//4.쿼리실행
		stmt.setString(1, member.getId());
		stmt.setString(2, member.getPwd());
		stmt.setString(3, member.getMem_name());
		stmt.setString(4, member.getNickname());
		stmt.setDate(5, new java.sql.Date(member.getBirthyear().getTime()));
		stmt.setString(6, member.getGender());
		stmt.setTimestamp(7, new Timestamp(member.getJoin_date().getTime()));
		stmt.setString(8, member.getMem_email());
		stmt.setString(9, member.getIsMarketing());
		
		int rowCnt = stmt.executeUpdate();
		System.out.println("MemberDAO-insert()실행결과="+ rowCnt);//콘솔확인용 1이면 성공 0이면 실패
	}
	
	//회원 정보 수정 업데이트
	public int update(Connection conn, int mem_no, String id, String mem_name,String nickname,Date birthyear,String gender,String mem_email,String isMarketing) throws SQLException {
		//3.객체준비하기
		String sql="update member" + 
				" set id=?,mem_name=?,nickname=?,birthyear=?,gender=?,mem_email=?,isMarketing=?" +
				" where mem_no=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		//4.쿼리실행
		stmt.setString(1, id);
		stmt.setString(2, mem_name);
		stmt.setString(3, nickname);
		stmt.setDate(4, new java.sql.Date(birthyear.getTime()));
		stmt.setString(5, gender);
		stmt.setString(6, mem_email);
		stmt.setString(7, isMarketing);
		stmt.setInt(8, mem_no);
		
		int rowCnt = stmt .executeUpdate();
		System.out.println("MemberDAO실행결과="+rowCnt);//콘솔확인 0이면 실패 1이면 성공
		return rowCnt;		
	}
	
	//비번변경
	public int pwdUpdate(Connection conn, Member member) throws SQLException{
		//3.객체준비하기
		String sql="update member" + 
				" set pwd=?" +
				" where mem_no=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		//4.쿼리실행
		stmt.setString(1, member.getPwd());
		stmt.setInt(2, member.getMem_no());
		int rowCnt = stmt .executeUpdate();
		System.out.println("MemberDAO실행결과="+rowCnt);//콘솔확인 0이면 실패 1이면 성공
		return rowCnt;
	}
	
	//delete
	public int delete(Connection conn, int mem_no)  throws SQLException {
		String sql = "delete from member where mem_no=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,mem_no);
			return stmt.executeUpdate();
			//delete가 성공되면 1리턴, 실패시 0리턴
		}finally {
			JdbcUtil.close(stmt);
		}
	}
	
	//-----회원 게시판 관련-----
	//멤버 수 count 메소드
	public int memberCnt(Connection conn, String option, String searchCon) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String search = "";
		if(searchCon!=null && !searchCon.equals("")) {
			search = " where "+option+" like '%"+searchCon.trim()+"%'";
		}
		try {
			String sql="select count(*) from member"+search;
			stmt = conn.prepareStatement(sql);
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
	
	//페이지네이션/리스트 출력 관련
	public List<Member> select(Connection conn,String option, String searchCon, int startRow, int size) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String search = "";
		if(searchCon!=null && !searchCon.equals("")) {
			search = " where "+option+" like '%"+searchCon.trim()+"%'";
		}
			String sql="select mem_no,id,pwd,mem_name,nickname,birthyear,gender,join_date,mem_email,isMarketing" 
					+" from member"+search
					+" order by mem_no desc limit ?,?";
		try {
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1,startRow);
			stmt.setInt(2,size);
			rs = stmt.executeQuery();
			
			List<Member> result = new ArrayList<>();
			while(rs.next()) {
				result.add(convertMember(rs));
			}
			return result;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		
	}
	private Member convertMember(ResultSet rs) throws SQLException {
		return new Member(rs.getInt("mem_no"),
				rs.getString("id"),
				rs.getString("pwd"),
				rs.getString("mem_name"),
				rs.getString("nickname"),
				rs.getDate("birthyear"),
				rs.getString("gender"),
				toDate(rs.getTimestamp("join_date")),
				rs.getString("mem_email"),
				rs.getString("isMarketing"));
	}
}
