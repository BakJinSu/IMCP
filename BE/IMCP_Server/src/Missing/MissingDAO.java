package Missing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import DBConnect.DBConnector;

public class MissingDAO {
	//  DB ���� ����
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL ���� ��� ����
	private ResultSet rs;
	
	public MissingDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	public String getMissingList() {    //  ���� �Ƶ� ��� ȹ��
		ArrayList<MissingDTO> list = new ArrayList<MissingDTO>();
		String sql = "select ChildKey, Image, Name, Birth, Phone from MISSING_INFO";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MissingDTO missingDTO = new MissingDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				list.add(missingDTO);
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("MissingDAO getMissingList SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("MissingDAO getMissingList close SQLException error");
			}
		}
		
		ArrayList<JSONObject> missingArray = new ArrayList<JSONObject>();    //  JSON �����͵��� ���� List
		for(int i = 0; i < list.size(); i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();    //  key�� value�� ����
			hashMap.put("key", list.get(i).getChildKey());
			hashMap.put("name", list.get(i).getName());
			hashMap.put("birth", list.get(i).getBirth());
			hashMap.put("image", list.get(i).getImg());
			hashMap.put("parentPhone", list.get(i).getPrentPhone());
			JSONObject childObject = new JSONObject(hashMap);    //  JSON �����ͷ� �����
			missingArray.add(childObject);    //  JSON List�� �߰�
		}
		
		return missingArray.toString();
	}
	
	public double[] getMissingGPS(String childKey) {    //  �����Ƶ� ��ġ ȹ��
		double[] gps = new double[2];
		String sql = "select Latitude, Longitude from CHILD_GPS where ChildKey = ? order by Time desc limit 1 ";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);    //  ���� �ĺ���
			rs = pstmt.executeQuery();
			if(rs.next()) {
				gps[0] = rs.getDouble(1);    //  ����
				gps[1] = rs.getDouble(2);    //  �浵
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("MissingDAO getMissingGPS SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("MissingDAO getMissingGPS close SQLException error");
			}
		}
		return gps;
	}
}
