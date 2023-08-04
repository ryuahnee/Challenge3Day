package member.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

//p594 회원가입페이지(joinForm.jsp)에서의 필수입력, 비번과 비번확인 일치여부 등을 확인하는 기능 수행
public class JoinRequest {
	//필드
	private String id;//VARCHAR(15) 
	private String pwd;//VARCHAR(50)
	private String mem_name;//VARCHAR(45)
	private String nickname;//VARCHAR(10)
	private Date birthyear;//DATE
	private String gender;//CHAR(5)
	private String mem_email;//VARCHAR(45)
	private String isMarketing;//CHAR(1)
	private String confirmPassword;
	//생성자
	//메서드
	public String getId() {
		return id;
	}

	public String getPwd() {
		return pwd;
	}

	public String getMem_name() {
		return mem_name;
	}

	public String getNickname() {
		return nickname;
	}

	public Date getBirthyear() {
		return birthyear;
	}

	public String getGender() {
		return gender;
	}

	public String getMem_email() {
		return mem_email;
	}

	public String getIsMarketing() {
		return isMarketing;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setBirthyear(Date birthyear) {
		this.birthyear = birthyear;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}

	public void setIsMarketing(String isMarketing) {
		this.isMarketing = isMarketing;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	//비번과 비번확인 일치여부
	public boolean isPasswordEqualToConfirm() {
		return pwd!=null && pwd.equals(confirmPassword);
	}

	//p595 48라인 항목별 에러체크
	public void validate(Map<String,Boolean>errors) {
		String strBirthyear = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(birthyear!=null) {
        	strBirthyear = format.format(birthyear);
        }
		checkEmpty(errors,id,"id");//회원아이디
		checkEmpty(errors,pwd,"pwd");//비밀번호
		checkEmpty(errors,mem_name,"mem_name");//이름
		checkEmpty(errors,nickname,"nickname");//닉네임
		checkEmpty(errors,strBirthyear,"birthyear");//생일
		checkEmpty(errors,gender,"gender");//성별
		checkEmpty(errors,mem_email,"mem_email");//이메일
		checkEmpty(errors,isMarketing,"isMarketing");//마케팅동의
		checkEmpty(errors,confirmPassword,"confirmPassword");//비번확인
		//비번과 비번확인 일치 여부 확인
		if(!errors.containsKey(confirmPassword)) {
			if(!isPasswordEqualToConfirm()) {
				errors.put("notMatch", Boolean.TRUE);//비번 비번확인 불일치
			}
		}
				
		
	}

	//p595 60라인 항목별 필수입력
	/* 파라미터 Map<String,Boolean>error-에러정보
	 * String value : 필드안의 값
	 * String fieldName : 필드명
	 */
	private void checkEmpty(Map<String, Boolean> errors,String value, String fieldName) {
		if(value==null || value.isEmpty()) {
			//에러가 있다면 (필수입력이 빈칸이면)
			//Map참조변수  errors에 필드명을 key로, 값을 True로 저장
			errors.put(fieldName, Boolean.TRUE);
		}
	}


	@Override
	public String toString() {
		return "JoinRequest [id=" + id + ", pwd=" + pwd + ", mem_name=" + mem_name + ", nickname=" + nickname + ", birthyear=" + birthyear
				+ ", gender=" + gender + ", mem_email=" + mem_email + ", isMarketing="
				+ isMarketing + ", confirmPassword=" + confirmPassword + "]";
	}
}