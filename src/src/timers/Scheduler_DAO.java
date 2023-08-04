package src.timers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

import jdbc.JdbcUtil;
import myPage.model.MypageDTO;

public class Scheduler_DAO {
	
	private static Connection con;
	private static PreparedStatement pstmt;		//sql문장을 실행을 가능하게 해줌
	
	// 자정에 데일리 인증을 완료하지 않은 회원의 챌린지 결과를 F로 업데이트
	public int challstatusUpdate(){
		  
		
		List<MypageDTO> list = new ArrayList<>();
		String driver = "com.mysql.cj.jdbc.Driver";
		String user = "ryu";
		String password = "1234";
		String url = "jdbc:mysql://172.30.1.16:3307/challenge3?useUnicode=true";

		try {
			Class.forName(driver);
			System.out.println("Mysql 드라이버 로딩 성공! "+new Date());
					
			con = DriverManager.getConnection(url,user,password);
			System.out.println("Connection 생성성공! "+new Date());
					
			String sql = "UPDATE member_chall_status AS a " + 
		             "JOIN ( " + 
		             "SELECT chall_manual_no " + 
		             "FROM member_chall_status " + 
		             "WHERE DATE_FORMAT(final_date, '%Y-%m-%d') < DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d'), INTERVAL 1 DAY) " + 
		             "AND chall_result = 'i' " + 
		             ") AS b " + 
		             "ON a.chall_manual_no = b.chall_manual_no " + 
		             "SET a.chall_result = 'F'";

			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			System.out.println("pstmt 성공");
			int rowsAffected = pstmt.executeUpdate();
			System.out.println("Rows affected: " + rowsAffected);
			return rowsAffected;
			}catch (Exception e) {
				System.out.println("데이터 베이스 연결 오류 " + e.getMessage());
				return -1;
			}finally {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
			}
		}

}//calss


		/*		
			String sql = "UPDATE member_chall_status AS a " + 
						"JOIN ( " + 
						"SELECT chall_manual_no " + 
						"FROM member_chall_status " + 
						"WHERE DATE_FORMAT(final_date, '%Y-%m-%d') < DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d'), INTERVAL 2 DAY) " + 
						"AND chall_result = 'I' " + 
						") AS b " + 
						"ON a.chall_manual_no = b.chall_manual_no " + 
						"SET a.chall_result = 'F' ";
				try {
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					while(rs.next()) {
						MypageDTO mypageDTO =  new MypageDTO(
								rs.getInt("chall_manual_no"),
								rs.getInt("chall_no"),
								rs.getInt("mem_no"),
								rs.getInt("chall_ing_status"),
								rs.getDate("start_date"),
								rs.getDate("final_date"),
								rs.getString("chall_result"));
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
		 */