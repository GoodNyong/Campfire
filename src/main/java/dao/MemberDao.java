package dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import dto.MemberDto;
import util.JDBConnect;

public class MemberDao extends JDBConnect {
	public MemberDao(ServletContext application) {
		super(application);
	}

	public MemberDto selectMember(String id) {
		// 1. 반환값을 저장할 변수를 선언
		MemberDto m = null;
		try {
			// 2. sql문 작성(role(관리자 구분) 및 가입날짜 추가),(member_id 조회 추가)
			String sql = "SELECT member_id, id, password, nickname, email, reg_date, role FROM members WHERE id = ?";

			// 3. psmt 객체 생성
			psmt = con.prepareStatement(sql);
			// 4. ?채우기
			psmt.setString(1, id);
			// 5. 실행
			rs = psmt.executeQuery();
			if (rs.next()) {
				System.out.println("----------------");
				m = new MemberDto();
				m.setMember_id(rs.getInt("member_id")); // ✅ member_id 저장
				m.setId(rs.getString("id"));
				m.setPassword(rs.getString("password"));
				m.setNickname(rs.getString("nickname"));
				m.setEmail(rs.getString("email"));
				m.setReg_date(rs.getTimestamp("reg_date")); // 가입 날짜
				m.setRole(rs.getInt("role")); // 권한 (일반 회원/관리자 구분)

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	/*
	 * insert ,update, delete - executeUpdate / int 1. 반환값을 저장할 변수를 선언 2. sql문 작성 3.
	 * psmt 객체 생성 - psmt = con.prepareStatement(sql) 4. ?채우기 - psmt.set타입(?순서, 값) 5.
	 * 실행
	 */
	public int insertMember(MemberDto member) {
		int result = 0;
		String sql = "INSERT INTO members (id, password, nickname, email, reg_date, role) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, member.getId());
			psmt.setString(2, member.getPassword());
			psmt.setString(3, member.getNickname());
			psmt.setString(4, member.getEmail());
			psmt.setTimestamp(5, new Timestamp(member.getReg_date().getTime())); // 가입 날짜 저장
			psmt.setInt(6, member.getRole());

			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateMember(MemberDto m) {
		int res = 0;
		try {
			// SQL문 동적 생성 (비밀번호 변경 여부에 따라 다르게 처리)
			String sql;
			if (m.getPassword() != null && !m.getPassword().isEmpty()) {
				sql = "UPDATE members SET password=?, nickname=?, email=? WHERE id=?";
			} else {
				sql = "UPDATE members SET nickname=?, email=? WHERE id=?";
			}

			psmt = con.prepareStatement(sql);

			if (m.getPassword() != null && !m.getPassword().isEmpty()) {
				psmt.setString(1, m.getPassword());
				psmt.setString(2, m.getNickname());
				psmt.setString(3, m.getEmail());
				psmt.setString(4, m.getId());
			} else {
				psmt.setString(1, m.getNickname());
				psmt.setString(2, m.getEmail());
				psmt.setString(3, m.getId());
			}

			res = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	/** 🔹 3. 비밀번호 확인 */
	public boolean checkPassword(String id, String password) {
		boolean isValid = false;
		String sql = "SELECT COUNT(*) FROM members WHERE id=? AND password=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, password);
			rs = psmt.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				isValid = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isValid;
	}

	/** 🔹 4. 회원 탈퇴 */
	public int deleteMember(String id) {
		int res = 0;
		String sql = "DELETE FROM members WHERE id=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			res = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	// 회원 아이디 가져오기(마이페이지용)
	public int getMemberIdByUserId(String userId) {
		int memberId = -1;
		String sql = "SELECT member_id FROM members WHERE id = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			if (rs.next()) {
				memberId = rs.getInt("member_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberId;
	}

	// 🔹 모든 회원 조회 (관리자 페이지용)
	public List<MemberDto> getAllMembers() {
		List<MemberDto> memberList = new ArrayList<>();
		String sql = "SELECT member_id, id, nickname, email, reg_date, role FROM members ORDER BY reg_date DESC";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int memberId = rs.getInt("member_id");
				String id = rs.getString("id");
				String nickname = rs.getString("nickname");
				String email = rs.getString("email");
				Timestamp regDate = rs.getTimestamp("reg_date"); // ✅ Timestamp로 가져오기
				int role = rs.getInt("role");

				// ✅ MemberDto에 맞는 생성자 사용
				MemberDto member = new MemberDto(memberId, id, nickname, email, regDate);
				member.setRole(role); // 역할(role)도 추가 설정
				memberList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("❌ [ERROR] 회원 목록 조회 중 오류 발생");
		}
		return memberList;
	}

	// 관리자 정보 조회
	public MemberDto getMemberById(String id) {
		MemberDto member = null;
		try {
			String sql = "SELECT * FROM members WHERE id = ?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();

			if (rs.next()) {
				member = new MemberDto(rs.getInt("member_id"), rs.getString("id"), rs.getString("nickname"),
						rs.getString("email"), rs.getTimestamp("reg_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}

	// 🔹 member_id로 회원 정보를 가져오는 메서드 추가
	public MemberDto getMemberById(int memberId) {
		MemberDto member = null;
		try {
			String sql = "SELECT * FROM members WHERE member_id = ?";
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, memberId);
			rs = psmt.executeQuery();

			if (rs.next()) {
				member = new MemberDto(rs.getInt("member_id"), rs.getString("id"), rs.getString("password"),
						rs.getString("nickname"), rs.getString("email"), rs.getTimestamp("reg_date"),
						rs.getInt("role"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}

	// 🔹 특정 회원 삭제 (관리자 기능)
	public int deleteMemberById(int memberId) {
		int res = 0;
		String sql = "DELETE FROM members WHERE member_id = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, memberId);
			res = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
