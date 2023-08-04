package member.model;

public class Auth {
	//필드
	private String isAuth;
	private String id;
	//생성자
	public Auth(String isAuth, String id) {
		super();
		this.isAuth = isAuth;
		this.id = id;
	}
	//메소드
	public String getIsAuth() {
		return isAuth;
	}
	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Auth [isAuth=" + isAuth + ", id=" + id + "]";
	}
}
