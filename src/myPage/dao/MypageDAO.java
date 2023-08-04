package myPage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import myPage.model.MypageDTO;

public class MypageDAO {
	
	
//	내가 참여한 챌린지  = I (진행중)
public List<MypageDTO> mycallList(Connection conn, String id, String chall_result){
	System.out.println("mycallList List진입");
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	List<MypageDTO> list = new ArrayList<>();
	
	int mem_no = selectBymem_no(conn,id); // mem_no의 값을 가져옴
	
	String sql = "select b.chall_manual_no, b.chall_no, a.mem_no, b.chall_ing_status, b.start_date, b.final_date, b.chall_result, a.id, c.chall_title, c.certifi_words, c.cate_no " + 
            "from member a " + 
            "left join member_chall_status b " + 
            "on a.mem_no = b.mem_no " + 
            "left join challenge_board c " + 
            "on b.chall_no = c.chall_no " + 
            "where a.mem_no = ? and chall_result = ? "+
            "order by b.final_date ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,mem_no);
			pstmt.setString(2,chall_result);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MypageDTO mypageDTO =  new MypageDTO(
						rs.getInt("chall_manual_no"),
						rs.getInt("chall_no"),
						rs.getInt("mem_no"),
						rs.getInt("chall_ing_status"),
						rs.getDate("start_date"),
						rs.getDate("final_date"),
						rs.getString("chall_result"),
						rs.getString("id"),
						rs.getString("chall_title"),
						rs.getString("certifi_words"),
						rs.getInt("cate_no")
						);
				list.add(mypageDTO);
					}
			return list;
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}finally {
		if(rs!=null||pstmt!=null) {
		JdbcUtil.close(rs);
		JdbcUtil.close(pstmt);
		}
	}
}

//내가 참여한 챌린지  =S,F (완료,종료)
public List<MypageDTO> mycallendList(Connection conn, String id, String chall_result){
	System.out.println("mycallList List진입");
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	List<MypageDTO> list = new ArrayList<>();
	
	int mem_no = selectBymem_no(conn,id); // mem_no의 값을 가져옴
	
	String sql = "select b.chall_manual_no, b.chall_no, a.mem_no, b.chall_ing_status, b.start_date, b.final_date, b.chall_result, a.id, c.chall_title, c.certifi_words,c.cate_no " + 
            "from member a " + 
            "left join member_chall_status b " + 
            "on a.mem_no = b.mem_no " + 
            "left join challenge_board c " + 
            "on b.chall_no = c.chall_no " + 
            "where a.mem_no = ? and (chall_result = 'S' or chall_result = ?)" +
   			"order by b.final_date desc";
	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,mem_no);
			pstmt.setString(2,chall_result);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MypageDTO mypageDTO =  new MypageDTO(
						rs.getInt("chall_manual_no"),
						rs.getInt("chall_no"),
						rs.getInt("mem_no"),
						rs.getInt("chall_ing_status"),
						rs.getDate("start_date"),
						rs.getDate("final_date"),
						rs.getString("chall_result"),
						rs.getString("id"),
						rs.getString("chall_title"),
						rs.getString("certifi_words"),
						rs.getInt("cate_no"));
				list.add(mypageDTO);
					}
			return list;
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}finally {
		if(rs!=null||pstmt!=null) {
		JdbcUtil.close(rs);
		JdbcUtil.close(pstmt);
		}
	}
}

//	내가만든 챌린지

public List<MypageDTO> mycallmakeList(Connection conn, String id){
	System.out.println("mycallmakeList List진입");
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	List<MypageDTO> list = new ArrayList<>();
	
	int mem_no = selectBymem_no(conn,id); // mem_no의 값을 가져옴
	
	String sql = "SELECT a.chall_no, a.cate_no, c.cate_name, a.chall_title, a.mem_no, a.chall_write_date, COUNT(b.mem_no) AS member_count " + 
				"FROM challenge_board a " + 
				"LEFT JOIN member_chall_status b ON a.chall_no = b.chall_no " + 
				"LEFT JOIN category c ON a.cate_no = c.cate_no " + 
				"WHERE a.mem_no = ? " + 
				"GROUP BY a.chall_no, a.cate_no, a.chall_title, a.mem_no, a.chall_write_date " + 
				"order by a.chall_no desc";
	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,mem_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MypageDTO mypageDTO =  new MypageDTO(
						rs.getInt("chall_no"),
						rs.getInt("mem_no"),
						rs.getString("chall_title"),
						rs.getInt("cate_no"),
						rs.getString("cate_name"),
						rs.getDate("chall_write_date"),
						rs.getInt("member_count"));
				list.add(mypageDTO);
					}
			return list;
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}finally {
		if(rs!=null||pstmt!=null) {
		JdbcUtil.close(rs);
		JdbcUtil.close(pstmt);
		}
	}
}

