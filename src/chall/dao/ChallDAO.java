package chall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chall.model.Chall;
import chall.model.ChallOpin;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;


public class ChallDAO {
	
	//글삭제
	public int delete(Connection conn, int no)  throws SQLException {
		String sql ="delete from challenge_board " + 
								"where chall_no=?";
		PreparedStatement stmt  = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,no);
		
			int deletedCount = stmt.executeUpdate();
			if(deletedCount>0) { //삭제성공시 삭제된글번호리턴, 실패시 0리턴
				return no;				
			}
		}finally {
			JdbcUtil.close(stmt);
		}
		return 0; //삭제실패시 0을 리턴
	}
	


	
	// id값으로 mem_no(pk값 찾기)	
	public int selectBymem_no(Connection conn,String id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select mem_no from member where id = ?";
		
		try {
			conn = ConnectionProvider.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				int mem_no = rs.getInt("mem_no");
				return mem_no;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null||stmt!=null||conn!=null) {
				JdbcUtil.close(rs);
				JdbcUtil.close(stmt);
			}
		}
		return -1;		//실패시 -1을 리턴한다.	
		}

		
		
	//상세보기
	//파라미터 int no : 상세조회할 글 번호
	public Chall getDetail(Connection conn, int no) throws SQLException {
		String sql="select cb.chall_no, cb.cate_no, cb.chall_title, cb.mem_no, m.nickname, cb.chall_write_date, cb.chall_con, cb.certifi_words,c.cate_name " + 
								"from challenge_board cb inner join member m " + 
								"on cb.mem_no = m.mem_no left join category c " + 
								"on cb.cate_no = c.cate_no " + 
								"where chall_no = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, no);
			rs = stmt.executeQuery();
			Chall chall = null;
			if(rs.next()) {
				chall = new Chall();
				//조회된 각 컬럼의 값을 가져와 Chall클래스 객체로 생성한다
				chall.setChallNo(rs.getInt("chall_no"));
				chall.setCateNo(rs.getInt("cate_no"));
				chall.setChallTitle(rs.getString("chall_title"));
				chall.setMemNo(rs.getInt("mem_no"));
				chall.setChallWriteDate(rs.getDate("chall_write_date"));
				chall.setChallCon(rs.getString("chall_con"));
				chall.setCertifiWords(rs.getString("certifi_words"));
				chall.setNickname(rs.getString("nickname"));
				chall.setCateName(rs.getString("cate_name"));
				
				System.out.println("ChallDAO에서  getDetail() chall ="+chall);
			}
			return chall;			
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}	
	}
	
	
	//목록조회(유진님 방식 바꾸면서 바꿈.)
