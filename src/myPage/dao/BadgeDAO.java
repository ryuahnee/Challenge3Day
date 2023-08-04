package myPage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import myPage.model.MemberBadge;

// badge 테이블 조회와 member_badge테이블 입력 조회 결과를 제공하는 클래스
public class BadgeDAO {

	//특정 회원이 갖고 있는 뱃지를 조회
	public List<MemberBadge> selectMyBadge(Connection conn, int memNo) throws SQLException {
		System.out.println("BadgeDAO-selectMyBadge()진입");
		
		String sql = "select mb.ach_badge_no, mb.badge_no, mb.ach_date, b.badge_name, b.cate_no " + 
				"from badge b " + 
				"inner join member_badge mb " + 
				"on b.badge_no = mb.badge_no " + 
				"where mem_no=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.prepareStatement(sql);
		
			stmt.setInt(1, memNo);
			rs = stmt.executeQuery();
			List<MemberBadge> result = new ArrayList<>();
			while(rs.next()) {
				result.add(new MemberBadge(rs.getInt("ach_badge_no"),
						   rs.getInt("badge_no"),
						   rs.getDate("ach_date"),
						   rs.getString("badge_name"),
						   rs.getInt("cate_no")
						   )); // end of result.add
			} //end of if
			
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	//모든 뱃지를 조회 - 흑백으로 기본으로 뿌려줘야 하니까
	public List<MemberBadge> selectAllBadge(Connection conn) throws SQLException {
		System.out.println("BadgeDAO-selectAllBadge()진입");
		String sql = "select badge_no, badge_name, cate_no from badge";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<MemberBadge> result = new ArrayList<>();
			while(rs.next()) {
				result.add(new MemberBadge(rs.getInt("badge_no"),
										   rs.getString("badge_name"),
										   rs.getInt("cate_no")));
			}
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		
	}
	
}
