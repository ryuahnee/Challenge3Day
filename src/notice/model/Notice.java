package notice.model;

import java.util.Date;
import java.util.Map;

//공지테이블 값을 저장 및 제공 등의 기능을 가진 클래스다. 
public class Notice {

	private Integer notiNo; //공지번호
	private String  notiTitle; //공지제목
	private String  notiContent; //공지내용
	private Date notiDate; //공지작성일
	private String id;
	
	//기본 생성자
	public Notice() {
		
	}
	
	//오버로드 생성자: 데이터 담기
	public Notice(Integer notiNo, String notiTitle, String notiContent, Date notiDate) {
		this.notiNo = notiNo;
		this.notiTitle = notiTitle;
		this.notiContent = notiContent;
		this.notiDate = notiDate;
	}
	
	
	//메서드
	public Integer getNotiNo() {
		return notiNo;
	}
	public void setNotiNo(Integer notiNo) {
		this.notiNo = notiNo;
	}
	public String getNotiTitle() {
		return notiTitle;
	}
	public void setNotiTitle(String notiTitle) {
		this.notiTitle = notiTitle;
	}
	public String getNotiContent() {
		return notiContent;
	}
	public void setNotiContent(String notiContent) {
		this.notiContent = notiContent;
	}
	public Date getNotiDate() {
		return notiDate;
	}
	public void setNotiDate(Date notiDate) {
		this.notiDate = notiDate;
	}
	
	//필수입력기능
	public void validate(Map<String,Boolean> errors) {
		if(  notiTitle==null ||  notiTitle.trim().isEmpty() ) {
			errors.put("notiTitle", Boolean.TRUE);
		}
		if(  notiContent==null ||  notiContent.isEmpty() ) {
			errors.put("notiContent", Boolean.TRUE);
		}
	}
	
	
	@Override
	public String toString() {
		return "Notice [notiNo=" + notiNo + ", notiTitle=" + notiTitle + ", notiContent=" + notiContent + ", notiDate="
				+ notiDate + "]";
	}


	public String getId() {
		return id;
	}
	
	
	

	
	
}