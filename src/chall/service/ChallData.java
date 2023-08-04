package chall.service;

import java.util.Date;

public class ChallData {
	// 필드
	private Integer challNo;
	private Integer cateNo;
	private String challTitle;
	private int memNo;
	private Date challWriteDate;
	private String challCon;
	private int challHit;
	private String certifiWords;
	private Integer challOrifileName;

	// 생성자
	public ChallData() {

	}

	public Integer getChallNo() {
		return challNo;
	}

	public void setChallNo(Integer challNo) {
		this.challNo = challNo;
	}

	
	public Integer getCateNo() { return cateNo; }
	
	public void setCateNo(Integer cateNo) { 
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

	public int getChallHit() {
		return challHit;
	}

	public void setChallHit(int challHit) {
		this.challHit = challHit;
	}

	public String getCertifiWords() {
		return certifiWords;
	}

	public void setCertifiWords(String certifiWords) {
		this.certifiWords = certifiWords;
	}

	public Integer getChallOrifileName() {
		return challOrifileName;
	}

	public void setChallOrifileName(Integer challOrifileName) {
		this.challOrifileName = challOrifileName;
	}

	@Override
	public String toString() {
		return "ChallData [challNo=" + challNo + ", cateNo=" /* + cateNo */ + ", challTitle=" + challTitle + ", memNo="
				+ memNo + ", challWriteDate=" + challWriteDate + ", challCon=" + challCon + ", challHit=" + challHit
				+ ", certifiWords=" + certifiWords + ", challOrifileName=" + challOrifileName + "]";
	}

}
