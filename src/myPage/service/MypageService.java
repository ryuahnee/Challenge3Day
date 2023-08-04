package myPage.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import auth.service.User;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import myPage.dao.MypageDAO;
import myPage.model.MypageDTO;

public class MypageService {
	
	private MypageDAO mypageDAO = new MypageDAO();
	
// 	챌린지 조회 chall_result : s, i ,f 가 올 수 있다.(s: 성공  / i : 진행중 / f : 종료)
	public List<MypageDTO> mychallList(String id, String chall_result) {
		System.out.println("MypageService mychallList()진입");
		Connection conn;
		List<MypageDTO> list = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			if(chall_result.equals("I")) {
				list = mypageDAO.mycallList(conn, id, chall_result);
			}else if(chall_result.equals("F")) {
				list = mypageDAO.mycallendList(conn, id, chall_result);
			}else if(chall_result.equals("my")){
				list = mypageDAO.mycallmakeList(conn, id);
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
// 내가 만든 챌린지 조회 
	public List<MypageDTO> mychallmakeList(String id) {
		System.out.println("MypageService mychallList()진입");
		Connection conn;
		List<MypageDTO> list = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			list = mypageDAO.mycallmakeList(conn, id);
				
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	// 상태 업데이트 
	public int challstatusUpdate(String id,int chall_no, String chall_result) {
		System.out.println("MypageService의 challstatusUpdate진입");
		Connection conn = null;
		int result = 0;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			int mem_no = mypageDAO.selectBymem_no(conn, id);
			
			if(mem_no != -1) {
				result = mypageDAO.challstatusUpdate(conn, id, chall_no, mem_no);
				conn.commit();
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			return -1;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	
	//소감 남기기 + 뱃지 insert 
	public int challengeopinionInsert(String id,int chall_no, String opin_con, int cate_no) {
		System.out.println("MypageService의 challengeopinionInsert진입");
		Connection conn = null;
		int result = 0;
		int b = 0;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//mem_no의 값을 구한다.
			int mem_no = mypageDAO.selectBymem_no(conn, id);
			
			// 소감을 남기는 동시에 상태를 업데이트한다. => 성공 'S'으로 바뀜 
			if(mem_no != -1) {
				result = mypageDAO.challengeopinion(conn, chall_no, mem_no, opin_con);
				result = mypageDAO.challstatusUpdate(conn, id, chall_no, mem_no);
				conn.commit();
			}
			
			// 상태를 업데이트 후 카테고리별 성공카운트를 계산한다.
			int scount = mypageDAO.memberSuccessConut(conn, mem_no, cate_no);
			
			System.out.println("scount: "+scount);
			System.out.println("mem_no: "+mem_no);
			System.out.println("cate_no: "+cate_no);
			//scount(성공카운트)가 1,3,6이면 member_badgeInsert를 실행 
			if(scount == 1 || scount == 3 || scount == 6) {
				switch (cate_no) {
					case 1 : b = mypageDAO.member_badgeInsert1(conn, mem_no, cate_no); break;
					case 2 : b = mypageDAO.member_badgeInsert2(conn, mem_no, cate_no); break;
					case 3 : b = mypageDAO.member_badgeInsert3(conn, mem_no, cate_no); break;
				}
				conn.commit();
				System.out.println("값을통과함 :"+b);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			return -1;
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
}

