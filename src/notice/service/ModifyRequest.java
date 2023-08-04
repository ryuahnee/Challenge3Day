package notice.service;

import java.util.Map;

//수정을 위해 세션에서 가져온 회원id, 글번호, 제목, 내용  db
public class ModifyRequest {
	private int notiNo; 		//글번호
	private String notiTitle; 		//db에서 가져온 제목
	private String notiContent; 	//db에서 가져온 내용 
	
	
	

	public ModifyRequest(int notiNo, String notiTitle, String notiContent) {
		this.notiNo = notiNo;
		this.notiTitle = notiTitle;
		this.notiContent = notiContent;
	}




	public int getNotiNo() {
		return notiNo;
	}


	public String getNotiTitle() {
		return notiTitle;
	}


	public String getNotiContent() {
		return notiContent;
	}


	//p667 유효성검사
	public void validate(Map<String, Boolean> errors) {
		if(notiTitle==null || notiTitle.trim().isEmpty()) {
			errors.put("notiTitle",Boolean.TRUE);
		}
		if(notiContent==null || notiContent.trim().isEmpty()) {
			errors.put("notiContent",Boolean.TRUE);
		}
	}



	@Override
	public String toString() {
		return "ModifyRequest [notiNo=" + notiNo + ", notiTitle=" + notiTitle + ", notiContent="
				+ notiContent + "]";
	}


	
	
	

	
	
}




