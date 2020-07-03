package Child;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnect.DBConnector;

public class ChildDAO {
	// DB ���� ����
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL ���� ��� ����
	private ResultSet rs;
	
	public ChildDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	private boolean checkKey(String childKey) {    //  ������ ����Ű�� �ߺ��Ǵ��� Ȯ��
		String sql = "select ChildKey from PRIVATE_KEY where ChildKey=?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);    //  ���� �ĺ���
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return false;	//	Ű�� �����Ͽ� ������ �Ұ��� �� ��
			} else {
				return true;    //  Ű�� �������� �ʾ� ������ ������ ��
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ChildDAO checkKey SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ChildDAO checkKey close SQLException error");
			}
		}
		return false;    //  DB ����
	}
	
	public int childRegister(String childKey, String password) {    //  ���� ����Ű ����
		if(checkKey(childKey)) {    //  Ű�� �������� �ʾ� ������ ������ ��
			String sql = "insert into PRIVATE_KEY values(?, ?)";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, childKey);    //  ���� �ĺ���
				pstmt.setString(2, password);    //  ����Ű ��й�ȣ
				return pstmt.executeUpdate();
			} catch (SQLException e) {    //  ����ó��
				System.err.println("ChildDAO childRegister SQLExceptoin error");
			} finally {    //  �ڿ�����
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
				} catch(SQLException e) {
					System.err.println("ChildDAO childRegister close SQLException error");
				}
			}
			return -1;    //  DB ����
		} else {
			return 0;    //  Ű�� �����Ͽ� ������ �Ұ����� ��
		}
	}
	
	public String childLogin(String childKey, String password) {    //  ���� ����Ű Ȯ���� ���� �α���
		String sql = "select ChildKey from PRIVATE_KEY where ChildKey = ? and Password = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);    //  ���� �ĺ���
			pstmt.setString(2, password);    //  ����Ű ��й�ȣ
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return "ChildLoginSuccess";    //  �α��� ����
			} else {
				return "ChildLoginFail";    //  �α��� ����
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ChildDAO childLogin SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ChildDAO childLogin close SQLException error");
			}
		}
		return "DBError";    //  DB ����
	}
	
	public int getAge(String birth) {    //  �� ���� ���ϱ�
		String sql = "select truncate(datediff((select curdate()), ?) /365, 0)";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, birth);    //  �������
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ChildDAO getAge SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ChildDAO getAge close SQLException error");
			}
		}
		return -1;    //  DB ����
	}
	
	public boolean checkChild(String childKey) {    //  ���������� ����Ǿ����� Ȯ��
		String sql = "select ChildKey from CHILD_INFO where ChildKey = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);    //  ���� �ĺ���
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;    //  ���� ������ ������ �Ǿ� ����
			} else {
				return false;    //  ���� ������ ����Ǿ� ���� ����
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ChildDAO checkChild SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ChildDAO checkChild close SQLException error");
			}
		}
		return false;    //  DB ����
	}
	
	private String getTime() {    //  ���� ���� �ð� ���ϱ�
		String sql = "select now()";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ChildDAO getTime SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ChildDAO getTime close SQLException error");
			}
		}
		return "";    //  DB ����
	}
	
	public int setChildGPS(String childKey, double lati, double longi) {    //  ���� ���� ��ġ ���
		if(checkChild(childKey)) {
			String sql = "insert into CHILD_GPS values(?, ?, ?, ?);";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, childKey);    //  ���� �ĺ���
				pstmt.setString(2, getTime());    //  ���� ����ð�
				pstmt.setDouble(3, lati);    //  ����
				pstmt.setDouble(4, longi);    //  �浵
				return pstmt.executeUpdate();
			} catch (SQLException e) {    //  ����ó��
				System.err.println("ChildDAO setChildGPS SQLExceptoin error");
			} finally {    //  �ڿ�����
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
				} catch(SQLException e) {
					System.err.println("ChildDAO setChildGPS close SQLException error");
				}
			}
			return -1;    //  DB ����	
		}
		else {
			return 0;    //  ���� ������ ����Ǿ� ���� ����
		}
	}
	
	public int childSOS(String childKey, boolean type) {    //  ���� SOS ���� ���
		if(checkChild(childKey)) {
			String sql = "update CHILD_INFO set Help = ? where ChildKey = ?";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setBoolean(1, type);    //  SOS ����
				pstmt.setString(2, childKey);    //  ���� �ĺ���
				return pstmt.executeUpdate();
			} catch (SQLException e) {    //  ����ó��
				System.err.println("ChildDAO childSOS SQLExceptoin error");
			} finally {    //  �ڿ�����
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
				} catch(SQLException e) {
					System.err.println("ChildDAO childSOS close SQLException error");
				}
			}
			return -1;    //  DB ����
		}
		return 0;    //  ���� ������ ����Ǿ� ���� ����
	}
}
