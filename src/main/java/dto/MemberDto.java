package dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class MemberDto {
	// 1. 변수
	private int member_id;
	private String id;
	private String password;
	private String nickname;
	private String email;
	private Timestamp reg_date;
	private int role;

	// 2. getter & setter
	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	// 3. 생성자
	public MemberDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberDto(int member_id, String id, String password, String nickname, String email, Timestamp reg_date,
			int role) {
		super();
		this.member_id = member_id;
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.reg_date = reg_date;
		this.role = role;
	}

	// 🔹 추가된 생성자 (MemberDao.getAllMembers()에서 사용)
	public MemberDto(int member_id, String id, String nickname, String email, Timestamp reg_date) {
		this.member_id = member_id;
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.reg_date = reg_date;
	}

	// toString
	@Override
	public String toString() {
		return "Members [member_id=" + member_id + ", id=" + id + ", password=" + password + ", nickname=" + nickname
				+ ", email=" + email + ", reg_date=" + reg_date + ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(member_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberDto other = (MemberDto) obj;
		return member_id == other.member_id;
	}
}
