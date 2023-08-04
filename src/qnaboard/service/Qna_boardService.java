package qnaboard.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import auth.service.User;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import qnaboard.dao.Qna_boardDAO;
import qnaboard.model.Qna_boardDTO;

public class Qna_boardService {
	
	private Qna_boardDAO qna_boardDAO = new Qna_boardDAO();
	
// 	문의사항 목록조회 . 관리자는 전체 목록을 조회할 수 있다/ 사용자는 본인이 입력한것만 조회할 수 있다.
	public List<Qna_boardDTO> qnaList(String id,int page,String field, String query) {
		System.out.println("Qna_boardService의 qnaList()진입");
		Connection conn;
		List<Qna_boardDTO> list = null;
		
		System.out.println("page:"+page);
		int count = 0;
		
		try {
			conn = ConnectionProvider.getConnection();
			if(!id.equals("admin")) {
				list = qna_boardDAO.mem_qnaList(conn, id, page, field, query);
			}else if(id.equals("admin")) {
				list = qna_boardDAO.admin_qnaList(conn, page, field, query) ;//계산된 페이지수로 구함
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	
// 	문의사항 목록조회 . 관리자는 전체 목록을 조회할 수 있다/ 사용자는 본인이 입력한것만 조회할 수 있다.
//	public List<Qna_boardDTO> qnaList(String id,int page,String field, String query) {
//		System.out.println("Qna_boardService의 qnaList()진입");
//		Connection conn;
//		List<Qna_boardDTO> list = null;
//		
//		System.out.println("page:"+page);
//		int count = 0;
//		
//		try {
//			conn = ConnectionProvider.getConnection();
//			if(!id.equals("admin")) {
//				list = qna_boardDAO.mem_qnaList(conn, id, page, field, query);
//			}else if(id.equals("admin")) {
//				list = qna_boardDAO.admin_qnaList(conn, page, field, query) ;//계산된 페이지수로 구함
//			}
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException();
//		}
//	}
	
// 	상세조회
	public Qna_boardDTO qnaDetail(int qna_no) {
		System.out.println("Qna_boardService의 qnaDetail()진입");
		Connection conn;
		
		try {
			conn = ConnectionProvider.getConnection();
			Qna_boardDTO qna_boardDTO = qna_boardDAO.qna_boardDetail(conn, qna_no);
			
			return qna_boardDTO;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	문의하기 : 파일첨부시 
	public void qnaWrite(String qna_title,String qna_con,String id, String qna_orifile_name) {
		System.out.println("Qna_boardService의 파일첨부된  qnaWrite()진입");
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			qna_boardDAO.qnaWrite(conn, qna_title, qna_con, id, qna_orifile_name);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
//	문의하기 : 파일 미첨부시 
	public void qnaWrite(String qna_title,String qna_con,String id) {
		System.out.println("Qna_boardService의 파일 미첨부qnaWrite()진입");
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//파일이 없을 경우 원본파일,사본파일 전부 null값으로 들어간다.
				qna_boardDAO.qnaWrite(conn, qna_title, qna_con, id);
				
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	

//	수정,  업로드한 파일 변경이 없다면 
	public void qnaModify(String qna_title,String qna_con,String id,int qna_no) {
		System.out.println("Qna_boardService의 qnaModify()진입");
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			qna_boardDAO.qnaModify(conn, qna_title, qna_con, qna_no);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}finally {
			JdbcUtil.close(conn);
		}
	}
	
//	검색 :관리자는 아이디 , 제목으로 조회가 가능하다 
	public List<Qna_boardDTO> qnaRead(String field, String query, String id, int page) {
		System.out.println("Qna_boardService의 qnaRead()진입");
		List<Qna_boardDTO> list = null;
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			System.out.println("field:"+field);
			System.out.println("query:"+query);
			System.out.println("id:"+id);
			//조회값이 null이면 ""으로 조회
			if(query==null) { query = ""; }
			if(!id.equals("admin")) {
				list = qna_boardDAO.mem_qnaRead(conn, field, query, id, page);
			}else if(id.equals("admin")){
				list = qna_boardDAO.admin_qnaRead(conn, field, query, page);
			}
			System.out.println("Qna_boardService의 qnaRead()의 끝 완료");
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
//	삭제 // 기존에 저장된 파일도 삭제된다.	
	public int qnadelete(int qna_no, String qna_fin_check) {
		System.out.println("Qna_boardService의 qnadelete()진입");
		Connection conn = null;
		int result = 0;
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				if(qna_fin_check.equals("N")) {
					result = qna_boardDAO.qnadelete(conn, qna_no);
				}
				conn.commit();
				return result;			//성공 시 1을 반환
			} catch (SQLException e) {
				e.printStackTrace();
				JdbcUtil.rollback(conn);
				return -1;
			}finally {
				JdbcUtil.close(conn);
			}	
	}
	
	//페이지수 계산
	public int lastpage(String id) {
		Connection conn;
		int count = 0;
		try {
			conn = ConnectionProvider.getConnection();
			if(!id.equals("admin")) {
				count =	qna_boardDAO.countpage(conn, id);
			}else if(id.equals("admin")) {
				count =	qna_boardDAO.allCountpage(conn);
			}
			System.out.println("Qna_boardService의 page()진입 :"+(int) Math.ceil((double)count/15) );
			return (int) Math.ceil((double)count/15);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	public int totalpage(String id) {
		Connection conn;
		int count = 0;
		try {
			conn = ConnectionProvider.getConnection();
			if(!id.equals("admin")) {
				count =	qna_boardDAO.countpage(conn, id);
			}else if(id.equals("admin")) {
				count =	qna_boardDAO.allCountpage(conn);
			}
			System.out.println("Qna_boardService의 page()진입 :"+(int) Math.ceil((double)count/15) );
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	
//	수정,test
//	public int qnaModify2(String qna_title,String qna_con,String id, String qna_orifile_name,String new_qna_orifile_name,int qna_no,String deletefileBtn) {
//		System.out.println("Qna_boardService의 qnaModify()진입");
//		Connection conn = null;
//		int result = 0;
//		
//		try {
//			
//			// 새로 업로드한 파일이 있을 경우
//			if (new_qna_orifile_name != null && !new_qna_orifile_name.equals("")) {
//				if (qna_orifile_name != null) {
//					result = qna_boardDAO.qnaModify(conn, qna_title, qna_con, new_qna_orifile_name, qna_no);
//				}else {
//					result = qna_boardDAO.qnaModify(conn, qna_title, qna_con, qna_no);
//				}
//			}
//			
//			//새로 업로드한 파일이 없을 경우 
//			if (new_qna_orifile_name.equals("") || new_qna_orifile_name == null ) {
//				//파일 삭제버튼을 눌렀고 기존 파일값이 있다면  => 기존파일 삭제 
//				if(deletefileBtn.equals("a") && qna_orifile_name !=null){
//					qna_orifile_name = null;
//					result = qna_boardDAO.qnaModify(conn, qna_title, qna_con, qna_orifile_name, qna_no);
//				}else {
//					// 기존 파일값이 있다면  그대로 유지
//					result = qna_boardDAO.qnaModify(conn, qna_title, qna_con, qna_no);
//				}
//			}
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			JdbcUtil.rollback(conn);
//			return -1;
//		}finally {
//			JdbcUtil.close(conn);
//		}
//	}
	
}

