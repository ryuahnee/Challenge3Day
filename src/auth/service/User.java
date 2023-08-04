package auth.service;

public class User {
	private int mem_no;
	private String id;//VARCHAR(15) 
	private String nickname;//VARCHAR(10)
	
	public User(int mem_no, String id, String nickname) {
		this.mem_no = mem_no;
		this.id = id;
		this.nickname = nickname;
	}

	public int getMem_no() {
		return mem_no;
	}
	public String getId() {
		return id;
	}

	public String getNickname() {
		return nickname;
	}

	@Override
	public String toString() {
		return "User [mem_no=" + mem_no + ", id=" + id + ", nickname=" + nickname + "]";
	}
	
}
