package auth.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jdbc.connection.ConnectionProvider;
import member.dao.MemberDAO;
import member.model.Member;

public class SearchIdPwdService {
	MemberDAO memberDAO = new MemberDAO();
	
	//존재하는 이메일인지
	public Member selectByEmail(String mem_email) {
		Connection conn=null;
		Member member = null;
		try {
			conn= ConnectionProvider.getConnection();
			member = memberDAO.selectByMemEmail(conn, mem_email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
	//인증 번호 생성기
	public String authCodeMaker() {
		
		String authCode = null;
		StringBuffer temp = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int rIndex = random.nextInt(3);
			switch (rIndex) {
			case 0:
				// a-z
				temp.append((char) ((int) (random.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				temp.append((char) ((int) (random.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
				temp.append((random.nextInt(10)));
				break;
			}
		}
		
		authCode = temp.toString();
		System.out.println(authCode);
		
		return authCode;
	}
	
	//이메일 전송

    public void sendEmail(String toEmail, String subject, String body) {
    	System.out.println("sendEmail진입");
        // 메일 발송
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            props.put("mail.smtp.host", "smtp.naver.com");
            props.put("mail.smtp.port", 587);

            // 발신자 인증 정보 설정
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("challllun", "cofflswl333!");
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("challllun@naver.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);
            message.setSentDate(new Date());

            Transport.send(message);
            System.out.println("이메일이 성공적으로 전송되었습니다.");
		} catch (AddressException e) { 
			e.printStackTrace(); 
		} catch (MessagingException e) { 
				e.printStackTrace(); 
		}
	}
    
    // 인증 코드 확인
	public boolean authCodeCheck(String authCode, String inputCode) {
		if(authCode.equals(inputCode)) {
			return true;
		}
		return false;
	}
	
	//아이디로 이메일 찾기
	public Member selectById(String id) {
		Connection conn=null;
		Member member = null;
		try {
			conn= ConnectionProvider.getConnection();
			member = memberDAO.selectById(conn, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
}
