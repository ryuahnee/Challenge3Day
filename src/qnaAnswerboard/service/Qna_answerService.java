package qnaAnswerboard.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import qnaboard.dao.Qna_answer_boardDAO;
import qnaboard.model.Qna_answer_boardDTO;

public class Qna_answerService {
	
	private Qna_answer_boardDAO qna_answer_boardDAO = new Qna_answer_boardDAO();
	
// 	상세조회
	public Qna_answer_boardDTO qna_answerDetail(int qna_no) {
		System.out.println("answerService의 qna_answerDetail()진입");
		Connection conn;
		
		try {
			conn = ConnectionProvider.getConnection();
			Qna_answer_boardDTO qna_answer_boardDTO = qna_answer_boardDAO.qna_answerDetail(conn, qna_no);
			
			return qna_answer_boardDTO;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	답변등록시 회원의 qna_fin_check -> 'Y'가 된다.
	public void qna_answerWrite(int qna_no,String qna_a_con,String id,String mem_email,String userid) {
		System.out.println("Qna_answerService의 qna_answerWrite진입");
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			if(id.equals("admin")) {
				qna_answer_boardDAO.qna_answerWrite(conn, qna_no, qna_a_con);
				qna_answer_boardDAO.qna_check_Y(conn, qna_no);
				conn.commit();
				// 메일발
				Qnamail.mailservice(mem_email, userid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
//	답변등록시 회원의 qna_fin_check -> 'Y'가 된다.
//	public void qna_answerWrite(int qna_no,String qna_a_con,String id) {
//		System.out.println("Qna_answerService의 qna_answerWrite진입");
//		Connection conn = null;
//		try {
//			conn = ConnectionProvider.getConnection();
//			conn.setAutoCommit(false);
//			if(id.equals("admin")) {
//				qna_answer_boardDAO.qna_answerWrite(conn, qna_no, qna_a_con);
//				qna_answer_boardDAO.qna_check_Y(conn, qna_no);
//			}
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JdbcUtil.rollback(conn);
//		} finally {
//			JdbcUtil.close(conn);
//		}
//	}
	
//	답변수정 (고객이 조회시 수정이 불가하는 조건문이 필요할듯)
	public void qna_answerModify(String qna_a_con) {
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			qna_answer_boardDAO.qna_answerModify(conn, qna_a_con);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}
	}
	
//	삭제시 고객 답변 여부도 N이 된다 
	//(고객이 조회시 삭제가 불가한 조건문이 필요할듯)
	public void qna_answerDelete(int qna_no) {
		System.out.println("Qna_boardService의 qnadelete()진입");
		Connection conn = null;
		
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				
				qna_answer_boardDAO.qna_answerqnadelete(conn, qna_no);
				qna_answer_boardDAO.qna_check_N(conn, qna_no);
				
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				JdbcUtil.rollback(conn);
			}
		}
	
}
