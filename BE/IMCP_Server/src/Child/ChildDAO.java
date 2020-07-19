package Child;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

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
	
	private ArrayList<String> getParentsToken(String childKey) {    //  �θ� fcm ��ū �� ȹ��
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select Token from PARENT_TOKEN where ID in (select ID from PtoC where ChildKey = ?)";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);    //  ���� �ĺ���
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ChildDAO getParentsToken SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ChildDAO getParentsToken close SQLException error");
			}
		}
		return list;
	}
	
	private int sendFCMSOS(ArrayList<String> list, boolean type) {    //  FCM ������ ����(SOS)
		int result = -200;
		
	    //  �����͸� ���� URL
		String fcmURL = "https://fcm.googleapis.com/fcm/send";
	    //  FCM Setting -> Cloud Messaging
		String fcmApiKey = "AAAAK-E7ezg:APA91bHMDCXaStMIhwELOcDylGkg-W8EuUPfl8Jt3d2T5B0kdp_o8-IxLvuf9zeCu_vV8KEbLn9Lu6C9XrAwE8ezlJR2kgcrgAz2G7LSgtYY8Gn24r85qR1zE3zno-xdJlhvdYAwY9sK";
		
		DataOutputStream dos = null;
		OutputStreamWriter osw = null;
		BufferedWriter writer = null;
		
		try {
			URL url = new URL(fcmURL);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setRequestProperty("Authorization", "key=" + fcmApiKey);
			httpUrlConnection.setRequestProperty("Content-Type", "application/json; UTF-8");

			//  FCM���� ���� JSONObject
			HashMap<String, Object> hashData = new HashMap<String, Object>();
			hashData.put("title", "SOS");    //  Notification Title
			hashData.put("body", "���̰� �����մϴ�.");    //  Notification Body
			if(type) {
				hashData.put("SOS", "on");    //  ���̰� �����û ����
			} else {
				hashData.put("SOS", "off");    //  ���� ���� ��û ���� �ذ�
			}
			JSONObject dataObject = new JSONObject(hashData);    //  HashMap�� JSONObject�� ��ȯ

			//  FCM���� �˸� ���� ��� JSONObject
			HashMap<String, Object> hashFCM = new HashMap<String, Object>();
			hashFCM.put("registration_ids", list);    //  ���� ������ ��ū
			hashFCM.put("data", dataObject);    //  ��⿡ ���� ������
			JSONObject sendObject = new JSONObject(hashFCM);    //  HashMap�� JSONObject�� ��ȯ

			dos = new DataOutputStream(httpUrlConnection.getOutputStream());
			osw = new OutputStreamWriter(dos);
			writer = new BufferedWriter(osw);
			writer.write(sendObject.toJSONString());
			writer.flush();
			httpUrlConnection.connect();
			result = httpUrlConnection.getResponseCode();
		} catch (MalformedURLException e) {
			System.err.println("ChildDAO sendFCMSOS MalformedURLException error");
		} catch (IOException e) {
			System.err.println("ChildDAO sendFCMSOS IOException error");
		} finally {
			try {
				if(dos != null) {dos.close();}
				if(osw != null) {osw.close();}
				if(writer != null) {writer.close();}
			} catch (IOException e) {
				System.err.println("ChildDAO sendFCMSOS close IOException error");
			}
			
		}
		return result;
	}
	
	public int childSOS(String childKey, boolean type) {    //  ���� SOS ���� ���
		if(checkChild(childKey)) {
			ArrayList<String> list = getParentsToken(childKey);
			int httpResult = sendFCMSOS(list, type);
			if(httpResult != 200) {
				return httpResult;
			}
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