/*	public List<Chall> select(Connection conn,int startRow, int size) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql="select a.chall_no, a.cate_no,chall_title, a.mem_no, a.chall_write_date, a.chall_con, a.certifi_words,b.nickname,c.cate_name " + 
								"from challenge_board a " + 
								"left join member b " + 
								"on a.mem_no = b.mem_no left join category c " + 
								"on a.cate_no = c.cate_no " +
								"order by chall_no desc  limit ?,?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,startRow);
			stmt.setInt(2,size);
			rs = stmt.executeQuery();
			
			List<Chall> result = new ArrayList<Chall>();
			while(rs.next()) {
				result.add( convertChall(rs) ); 
			}
			
			return result;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	*/
	//목록조회(기존)
		public List<Chall> select(Connection conn,String option, String searchCon,int startRow, int size) throws SQLException {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String search = "";
			if(searchCon != null && !searchCon.contentEquals("")) {
				search = "where "+option+" like concat('%', '"+searchCon.trim()+"', '%') ";
			}
			try {
			String sql="select a.chall_no, a.cate_no, a.chall_title, a.mem_no, a.chall_write_date, a.chall_con, a.certifi_words, b.nickname, c.cate_name " + 
									"from challenge_board a " + 
									"left join member b " + 
									"on a.mem_no = b.mem_no left join category c " + 
									"on a.cate_no = c.cate_no " + search+
									"order by chall_no desc  limit ?,?";
			
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1,startRow);
				stmt.setInt(2,size);
				rs = stmt.executeQuery();
				
				List<Chall> result = new ArrayList<Chall>();
				while(rs.next()) {
					result.add( convertChall(rs) ); 
				}
				
				return result;
			}finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(stmt);
			}
		}

		
	//select쿼리를 실행한 결과집합(ResultSet)을 이용하여 Chall클래스의 객체를 생성
	private Chall convertChall(ResultSet rs) throws SQLException {
		return  new Chall(rs.getInt("chall_no"),
				rs.getInt("cate_no"),
				rs.getString("chall_title"),
				rs.getInt("mem_no"),
				rs.getDate("chall_write_date"),
				rs.getString("chall_con"),
				rs.getString("certifi_words"),
				rs.getString("nickname"),
				rs.getString("cate_name"));
		
	}
	
	private Chall convertCateChall(ResultSet rs) throws SQLException {
		return  new Chall(rs.getInt("chall_no"),
						rs.getInt("cate_no"),
						rs.getString("chall_title"),
						rs.getInt("mem_no"),
						rs.getDate("chall_write_date"),
						rs.getString("chall_con"),
						rs.getString("certifi_words"),
						rs.getString("nickname"),
						rs.getString("cate_name"));
	}
	
		
		
	//총 게시물수 조회(유진님 바꾸면서 변경)
