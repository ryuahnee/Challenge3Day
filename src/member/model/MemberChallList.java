package member.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class MemberChallList {
	private int chall_manual_no; //int AI PK 
	private int chall_no; //int 
	private String chall_title;
	private int mem_no; //int 
	private int chall_ing_status; //int 
	private Date start_date; //datetime 
	private Date final_date; //datetime 
	private String chall_result; //char(1)
	
	public MemberChallList(int chall_manual_no, int chall_no, String chall_title, int mem_no,
			int chall_ing_status, Date start_date, Date final_date, String chall_result) {
		super();
		this.chall_manual_no = chall_manual_no;
		this.chall_no = chall_no;
		this.chall_title = chall_title;
		this.mem_no = mem_no;
		this.chall_ing_status = chall_ing_status;
		this.start_date = start_date;
		this.final_date = final_date;
		this.chall_result = chall_result;
	}

	public int getChall_manual_no() {
		return chall_manual_no;
	}

	public void setChall_manual_no(int chall_manual_no) {
		this.chall_manual_no = chall_manual_no;
	}

	public int getChall_no() {
		return chall_no;
	}

	public void setChall_no(int chall_no) {
		this.chall_no = chall_no;
	}

	public String getChall_title() {
		return chall_title;
	}

	public void setChall_title(String chall_title) {
		this.chall_title = chall_title;
	}

	public int getMem_no() {
		return mem_no;
	}

	public void setMem_no(int mem_no) {
		this.mem_no = mem_no;
	}

	public int getChall_ing_status() {
		return chall_ing_status;
	}

	public void setChall_ing_status(int chall_ing_status) {
		this.chall_ing_status = chall_ing_status;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getFinal_date() {
		return final_date;
	}

	public void setFinal_date(Date final_date) {
		this.final_date = final_date;
	}

	public String getChall_result() {
		return chall_result;
	}

	public void setChall_result(String chall_result) {
		this.chall_result = chall_result;
	}

	public void validate(Map<String,Boolean>errors) {
		String strStartDate = null;
		String strFinalDate = null;
		String strCn = null;
		String strMn = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		if(chall_no!=0) {
			strCn = Integer.toString(chall_no);
		}
		if(mem_no!=0) {
			strMn = Integer.toString(mem_no);
		}
        if(start_date!=null) {
        	strStartDate = format.format(start_date);
        }
        if(final_date!=null) {
        	strFinalDate = format.format(final_date);
        }

		checkEmpty(errors,strCn,"chall_no");
		checkEmpty(errors,chall_title,"chall_title");
		checkEmpty(errors,strMn,"mem_no");
		checkEmpty(errors,strStartDate,"start_date");
		checkEmpty(errors,strFinalDate,"final_date");
		checkEmpty(errors,chall_result,"chall_result");
	}

	private void checkEmpty(Map<String, Boolean> errors,Object value, String fieldName) {
		if(value==null || ((String) value).isEmpty()) {
			//에러가 있다면 (필수입력이 빈칸이면)
			//Map참조변수  errors에 필드명을 key로, 값을 True로 저장
			errors.put(fieldName, Boolean.TRUE);
		}   
	}

	@Override
	public String toString() {
		return "MemberChallList [chall_manual_no=" + chall_manual_no + ", chall_no=" + chall_no + ", chall_title="
				+ chall_title + ", mem_no=" + mem_no + ", chall_ing_status=" + chall_ing_status + ", start_date="
				+ start_date + ", final_date=" + final_date + ", chall_result=" + chall_result + "]";
	}
	
	
}

