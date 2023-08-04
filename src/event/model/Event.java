package event.model;

import java.util.Date;

//Event테이블의 값을 저장 및 제공 등의 기능을 담당.
public class Event {
	//필드
	private int event_no;
	private String event_title;
	private String event_con;
	private Date event_regdate;
	private String has_btn;
	
	//생성자
	public Event() {}
	
	public Event(int event_no, String event_title, Date event_regdate) {
		this.event_no=event_no;
		this.event_title = event_title;
		this.event_regdate = event_regdate;
	}
	public Event(String event_title, Date event_regdate) {
		this.event_title = event_title;
		this.event_regdate = event_regdate;
	}
	public Event(String event_title, String event_con, Date event_regdate) {
		this.event_title = event_title;
		this.event_con = event_con;
		this.event_regdate = event_regdate;
	}
	
	
	public Event(int event_no, String event_title, String event_con, Date event_regdate, String has_btn) {
		this.event_no = event_no;
		this.event_title = event_title;
		this.event_con = event_con;
		this.event_regdate = event_regdate;
		this.has_btn=has_btn;
	}
	
	//메소드
	@Override
	public String toString() {
		return "Event [event_no=" + event_no + ", event_title=" + event_title + ", event_con=" + event_con
				+ ", event_regdate=" + event_regdate + ", has_btn=" + has_btn + "]";
	}
	
	public int getEvent_no()  {
		return event_no;
	}
	public String getEvent_title() {
		return event_title;
	}
	public String getEvent_con() {
		return event_con;
	}
	public String getHas_btn() {
		return has_btn;
	}
	public Date getEvent_regdate() {
		return event_regdate;
	}
	public void setEvent_no(int event_no) {
		this.event_no = event_no;
	}
	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}
	public void setEvent_con(String event_con) {
		this.event_con = event_con;
	}
	public void setEvent_regdate(Date event_regdate) {
		this.event_regdate = event_regdate;
	}
	public void setHas_btn(String has_btn) {
		this.has_btn = has_btn;
	}
	
}
