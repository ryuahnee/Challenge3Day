package qnaboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import auth.service.User;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import qnaboard.model.Qna_boardDTO;

public class Qna_boardDAO {
	
//	list 조회 시 사용 : 관리자 전용 (회원 id도 확인가능)
public List<Qna_boardDTO> admin_qnaList(Connection conn,int page, String field,String query) {
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	List<Qna_boardDTO> list = new ArrayList<>();
	
	String sql ="SELECT row_number() OVER (ORDER BY qna_date DESC) AS num, a.qna_no,a.mem_no, b.id, a.qna_title, a.qna_con, a.qna_date,a.qna_fin_check, a.qna_orifile_name, a.qna_copyfile_name " + 
				"FROM qna_board a " + 
				"LEFT JOIN member b ON a.mem_no = b.mem_no " +
				"where "+ field + " like ? " +
				"LIMIT ? , ? ";
	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + query + "%");
			pstmt.setInt(2, (page-1)*15);
			pstmt.setInt(3, page*15);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Qna_boardDTO qna_boardDTO =  new Qna_boardDTO(
						rs.getInt("qna_no"),
						rs.getString("qna_title"),
						rs.getString("qna_con"),
						rs.getInt("mem_no"),
						rs.getDate("qna_date"),
						rs.getString("qna_fin_check"),
						rs.getString("qna_orifile_name"),
						rs.getInt("qna_copyfile_name"),
						rs.getString("id"),
						rs.getInt("num"));
					list.add(qna_boardDTO);
					}
			return list;
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		if(rs!=null||pstmt!=null||conn!=null) {
		JdbcUtil.close(rs);
		JdbcUtil.close(pstmt);
		}
	}
	return null;
}

//	list 조회 시 사용 : 회원 전용
	public List<Qna_boardDTO> mem_qnaList(Connection conn, String id, int page, String field,String query) {
		int mem_no = selectBymem_no(conn,id); // mem_no의 값을 가져옴
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<Qna_boardDTO> list = new ArrayList<>();
		
		String sql ="SELECT row_number() OVER (ORDER BY qna_date DESC) AS num, qna_no, mem_no, qna_title, qna_con, qna_date, qna_fin_check, qna_orifile_name, qna_copyfile_name " + 
				"FROM qna_board " + 
				"WHERE "+field+" like ? && mem_no = ? " +
				"LIMIT ? , ? ";
		
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + query + "%");
				pstmt.setInt(2, mem_no);
				pstmt.setInt(3, (page-1)*15);
				pstmt.setInt(4, page*15);
				rs = pstmt.executeQuery();
			while(rs.next()) {
				Qna_boardDTO qna_boardDTO =  new Qna_boardDTO(
						rs.getInt("qna_no"),
						rs.getString("qna_title"),
						rs.getString("qna_con"),
						rs.getInt("mem_no"),
						rs.getDate("qna_date"),
						rs.getString("qna_fin_check"),
						rs.getString("qna_orifile_name"),
						rs.getInt("qna_copyfile_name"));
					list.add(qna_boardDTO);
			}
				return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null||pstmt!=null||conn!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			}
		}
		return null;
	}
	
//	상세조회 : 문의사항 번호에 따른 조회 +id 포함문
	public Qna_boardDTO qna_boardDetail(Connection conn,int qnano) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Qna_boardDTO qna_boardDTO =null ;
		
		String sql ="select  a.qna_no,a.mem_no, b.id, a.qna_title, a.qna_con, a.qna_date,a.qna_fin_check, a.qna_orifile_name, a.qna_copyfile_name, b.mem_email " + 
				"from qna_board a  " + 
				"left join member b " + 
				"on a.mem_no = b.mem_no " + 
				"where a.qna_no = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnano);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int qna_no =rs.getInt("qna_no");
				int mem_no =rs.getInt("mem_no");
				String id =rs.getString("id");
				String qna_title =rs.getString("qna_title");
				String qna_con =rs.getString("qna_con");
				Date qna_date =rs.getDate("qna_date");
				String qna_fin_check =rs.getString("qna_fin_check");
				String qna_orifile_name =rs.getString("qna_orifile_name");
				int qna_copyfile_name =rs.getInt("qna_copyfile_name");
				String mem_email =rs.getString("mem_email");
				
				qna_boardDTO = new Qna_boardDTO(
						qna_no, qna_title,  qna_con,  mem_no,  qna_date,
						 qna_fin_check,  qna_orifile_name,  qna_copyfile_name,  id,  mem_email
						);}
			
//				qna_boardDTO = new Qna_boardDTO(
//						qna_no, mem_no, id, qna_title, qna_con, qna_date, qna_fin_check, qna_orifile_name, qna_copyfile_name, mem_email
//			);}
				return qna_boardDTO;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null||pstmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			}
		}
			return null;
	}
	
