package Parent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Child.*;
import DBConnect.DBConnector;

public class ParentDAO {
	// DB ���� ����
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL ���� ��� ����
	private ResultSet rs;
	
	public ParentDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	private boolean checkID(String id) {    //  ���̵� �ߺ�Ȯ��
		String sql = "select ID from PARENT_INFO where ID = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);    //  ���̵�
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return false;	//  ���̵� �ߺ�
			} else {
				return true;    //  ���̵� ��밡��
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO checkID SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PatentDAO checkID close SQLException error");
			}
		}
		return false;    //  DB ����
	}
	
	public int join(String id, String password, String name, String phone, String email) {    //  ȸ������
		if(checkID(id)) {
			String sql = "insert into PARENT_INFO values(?, ?, ?, ?, ?)";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);    //  ���̵�
				pstmt.setString(2, password);    //  ��й�ȣ
				pstmt.setString(3, name);    //  �̸�
				pstmt.setString(4, phone);    //  �ڵ�����ȣ
				pstmt.setString(5, email);    //  �̸���
				return pstmt.executeUpdate();
			} catch (SQLException e) {    //  ����ó��
				System.err.println("ParentDAO join SQLExceptoin error");
			} finally {    //  �ڿ�����
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
				} catch(SQLException e) {
					System.err.println("PatentDAO join close SQLException error");
				}
			}
			return -1;    //  DB ����
		} else {
			return 0;    //  ���̵� �ߺ�
		}
	}
	
	public String login(String id, String password) {    //  �α���
		String sql = "select Password from PARENT_INFO where ID = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);    //  �θ� ���̵�
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(password)) {
					return "LoginSuccess";    //  �α��� ����
				} else {
					return "LoginFail";    //  ��й�ȣ ����
				}
			}
			return "NoID";    //  ���̵� ����
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO login SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PatentDAO login close SQLException error");
			}
		}
		return "DBError";    //  DB ����
	}
	
	public String findID(String name, String email) {    //  ���̵� ã��
		String sql = "select ID from PARENT_INFO where Name = ? and Email = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);    //  �θ� �̸�
			pstmt.setString(2, email);    //  �θ� �̸���
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);    //  ã�� ���̵� ��ȯ
			}
			return "NoID";    //  �ش��ϴ� �ƴϵ� ����
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO findID SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PatentDAO findID close SQLException error");
			}
		}
		return "DBError";    //  DB ����
	}
	
	public String findPW(String id, String name, String email) {    //  �н����� ã��
		String sql = "select ID from PARNET_INFO where ID = ? and Name = ? and Email = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);    //  ���̵�
			pstmt.setString(2, name);    //  �̸�
			pstmt.setString(3, email);    //  �ڵ�����ȣ
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return "FindPWSuccess";    //  �ش���̵� Ȯ�� ����, ��й�ȣ ���� ����
			}
			return "FindPWFail";    //  �ش���̵� Ȯ�� ����, ��й�ȣ ���� �Ұ�
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO findPW SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PatentDAO findPW close SQLException error");
			}
		}
		return "DBError";    //  DB ����
	}
	
	public int newPW(String id, String newPassword, String email) {    //  ���ο� ��й�ȣ
		String sql = "update PARENT_INFO set password = ? where ID = ? and Email = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPassword);    //  ���ο� ��й�ȣ
			pstmt.setString(2, id);    //  ���̵�
			pstmt.setString(3, email);    //  �ڵ�����ȣ
			return pstmt.executeUpdate();    //  ��й�ȣ ���� �Ϸ�
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO newPW SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("PatentDAO newPW close SQLException error");
			}
		}
		return -1;    //  DB ����
	}
	
	public int changePW(String id, String password, String newPassword) {    //  �н����� �ٲٱ�
		String sql = "update PARENT_INFO set Password = ? where ID = ? and Password = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPassword);    //  ���ο� ��й�ȣ
			pstmt.setString(2, id);    //  ���̵�
			pstmt.setString(3, password);    //  ������ȣ
			return pstmt.executeUpdate();    //  ��й�ȣ ���� �Ϸ�
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO changePW SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("PatentDAO changePW close SQLException error");
			}
		}
		return -1;    //  DB ����
	}
	
	public String getParentInfo(String parentID) {    //  �θ� ���� ȹ��
		ParentDTO parentDTO = null;    //  �θ� ���� ���� ����
		
		String sql = "select ID, Name, Phone, Email from PARENT_INFO where ID = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, parentID);    //  �θ� ���̵�
			rs = pstmt.executeQuery();
			if(rs.next()) {
				parentDTO = new ParentDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO getParentInfo SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PatentDAO getParentInfo close SQLException error");
			}
		}
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();    //  key�� value�� ����
		hashMap.put("id", parentDTO.getId());    //  �θ� ���̵�
		hashMap.put("name", parentDTO.getName());    //  �θ� �̸�
		hashMap.put("phone", parentDTO.getPhone());    //  �θ� �ڵ�����ȣ
		hashMap.put("email", parentDTO.getEmail());    //  �θ� �̸���
		JSONObject parentObject = new JSONObject(hashMap);    //  JSON�����ͷ� �����
		
		return parentObject.toJSONString();    //  JSON������ String���� ��ȯ�Ͽ� ��ȯ
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
			System.err.println("ParentDAO getTime SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ParentDAO getTime close SQLException error");
			}
		}
		return "";    //  DB ����
	}
	
	private int checkParentGPS(String id) {    //  �θ��� ��ġ ������ ����Ǿ� �ִ��� Ȯ��
		String sql = "select * from PARENT_GPS where ID = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);    //  �θ� ���̵�
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return 1;    //  ����� ��ġ ������ ���� ���
			}
			return 0;    //  �����  ��ġ ������ ���� ���
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO checkParentGPS SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PatentDAO checkParentGPS close SQLException error");
			}
		}
		return -1;    //  DB ����
	}
	
	public int setParentGPS(String id, double lati, double longi) {    //  �θ� ���� ��ġ ����(���� x, ���� o)
		int check = checkParentGPS(id);
		String sql = "";
		if(check == 1) {    //  �θ� ��ġ ���� ������ ���� ���
			sql = "update PARENT_GPS set Time = ?, Latitude = ?, Longitude = ? where ID = ?";
		} else if(check == 0){    //  �θ� ��ġ ���� ������ ���� ���
			sql = "insert into PARENT_GPS values(?, ?, ?, ?)";
		} else {
			return 0;    //  �θ� ��ġ ���� ���� ���� Ȯ�� ����
		}
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if(check == 1) {    //  �θ� ��ġ ���� ������ ���� ���
				pstmt.setString(1, getTime());    //  ���� �ð�
				pstmt.setDouble(2, lati);    //  ����
				pstmt.setDouble(3, longi);    //  �浵
				pstmt.setString(4, id);    //  �θ� ���̵�
			} else {    //  �θ� ��ġ ���� ������ ���� ���
				pstmt.setString(1, id);    //  �θ� ���̵�
				pstmt.setString(1, getTime());    //  ���� �ð�
				pstmt.setDouble(1, lati);    //  ����
				pstmt.setDouble(4, longi);    //  �浵
			}
			return pstmt.executeUpdate();
		}  catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO setParentGPS SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("PatentDAO setParentGPS close SQLException error");
			}
		}
		return -1;    //  DB ����
	}
	
	private int checkChildKey(String childKey, String password) {    //  ���� �ĺ��� ���� Ȯ��
		String sql = "select ChildKey from PRIVATE_KEY where ChildKey = ? and Password = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);    //  ���� �ĺ���
			pstmt.setString(2, password);    //  ���� �ĺ��� Ȯ�� ��й�ȣ
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return 1;    //  ���� ����Ű ���� ����, ��й�ȣ�� ��ġ
			}
			return 0;    //  ���� ����Ű ���� ���� �Ǵ� ��й�ȣ ����ġ
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO checkChildKey SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PatentDAO checkChildKey close SQLException error");
			}
		}
		return -1;    //  DB ����
	}
	
	public int addChild(String childKey, String password, String name, String birth, String img) {    //  ���� �߰�
		if(checkChildKey(childKey, password) == 1) {
			String sql = "insert into CHILD_INFO values(?, ?, ?, ?)";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, childKey);    //  ���� �ĺ���
				pstmt.setString(2, name);    //  ���� �̸�
				pstmt.setString(3, birth);    //  ���� ����
				pstmt.setString(4, img);    //  ���� �������� ���
				return pstmt.executeUpdate();
			} catch (SQLException e) {    //  ����ó��
				System.err.println("ParentDAO addChild SQLExceptoin error");
			} finally {    //  �ڿ�����
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
				} catch(SQLException e) {
					System.err.println("PatentDAO addChild close SQLException error");
				}
			}
			return -1;    //  DB ����
		} else if(checkChildKey(childKey, password) == 0){
			return 0;    //  ���� �ĺ��� ����
		} else {
			return -1;    //  ���� �ĺ��� check DB ����
		}
	}
	
	public String getChildList(String parentID) {    //  �ش� �θ��� ���̵� ����Ʈ ȹ��
		ArrayList<ChildDTO> list = new ArrayList<ChildDTO>();    //  ���̵� ����Ʈ�� ������ ����Ʈ ����
		
		String sql = "select ChildKey, Name, Birth, Image from CHILD_INFO where ChildKey in (select ChildKey from PtoC where ID = ?)";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, parentID);    //  �θ� ���̵�
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ChildDTO childDTO = new ChildDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				list.add(childDTO);
			}
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO getChildList SQLExceptoin error");
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ParentDAO getChildList close SQLException error");
			}
		}
		
		ArrayList<JSONObject> childArray = new ArrayList<JSONObject>();    //  JSON�����͵��� ���� List
		for(int i = 0; i < list.size(); i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();    //  key�� value�� ����
			hashMap.put("key", list.get(i).getKey());    //  ���� �ĺ���
			hashMap.put("name", list.get(i).getName());    //  ���� �̸�
			hashMap.put("birth", list.get(i).getBirth());    //  ���� �������
			hashMap.put("image", list.get(i).getImg());    //  ���� ���� ���
			JSONObject childObject = new JSONObject(hashMap);    //  JSON�����ͷ� �����
			childArray.add(childObject);    //  JSON List�� �߰�
		}
		
		return childArray.toString();
	}
	
	private boolean checkChildInfo(String childKey) {    //  ���� ���� ���� Ȯ��
		ChildDAO childDAO = new ChildDAO();
		return childDAO.checkChild(childKey);
	}
	
	public ChildDTO getChildInfo(String childKey) {    //  ���� ���� ȹ��
		ChildDTO childDTO = null;
		if(checkChildInfo(childKey)) {
			String sql = "select Name, Birth, Image from CHILD_INFO where ChildKey = ?";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, childKey);    //  ���� �ĺ���
				rs = pstmt.executeQuery();
				if(rs.next()) {
					childDTO = new ChildDTO(childKey, rs.getString(1), rs.getString(2), rs.getString(3));
				}
			} catch (SQLException e) {    //  ����ó��
				System.err.println("ParentDAO getChildInfo SQLExceptoin error");
			} finally {    //  �ڿ�����
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
					if(rs != null) {rs.close();}
				} catch(SQLException e) {
					System.err.println("ParentDAO getChildInfo close SQLException error");
				}
			}
		}
		return childDTO;
	}
	
	public String getChildGPS(String childKey) {    //  ���� ���� ��ġ ȹ��
		JSONObject gpsObject = new JSONObject();
		if(checkChildInfo(childKey)) {
			String sql = "select Latitude, Longitude from CHILD_GPS where ChildKey = ? order by Time desc limit 1";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, childKey);    //  ���� �ĺ���
				rs = pstmt.executeQuery();
				if(rs.next()) {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();    //  key�� value�� ����
					hashMap.put("lati", rs.getDouble(1));    //  ����
					hashMap.put("longi", rs.getDouble(2));    //  �浵
					gpsObject = new JSONObject(hashMap);    //  JSON�����ͷ� �����
				}
			} catch (SQLException e) {    //  ����ó��
				System.err.println("ParentDAO getChildGPS SQLExceptoin error");
			} finally {    //  �ڿ�����
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
					if(rs != null) {rs.close();}
				} catch(SQLException e) {
					System.err.println("ParentDAO getChildGPS close SQLException error");
				}
			}	
		}
		return gpsObject.toJSONString();    //  JSON������ String���� ��ȯ�Ͽ� ��ȯ
	}
	
	public int modifyChildInfo(String childKey, String childPassword, ChildDTO childDTO) {    //  ���� ���� ����
		if(checkChildKey(childKey, childPassword) == 1) {
			String sql = "update CHILD_INFO set ChildKey = ?, Name = ?, Birth = ?, Image = ? where ChildKey = ?";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, childDTO.getKey());    //  ���� �ĺ���
				pstmt.setString(2, childDTO.getName());    //  ���� �̸�
				pstmt.setString(3, childDTO.getBirth());    //  ���� �������
				pstmt.setString(4, childDTO.getImg());    //  ���� ���� ���� ���
				pstmt.setString(5, childKey);    //  ���� ���� �ĺ���(���� �ڵ����̳� ��Ⱑ ����Ǿ��� ���)
				return pstmt.executeUpdate();
			} catch (SQLException e) {    //  ����ó��
				System.err.println("ParentDAO modifyChildInfo SQLExceptoin error");
			} finally {    //  �ڿ�����
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
				} catch(SQLException e) {
					System.err.println("PatentDAO modifyChildInfo close SQLException error");
				}
			}
			return -1;    //  DB ����
		} else {
			return 0;    //  ���� �ĺ��� ����
		}
	}
	
	private boolean deleteInitial(String childKey) {    //  �ʱ� ���� ��ġ ��Ͻ� ������ ����� Initial ����
		int result = 0;
		String sql = "delete from CHILD_GPS_INITIAL where ChildKey = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, childKey);    //  ���� �ĺ���
			result = pstmt.executeUpdate();
		} catch (SQLException e) {    //  ����ó��
			System.err.println("ParentDAO deleteInitial SQLException error");
			result = -1;    //  DB ����
		} finally {    //  �ڿ�����
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("ParentDAO deleteInitial SQLException error");
			}
		}
		if(result == 1) {
			return true;    //  delete ������
		} else {
			return false;    //  delete ���н�
		}
	}
	
	public int setChildIntial(String childKey, String gpsData) {    //  ���� �ʱ� ���� ��ġ ����
		int result = 0;
		if(checkChildInfo(childKey)) {
			if(deleteInitial(childKey)) {
				JSONParser jsonParser = new JSONParser();
				try {
					JSONArray gpsArray = (JSONArray) jsonParser.parse(gpsData);
					for(int i = 0; i < gpsArray.size(); i++) {
						JSONObject gpsJSON = (JSONObject) gpsArray.get(i);
						double lati = Double.parseDouble(gpsJSON.get("lati").toString());
						double longi = Double.parseDouble(gpsJSON.get("longi").toString());
						String sql = "insert into CHILD_GPS_INITIAL values(?, ?, ?)";
						conn = dbConnector.getConnection();
						PreparedStatement pstmt = null;
						try {
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, childKey);    //  ���� �ĺ���
							pstmt.setDouble(2, lati);    //  ����
							pstmt.setDouble(3, longi);    //  �浵
							result = pstmt.executeUpdate();
						} catch (SQLException e) {    //  ����ó��
							System.err.println("ParentDAO setChildInitial SQLException error");
							result = -1;    //  DB ����
						} finally {    //  �ڿ�����
							try {
								if(conn != null) {conn.close();}
								if(pstmt != null) {pstmt.close();}
							} catch(SQLException e) {
								System.err.println("ParentDAO setChildInitial SQLException error");
							}
						}
						if(result == -1) {
							break;
						}
					}
				} catch (ParseException e) {    //  JSON ����ó��
					System.err.println("ParentDAO setChildInitial ParseException error");
					result = -2;    //  JSON ����
				}
			} else {
				result = -3;    //  �ʱ� ��ġ ���� ����
			}
			
		}
		/*
		 * result = 0: ����� ���� ���� ����
		 * result = 1: ��ġ���� ����
		 * result = -1: DB ����
		 * result = -2: JSON ����
		 * result = -3: ���� �ִ� ��ġ ���� ����
		 */
		return result;
	}
	
	public String getChildInitial(String childKey) {    //  ���� �ʱ� ���� ��ġ ���ð� ȹ��
		ArrayList<JSONObject> initialArray = new ArrayList<JSONObject>();
		if(checkChildInfo(childKey)) {
			String sql = "select Latitude, Longitude from CHILD_GPS_INITIAL where ChildKey = ?";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, childKey);    //  ���� �ĺ���
				rs = pstmt.executeQuery();
				while(rs.next()) {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("lati", rs.getDouble(1));    //  ����
					hashMap.put("longi", rs.getDouble(2));    //  �浵
					JSONObject initialObject = new JSONObject(hashMap);
					initialArray.add(initialObject);    //  JSON List�� �߰�
				}
			} catch (SQLException e) {    //  ����ó��
				System.err.println("ParentDAO getChildInitial SQLExceptoin error");
			} finally {    //  �ڿ�����
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
					if(rs != null) {rs.close();}
				} catch(SQLException e) {
					System.err.println("ParentDAO getChildInitial close SQLException error");
				}
			}
		}
		return initialArray.toString();
	}
	
	public int childMissing(String childKey, boolean type) {    //  ���� �������� ���
		if(checkChildInfo(childKey)) {
			String sql = "update CHILD_INFO set Missing = ? where ChildKey = ?";
			conn = dbConnector.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setBoolean(1, type);    //  ���� ���� ����
				pstmt.setString(2, childKey);    //  ���� �ĺ���
				return pstmt.executeUpdate();
			} catch (SQLException e) {    //  ����ó��
				System.err.println("ParentDAO childMissing SQLExceptoin error");
			} finally {    //  �ڿ�����
				try {
					if(conn != null) {conn.close();}
					if(pstmt != null) {pstmt.close();}
				} catch(SQLException e) {
					System.err.println("ParentDAO childMissing close SQLException error");
				}
			}
			return -1;    //  DB ����
		}
		return 0;    //  ���� ������ ����Ǿ� ���� ����
	}
}
