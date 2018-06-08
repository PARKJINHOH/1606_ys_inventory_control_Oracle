package buy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class BuyDAO {
	
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;
	Buyform bf;
	
	public BuyDAO() {
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
	}
	
	
	public void dbClose() {
		try {
			if (rs != null) rs.close();
			if (st != null)	st.close();
			if (ps != null)	ps.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}
	
	
	
	
	public int buyBuy(String name) {
		int result = 0;
		try {
			ps = con.prepareStatement("update GOODs set remain=remain-1 where name = ? ");
			ps.setString(1, name.trim());
			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=> buyupdate fail");
		}finally {
			dbClose();
		}

		return result;
	}
	
	
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
	}

}