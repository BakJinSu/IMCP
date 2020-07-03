package Child;

public class ChildDTO {
	private String key;    //  ���� ����Ű
	private String name;    //  ���� �̸�
	private String birth;    //  ���� �������
	private String img;    //  �̹��� ���� ���
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	public ChildDTO(String key, String name, String birth, String img) {
		super();
		this.key = key;
		this.name = name;
		this.birth = birth;
		this.img = img;
	}	
}
