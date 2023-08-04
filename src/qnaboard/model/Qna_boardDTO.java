package qnaboard.model;

import java.util.Date;

//
public class Qna_boardDTO {
	
	    private int qna_no;					// NOT NULL AUTO_INCREMENT COMMENT '문의 번호' 
	    private String qna_title;       	//VARCHAR(45)     NOT NULL    COMMENT '문의 제목'
	    private String qna_con;         	//TEXT            NOT NULL    COMMENT '문의 내용' 
	    private int mem_no;             	//INT             NOT NULL    COMMENT '회원 번호' 
	    private Date qna_date;          	// DATETIME        NOT NULL    COMMENT '문의 일' 
	    private String qna_fin_check;   	//VARCHAR(45)     NOT NULL    COMMENT '문의 완료 확인'
	    private String qna_orifile_name;   //VARCHAR(200)    NULL        COMMENT '문의 원본파일 명', 
	    private int qna_copyfile_name;  //VARCHAR(200)    NULL        COMMENT '문의 사본파일 명', 
	    private String id;					// member db에 있는 id -> 관리자 조회용
	    private int num;  //VARCHAR(200)    NULL        COMMENT '문의 사본파일 명', 
	    private String mem_email;
		
	    
	    public Qna_boardDTO() {}
	    
	    public Qna_boardDTO(int qna_no, int mem_no,String id,String qna_title, String qna_con, Date qna_date, String qna_fin_check,
				String qna_orifile_name, int qna_copyfile_name ) {
			this.qna_no = qna_no;
			this.qna_title = qna_title;
			this.qna_con = qna_con;
			this.mem_no = mem_no;
			this.qna_date = qna_date;
			this.qna_fin_check = qna_fin_check;
			this.qna_orifile_name = qna_orifile_name;
			this.qna_copyfile_name = qna_copyfile_name;
			this.id = id;
			
		}
	    
	    public Qna_boardDTO(int qna_no, String qna_title, String qna_con, int mem_no, Date qna_date, String qna_fin_check,
				String qna_orifile_name, int qna_copyfile_name) {
			this.qna_no = qna_no;
			this.qna_title = qna_title;
			this.qna_con = qna_con;
			this.mem_no = mem_no;
			this.qna_date = qna_date;
			this.qna_fin_check = qna_fin_check;
			this.qna_orifile_name = qna_orifile_name;
			this.qna_copyfile_name = qna_copyfile_name;
		}

	    
	    
	    // 관리가 조회용 (rownum추가)	
		public Qna_boardDTO(int qna_no, String qna_title, String qna_con, int mem_no, Date qna_date,
				String qna_fin_check, String qna_orifile_name, int qna_copyfile_name, String id, int num) {
			this.qna_no = qna_no;
			this.qna_title = qna_title;
			this.qna_con = qna_con;
			this.mem_no = mem_no;
			this.qna_date = qna_date;
			this.qna_fin_check = qna_fin_check;
			this.qna_orifile_name = qna_orifile_name;
			this.qna_copyfile_name = qna_copyfile_name;
			this.id = id;
			this.num = num;
		}
		
		
		
		
		// 상세조회 
		public Qna_boardDTO(int qna_no, String qna_title, String qna_con, int mem_no, Date qna_date,
				String qna_fin_check, String qna_orifile_name, int qna_copyfile_name, String id, String mem_email) {
			super();
			this.qna_no = qna_no;
			this.qna_title = qna_title;
			this.qna_con = qna_con;
			this.mem_no = mem_no;
			this.qna_date = qna_date;
			this.qna_fin_check = qna_fin_check;
			this.qna_orifile_name = qna_orifile_name;
			this.qna_copyfile_name = qna_copyfile_name;
			this.id = id;
			this.mem_email = mem_email;
		}

		public int getQna_no() {
			return qna_no;
		}

		public void setQna_no(int qna_no) {
			this.qna_no = qna_no;
		}

		public String getQna_title() {
			return qna_title;
		}

		public void setQna_title(String qna_title) {
			this.qna_title = qna_title;
		}

		public String getQna_con() {
			return qna_con;
		}

		public void setQna_con(String qna_con) {
			this.qna_con = qna_con;
		}

		public int getMem_no() {
			return mem_no;
		}

		public void setMem_no(int mem_no) {
			this.mem_no = mem_no;
		}

		public Date getQna_date() {
			return qna_date;
		}

		public void setQna_date(Date qna_date) {
			this.qna_date = qna_date;
		}

		public String getQna_fin_check() {
			return qna_fin_check;
		}

		public void setQna_fin_check(String qna_fin_check) {
			this.qna_fin_check = qna_fin_check;
		}

		public String getQna_orifile_name() {
			return qna_orifile_name;
		}

		public void setQna_orifile_name(String qna_orifile_name) {
			this.qna_orifile_name = qna_orifile_name;
		}

		public int getQna_copyfile_name() {
			return qna_copyfile_name;
		}

		public void setQna_copyfile_name(int qna_copyfile_name) {
			this.qna_copyfile_name = qna_copyfile_name;
		}
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		
		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}
		

		public String getMem_email() {
			return mem_email;
		}

		public void setMem_email(String mem_email) {
			this.mem_email = mem_email;
		}

		@Override
		public String toString() {
			return "Qna_boardDTO [qna_no=" + qna_no + ", qna_title=" + qna_title + ", qna_con=" + qna_con + ", mem_no="
					+ mem_no + ", qna_date=" + qna_date + ", qna_fin_check=" + qna_fin_check + ", qna_orifile_name="
					+ qna_orifile_name + ", qna_copyfile_name=" + qna_copyfile_name + ", id=" + id + ", num=" + num
					+ "]";
		}

		
}
