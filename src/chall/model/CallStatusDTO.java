package chall.model;

import java.util.Date;

public class CallStatusDTO {
	
	private int chall_manual_no; 	//int AI PK -챌린지 관리번호
	private int chall_no; 			//int 	-챌린지 번호
	private int mem_no;				//int	-회원 번호
	private int chall_ing_status; 	//int 	-챌린지 진행 상태 
	private Date start_date;		//datetime 	-시작일
	private Date final_date; 		//datetime 	-종료일
	private char chall_result; 		//char(1)	-챌린지 결과
	
	public CallStatusDTO(int chall_manual_no, int chall_no, int mem_no, int chall_ing_status, Date start_date,
			Date final_date, char chall_result) {
		this.chall_manual_no = chall_manual_no;
		this.chall_no = chall_no;
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

	public char getChall_result() {
		return chall_result;
	}

	public void setChall_result(char chall_result) {
		this.chall_result = chall_result;
	}


	@Override
	public String toString() {
		return "CallStatus [chall_manual_no=" + chall_manual_no + ", chall_no=" + chall_no + ", mem_no=" + mem_no
				+ ", chall_ing_status=" + chall_ing_status + ", start_date=" + start_date + ", final_date=" + final_date
				+ ", chall_result=" + chall_result + "]";
	}
	
	
}
