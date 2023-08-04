package qnaAnswerboard.service;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Qnamail {
	
	public static void mailservice(String mem_email, String userid) {
		
		//수신
		Random random = new Random();
		int randomcode = random.nextInt(10000);
		
		String recipient = mem_email;
		String username = userid;
		
		//발신자 이메일 
		String user = "lry9478@naver.com";
		String pw = "DDBXQM4R539N";
		
		//Properties에 정보저장 
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.naver.com");
		props.put("mail.smtp.post", 465);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.ciphers", "TLS_AES_128_GCM_SHA256");
        props.put("mail.smtp.ssl.trust", "smtp.naver.com");  //확인필요
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        	return new PasswordAuthentication(user,pw);
        	}
        });
        
        MimeMessage message = new MimeMessage(session);
        
        try {
			message.setFrom(new InternetAddress(user));
			
			//수신자메일주소
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
			
			// Subject -제목 
			message.setSubject("[안내]작심삼일 1:1 문의답변");
			
			// 내용 	
			message.setContent("안녕하세요. [" + username + "]님<br/><br/>" +
					            "작심삼일을 이용해주셔서 감사합니다. 문의주신 내용에 답변이 등록되었습니다.<br/><br/>" +
					            "<a href='http://localhost:8081/login.do'>[답변 확인하러가기]</a><br/><br/>" +
					            "감사합니다,<br/>" +
					            "작심삼일 드림", "text/html; charset=UTF-8");

			
			Transport.send(message); 
			
			System.out.println("전송완료");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
        
		
	}

}
