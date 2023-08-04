package event.service;

import java.util.Map;

//수정을 위해 세션에서 가져온 글번호, db에서 가져온 event_title, event_con
public class ModifyEventRequest {
	private int event_no;
	private String event_title;
	private String event_con;
	private String has_btn;
	
	public ModifyEventRequest(int event_no, String event_title, String event_con, String has_btn) {
		this.event_no = event_no;
		this.event_title = event_title;
		this.event_con = event_con;
		this.has_btn = has_btn;
	}

	public int getEvent_no() {
		return event_no;
	}

	public void setEvent_no(int event_no) {
		this.event_no = event_no;
	}

	public String getEvent_title() {
		return event_title;
	}

	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}

	public String getEvent_con() {
		return event_con;
	}

	public void setEvent_con(String event_con) {
		this.event_con = event_con;
	}
	
	public String getHas_btn() {
		return has_btn;
	}

	public void setHas_btn(String has_btn) {
		this.has_btn = has_btn;
	}

	//유효성 검사
	public  void validate(Map<String, Boolean> errors) {
		if(event_title==null||event_title.trim().isEmpty()) {
			errors.put("event_title", Boolean.TRUE);
		}
		if(event_con==null||event_con.trim().isEmpty()) {
			errors.put("event_con", Boolean.TRUE);
		}
	}

	@Override
	public String toString() {
		return "ModifyEventRequest [event_no=" + event_no + ", event_title=" + event_title + ", event_con=" + event_con
				+ ", has_btn=" + has_btn + "]";
	}

	
	
	
}