/*	public int selectCount(Connection conn) throws SQLException {
		String sql="select count(*) from challenge_board";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			int totalCNT = 0; //총 게시물수를 저장하기 위한 변수 선언 및 초기화
			if(rs.next()) {
				totalCNT = rs.getInt(1);
			}
			return totalCNT;			
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
*/
	//총 게시물수 조회(기존)
		public int selectCount(Connection conn,String option, String searchCon) throws SQLException {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String search = "";
			if(searchCon != null && !searchCon.contentEquals("")) {
				search = "where "+option+" like concat('%', '"+searchCon.trim()+"', '%') ";
				System.out.println("총게시물수 조회search="+search);
			}
			try {
				String sql="select count(*) from challenge_board "+search;
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				int totalCNT = 0; //총 게시물수를 저장하기 위한 변수 선언 및 초기화
				if(rs.next()) {
					totalCNT = rs.getInt(1);
				}
				return totalCNT;			
			}finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(stmt);
			}
		}
	
	//카테고리 총 게시물 수 조회
	public int selectCateCount(Connection conn,int cate_no,String option, String searchCon) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String search = "";
		if(searchCon != null && !searchCon.contentEquals("")) {
			search = "and "+option+" like concat('%', '"+searchCon.trim()+"', '%') ";
			System.out.println("총게시물수 조회search="+search);
		}
		try {
			String sql="select count(*) from challenge_board " + 
					"where cate_no=? "+search;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,cate_no);
			rs = stmt.executeQuery();
			int totalCNT = 0; //총 게시물수를 저장하기 위한 변수 선언 및 초기화
			if(rs.next()) {
				totalCNT = rs.getInt(1);
			}
			return totalCNT;			
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	

	
	//소감조회
	public List<ChallOpin> getOpin(Connection conn, int no) throws SQLException{
		System.out.println("DAO소감 가져오기");
		String sql="select a.chall_opin_no, a.chall_no, a.mem_no, a.opin_con, a.write_date, b.nickname " + 
								"from challenge_opinion a " + 
								"left join member b " + 
								"on a.mem_no=b.mem_no " + 
								"where chall_no=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ChallOpin> challOpinList = new ArrayList<>();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, no);
			rs = stmt.executeQuery();
			while(rs.next()) {
				ChallOpin challopin = new ChallOpin(rs.getInt("chall_opin_no"),
										rs.getInt("chall_no"),
										rs.getInt("mem_no"),
										rs.getString("opin_con"),
										rs.getDate("write_date"),
										rs.getString("nickname")
						);
				challOpinList.add(challopin);
				System.out.println(challopin.getChallNo());
				System.out.println("ChallDAO에서  getOpin() chalopin ="+challopin);
			}
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}	
		return challOpinList;			
	}
		
		
	//검색(기존 검색)
	public  List<Chall> search(Connection conn, String option, String searchCon, int startRow, int size) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String search = "";
		if(searchCon != null && !searchCon.contentEquals("")) {
			search = "where "+option+" like concat('%', '"+searchCon.trim()+"', '%') ";
			System.out.println("search="+search);
		}
		String sql = "select a.chall_no, a.cate_no, a.chall_title, a.mem_no, a.chall_write_date, a.chall_con, a.certifi_words, b.nickname, c.cate_name " + 
									"from challenge_board a " + 
									"left join member b " + 
									"on a.mem_no = b.mem_no left join category c " + 
									"on a.cate_no = c.cate_no " +search+
									"order by chall_no desc  limit ?,?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, startRow);
			stmt.setInt(2, size);
			rs = stmt.executeQuery();
			
			List<Chall> result = new ArrayList<>();
			while(rs.next()) {
				result.add( convertChall(rs) );
			} 
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
		
	
/*	//챌린지 제목 또는 내용 검색(유진님 검색)
	public List<Chall> searchChall(Connection conn, String searchField, String searchText,int startRow, int size) throws SQLException { // 특정 리스트를 받아서 반환 
		
		List<Chall> searchResults  = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		 
	    String sql = "select a.chall_no, a.cate_no,chall_title, a.mem_no, a.chall_write_date, a.chall_con, a.certifi_words, b.nickname,c.cate_name " + 
	    						"from challenge_board a " + 
	    						"left join member b " + 
	    						"on a.mem_no = b.mem_no left join category c " + 
	    						"on a.cate_no = c.cate_no   where " + searchField.trim() + " LIKE ? LIMIT ?, ?";
	    try {
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, "%" + searchText.trim() + "%");
	        stmt.setInt(2, startRow);
	        stmt.setInt(3, size);
	        rs = stmt.executeQuery(); // 쿼리 실행


	        while (rs.next()) {
	            Chall searchN = new Chall(
	                    rs.getInt("chall_no"),
	                    rs.getInt("cate_no"),
	                    rs.getString("chall_title"),
	                    rs.getInt("mem_no"),
	                    rs.getDate("chall_write_date"),
	                    rs.getString("chall_con"),
	                    rs.getString("certifi_words"),
	                    rs.getString("nickname"),
	                    rs.getString("cate_name")
	                );
	            searchResults .add(searchN);
	        }
	    } finally { // 자원 반납
	        JdbcUtil.close(rs);
	        JdbcUtil.close(stmt);
	    }
	    return searchResults ;
	}
	
	//검색결과 수 조회 
    public int getTotalChallCount(Connection conn, String searchField, String searchText) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int totalChallCount = 0;

        String sql = "SELECT COUNT(*) " + 
        							"FROM challenge_board WHERE " + searchField.trim() + "LIKE ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + searchText.trim() + "%");
            rs = stmt.executeQuery();

            if (rs.next()) {
            	totalChallCount = rs.getInt("count");
            }
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(stmt);
        }

        return totalChallCount;
    }
	*/

	//도전중인 사람 수 조회
	public int isChall(Connection conn, int no) throws SQLException {
		String sql="select count(mem_no) " + 
				"from member_chall_status " + 
				"where chall_result = 'I' and chall_no=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, no);
		rs = stmt.executeQuery();
		if(rs.next()) {
			return rs.getInt(1);
		}
		return 0;			
		}finally {
		JdbcUtil.close(rs);
		JdbcUtil.close(stmt);
		}	
	}
	
	
	
	
	//챌린지 챌린지도전하기 누르면 상태값(chall_ing_status) 0으로 시작 
	public int challing(Connection conn, int chall_no, int mem_no) {
		String sql="insert into member_chall_status (chall_no, mem_no) value (?,?)";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			//4.쿼리실행
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1,chall_no);
			stmt.setInt(2,mem_no);
			int result = stmt.executeUpdate();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null||stmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			}
		}
			return -1;		//실패시 -1을 리턴한다.	
	}

	//도전하기 누르고 상태값 변경해주기 전에! 해당 챌린지에 이미 도전했는지 여부 확인
	public int confirmChallHistory(Connection conn, int chall_no, int mem_no) {
		String sql ="select chall_no, mem_no, chall_result " + 
					"from member_chall_status " + 
					"where mem_no = ? and chall_no = ? and chall_result='I'";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int history = 0;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mem_no);
			stmt.setInt(2, chall_no);
			rs = stmt.executeQuery();
			while(rs.next()) {
				history = 1;
			}
			return history;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	
