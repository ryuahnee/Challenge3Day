package notice.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import notice.model.Notice;

//notice 테이블  db작업용 클래스
public class NoticeBoardDAO {

	
	//검색결과 수 조회 
    public int getTotalNoticeCount(Connection conn, String searchField, String searchText) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int totalNoticeCount = 0;

        String sql = "SELECT COUNT(*) AS count FROM notice_board WHERE " + searchField.trim() + " LIKE ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + searchText.trim() + "%");
            rs = stmt.executeQuery();

            if (rs.next()) {
                totalNoticeCount = rs.getInt("count");
            }
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(stmt);
        }

        return totalNoticeCount;
    }
	
	
	
	
	
	// 공지사항 제목 또는 내용 검색
	public List<Notice> searchNotices(Connection conn, String searchField, String searchText,int startRow, int size) throws SQLException { // 특정 리스트를 받아서 반환 
		
		List<Notice> searchResults  = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		 
	    String sql = "SELECT * FROM notice_board WHERE " + searchField.trim() + " LIKE ? order by noti_no desc LIMIT ?, ?";
	    try {
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, "%" + searchText.trim() + "%");
	        stmt.setInt(2, startRow);
	        stmt.setInt(3, size);
	        rs = stmt.executeQuery(); // 쿼리 실행


	        while (rs.next()) {
	            Notice searchN = new Notice(
	                    rs.getInt("noti_no"),
	                    rs.getString("noti_title"),
	                    rs.getString("noti_con"),
	                    rs.getDate("noti_date"));

	            searchResults .add(searchN);
	        }
	    } finally { // 자원 반납
	        JdbcUtil.close(rs);
	        JdbcUtil.close(stmt);
	    }
	    return searchResults ;
	}
	
	/*sql 조건문이 다음과 같으면 option 값이 각각 받지 못함 
	 *  Unknown column 'notiNo' in 'where clause']
	 *  Unknown column 'notiNo' in 'where clause'
	 *  
	 * if (searchText != null && !searchText.equals("") && "notiNo".equals(option))
	 * { search = " where " + option + " like '%" + searchText.trim() + "%'";
	 * 
	 * }
	 */
	
	/*String search="";
	if (searchText != null && !searchText.equals("")) {
        if ("notiNo".equals(option)) { //검색창 글번호로 검색 시 
            search = " where noti_no like '%" + searchText.trim() + "%'";
        } else if ("notiTitle".equals(option)) { //검색창 글 제목으로 검색 시 
            search = " where noti_title like '%" + searchText.trim() + "%'";
        }
    }*.
	

	// 목록조회 
	/*파라미터  int startRow-페이지에 따른 row시작번호
	int size    -1페이지당 출력할 게시글수  */
	
	public List<Notice> select(Connection conn,int startRow, int size) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
	
			String sql="select noti_no, noti_title, noti_date" 
				+" from notice_board"
				+" order by noti_no desc limit ?,?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,startRow);
			stmt.setInt(2,size);
			rs = stmt.executeQuery();
			
			List<Notice> result = new ArrayList<Notice>();
			while(rs.next()) {
				result.add( convertNotice(rs) ); 
			}
			
			return result;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	//삭제-delete쿼리를 통한 글삭제
	public int deleteNotice(Connection conn, int notiNo)  throws SQLException {
		String sql = "delete from notice_board where noti_no=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			
			//쿼리실행 
			stmt.setInt(1,notiNo);
			
			
			int deletedCount = stmt.executeUpdate();
			//삭제성공시 삭제된글번호리턴, 실패시 0리턴
			if(deletedCount>0) {
				return notiNo;				
			}
			
		}finally {
			JdbcUtil.close(stmt);
		}
		return 0; //삭제실패시 0을 리턴
	}
	
			
	//공지사항 수정
	/*파라미터  int no : update하고자하는 글번호 
    String title : 수정하고자하는 새 제목 	 
	 *리턴 		int : update가 성공되면 1리턴, 실패시 0리턴
	 */
	public void modify(Connection conn, Notice notice)  throws SQLException {
		System.out.println("NoticeBoardDAO - modify() 진입 ");
	String sql = "update notice_board " + 
			 "set noti_title=?, noti_con=?, noti_date=now() " + 
			 "where noti_no=?";
	PreparedStatement stmt = null;
	try {
	stmt = conn.prepareStatement(sql);
	
	stmt.setString(1,notice.getNotiTitle()); //제목
	stmt.setString(2,notice.getNotiContent()); // 내용 
	stmt.setInt(3,notice.getNotiNo()); // 글번호
	
	int updatedCount=stmt.executeUpdate();
	//update가 성공되면 1리턴, 실패시 0리턴
	System.out.println("dao레코드수 = "+updatedCount);
	}finally {
	JdbcUtil.close(stmt);
	}
}
	
	
	//상세보기
	//파라미터 int no 상세조회할 글 번호
	//리턴 : 글번호, 제목, 작성일 ,내용 
	public Notice getDetail(Connection conn, int notiNo) throws SQLException {
		String sql="select noti_no,noti_title,noti_con,noti_date " + 
				   "from notice_board " + 
				   "where noti_no=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, notiNo);
			
			rs = stmt.executeQuery();
			Notice notice = null;
			if(rs.next()) {
				notice = new Notice();
			//조회된 각 컬럼의 값을 가져와 Notice클래스 객체로 생성한다
			notice.setNotiNo(rs.getInt("noti_no"));	
			notice.setNotiTitle(rs.getString("noti_title"));	
			notice.setNotiContent(rs.getString("noti_con"));	
			notice.setNotiDate(rs.getDate("noti_date"));	
			
		}
			return notice;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}	
	
	

	//select쿼리를 실행한 결과집합(ResultSet)을 이용하여 Notice클래스의 객체를 생성
	private Notice convertNotice(ResultSet rs) throws SQLException {

	    return new Notice(rs.getInt("noti_no"),
	    		rs.getString("noti_title"), null,
	    		rs.getDate("noti_date"));
	}

	
	
	//총 게시물수 조회
	public int selectCount(Connection conn) throws SQLException {
		String sql="select count(*) from notice_board";
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
	
	//공지 등록 : 삽입 - 글 제목, 글 내용, 작성일
	//리턴 : inserted된 정보 글번호,글 제목, 글 내용, 작성일
	public int insert(Connection conn,Notice notice) throws SQLException {
		System.out.println("NoticeDAO-insert()진입");

		String sql="insert into notice_board(noti_title, noti_con, noti_date) "+
					"values(?,?,now())";
		
		PreparedStatement stmt = null; //insert
		int insertedCount = 0;
		try {
			stmt = conn.prepareStatement(sql);
			
			//4.쿼리실행
			stmt.setString(1,notice.getNotiTitle());
			stmt.setString(2,notice.getNotiContent());
			
			insertedCount = stmt.executeUpdate();
			//입력성공시 1리턴, 실패시 0리턴

	    } finally {
	        JdbcUtil.close(stmt);
	    }
		return insertedCount;
	}
	
	//p635 52라인 - Date타입을 Timestamp타입으로 변환 
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
	

	public Notice selectById(Connection conn, int notiNo) {
		// TODO Auto-generated method stub
		return null;
	}









	
}