//		문의사항  : 원본파일 첨부시 
	public int qnaWrite(Connection conn,String qna_title,String qna_con, String id,String qna_orifile_name) {
		System.out.println("원본파일첨부한 DAO");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO qna_board (qna_title, qna_con, mem_no, qna_orifile_name, qna_copyfile_name) " + 
				"SELECT ?, ?, ?, ? , " + 
				"IFNULL(( " + 
				"SELECT CONCAT(DATE_FORMAT(CURDATE(), '%Y%m%d'), LPAD(MAX(SUBSTRING(qna_copyfile_name, 9)) + 1, 2, '0')) " + 
				"FROM qna_board " + 
				"WHERE DATE(qna_date) = CURDATE()), " + 
				"CONCAT(DATE_FORMAT(CURDATE(), '%Y%m%d'), '01') " + 
				") " + 
				"FROM dual";
		try {
			int mem_no = selectBymem_no(conn, id);	//mem_no값을 가져옴
			pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, qna_title);
				pstmt.setString(2, qna_con);
				pstmt.setInt(3, mem_no);
				pstmt.setString(4, qna_orifile_name);
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null||pstmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			}
		}
			return -1;		//실패시 -1을 리턴한다.	
	}
	
	
//		문의사항 	 : 원본파일 미첨부시 
	public int qnaWrite(Connection conn,String qna_title,String qna_con, String id) {
		System.out.println("원본파일미첨부한 DAO");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "INSERT INTO qna_board (qna_title, qna_con, mem_no) " + 
					"value (?,?,?)";
		try {
			int mem_no = selectBymem_no(conn, id);	//mem_no값을 가져옴
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna_title);
			pstmt.setString(2, qna_con);
			pstmt.setInt(3, mem_no);
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null||pstmt!=null) {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
		}
		return -1;		//실패시 -1을 리턴한다.	
	}
	
	
// id값으로 mem_no(pk값 찾기)	
	public int selectBymem_no(Connection conn,String id) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select mem_no from member where id = ?";
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int mem_no = rs.getInt("mem_no");
				return mem_no;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null||pstmt!=null||conn!=null) {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
		}
		return -1;		//실패시 -1을 리턴한다.	
	}
	
//	update : 원본파일 유지 update문 
	public int qnaModify(Connection conn,String qna_title,String qna_con,int qna_no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		String sql = "UPDATE qna_board set qna_title = ? ,qna_con = ?,qna_date = now() where qna_no= ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna_title);
			pstmt.setString(2, qna_con);
			pstmt.setInt(3, qna_no);
			int result = pstmt.executeUpdate();
		return result;			//성공시 1을 반환
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		if(rs!=null||pstmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	return -1;					//실패시 -1을 반환
}
	
	// 수정 : 기존파일이 없을 경우 수정시에 파일을 업로드 하는 쿼리문(사본파일명을 생성한다)	
	public int qnaModify(Connection conn,String qna_title,String qna_con,String qna_orifile_name, int qna_no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		String sql = "UPDATE qna_board " +
                "SET qna_title = ? , " +
                "    qna_con = ? , " +
                "    qna_date = NOW(), " +
                "    qna_orifile_name = ? , " +
                "    qna_copyfile_name = IFNULL( " +
                "        ( " +
                "            SELECT CONCAT(DATE_FORMAT(CURDATE(), '%Y%m%d'), LPAD(MAX(SUBSTRING(tmp.qna_copyfile_name, 9)) + 1, 2, '0')) " +
                "            FROM (SELECT qna_copyfile_name FROM qna_board WHERE DATE(qna_date) = CURDATE()) AS tmp " +
                "        ), " +
                "        CONCAT(DATE_FORMAT(CURDATE(), '%Y%m%d'), '01') " +
                "    ) " +
                "WHERE qna_no = ? ";
				
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna_title);
			pstmt.setString(2, qna_con);
			pstmt.setString(3, qna_orifile_name);
			pstmt.setInt(4, qna_no);
			int result = pstmt.executeUpdate();
		return result;			//성공시 1을 반환
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		if(rs!=null||pstmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	return -1;					//실패시 -1을 반환
}
	
//	원본파일 미첨부 시 사본값을 지우는 쿼리문 
	public int qnaModify_nullfile(Connection conn,String qna_orifile_name,int qna_no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		String sql = "UPDATE qna_board SET qna_copyfile_name = IF( ? IS NULL, NULL, qna_copyfile_name) "
					+ "WHERE qna_no = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna_orifile_name);
			pstmt.setInt(2, qna_no);
			int result = pstmt.executeUpdate();
		return result;			//성공시 1을 반환
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		if(rs!=null||pstmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	return -1;					//실패시 -1을 반환
}
	
//	검색기능 : 고객용 
	public List<Qna_boardDTO> mem_qnaRead(Connection conn, String field, String query, String id, int page) {
		int mem_no = selectBymem_no(conn,id); // mem_no의 값을 가져옴
		System.out.println("dao진입 ");
		System.out.println("field " + field);
		System.out.println("query " + query);
		System.out.println("id " +id);
		System.out.println("page " +page);
		System.out.println("mem_no " +mem_no);
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<Qna_boardDTO> list = new ArrayList<>();
		
		String sql = "select  row_number() over (order by a.qna_date desc)num, a.qna_no,a.mem_no, b.id, a.qna_title, a.qna_con, a.qna_date,a.qna_fin_check, a.qna_orifile_name, a.qna_copyfile_name " + 
				"from qna_board a " + 
				"left join member b " + 
				"on a.mem_no = b.mem_no " + 
				"where " + field +" like ?  && a.mem_no= ? " +
				"LIMIT ? , ? ";
		try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + query + "%");
				pstmt.setInt(2, mem_no);
				pstmt.setInt(3, (page-1)*15);
				pstmt.setInt(4, page*15);
				rs = pstmt.executeQuery();
				int count = 0;
			while(rs.next()) {
				Qna_boardDTO qna_boardDTO = new Qna_boardDTO(
						rs.getInt("qna_no"),
						rs.getString("qna_title"),
						rs.getString("qna_con"),
						rs.getInt("mem_no"),
						rs.getDate("qna_date"),
						rs.getString("qna_fin_check"),
						rs.getString("qna_orifile_name"),
						rs.getInt("qna_copyfile_name"));
				list.add(qna_boardDTO);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null||pstmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			}
		}
		return null;
	}
	