//	챌린지 도전하기
public int challstatusUpdate(Connection conn,String id, int chall_no, int mem_no) {
	System.out.println("챌린지 상태 업데이트");
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	 String sql = "UPDATE member_chall_status " +
                   "SET mem_no = ? , " +
                     "    chall_ing_status = CASE " +
                     "                         WHEN (date_format(start_date,'%y%m%d') - date_format(now(),'%y%m%d'))=0 then 1 " +
                     "                         WHEN (DATE_FORMAT(start_date, '%y%m%d') - DATE_FORMAT(NOW(), '%y%m%d')) = -1 AND chall_ing_status = 1 THEN 2 " +
                     "                         WHEN (DATE_FORMAT(start_date, '%y%m%d') - DATE_FORMAT(NOW(), '%y%m%d')) = -2 AND chall_ing_status = 2 THEN 3 " +
                     "                         ELSE 0 " +
                     "                         END, " +
                     "    final_date = NOW(), " +
                     "    chall_result = IF(chall_ing_status = 3, 'S', 'I') " +
                     "WHERE chall_no = ? ";
			
	try {
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mem_no);
		pstmt.setInt(2, chall_no);
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


// 카테고리별(cate_no) , 성공('s')횟수 
public int memberSuccessConut(Connection conn, int mem_no, int cate_no){
	System.out.println("challengeopinion 진입");
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	// 성공 횟수 
	int scount = 0; 
	
	String sql = "    SELECT a.mem_no, c.cate_no, COUNT(a.mem_no) AS scount " + 
				"    FROM member_chall_status a " + 
				"    LEFT JOIN challenge_board b ON a.chall_no = b.chall_no " + 
				"    LEFT JOIN category c ON b.cate_no = c.cate_no " + 
				"    WHERE a.mem_no = ? AND a.chall_result = 's' and c.cate_no= ? " + 
				"    GROUP BY a.mem_no, c.cate_no";
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mem_no);
		pstmt.setInt(2, cate_no);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			int mem_no_ =  rs.getInt("mem_no");
			int cate_no_ = rs.getInt("cate_no");
			scount = rs.getInt("scount");
		}
		return scount;
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

//뱃지 증정  카테고리1번일때 
public int member_badgeInsert1(Connection conn, int mem_no, int cate_no){
	System.out.println("challengeopinion 진입");
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	
	String sql = "INSERT INTO member_badge (mem_no, badge_no) " +
	            "SELECT d.mem_no, " +
	            "       CASE " +
	            "            WHEN d.cate_no = 1 AND scount = 1 THEN 1 " +
	            "            WHEN d.cate_no = 1 AND scount = 3 THEN 2 " +
	            "            WHEN d.cate_no = 1 AND scount = 6 THEN 3 " +
	            "       END " +
	            "FROM ( " +
	            "    SELECT a.mem_no, c.cate_no, COUNT(a.mem_no) AS scount " +
	            "    FROM member_chall_status a " +
	            "    LEFT JOIN challenge_board b ON a.chall_no = b.chall_no " +
	            "    LEFT JOIN category c ON b.cate_no = c.cate_no " +
	            "    WHERE a.mem_no = ? AND a.chall_result = 's' AND c.cate_no=1 " +
	            "    GROUP BY a.mem_no, c.cate_no " +
	            ") AS d;";

	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mem_no);
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

// 뱃지 증정  카테고리2번일때 
public int member_badgeInsert2(Connection conn, int mem_no, int cate_no){
	System.out.println("challengeopinion 진입");
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	
	String sql = "INSERT INTO member_badge (mem_no, badge_no) " +
	            "SELECT d.mem_no, " +
	            "       CASE " +
	            "            WHEN d.cate_no = 2 AND scount = 1 THEN 4 " +
	            "            WHEN d.cate_no = 2 AND scount = 3 THEN 5 " +
	            "            WHEN d.cate_no = 2 AND scount = 6 THEN 6 " +
	            "       END " +
	            "FROM ( " +
	            "    SELECT a.mem_no, c.cate_no, COUNT(a.mem_no) AS scount " +
	            "    FROM member_chall_status a " +
	            "    LEFT JOIN challenge_board b ON a.chall_no = b.chall_no " +
	            "    LEFT JOIN category c ON b.cate_no = c.cate_no " +
	            "    WHERE a.mem_no = ? AND a.chall_result = 's' AND c.cate_no=2 " +
	            "    GROUP BY a.mem_no, c.cate_no " +
	            ") AS d;";

	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mem_no);
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


//뱃지 증정  카테고리3번일때 
public int member_badgeInsert3(Connection conn, int mem_no, int cate_no){
	System.out.println("challengeopinion 진입");
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	
	String sql = "INSERT INTO member_badge (mem_no, badge_no) " +
	            "SELECT d.mem_no, " +
	            "       CASE " +
	            "            WHEN d.cate_no = 3 AND scount = 1 THEN 7 " +
	            "            WHEN d.cate_no = 3 AND scount = 3 THEN 8 " +
	            "            WHEN d.cate_no = 3 AND scount = 6 THEN 9 " +
	            "       END " +
	            "FROM ( " +
	            "    SELECT a.mem_no, c.cate_no, COUNT(a.mem_no) AS scount " +
	            "    FROM member_chall_status a " +
	            "    LEFT JOIN challenge_board b ON a.chall_no = b.chall_no " +
	            "    LEFT JOIN category c ON b.cate_no = c.cate_no " +
	            "    WHERE a.mem_no = ? AND a.chall_result = 's' AND c.cate_no=3 " +
	            "    GROUP BY a.mem_no, c.cate_no " +
	            ") AS d;";

	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mem_no);
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


// 소감 남기기 
public int challengeopinion(Connection conn,int chall_no, int mem_no,String opin_con){
	System.out.println("challengeopinion 진입");
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	List<MypageDTO> list = new ArrayList<>();
	
	String sql = "insert into challenge_opinion ( chall_no, mem_no, opin_con ) value ( ? , ? , ? )";

	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, chall_no);
		pstmt.setInt(2, mem_no);
		pstmt.setString(3, opin_con);
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
public int selectBymem_no(Connection conn, String id) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select mem_no from member where id = ?";
    int mem_no = 0;

    try {
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            mem_no = rs.getInt("mem_no");
        }
        return mem_no;
    } catch (SQLException e) {
        e.printStackTrace();
        return -1; // 실패시 -1을 리턴한다.
    } finally {
        JdbcUtil.close(rs);
        JdbcUtil.close(pstmt);
    }
}

}
