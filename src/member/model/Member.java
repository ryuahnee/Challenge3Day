package member.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

//멤버DTO
public class Member {
	//필드
	private int mem_no;//INT
	private String id;//VARCHAR(15) 
	private String pwd;//VARCHAR(50)
	private String mem_name;//VARCHAR(45)
	private String nickname;//VARCHAR(10)
	private Date birthyear;//DATE
	private String gender;//CHAR(5)
	private Date join_date;//DATETIME
	private String mem_email;//VARCHAR(45)
	private String isMarketing;//CHAR(1)
    
	//생성자
    public Member(int mem_no, String id, String pwd, String mem_name, String nickname, Date birthyear, String gender,
    		Date join_date, String mem_email, String isMarketing) {
    	this.mem_no = mem_no;
    	this.id = id;
    	this.pwd = pwd;
    	this.mem_name = mem_name;
    	this.nickname = nickname;
    	this.birthyear = birthyear;
    	this.gender = gender;
    	this.join_date = join_date;
    	this.mem_email = mem_email;
    	this.isMarketing = isMarketing;
    }

    public Member(int mem_no, String id, String mem_name, String nickname, Date birthyear, String gender,
    		Date join_date, String mem_email, String isMarketing) {
    	this.mem_no = mem_no;
    	this.id = id;
    	this.mem_name = mem_name;
    	this.nickname = nickname;
    	this.birthyear = birthyear;
    	this.gender = gender;
    	this.join_date = join_date;
    	this.mem_email = mem_email;
    	this.isMarketing = isMarketing;
    }

    public Member(String id, String pwd, String mem_name, String nickname, Date birthyear, String gender,
    		Date join_date, String mem_email, String isMarketing) {
    	this.id = id;
    	this.pwd = pwd;
    	this.mem_name = mem_name;
    	this.nickname = nickname;
    	this.birthyear = birthyear;
    	this.gender = gender;
    	this.join_date = join_date;
    	this.mem_email = mem_email;
    	this.isMarketing = isMarketing;
    }
    
	//메서드 
    public int getMem_no() {
    	return mem_no;
    }

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
    
    public Date getJoin_date() {
    	return join_date;
    }
    
    public String getMem_email() {
    	return mem_email;
    }
    
    public String getIsMarketing() {
    	return isMarketing;
    }
    
	//p595 48라인 항목별 에러체크
	public void validate(Map<String,Boolean>errors) {
		String strBirthyear = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(birthyear!=null) {
        	strBirthyear = format.format(birthyear);
        }
		checkEmpty(errors,id,"id");//회원아이디
		checkEmpty(errors,mem_name,"mem_name");//이름
		checkEmpty(errors,nickname,"nickname");//닉네임
		checkEmpty(errors,strBirthyear,"birthyear");//생일
		checkEmpty(errors,gender,"gender");//성별
		checkEmpty(errors,mem_email,"mem_email");//이메일
		checkEmpty(errors,isMarketing,"isMarketing");//마케팅동의
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
	//p592-필드의 패스워드와 매개변수 pwd가 일치하면 true리턴 , 불일치시 false 리턴
	//=>member테이블의 비번컬럼의 값과 매개변수pwd가 일치하면 true리턴, 불일치하면 false 리턴
	public boolean matchPwd(String pwd) {
		return this.pwd.equals(pwd);
	}
	

	//p619 line20
	//비번을 새 비번으로 변경
	public void changePwd(String newPwd) {
		this.pwd=newPwd;
	}

	@Override
	public String toString() {
		return "Member [mem_no=" + mem_no + ", id=" + id + ", pwd=" + pwd + ", mem_name=" + mem_name + ", nickname="
				+ nickname + ", birthyear=" + birthyear + ", gender=" + gender + ", join_date=" + join_date
				+ ", mem_email=" + mem_email + ", isMarketing=" + isMarketing + "]";
	}
	
}
