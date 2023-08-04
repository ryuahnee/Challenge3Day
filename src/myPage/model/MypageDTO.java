package myPage.model;

import java.util.Date;

public class MypageDTO {
	
	private int chall_manual_no; 	//int AI PK -챌린지 관리번호
	private int chall_no; 			//int 	-챌린지 번호
	private int mem_no;				//int	-회원 번호
	private int chall_ing_status; 	//int 	-챌린지 진행 상태 
	private Date start_date;		//datetime 	-시작일
	private Date final_date; 		//datetime 	-종료일
	private String chall_result; 	//char(1)	-챌린지 결과
	private String certifi_words; 	//char(1)	-챌린지 결과
	private String id;				//  member의 id
	private String chall_title;		//	challenge_board의 chall_title
	private int cate_no;			//	카테고리 번호
	private String cate_name;		//	카테고리 명
	private Date chall_write_date;	//챌린지 작성일
	private int member_count;		// 참여한 맴버수 => 내가만든 챌린지 조회용으로 임시로만듬 실제로 db에 없음.

	//내가만든 챌린지 조회용 
	public MypageDTO(int chall_no, int mem_no, String chall_title, int cate_no, String cate_name, Date chall_write_date,
			int member_count) {
		super();
		this.chall_no = chall_no;
		this.mem_no = mem_no;
		this.chall_title = chall_title;
		this.cate_no = cate_no;
		this.cate_name = cate_name;
		this.chall_write_date = chall_write_date;
		this.member_count = member_count;
	}

	//참여중인 챌린지 조회용
	public MypageDTO(int chall_manual_no, int chall_no, int mem_no, int chall_ing_status, Date start_date,
			Date final_date, String chall_result, String id, String chall_title, String certifi_words,int cate_no) {
		this.chall_manual_no = chall_manual_no;
		this.chall_no = chall_no;
		this.mem_no = mem_no;
		this.chall_ing_status = chall_ing_status;
		this.start_date = start_date;
		this.final_date = final_date;
		this.chall_result = chall_result;
		this.id = id;
		this.chall_title = chall_title;
		this.certifi_words = certifi_words;
		this.cate_no = cate_no;
	}
	

	/*
	 * public MypageDTO(int chall_manual_no, int chall_no, int mem_no, int
	 * chall_ing_status, Date start_date, Date final_date, String chall_result) {
	 * super(); this.chall_manual_no = chall_manual_no; this.chall_no = chall_no;
	 * this.mem_no = mem_no; this.chall_ing_status = chall_ing_status;
	 * this.start_date = start_date; this.final_date = final_date; this.chall_result
	 * = chall_result; }
	 */

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

	public String getChall_result() {
		return chall_result;
	}

	public void setChall_result(String chall_result) {
		this.chall_result = chall_result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChall_title() {
		return chall_title;
	}

	public void setChall_title(String chall_title) {
		this.chall_title = chall_title;
	}

	public int getCate_no() {
		return cate_no;
	}

	public void setCate_no(int cate_no) {
		this.cate_no = cate_no;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public Date getChall_write_date() {
		return chall_write_date;
	}

	public void setChall_write_date(Date chall_write_date) {
		this.chall_write_date = chall_write_date;
	}

	public int getMember_count() {
		return member_count;
	}

	public void setMember_count(int member_count) {
		this.member_count = member_count;
	}

	public String getCertifi_words() {
		return certifi_words;
	}

	public void setCertifi_words(String certifi_words) {
		this.certifi_words = certifi_words;
	}

	@Override
	public String toString() {
		return "MypageDTO [chall_manual_no=" + chall_manual_no + ", chall_no=" + chall_no + ", mem_no=" + mem_no
				+ ", chall_ing_status=" + chall_ing_status + ", start_date=" + start_date + ", final_date=" + final_date
				+ ", chall_result=" + chall_result + ", certifi_words=" + certifi_words + ", id=" + id
				+ ", chall_title=" + chall_title + ", cate_no=" + cate_no + ", cate_name=" + cate_name
				+ ", chall_write_date=" + chall_write_date + ", member_count=" + member_count + "]";
	}

	
}
