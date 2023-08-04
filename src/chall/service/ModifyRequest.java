package chall.service;


//글 수정할 때 필요한 데이터를 제공.
public class ModifyRequest {
	private int chall_no;
	private int cate_no;
	private String chall_title; 
	private String chall_con; 	
	private String certifi_words;
	
	
	
	public ModifyRequest(int chall_no, int cate_no, String chall_title, String chall_con, String certifi_words) {
		this.chall_no = chall_no;
		this.cate_no = cate_no;
		this.chall_title = chall_title;
		this.chall_con = chall_con;
		this.certifi_words = certifi_words;
	}
	
	public int getChall_no() {
		return chall_no;
	}
	public void setChall_no(int chall_no) {
		this.chall_no = chall_no;
	}
	public int getCate_no() {
		return cate_no;
	}
	public void setCate_no(int cate_no) {
		this.cate_no = cate_no;
	}
	public String getChall_title() {
		return chall_title;
	}
	public void setChall_title(String chall_title) {
		this.chall_title = chall_title;
	}
	public String getChall_con() {
		return chall_con;
	}
	public void setChall_con(String chall_con) {
		this.chall_con = chall_con;
	}
	public String getCertifi_words() {
		return certifi_words;
	}
	public void setCertifi_words(String certifi_words) {
		this.certifi_words = certifi_words;
	}

	@Override
	public String toString() {
		return "ModifyRequest [chall_no=" + chall_no + ", cate_no=" + cate_no + ", chall_title=" + chall_title
				+ ", chall_con=" + chall_con + ", certifi_words=" + certifi_words + "]";
	}
	
	
	
}
