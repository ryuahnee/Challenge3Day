package qnaboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import qnaboard.model.Qna_answer_boardDTO;

public class Qna_answer_boardDAO {

	// 답변 조회 (특정)
	public Qna_answer_boardDTO qna_answerDetail(Connection conn,int qnano) {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Qna_answer_boardDTO qna_answer_boardDTO = null ;
		
		String sql ="select qna_a_no, qna_no, qna_a_con, qna_a_date from qna_answer_board where qna_no = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnano);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int qna_a_no =rs.getInt("qna_a_no");
				int qna_no =rs.getInt("qna_no");
				String qna_a_con =rs.getString("qna_a_con");
				Date qna_a_date =rs.getDate("qna_a_date");
				
				qna_answer_boardDTO = new Qna_answer_boardDTO(
						qna_a_no,qna_no,qna_a_con,qna_a_date);
				 System.out.println("qna_a_con value: " + qna_answer_boardDTO.getQna_a_con());
			}
				return qna_answer_boardDTO;
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
	
	
	//답변등록
	public int qna_answerWrite(Connection conn,int qna_no,String qna_a_con) {
		PreparedStatement pstmt = null;
		
		String sql = "insert into qna_answer_board (qna_no, qna_a_con) value ( ? , ? )";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_no);
			pstmt.setString(2, qna_a_con);
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				JdbcUtil.close(pstmt);
			}
		}
		return -1;		//실패시 -1을 리턴한다.	
	}
	
	
	//답변등록 시 회원의 qna_fin_check는 'Y'가 된다.
		public int qna_check_Y(Connection conn,int qna_no) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String sql = "update qna_board set qna_fin_check = 'Y' where qna_no = ? ";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, qna_no);
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
	

		//답변삭제시 회원의 qna_fin_check는 N'가 된다.
			public int qna_check_N(Connection conn,int qna_no) {
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				String sql = "update qna_board set qna_fin_check = 'N' where qna_no = ? ";
				
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, qna_no);
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
		
		
	// 답변수정
		public int qna_answerModify(Connection conn,String qna_a_con) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "update qna_answer_board set qna_a_con = ? ,  qna_a_date = now()";
			try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, qna_a_con);
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
		
	// 답변 삭제
	public int qna_answerqnadelete(Connection conn, int qna_no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "delete from qna_answer_board where qna_no = ? ";
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

	
	
}
