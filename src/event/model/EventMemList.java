package event.model;

import java.sql.Date;

//eventmem_list 테이블의 값 저장 및 제공,
//eventmem_list 와 member테이블 join한 값 저장 및 제공.
public class EventMemList {
	//필드
	//e.event_no, e.mem_no, m.id, m.mem_name, m.nickname, m.birthyear, m.gender, m.isMarketing "
	private int event_no;
	private int mem_no;
	private String id;
	private String mem_name;
	private String nickname;
	private Date birthyear;
	private String gender;
	private String isMarketing;
	private int event_appli_no;
	private String isSelector;
	
	
	//신청회원목록조회시 사용되는 생성자
	public EventMemList () {}
	public EventMemList(int event_appli_no, int event_no, int mem_no, String id, String mem_name, String nickname, Date birthyear,
			String gender, String isMarketing, String isSelector) {
		this.event_appli_no = event_appli_no;
		this.event_no = event_no;
		this.mem_no = mem_no;
		this.id = id;
		this.mem_name = mem_name;
		this.nickname = nickname;
		this.birthyear = birthyear;
		this.gender = gender;
		this.isMarketing = isMarketing;
		this.isSelector=isSelector;
	}
	
	public int getEvent_no() {
		return event_no;
	}
	public void setEvent_no(int event_no) {
		this.event_no = event_no;
	}
	public int getMem_no() {
		return mem_no;
	}
	public void setMem_no(int mem_no) {
		this.mem_no = mem_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getBirthyear() {
		return birthyear;
	}
	public void setBirthyear(Date birthyear) {
		this.birthyear = birthyear;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIsMarketing() {
		return isMarketing;
	}
	public void setIsMarketing(String isMarketing) {
		this.isMarketing = isMarketing;
	}
	public int getEvent_appli_no() {
		return event_appli_no;
	}
	public void setEvent_appli_no(int event_appli_no) {
		this.event_appli_no = event_appli_no;
	}
	public String getIsSelector() {
		return isSelector;
	}
	public void setIsSelector(String isSelector) {
		this.isSelector = isSelector;
	}
	
	
	@Override
	public String toString() {
		return "EventMemList [event_no=" + event_no + ", mem_no=" + mem_no + ", id=" + id + ", mem_name=" + mem_name
				+ ", nickname=" + nickname + ", birthyear=" + birthyear + ", gender=" + gender + ", isMarketing="
				+ isMarketing + ", event_appli_no=" + event_appli_no + ", isSelector=" + isSelector + "]";
	}

	

}
