package chall.model;

import java.util.Date;

//Challenge_board테이블의 값을 저장 및 제공 등의 기능을 가진 클래스이다
public class Chall {
	//필드
	private int challNo;
	private int cateNo;
	private String challTitle;
	private int memNo;
	private Date  challWriteDate;
	private String  challCon;
	private String certifiWords;
	private String nickname;
	private String cateName;
	
	public Chall() {}




	public Chall(int challNo, int cateNo, String challTitle, int memNo, Date challWriteDate, String challCon
			, String certifiWords) {
		this.challNo = challNo;
		this.cateNo = cateNo;
		this.challTitle = challTitle;
		this.memNo = memNo;
		this.challWriteDate = challWriteDate;
		this.challCon = challCon;
		this.certifiWords = certifiWords;
	}
	
	public Chall(int challNo, int cateNo, String challTitle, int memNo, Date challWriteDate, String challCon
			, String certifiWords, String nickname,String cateName) {
		this.challNo = challNo;
		this.cateNo = cateNo;
		this.challTitle = challTitle;
		this.memNo = memNo;
		this.challWriteDate = challWriteDate;
		this.challCon = challCon;
		this.certifiWords = certifiWords;
		this.nickname = nickname;
		this.cateName = cateName;
	}
	


	public int getChallNo() {
		return challNo;
	}

	public void setChallNo(int challNo) {
		this.challNo = challNo;
	}

	public int getCateNo() {
		return cateNo;
	}

	public void setCateNo(int cateNo) {
		this.cateNo = cateNo;
	}

	public String getChallTitle() {
		return challTitle;
	}

	public void setChallTitle(String challTitle) {
		this.challTitle = challTitle;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public Date getChallWriteDate() {
		return challWriteDate;
	}

	public void setChallWriteDate(Date challWriteDate) {
		this.challWriteDate = challWriteDate;
	}

	public String getChallCon() {
		return challCon;
	}

	public void setChallCon(String challCon) {
		this.challCon = challCon;
	}


	public String getCertifiWords() {
		return certifiWords;
	}

	public void setCertifiWords(String certifiWords) {
		this.certifiWords = certifiWords;
	}


	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	@Override
	public String toString() {
		return "Chall [challNo=" + challNo + ", cateNo=" + cateNo + ", challTitle=" + challTitle + ", memNo=" + memNo
				+ ", challWriteDate=" + challWriteDate + ", challCon=" + challCon + ", certifiWords=" + certifiWords
				+ ", nickname=" + nickname + ", cateName=" + cateName + "]";
	}



}