//----------------------------
	//파일 없이 수정하기
	public void modify(Connection conn, Chall chall) throws SQLException {
		System.out.println("ChallDAO-modify()진입");
		//3.객체준비
		String sql ="update challenge_board " + 
								"set cate_no=?, chall_title=?, chall_write_date=now(), chall_con=? ,certifi_words=? " + 
								"where chall_no=? ";
		PreparedStatement stmt  = null; 
		
		try {
			stmt = conn.prepareStatement(sql);
			//4.쿼리실행
			//stmt.set데이터타입(?순서,값);
			stmt.setInt(1,chall.getCateNo());
			stmt.setString(2,chall.getChallTitle());
			stmt.setString(3,chall.getChallCon());
			stmt.setString(4,chall.getCertifiWords());
			stmt.setInt(5,chall.getChallNo());
		
			int updatedCount = stmt.executeUpdate();
			//성공성공시 1리턴, 실패시 0리턴
			System.out.println("dao의 수정레코드수="+updatedCount);

		}finally {
			JdbcUtil.close(stmt);
		}
	}
	
	//챌린지 등록
	public void insert(Connection conn, int cate_no,String chall_title,String chall_con,String certifi_words,String id) throws SQLException {
		PreparedStatement stmt = null;
		String sql = "insert into challenge_board(cate_no,chall_title,chall_write_date,chall_con,certifi_words,mem_no) " + 
								"values(?,?,now(),?,?,?)";
		try {
			int mem_no = selectBymem_no(conn, id);
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,cate_no);
			stmt.setString(2,chall_title);
			stmt.setString(3,chall_con);
			stmt.setString(4,certifi_words);
			stmt.setInt(5,mem_no);

			int insertCnt= stmt.executeUpdate();
			System.out.println("ChallDAO-insert(날짜자동입력)결과="+insertCnt+"행 입력"); 
			
		} finally {
			JdbcUtil.close(stmt);
		}
	}
	

	//카테고리
	public List<Chall> getCate(Connection conn,int cate_no,String option, String searchCon,int startRow, int size) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String search = "";
		if(searchCon!=null && !searchCon.equals("")) {
			search = " where "+option+" like '%"+searchCon.trim()+"%'";
		}
		String sql="select a.chall_no, a.cate_no,chall_title, a.mem_no, a.chall_write_date, a.chall_con, a.certifi_words,b.nickname,c.cate_name " + 
								"from challenge_board a " + 
								"left join member b " + 
								"on a.mem_no = b.mem_no left join category c " + 
								"on a.cate_no = c.cate_no " + 
								"where a.cate_no=? " +search+
								"order by chall_no desc  limit ?,?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,cate_no);
			stmt.setInt(2,startRow);
			stmt.setInt(3,size);
			rs = stmt.executeQuery();
			
			List<Chall> result = new ArrayList<Chall>();
			while(rs.next()) {
				result.add( convertCateChall(rs) ); 
			}
			return result;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}



	
	
}