//	검색기능 : 관리자용
	public List<Qna_boardDTO> admin_qnaRead(Connection conn, String field, String query, int page) {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<Qna_boardDTO> list = new ArrayList<>();
		
		//관리자용 검색쿼리문 
		String sql = "select row_number() over (order by a.qna_date desc)num, a.qna_no,a.mem_no, b.id, a.qna_title, a.qna_con, a.qna_date,a.qna_fin_check, a.qna_orifile_name, a.qna_copyfile_name " + 
				"from qna_board a " + 
				"left join member b " + 
				"on a.mem_no = b.mem_no " + 
				"where " + field +" like ? " +
				"LIMIT ? , ? ";
		
		try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + query + "%");
				pstmt.setInt(2, (page-1)*15);
				pstmt.setInt(3, page*15);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Qna_boardDTO qna_boardDTO =  new Qna_boardDTO(
							rs.getInt("qna_no"),
							rs.getString("qna_title"),
							rs.getString("qna_con"),
							rs.getInt("mem_no"),
							rs.getDate("qna_date"),
							rs.getString("qna_fin_check"),
							rs.getString("qna_orifile_name"),
							rs.getInt("qna_copyfile_name"),
							rs.getString("id"),
							rs.getInt("num"));
						list.add(qna_boardDTO);
				}
				return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null||pstmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			}
		}
		return null;
	}
	
//	검색기능 : 관리자용
//	public List<Qna_boardDTO> admin_qnaRead(Connection conn, String field, String query, int page) {
//		
//		ResultSet rs = null;
//		PreparedStatement pstmt = null;
//		List<Qna_boardDTO> list = new ArrayList<>();
//		
//		//관리자용 검색쿼리문 
//		String sql = "select row_number() over (order by a.qna_date desc)num, a.qna_no,a.mem_no, b.id, a.qna_title, a.qna_con, a.qna_date,a.qna_fin_check, a.qna_orifile_name, a.qna_copyfile_name " + 
//				"from qna_board a " + 
//				"left join member b " + 
//				"on a.mem_no = b.mem_no " + 
//				"where " + field +" like ? " +
//				"LIMIT ? , ? ";
//		
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, "%" + query + "%");
//			pstmt.setInt(2, (page-1)*15);
//			pstmt.setInt(3, page*15);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				Qna_boardDTO qna_boardDTO =  new Qna_boardDTO(
//						rs.getInt("qna_no"),
//						rs.getInt("mem_no"),
//						rs.getString("id"),
//						rs.getString("qna_title"),
//						rs.getString("qna_con"),
//						rs.getDate("qna_date"),
//						rs.getString("qna_fin_check"),
//						rs.getString("qna_orifile_name"),
//						rs.getInt("qna_copyfile_name"),
//						rs.getInt("num"));
//				list.add(qna_boardDTO);
//			}
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			if(rs!=null||pstmt!=null) {
//				JdbcUtil.close(rs);
//				JdbcUtil.close(pstmt);
//			}
//		}
//		return null;
//	}
	
//	삭제 문
	public int qnadelete(Connection conn,int qna_no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "delete from qna_board where qna_no= ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_no);
			int result = pstmt.executeUpdate();
			return result;			//성공시 1을 반환
		} catch (SQLException e) {
			e.printStackTrace();
		}finally 	{
			if(rs!=null||pstmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			}
		}
		return -1;					//실패시 -1을 반환
	}
	
	
	//페이지 수 
	public int countpage(Connection conn,String id) {
		int mem_no = selectBymem_no(conn,id); // mem_no의 값을 가져옴
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String sql = "select count(qna_no) from qna_board where mem_no = "+mem_no;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
				}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally 	{
			if(rs!=null||pstmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			}
		}
		return -1;					//실패시 -1을 반환
	}
	//페이지 수  전체
	public int allCountpage(Connection conn) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String sql = "select count(qna_no) from qna_board ";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
				}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally 	{
			if(rs!=null||pstmt!=null) {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			}
		}
		return -1;					//실패시 -1을 반환
	}
	
	
} //class
