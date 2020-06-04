package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnect.DBConnector;

public class MemberDAO {
	private DBConnector dbConnector;
	private Connection conn;
	private MemberDTO memDTO;
	
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;
	private String results = "";
	private String error = "non";
	public MemberDAO(MemberDTO memDTO) {
		this.memDTO = memDTO;
		dbConnector = new DBConnector();
	}

	public String memberJoin() {	//	ȸ������
		try {
			conn = dbConnector.getConnection();
			sql = "select * from PARENT_INFO where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memDTO.getId());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return "memberAlreadyExist";
			}
			else {
				sql2 = "insert into PARENT_INFO(id, password, name, phone, email) values (?, ?, ?, ?, ?)";
				pstmt.setString(1, memDTO.getId());
				pstmt.setString(2, memDTO.getPassword());
				pstmt.setString(3, memDTO.getName());
				pstmt.setString(4, memDTO.getPhone());
				pstmt.setString(5, memDTO.getEmail());
				pstmt.executeUpdate();
				
				return "memberAddSuccess";
			}			
		} catch (SQLException e) {
			System.err.println("MemberDAO memberJoin SQLExceptoin error");
		} finally {
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
					if(rs != null) {rs.close();}
				} catch (SQLException e) {
					System.err.println("MemberDAO memberJoin close SQLExceptoin error");
				}
		}
		//	���� �߻���
		return error;
	}
	
	public String memberLogin() {	//	�α���
		try {
			conn = dbConnector.getConnection();
			sql = "select id, password from PARENT_INFO where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memDTO.getId());
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString("password").equals(memDTO.getPassword())) {
					return "memberLoginSuccess";
				}
			}
		} catch (SQLException e) {
			System.err.println("MemberDAO memberLogin SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("MemberDAO memberLogin close SQLExceptoin error");
			}
		}
		//	���� Ȥ�� id�� Ʋ�Ȱų� ��й�ȣ�� ���� ������
		return error;
	}
	
	public String memberFindID() {	//	��й�ȣ ã��
		try {
			conn = dbConnector.getConnection();
			sql = "select name, email, id from PARENT_INFO where name=? and email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memDTO.getName());
			pstmt.setString(2, memDTO.getEmail());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//	ã�� ID�� ���� �����ش� ����� '_'�� ����
				return rs.getString("id")+"_memberFindSuccess";
			}
		} catch (SQLException e) {
			System.err.println("MemberDAO memberFindID SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("MemberDAO memberFindID close SQLExceptoin error");
			}
		}
		//	���� Ȥ�� �̸��� email�� ��ġ�ϴ� �θ� �����ͺ��̽��� ���� ��
		return error;
	}
	
	public String memberFindPW() {	//	��й�ȣ ã��
		try {
			conn = dbConnector.getConnection();
			sql = "select id, name, email from PARENT_INFO where id=? and name=? and email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memDTO.getId());
			pstmt.setString(2, memDTO.getName());
			pstmt.setString(3, memDTO.getEmail());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return "memberFindPasswordSuccess";
			}
			
		} catch (SQLException e) {
			System.err.println("MemberDAO memberFindPW SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("MemberDAO memberFindPW close SQLExceptoin error");
			}
		}
		//	���� Ȥ�� id, �̸�, �̸����� ��ġ�ϴ� ������ ���� ��
		return error;
	}
	
	public String memberChangePW() {	//	��й�ȣ ����
		try {
			conn = dbConnector.getConnection();
			sql = "select password from PARENT_INFO where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memDTO.getId());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				sql2 = "update PARENT_INFO set password=? where id=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, memDTO.getPassword());
				pstmt2.setString(2, memDTO.getId());
			}
		} catch (SQLException e) {
			System.err.println("MemberDAO memberChangePW SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("MemberDAO memberChangePW close SQLExceptoin error");
			}
		}
		//	���� Ȥ�� id�� ������
		return error;
	}
}
