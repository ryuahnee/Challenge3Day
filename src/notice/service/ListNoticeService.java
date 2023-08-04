package notice.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import notice.dao.NoticeBoardDAO;
import notice.model.Notice;
import notice.service.NoticeNotFoundException;

public class ListNoticeService {

		//필드
		private NoticeBoardDAO noticeBoardDAO = new NoticeBoardDAO();
		int size = 15; // 1페이지당 출력할 게시글수 

		//5.검색어 조회
		// 공지사항 검색 기능 구현
		  public NoticePage getSearchNotices(String searchField, String searchText, int pageNo, int size) throws ServiceException, SQLException, NamingException {
		        
			  try (Connection conn = ConnectionProvider.getConnection()) {
				    int startRow = (pageNo - 1) * size;
		            List<Notice> searchResults  = noticeBoardDAO.searchNotices(conn, searchField, searchText, startRow, size);
		            int totalNoticeCount = noticeBoardDAO.getTotalNoticeCount(conn, searchField, searchText);
		            int totalPages = (int) Math.ceil((double) totalNoticeCount / size);
		            return new NoticePage(searchResults, pageNo, totalNoticeCount, totalPages);
		        } catch (SQLException e1) {
		            throw new ServiceException("SQL 오류: " + e1.getMessage(), e1);
		        } catch (Exception e) {
		            throw new ServiceException("서비스 오류: " + e.getMessage(), e);
		        }
		  }
		
		
		//1. 총게시글 수 , 글 목록조회
		//파라미터 int pageNum : 보고싶은 페이지 
		// String searchText: 검색창의 입력어, String option: 검색 분류
		public NoticePage getNoticePage(int pageNum) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				
				int total= noticeBoardDAO.selectCount(conn); //총게시글수
				List<Notice> content = noticeBoardDAO.select(conn,(pageNum-1)*size,size); // 목록조회 
				
				/*파라미터  int total		//총게시글수
	 			int pageNum 			//보고싶은 페이지
	 			int size    			//1페이지당 출력할 게시글 수
				List<Notice> content;    //Notice목록 */
				
				NoticePage np = new NoticePage(content, pageNum, size, total);
				System.out.println("getNoticePage()의 결과 np="+np);
				
				return np;
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		//2. 상세조회 
			//파라미터 int nptoNo : 상세조회할 글 번호
			//리턴 Board : 글번호,작성자id,제목,작성일,내용,이미지파일명
			public Notice getDetail(int notiNo) {
				Connection conn;
				try {
					conn = ConnectionProvider.getConnection();
					
					Notice notice = noticeBoardDAO.getDetail(conn, notiNo);
					if(notice==null) {
						throw new NoticeNotFoundException();
					}
					
					return notice;
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}		
			}	
			
	//3.글 등록하기 
	//파라미터 noice: 세션의 회원id,글제목,내용,첨부파일명
	//리턴     savedNoticeNo : 방금전 insert된 글번호
			public int write(Notice notice) {
				Connection conn = null;
				try {
					conn = ConnectionProvider.getConnection();
					conn.setAutoCommit(false);
				
					//작성된 정보는 WriteRequest에 저장됨(제목,내용)
					//리턴 Notice insert 된 글 번호!
					Date now = new Date();
					notice.setNotiDate(now);
					
					int newNoticeNo = noticeBoardDAO.insert(conn, notice);
					if(newNoticeNo==0) {
						throw new RuntimeException("Fail to insert article");
					}
					 
					
					conn.commit();
					
					//입력된 글번호 -> jsp에서  글등록 성공 시 글 상세보기 기능으로 사용
					return newNoticeNo;
					
					
				 } catch (SQLException e) {
				        e.printStackTrace();
				        JdbcUtil.rollback(conn);
				        throw new RuntimeException(e);
				    } catch (RuntimeException e) {
				        e.printStackTrace();
				        JdbcUtil.rollback(conn);
				        throw e;
				    } finally {
				        JdbcUtil.close(conn);
				    }
				}
			
	//글 수정하기
		//파라미터 ModifyRequest modReq-수정처리를 위한 세션에서 가져온  글번호,제목,내용
		public void modify(Notice notice) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				
				//파라미터 board - board:글번호,글수정제목,수정내용 받아오기
				noticeBoardDAO.modify(conn,notice);
							
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				JdbcUtil.rollback(conn);
				throw new RuntimeException(e);
			} catch (RuntimeException e) {
				e.printStackTrace();
				JdbcUtil.rollback(conn);
				throw e;
			} finally {
				JdbcUtil.close(conn);
			}
		}
		
	//글삭제
	//파라미터 no - 삭제할  글번호
	//리턴 int 삭제성공시 삭제된글번호리턴, 실패시 0
		public void deleteNotice(int no) {
			
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				
		//1.Notice테이블에 delete하기전 해당글번호 가져오기
			//파라미터 no - 삭제할  글번호
			//리턴 int 삭제성공시 삭제된글번호리턴, 실패시 0
			Notice notice = noticeBoardDAO.getDetail(conn,no);
			if(notice==null) {
				throw new NoticeNotFoundException();
			}
			
		//2. notice테이블에서 delete 메서드 호출
			noticeBoardDAO.deleteNotice(conn, no);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
		
			
}