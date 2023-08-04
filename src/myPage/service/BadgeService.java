package myPage.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import myPage.dao.BadgeDAO;
import myPage.model.MemberBadge;

// 뱃지 관련 요청을 담당하는 서비스
// 회원이 뱃지를 얻거나, 뱃지를 조회하거나 하는 걸 담당.
public class BadgeService {
	//private MemberBadge  memberBadge = new  MemberBadge();
	private BadgeDAO badgeDAO = new BadgeDAO();
	
	//특정회원이 갖고 있는 뱃지를 조회
	public List<MemberBadge> selectBadge(int memNo) {
		System.out.println("BadgeService-selectBadge()진입. 특정회원이 갖고 있는 뱃지 조회");
		//여기서 커넥션 제공
		Connection conn;
		List<MemberBadge> memberBdgList =  null;
		try {
			conn = ConnectionProvider.getConnection();
			memberBdgList = badgeDAO.selectMyBadge(conn, memNo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberBdgList;
		
	}
	
	// 모든 뱃지 리스트를 조회 - 기본으로 흑백처리해서 보여줄 용도
	public List<MemberBadge> selectAllBadge() {
		System.out.println("BadgeService-selectAllBadge()진입");
		//커넥션 제공
		Connection conn;
		List<MemberBadge> monoBadgeList = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			monoBadgeList = badgeDAO.selectAllBadge(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return monoBadgeList;
	}
	
	

}
