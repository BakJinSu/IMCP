package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnect.DBConnector;

public class ParentDAO {
	private DBConnector dbConnector;
	private Connection conn;
	
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;
	private String results = "";
	private String error = "non";

	public ParentDAO(){
		dbConnector = new DBConnector();
	}
	
	public String addChild(String img_file, String img_realfile,
							String name, String childKey, String birth) {	//	�� ���� �߰��ϱ�
		try {
			conn = dbConnector.getConnection();
			sql = "select * from CHILD_INFO where childkey=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	��ϵ� �����϶�
				return "childAddFail";
			}
			else {	//	���̸� ���
				sql2 = "insert into CHILD_INFO (img_file, img_realfile, name, childkey, birth) values (?, ?, ?, ?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, img_file);
				pstmt2.setString(2, img_realfile);
				pstmt2.setString(3, name);
				pstmt2.setString(4, childKey);
				pstmt2.setString(5, birth);
				/*
				 * �̹��� ������ ������ �����ϴ� �ڵ� �ʿ�
				 */
				return "childAddSuccess";
			}
		} catch (SQLException e) {
			System.err.println("ParentDAO addChild SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("ParentDAO addChild close SQLExceptoin error");
			}
		}
		return error;
	}
	
	public String modifyChildInfo(String childKey, String img_file, String img_realfile, 
									String name, String birth) {	//	���� ���� ����(����, �̸�, �������, GPS-���� �湮�ϴ� ��)
		try {
			conn = dbConnector.getConnection();
			sql = "select * from CHILD_INFO where childkey=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	���� ������ ������ ��
				sql2 = "update CHILD_INFO set img_file=?, img_realfile=?, name=?, birth=? where childkey=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, img_file);
				pstmt2.setString(2, img_realfile);
				pstmt2.setString(3, name);
				pstmt2.setString(4, birth);
				pstmt2.setString(5, childKey);
				pstmt2.executeUpdate();
				/*
				 * �̹��� ������ ������ �����ϴ� �ڵ� �ʿ�
				 */
				
				return "childInfoUpdateSuccess";
			}
			else {
				return "childInfoUpdateFail";
			}
			
		} catch (SQLException e) {
			System.err.println("ParentDAO modifyChildInfo SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("ParentDAO modifyChildInfo close SQLExceptoin error");
			}
		}
		return error;
	}
}
