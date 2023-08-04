package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberChallStatusDAO;
import member.exception.ChallNotFoundException;
import member.exception.MemberChallStatusNotFoundException;
import member.exception.MemberNotFoundException;
import member.model.MemberChallList;
import member.model.MemberChallStatus;

public class MemberChallStatusService {	
	MemberChallStatusDAO memberChallStatusDAO = new MemberChallStatusDAO();
	
	//삭제 메소드
	public void delete(int chall_manual_no) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//1.article테이블에 delete하기전 해당글번호 가져오기
			MemberChallStatus mcs = memberChallStatusDAO.selectByChallMenualNo(conn, chall_manual_no);
			if(mcs==null) {
				throw new MemberChallStatusNotFoundException();
			}
			
			//2.article테이블에 delete하는 메서드호출
			memberChallStatusDAO.delete(conn, chall_manual_no);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException();
		} catch (MemberChallStatusNotFoundException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn);
		}
		
	}
	
	//리스트 페이지 출력 관련 메소드
	int size = 15;
	public MemberChallListPage getMemberChallListPage(String option, String searchCon,int pageNum, int mem_no) {
		System.out.println("MemberChallStatusService-getMemberChallListPage()진입");
		Connection conn;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			//p651 18라인
			int total = memberChallStatusDAO.memberChallCnt(conn,mem_no,option,searchCon); //총게시글수
			System.out.println("total=" + total);
			List<MemberChallList> content = memberChallStatusDAO.select(conn,mem_no,option,searchCon,(pageNum-1)*size,size);//목록조회-p651 19라인
			
			MemberChallListPage mcp = new MemberChallListPage(total, pageNum, size, content);
			System.out.println("MemberChallStatusService- getMemberChallListPage()의 결과 mp="+mcp);
			return mcp;
		}  catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//챌린지 정보 불러오기
	//멤버 정보 불러오는 메소드
	public MemberChallStatus getMemberChallStatusDetail(int chall_manual_no) {
		Connection conn;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			MemberChallStatus memberChallStatus = memberChallStatusDAO.selectByChallMenualNo(conn, chall_manual_no);
			if(memberChallStatus==null) {
				throw new MemberNotFoundException();
			}
			return memberChallStatus;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//멤버 챌린지 정보 수정 메소드
	public void Modify(MemberChallStatus memberChallStatus) {
		//확인용 콘솔출력
		System.out.println("MemberModifyService의 join()진입");
		Connection conn = null;
		try {			
			conn=ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//있는 챌린지인지 확인
			System.out.println("selectByChallMenualNo하는 챌린지 관리 번호="+memberChallStatus.getChall_manual_no());
			System.out.println("selectByChallMenualNo하는 챌린지 정보="+memberChallStatus);
			MemberChallStatus mcs = memberChallStatusDAO.selectByChallMenualNo(conn, memberChallStatus.getChall_manual_no());
			if(mcs==null) {
				throw new ChallNotFoundException();
			}
			//수정
			memberChallStatusDAO.modify(conn, memberChallStatus.getChall_manual_no(), memberChallStatus.getChall_no(), memberChallStatus.getMem_no(), memberChallStatus.getChall_ing_status(), memberChallStatus.getStart_date(), memberChallStatus.getFinal_date(), memberChallStatus.getChall_result());
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException();
		} catch (ChallNotFoundException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
		}
	}

	public String getChallName(int chall_no) {
		Connection conn;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			String challName = memberChallStatusDAO.selectByChallName(conn, chall_no);
			if(challName==null) {
				throw new MemberNotFoundException();
			}
			return challName;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}		
	}

}
