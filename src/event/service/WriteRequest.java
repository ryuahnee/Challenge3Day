package event.service;

import java.util.Date;

//새로운 이벤트 게시글을 등록할 때 필요한 데이터를 제공.
//WriteEventService가 게시글 등록 기능을 구현할 때, 필요한 요청 데이터를 담는 클래스.
public class WriteRequest {
	//필드
	private String title; //이벤트 게시글 제목
	private String content; //이벤트 게시글 내용
	private Date regDate;
	private String has_btn;
	
	//메소드
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getHas_btn() {
		return has_btn;
	}
	public void setHas_btn(String has_btn) {
		this.has_btn = has_btn;
	}

	//유효성 검사할 게 있다면 메소드를 추가하기
	//사실 여기보다 뷰단에서 제이쿼리 이용하는게 좋을 거 같음.
	
}
