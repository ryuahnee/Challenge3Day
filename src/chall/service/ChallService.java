package chall.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import chall.dao.ChallDAO;
import chall.model.Chall;
import chall.model.ChallOpin;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ChallService {

	private static ChallDAO challDAO = new ChallDAO();
		int size = 15;//1페이지당 출력할 게시글수
		
		
		//상세조회
		public Chall getDetail(int no) {
			Connection conn;
			try {
				conn = ConnectionProvider.getConnection();
				
				Chall challData = challDAO.getDetail(conn, no);
				if(challData==null) {
					throw new ChallNotFoundException();
				}
				System.out.println("서비스-상세조회getDetail");
				return challData;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}		
		}

		
		//(총게시글수+)글목록조회 (유진님 검색)
	/*	public ChallPage  getChallPage(String option, String searchCon, int pageNum) {
			System.out.println("Service-getChallPage()진입");
			Connection conn=null;
			try {
				conn = ConnectionProvider.getConnection();
				
				int total = challDAO.selectCount(conn,option,searchCon); //총게시글수
				System.out.println("total=" + total);
				List<Chall> content = challDAO.select(conn,option,searchCon,(pageNum-1)*size,size);
				
			    ChallPage cp = new ChallPage(total, pageNum, size, content);
				System.out.println("ChallService- getChallPage()의 결과 cp="+cp);
				return cp;
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
*/
		//(총게시글수+)글목록조회 (기존)
		public ChallPage  getChallPage(String option, String searchCon, int pageNum) {
			System.out.println("Service-getChallPage()진입");
			Connection conn=null;
			try {
				conn = ConnectionProvider.getConnection();
				
				int total = challDAO.selectCount(conn,option,searchCon); //총게시글수
				List<Chall> content = challDAO.select(conn, option, searchCon, (pageNum-1)*size, size);
				
			    ChallPage cp = new ChallPage(total, pageNum, size, content);
				return cp;
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		//소감조회
		public List<ChallOpin> getOpin(int no) {
			Connection conn;
			try {
				conn = ConnectionProvider.getConnection();
				
				List<ChallOpin> challOpin = challDAO.getOpin(conn, no);
				System.out.println("no="+no);
				if(challOpin==null) {
					throw new ChallNotFoundException();
				}
				return challOpin;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}		
		}
		

		//수정하기
				public void modify(Chall chall) {
					Connection conn = null;
					try {
						conn = ConnectionProvider.getConnection();
						conn.setAutoCommit(false);
						
						challDAO.modify(conn,chall);
									
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
				
				
		//챌린지 등록
		public void writeChall( int cate_no,String chall_title,String chall_con,String certifi_words,String id) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				if(certifi_words==null||certifi_words.equals("")){ 
					certifi_words ="오늘도 화이팅!";
				}
				challDAO.insert(conn, cate_no,chall_title,chall_con,certifi_words,id);
				conn.commit();
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				JdbcUtil.close(conn);
			}
		}
						
		
		//삭제
		public int delete(int no) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				
				//1.article테이블에 delete하기전 해당글번호 가져오기
				Chall chall = challDAO.getDetail(conn, no);
				if(chall==null) {
					throw new ChallNotFoundException();
				}
				
				//2.chall테이블에 delete하는 메서드호출
				challDAO.delete(conn, no);
				conn.commit();
				return 1;
			} catch (SQLException e) {
				e.printStackTrace();
				JdbcUtil.rollback(conn);
				return -1;
			}finally {
				JdbcUtil.close(conn);
			}
			
		}
	

		
		//검색(원래방식)
		public ChallPage getChallPage(int pageNo, String option, String searchCon) {
			Connection conn = null;

			try {
				conn = ConnectionProvider.getConnection();
			
				int totalCount = challDAO.selectCount(conn,option,searchCon); //총 게시글 수
				List<Chall> searchResult = challDAO.search(conn, option, searchCon, (pageNo-1)*size, size);
			
				ChallPage ep = new ChallPage(totalCount, pageNo, size, searchResult);
				System.out.println("ChallPage의 getChallPage()진입"); 
				return ep;
				
			
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		
		//5.검색어 조회
	// 검색 기능 구현(유진님 방식)
	/*	  public ChallPage getSearchChall(String searchField, String searchText, int pageNo, int size) throws ServiceException, SQLException{
		        
			  try (Connection conn = ConnectionProvider.getConnection()) {
				    int startRow = (pageNo - 1) * size;
		            List<Chall> searchResults  = challDAO.searchChall(conn, searchField, searchText, startRow, size);
		            int totalChallCount = challDAO.getTotalChallCount(conn, searchField, searchText);
		            int totalPages = (int) Math.ceil((double) totalChallCount / size);
		            return new ChallPage(searchResults, pageNo, totalChallCount, totalPages);
		        } catch (SQLException e1) {
		            throw new ServiceException("SQL 오류: " + e1.getMessage(), e1);
		        } catch (Exception e) {
		            throw new ServiceException("서비스 오류: " + e.getMessage(), e);
		        }
		  }
		*/
		
		//도전중인 사람 수 가져오기
		public int  isChall(int no) {
			System.out.println("서비스 isChall");
			Connection conn;
			try {
				conn = ConnectionProvider.getConnection();
				int count = challDAO.isChall(conn, no);
				return count;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}		
			}

		public int insertchall(Chall chall) {
			return 0;
		}
		
		
		
		//도전하기 누르면 상태변경
		public int challing( int chall_no, int mem_no) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				
				int resuelt = challDAO.challing(conn, chall_no,mem_no);
				
				conn.commit();
				return resuelt;
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
		
		//똑같은 챌린지에 도전중인지 확인
		public int confirmChallHistory(int chall_no, int mem_no) throws SQLException {
			Connection conn = null;
			conn = ConnectionProvider.getConnection();
			int history = 0;
			history = challDAO.confirmChallHistory(conn, chall_no, mem_no); //조회 결과 있으면 1, 없으면 0
			
			return history;
		}
	



	//카테고리
	public ChallPage  getCate(int cate_no,String option, String searchCon,int pageNum) {
		System.out.println("서비스 getCate진입");
		Connection conn=null;
		try {
			conn = ConnectionProvider.getConnection();
			
			int total = challDAO.selectCount(conn,option,searchCon); //총게시글수
			List<Chall> content = challDAO.getCate(conn,cate_no,option,searchCon,(pageNum-1)*size,size);//목록조회
			
		    ChallPage cp = new ChallPage(total, pageNum, size, content);
			System.out.println("ChallService- getChallPage()의 결과 cp="+cp);
			return cp;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//카테고리 목록조회
	public ChallPage  getCateChallPage(int cate_no,String option, String searchCon,int pageNo) {
		Connection conn=null;
		try {
			conn = ConnectionProvider.getConnection();
			
			int total = challDAO.selectCateCount(conn,cate_no,option,searchCon); //총게시글수
			List<Chall> content = challDAO.getCate(conn,cate_no,option,searchCon,(pageNo-1)*size,size);
			
		    ChallPage cp = new ChallPage(total, pageNo, size, content);
			System.out.println("ChallService- getChallPage()의 결과 cp="+cp);
			return cp;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}