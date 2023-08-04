package myPage.model;

import java.sql.Date;

// member_badge테이블 데이터를 제공해주는 클래스
// 덤으로 badge 테이블 데이터도 제공해주자
public class MemberBadge {
	//필드
	int ach_badge_no;
	int badge_no;
	Date ach_date;
	String badge_name;
	int cate_no;
	
	//멤버가 획득한 뱃지 테이블 정보
	public MemberBadge(int ach_badge_no, int badge_no, Date ach_date, String badge_name, int cate_no) {
		this.ach_badge_no = ach_badge_no;
		this.badge_no = badge_no;
		this.ach_date = ach_date;
		this.badge_name = badge_name;
		this.cate_no = cate_no;
	}
	
	//뱃지 테이블 정보
	public MemberBadge(int badge_no, String badge_name, int cate_no) {
		this.badge_no = badge_no;
		this.badge_name = badge_name;
		this.cate_no = cate_no;
	}

	public int getAch_badge_no() {
		return ach_badge_no;
	}

	public void setAch_badge_no(int ach_badge_no) {
		this.ach_badge_no = ach_badge_no;
	}

	public int getBadge_no() {
		return badge_no;
	}

	public void setBadge_no(int badge_no) {
		this.badge_no = badge_no;
	}

	public Date getAch_date() {
		return ach_date;
	}

	public void setAch_date(Date ach_date) {
		this.ach_date = ach_date;
	}

	public String getBadge_name() {
		return badge_name;
	}

	public void setBadge_name(String badge_name) {
		this.badge_name = badge_name;
	}

	public int getCate_no() {
		return cate_no;
	}

	public void setCate_no(int cate_no) {
		this.cate_no = cate_no;
	}

	@Override
	public String toString() {
		return "MemberBadge [ach_badge_no=" + ach_badge_no + ", badge_no=" + badge_no + ", ach_date=" + ach_date
				+ ", badge_name=" + badge_name + ", cate_no=" + cate_no + "]";
	}
	
	
	
}
