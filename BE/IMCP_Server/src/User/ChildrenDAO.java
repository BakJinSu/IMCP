package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnect.DBConnector;

public class ChildrenDAO {
	private DBConnector dbConnector;
	private Connection conn;
	private ChildrenDTO childDTO;
	
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;
	private String results = "";
	private String error = "non";

	public ChildrenDAO(ChildrenDTO childDTO) {
		this.childDTO = childDTO;
		dbConnector = new DBConnector();
	}
	
	public String childrenRegister() {	//	���� ����Ű ����
		try {
			
			conn = dbConnector.getConnection();
			sql = "select key from CHILD where key=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childDTO.getKey());
			pstmt.setString(2, childDTO.getPassword());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return "keyAlreadyExist";	//	Ű�� �����Ͽ� �������� ���� ��
			}
			else {
				sql2 = "insert into CHILD (key, password) values (?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, childDTO.getKey());
				pstmt2.setString(2, childDTO.getPassword());
				pstmt2.executeUpdate();
				return "childRegisterSuccess";	//	Ű ���� ����
			}
			
		} catch (SQLException e) {
			System.err.println("ChildrenDAO childrenRegister SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("ChildrenDAO childrenRegister close SQLExceptoin error");
			}
		}
		return error;
	}
	
	public String childrenLogin() {	//	���� ����Ű Ȯ���� ���� �α���
		try {
			conn = dbConnector.getConnection();
			sql = "select * from CHILD where key=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childDTO.getKey());
			pstmt.setString(2, childDTO.getPassword());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return "childLoginSuccess";	//	�α��� ����
			}
			else {
				return "childLoginFail";	//	�α��� ����
			}
		} catch (SQLException e) {
			System.err.println("ChildrenDAO childrenLogin SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("ChildrenDAO childrenLogin close SQLExceptoin error");
			}
		}
		return error;
	}
	
	public String childrenGPSSet() {
		try {
			conn = dbConnector.getConnection();
			sql = "select * from GPS_INITIALIZATION where childkey=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childDTO.getChildKey());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				sql2 = "insert into GPS_INITIALIZATION (latitude, longtitude) values (?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setDouble(1, childDTO.getLatitude());
				pstmt2.setDouble(2, childDTO.getLongtitude());
				pstmt2.executeUpdate();
				return "childGPSSetSuccess";
			}
			else {	//	���̰���Ű(���̽ĺ���)�� �߸��� ���
				return "childGPSSetFail";
			}
			
		} catch (SQLException e) {
			System.err.println("ChildrenDAO childGPSSet close SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("ChildrenDAO childGPSSet close SQLExceptoin error");
			}
		}
		return error;
	}
	
	public String childrenSOS() {
		try {
			conn = dbConnector.getConnection();
			sql = "select * from CHILD_INFO where childkey=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childDTO.getChildKey());
			pstmt.executeQuery();
			if(rs.next()) {
				boolean temp = rs.getBoolean("help");	//	�����ͺ��̽��� ����� help(boolean)���� �����´�
				sql2 = "update CHILD_INFO set help=? where childkey=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setBoolean(1, !temp);
				pstmt2.setString(2, childDTO.getChildKey());
				pstmt2.executeUpdate();
				return "childSOSUpdateSuccess";
			}
			else {
				return "childSOSUpdateFail";
			}
		} catch (SQLException e) {
			System.err.println("ChildrenDAO childSOS close SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("ChildrenDAO childSOS close SQLExceptoin error");
			}
		}
		return error;
	}
	
	public String childrenDanger() {
		try {
			conn = dbConnector.getConnection();
			sql = "select * from CHILD_INFO where childkey=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childDTO.getChildKey());
			pstmt.executeQuery();
			if(rs.next()) {
				boolean temp = rs.getBoolean("dangerous");	//	�����ͺ��̽��� ����� dangerous(boolean)���� �����´�
				sql2 = "update CHILD_INFO set dangerous=? where childkey=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setBoolean(1, !temp);
				pstmt2.setString(2, childDTO.getChildKey());
				pstmt2.executeUpdate();
				return "childDangerUpdateSuccess";
			}
			else {
				return "childDangerUpdateFail";
			}
		} catch (SQLException e) {
			System.err.println("ChildrenDAO childDanger close SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("ChildrenDAO childDanger close SQLExceptoin error");
			}
		}
		return error;
	}
	
	public String childrenMissing() {
		try {
			conn = dbConnector.getConnection();
			sql = "select * from CHILD_INFO where childkey=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childDTO.getChildKey());
			pstmt.executeQuery();
			if(rs.next()) {
				boolean temp = rs.getBoolean("missing");	//	�����ͺ��̽��� ����� missing(boolean)���� �����´�
				sql2 = "update CHILD_INFO set missing=? where childkey=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setBoolean(1, !temp);
				pstmt2.setString(2, childDTO.getChildKey());
				pstmt2.executeUpdate();
				return "childMissingUpdateSuccess";
			}
			else {
				return "childMissingUpdateFail";
			}
		} catch (SQLException e) {
			System.err.println("ChildrenDAO childMissing close SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("ChildrenDAO childMissing close SQLExceptoin error");
			}
		}
		return error;
	}
}
