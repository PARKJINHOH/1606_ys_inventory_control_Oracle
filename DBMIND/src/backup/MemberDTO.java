package backup;

public class MemberDTO {

	private String id;
	private String pwd;
	private String name;
	private String tel;
	private String gender;
	private String email;

	// ��Ŭ������ : Getter/Setter �����
	// ��Ŭ�� -> source->Generate Getters And Setters-> [Select AlL] -> [OK]

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// DTO ��ü Ȯ��
	// ��Ŭ������ : toString() �ڵ�����: ��Ŭ�� -> source -> Generate toString->[OK]
	@Override
	public String toString() {
		return "MemberDTO [id=" + id + ", pwd=" + pwd + ", name=" + name + ", tel=" + tel + ", gender=" + gender
				+ ", email=" + email + "]";
	}
}
