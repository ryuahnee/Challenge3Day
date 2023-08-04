package chall.service;


//새로운 게시글을 등록할 때 필요한 데이터를 제공.
//WriteChallService가 게시글 등록 기능을 구현할 때, 필요한 요청 데이터를 담는 클래스.
public class WriteRequest {
	//필드
	private int cate_no;
	private int mem_no;
	private String chall_title; 
	private String chall_con; 	
	private String certifi_words;
	
	
	public int getCate_no() {
		return cate_no;
	}
	public void setCate_no(int cate_no) {
		this.cate_no = cate_no;
	}
	public int getMem_no() {
		return mem_no;
	}
	public void setMem_no(int mem_no) {
		this.mem_no = mem_no;
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
	
	
	
}
