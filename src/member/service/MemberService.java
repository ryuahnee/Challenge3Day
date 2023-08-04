package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDAO;
import member.exception.EmailDuplicationException;
import member.exception.IdDuplicationException;
import member.exception.MemberNotFoundException;
import member.exception.NicknameDuplicationException;
import member.exception.PermissionDeniedException;
import member.model.Member;

public class MemberService {
	private MemberDAO memberDAO = new MemberDAO();
	
	//회원가입 처리 메소드
	public void join(JoinRequest joinReq) {
		//확인용 콘솔출력
		System.out.println("JoinService의 join()진입");
		Connection conn = null;
		try {			
			conn=ConnectionProvider.getConnection();

			//오토커밋 하지마!
			conn.setAutoCommit(false);
			
			//아이디 중복검사
			//파라미터가 conn-커넥션 joinRequest.getMemberId()-user가 입력한 id
			Member mem = memberDAO.selectById(conn, joinReq.getId());
			if(mem!=null) {//회원정보가 존재하면 조회한 아이디를 이미 사용하는 회원이 있다는 뜻
				JdbcUtil.rollback(conn);
				throw new IdDuplicationException();//id중복처리예외(사용자정의에러) 생성
			}
			
			//닉네임 중복검사
			Member mem2 = memberDAO.selectByNickName(conn, joinReq.getNickname());
			if(mem2!=null) {//회원정보가 존재하면 조회한 닉네임를 이미 사용하는 회원이 있다는 뜻
				JdbcUtil.rollback(conn);
				throw new NicknameDuplicationException();//id중복처리예외(사용자정의에러) 생성
			}

			//닉네임 중복검사
			Member mem3 = memberDAO.selectByMemEmail(conn, joinReq.getMem_email());
			if(mem3!=null) {//회원정보가 존재하면 조회한 이메일를 이미 사용하는 회원이 있다는 뜻
				JdbcUtil.rollback(conn);
				throw new EmailDuplicationException();//id중복처리예외(사용자정의에러) 생성
			}
			
			//회원정보 등록insert처리
			/*파라미터 conn-커넥션
			 * 		user가 입력한 정보 - id name password ...*/
			Member member = new Member(0,joinReq.getId(),joinReq.getPwd(),joinReq.getMem_name(),joinReq.getNickname(),joinReq.getBirthyear(),joinReq.getGender(),new Date(),joinReq.getMem_email(),"Y");
			System.out.println(joinReq.getId());
			memberDAO.insert(conn, member);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		}finally {
			JdbcUtil.close(conn); //p597 37라인
		}	
	}
	
	//멤버 정보 불러오는 메소드
	public Member getMemberDetail(int mem_no) {
		Connection conn;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			Member member = memberDAO.selectByMemNo(conn, mem_no);
			if(member==null) {
				throw new MemberNotFoundException();
			}
			return member;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//멤버 정보 수정 메소드
	public void Modify(Member memReq) throws PermissionDeniedException {
		//확인용 콘솔출력
		System.out.println("MemberModifyService의 join()진입");
		Connection conn = null;
		try {			
			conn=ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//기존 회원인지 검사
			Member member = memberDAO.selectByMemNo(conn, memReq.getMem_no());
			if(member==null) {
				throw new MemberNotFoundException();
			}
			//수정 권한 검사
			if(!canModify(memReq.getMem_no(),member)) {
				throw new PermissionDeniedException();
			}
			//아이디 중복검사
			System.out.println(memReq.getId());
			Member mem = memberDAO.selectById(conn, memReq.getId());
			System.out.println("service"+mem);
			if(mem!=null) {//이 아이디를 사용하는 사람이 있는데
				if(mem.getMem_no()!=memReq.getMem_no()) {//그게 지금 나 말고 다른 사람이라면(아이디 변경 안할경우는 통과해야하므로)
				JdbcUtil.rollback(conn);
				throw new IdDuplicationException();
				}
			}
			
			//닉네임 중복검사
			Member mem2 = memberDAO.selectByNickName(conn, memReq.getNickname());
			if(mem2!=null) {
				if(mem2.getMem_no()!=memReq.getMem_no()) {//회원정보가 존재하면 조회한 닉네임를 이미 사용하는 회원이 있다는 뜻
					JdbcUtil.rollback(conn);
					throw new NicknameDuplicationException();//id중복처리예외(사용자정의에러) 생성
				}
			}
			
			//이메일 중복검사
			Member mem3 = memberDAO.selectByMemEmail(conn, memReq.getMem_email());
			System.out.println(memReq.getMem_email());
			if(mem3!=null) {
				if(mem3.getMem_no()!=memReq.getMem_no()) {//회원정보가 존재하면 조회한 이메일를 이미 사용하는 회원이 있다는 뜻
					System.out.println(mem3.getMem_no());
					System.out.println(memReq.getMem_no());
					JdbcUtil.rollback(conn);
					throw new EmailDuplicationException();//id중복처리예외(사용자정의에러) 생성
				}
			}
			
			memberDAO.update(conn, memReq.getMem_no(), memReq.getId(), memReq.getMem_name(), memReq.getNickname(), memReq.getBirthyear(), memReq.getGender(), memReq.getMem_email(), memReq.getIsMarketing());
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException();
		} catch(PermissionDeniedException e){ //수정불가시
			JdbcUtil.rollback(conn);
			throw e;
		}finally {
			JdbcUtil.close(conn);
		}
	}
	
	private boolean canModify(int modifyingUserNo, Member member) {
		if(modifyingUserNo==member.getMem_no() || member.getMem_no()==1) {
			return true;
		}
		return false;
	}
	
	//리스트 페이지 출력 관련 메소드
	int size = 15;
	public MemberPage getMemberPage(String option, String searchCon, int pageNum) {
		System.out.println("MemberListService-getMemberPage()진입");
		Connection conn;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			//p651 18라인
			int total = memberDAO.memberCnt(conn,option,searchCon); //총게시글수
			System.out.println("total=" + total);
			List<Member> content = memberDAO.select(conn,option,searchCon,(pageNum-1)*size,size);//목록조회-p651 19라인
			
			MemberPage mp = new MemberPage(total, pageNum, size, content);
			System.out.println("MemberListService- getMemberPage()의 결과 mp="+mp);
			return mp;
		}  catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//delete쿼리를 통한 글삭제
	public void delete(int mem_no) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//1.article테이블에 delete하기전 해당글번호 가져오기
			Member member = memberDAO.selectByMemNo(conn, mem_no);
			if(member==null) {
				throw new MemberNotFoundException();
			}
			
			//2.article테이블에 delete하는 메서드호출
			memberDAO.delete(conn, mem_no);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException();
		}finally {
			JdbcUtil.close(conn);
		}
		
	}

}

