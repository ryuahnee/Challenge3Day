package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import member.dao.MemberDAO;
import member.model.Member;

//로그인 서비스
public class LoginService {
	//필드
	private MemberDAO memberDAO = new MemberDAO();
	//생성자
	//로그인처리 by 파라미터(String id, String password) 유저가 입력한 아이디 비번
	//리턴 User-로그인에 성공한 회원정보(회원번호,회원id,회원명,회원닉네임)
	public User login(String id, String pwd) {
		try {
			Connection conn = ConnectionProvider.getConnection();
			Member member = memberDAO.selectById(conn, id);
			if(member==null) {
				throw new LoginFailException();
			}
			
			//p606 20line
			if(!member.matchPwd(pwd)) {
				throw new LoginFailException();
			}
			return new User(member.getMem_no(),member.getId(),member.getNickname());//세션에다가 비밀번호 넣으면 안됨!! 우리는 닉네임 세션에 넣어야함 뿌려줄거니까!
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
}

