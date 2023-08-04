package chall.model;

import java.util.Date;

public class ChallOpin {
	private int challOpinNo;
	private int challNo;
	private int memNo;
	private String opinCon;
	private Date writeDate;
	private String nickname;
	
	public ChallOpin() {}
	
	public ChallOpin(int challOpinNo, int challNo, int memNo, String opinCon, Date writeDate, String nickname) {
		super();
		this.challOpinNo = challOpinNo;
		this.challNo = challNo;
		this.memNo = memNo;
		this.opinCon = opinCon;
		this.writeDate = writeDate;
		this.nickname = nickname;
	}

	public int getChallOpinNo() {
		return challOpinNo;
	}

	public void setChallOpinNo(int challOpinNo) {
		this.challOpinNo = challOpinNo;
	}

	public int getChallNo() {
		return challNo;
	}

	public void setChallNo(int challNo) {
		this.challNo = challNo;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public String getOpinCon() {
		return opinCon;
	}

	public void setOpinCon(String opinCon) {
		this.opinCon = opinCon;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "ChallOpin [challOpinNo=" + challOpinNo + ", challNo=" + challNo + ", memNo=" + memNo + ", opinCon="
				+ opinCon + ", writeDate=" + writeDate + ", nickname=" + nickname + "]";
	}

	
}
