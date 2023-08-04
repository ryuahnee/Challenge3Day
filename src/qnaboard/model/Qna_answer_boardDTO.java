package qnaboard.model;

import java.util.Date;

public class Qna_answer_boardDTO{
	
	private int qna_a_no;    //INT  PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '문의 답변 번호', 
	private int qna_no;      		//INT NOT NULL COMMENT '문의 번호', 
	private String qna_a_con;   		//TEXT NOT NULL COMMENT '문의 답변 내용', 
	private Date qna_a_date;  		//DATETIME NOT NULL COMMENT '문의 답변 일', 
	
	public Qna_answer_boardDTO() {}
	
	public Qna_answer_boardDTO(int qna_a_no, int qna_no, String qna_a_con, Date qna_a_date) {
		this.qna_a_no = qna_a_no;
		this.qna_no = qna_no;
		this.qna_a_con = qna_a_con;
		this.qna_a_date = qna_a_date;
	}

	public int getQna_a_no() {
		return qna_a_no;
	}


	public void setQna_a_no(int qna_a_no) {
		this.qna_a_no = qna_a_no;
	}


	public int getQna_no() {
		return qna_no;
	}


	public void setQna_no(int qna_no) {
		this.qna_no = qna_no;
	}


	public String getQna_a_con() {
		return qna_a_con;
	}


	public void setQna_a_con(String qna_a_con) {
		this.qna_a_con = qna_a_con;
	}


	public Date getQna_a_date() {
		return qna_a_date;
	}


	public void setQna_a_date(Date qna_a_date) {
		this.qna_a_date = qna_a_date;
	}

	@Override
	public String toString() {
		return "Qna_answer_boardDTO [qna_a_no=" + qna_a_no + ", qna_no=" + qna_no + ", qna_a_con=" + qna_a_con
				+ ", qna_a_date=" + qna_a_date + "]";
	}

	
}