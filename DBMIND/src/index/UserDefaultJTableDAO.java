package index;

//UserDefaultJTableDAO.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class UserDefaultJTableDAO {
	
	/**
	 * �ʿ��� ��������
	 * */
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	/**
	 * �ε� ������ ���� ������
	 * */
	public UserDefaultJTableDAO() {
		try {
			// �ε�
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ����
			con = DriverManager
					.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
							"DBMIND", "1234");

		} catch (ClassNotFoundException e) {
			System.out.println(e + "=> �ε� fail");
		} catch (SQLException e) {
			System.out.println(e + "=> ���� fail");
		}
	}//������ 

	/**
	 * DB�ݱ� ��� �޼ҵ�
	 * */
	public void dbClose() {
		try {
			if (rs != null) rs.close();
			if (st != null)	st.close();
			if (ps != null)	ps.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}//dbClose() ---

	/**
	 * �μ��� ���� ID�� �ش��ϴ� ���ڵ� �˻��Ͽ� �ߺ����� üũ�ϱ� ���ϰ��� true =��밡�� , false = �ߺ���
	 * */
	public boolean getIdByCheck(String name) {
		boolean result = true;

		try {
			ps = con.prepareStatement("SELECT * FROM GOODs WHERE name=?");
			ps.setString(1, name.trim());
			rs = ps.executeQuery(); //����
			if (rs.next())
				result = false; //���ڵ尡 �����ϸ� false

		} catch (SQLException e) {
			System.out.println(e + "=>  getIdByCheck fail");
		} finally {
			dbClose();
		}

		return result;

	}//getIdByCheck()

	/**
	 * userlist ȸ�������ϴ� ��� �޼ҵ�
	 * */
	public int userListInsert(UserJDailogGUI user) {
		int result = 0;
		try {
			ps = con.prepareStatement("insert into GOODs values(?,?,?,?)");
			ps.setString(1, user.name.getText());
			ps.setInt(2, Integer.parseInt(user.remain.getText()));
			ps.setString(3, user.sales.getText());
			ps.setString(4, user.etc.getText());

			result = ps.executeUpdate(); //���� -> ����

		} catch (SQLException e) {
			System.out.println(e + "=> userListInsert fail");
		} finally {
			dbClose();
		}

		return result;

	}//userListInsert()

	/**
	 * userlist�� ��� ���ڵ� ��ȸ
	 * */
	public void userSelectAll(DefaultTableModel t_model) {
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from GOODs order by remain");

			// DefaultTableModel�� �ִ� ���� ������ �����
			for (int i = 0; i < t_model.getRowCount();) {
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getInt(2),
						rs.getString(3), rs.getString(4) };

				t_model.addRow(data); //DefaultTableModel�� ���ڵ� �߰�
			}

		} catch (SQLException e) {
			System.out.println(e + "=> userSelectAll fail");
		} finally {
			dbClose();
		}
	}//userSelectAll()

	/**
	 * ID�� �ش��ϴ� ���ڵ� �����ϱ�
	 * */
	public int userDelete(String name) {
		int result = 0;
		try {
			ps = con.prepareStatement("delete GOODs where name = ? ");
			ps.setString(1, name.trim());
			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=> userDelete fail");
		}finally {
			dbClose();
		}

		return result;
	}//userDelete()

	/**
	 * ID�� �ش��ϴ� ���ڵ� �����ϱ�
	 * */
	public int userUpdate(UserJDailogGUI user) {
		int result = 0;
		String sql = "UPDATE GOODs SET remain=?, sales=? , etc=? WHERE name=?";

		try {
			ps = con.prepareStatement(sql);
			// ?�� ������� �� �ֱ�
			ps.setString(1, user.remain.getText());
			ps.setString(2, user.sales.getText());
			ps.setString(3, user.etc.getText());
			ps.setString(4, user.name.getText().trim());

			// �����ϱ�
			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=> userUpdate fail");
		} finally {
			dbClose();
		}

		return result;
	}//userUpdate()

	/**
	 * �˻��ܾ �ش��ϴ� ���ڵ� �˻��ϱ� (like�����ڸ� ����Ͽ� _, %�� ����Ҷ��� PreparedStatemnet�ȵȴ�. �ݵ��
	 * Statement��ü�� �̿���)
	 * */
	public void getUserSearch(DefaultTableModel dt, String fieldName,
			String word) {
		String sql = "SELECT * FROM GOODs WHERE " + fieldName.trim()
				+ " LIKE '%" + word.trim() + "%'";

		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);

			// DefaultTableModel�� �ִ� ���� ������ �����
			for (int i = 0; i < dt.getRowCount();) {
				dt.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getInt(2),
						rs.getString(3), rs.getString(4) };

				dt.addRow(data);
			}

		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbClose();
		}

	}//getUserSearch()

}// Ŭ������

