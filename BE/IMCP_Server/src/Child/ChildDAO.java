package Child;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnect.DBConnector;

public class ChildDAO {
	// DB 연결 변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL 질의 결과 변수
	private ResultSet rs;
	
	public ChildDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	private boolean checkKey(String childKey) {    //  저장할 고유키가 중복되는지 확인
		String sql = "select ChildKey from PRIVATE_KEY where ChildKey=?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);    //  아이 식별값
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return false;	//	키가 존재하여 저장이 불가능 할 때
			} else {
				return true;    //  키가 존재하지 않아 저장이 가능할 때
			}
		} catch (SQLException e) {    //  예외처리
			System.err.println("ChildDAO checkKey SQLExceptoin error");
		} finally {    //  자원해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ChildDAO checkKey close SQLException error");
			}
		}
		return false;    //  DB 오류
	}
	
	public int childRegister(String childKey, String password) {    //  아이 고유키 저장
		if(checkKey(childKey)) {    //  키가 존재하지 않아 저장이 가능할 때
			String sql = "insert into PRIVATE_KEY values(?, ?)";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, childKey);    //  아이 식별값
				pstmt.setString(2, password);    //  고유키 비밀번호
				return pstmt.executeUpdate();
			} catch (SQLException e) {    //  예외처리
				System.err.println("ChildDAO childRegister SQLExceptoin error");
			} finally {    //  자원해제
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
				} catch(SQLException e) {
					System.err.println("ChildDAO childRegister close SQLException error");
				}
			}
			return -1;    //  DB 오류
		} else {
			return 0;    //  키가 존재하여 저장이 불가능할 때
		}
	}
	
	public String childLogin(String childKey, String password) {    //  아이 고유키 확인을 위한 로그인
		String sql = "select ChildKey from PRIVATE_KEY where ChildKey = ? and Password = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);    //  아이 식별값
			pstmt.setString(2, password);    //  고유키 비밀번호
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return "ChildLoginSuccess";    //  로그인 성공
			} else {
				return "ChildLoginFail";    //  로그인 실패
			}
		} catch (SQLException e) {    //  예외처리
			System.err.println("ChildDAO childLogin SQLExceptoin error");
		} finally {    //  자원해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ChildDAO childLogin close SQLException error");
			}
		}
		return "DBError";    //  DB 오류
	}
	
	public boolean checkChild(String childKey) {    //  아이정보가 저장되었는지 확인
		String sql = "select ChildKey from CHILD_INFO where ChildKey = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);    //  아이 식별값
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;    //  아이 정보가 저장이 되어 있음
			} else {
				return false;    //  아이 정보가 저장되어 있지 않음
			}
		} catch (SQLException e) {    //  예외처리
			System.err.println("ChildDAO checkChild SQLExceptoin error");
		} finally {    //  자원해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ChildDAO checkChild close SQLException error");
			}
		}
		return false;    //  DB 오류
	}
	
	private String getTime() {    //  서버 현재 시간 구하기
		String sql = "select now()";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {    //  예외처리
			System.err.println("ChildDAO getTime SQLExceptoin error");
		} finally {    //  자원해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ChildDAO getTime close SQLException error");
			}
		}
		return "";    //  DB 오류
	}
	
	public int setChildGPS(String childKey, double lati, double longi) {    //  아이 현재 위치 등록
		if(checkChild(childKey)) {
			String sql = "insert into CHILD_GPS values(?, ?, ?, ?);";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, childKey);    //  아이 식별값
				pstmt.setString(2, getTime());    //  서버 현재시간
				pstmt.setDouble(3, lati);    //  위도
				pstmt.setDouble(4, longi);    //  경도
				return pstmt.executeUpdate();
			} catch (SQLException e) {    //  예외처리
				System.err.println("ChildDAO setChildGPS SQLExceptoin error");
			} finally {    //  자원해제
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
				} catch(SQLException e) {
					System.err.println("ChildDAO setChildGPS close SQLException error");
				}
			}
			return -1;    //  DB 오류	
		}
		else {
			return 0;    //  아이 정보가 저장되어 있지 않음
		}
	}
	
	public int childSOS(String childKey, boolean type) {    //  아이 SOS 여부 등록
		if(checkChild(childKey)) {
			String sql = "update CHILD_INFO set Help = ? where ChildKey = ?";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setBoolean(1, type);    //  SOS 여부
				pstmt.setString(2, childKey);    //  아이 식별값
				return pstmt.executeUpdate();
			} catch (SQLException e) {    //  예외처리
				System.err.println("ChildDAO childSOS SQLExceptoin error");
			} finally {    //  자원해제
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
				} catch(SQLException e) {
					System.err.println("ChildDAO childSOS close SQLException error");
				}
			}
			return -1;    //  DB 오류
		}
		return 0;    //  아이 정보가 저장되어 있지 않음
	}
}
