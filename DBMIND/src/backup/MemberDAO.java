package backup;
//�̸� ��Ģ : ���̺��DAO , ���̺��DTO
//CRUD : Create;insert , Read;Select, Update, delete

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

//DB ó��
public class MemberDAO {

	Member_List mList;
	
	public MemberDAO() {
	
	}
	
	public MemberDAO(Member_List mList){
		this.mList = mList;
		System.out.println("DAO=>"+mList);
	}
	
	/**DB���� �޼ҵ�*/
	public Connection getConn(){
		Connection conn = null;
        try {
            String user = "DBMIND"; 
            String pw = "1234";
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            
            Class.forName("oracle.jdbc.driver.OracleDriver");        
            conn = DriverManager.getConnection(url, user, pw);
            
            System.out.println("Database�� ����Ǿ����ϴ�.\n");
            
        } catch (ClassNotFoundException cnfe) {
            System.out.println("DB ����̹� �ε� ���� :"+cnfe.toString());
        } catch (SQLException sqle) {
            System.out.println("DB ���ӽ��� : "+sqle.toString());
        } catch (Exception e) {
            System.out.println("Unkonwn error");
            e.printStackTrace();
        }
        return conn;     
	}
	
	
	/**�ѻ���� ȸ�� ������ ��� �޼ҵ�*/
	public MemberDTO getMemberDTO(String id){
		
		MemberDTO dto = new MemberDTO();
		
		Connection con = null; 		 //����
		PreparedStatement ps = null; //���
		ResultSet rs = null;		 //���
		
		try {
			
			con = getConn();
			String sql = "select * from MEMBER where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("Pwd"));
				dto.setName(rs.getString("Name"));
				dto.setTel(rs.getString("tel"));
				dto.setGender(rs.getString("gender"));
				dto.setEmail(rs.getString("email"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return dto;		
	}
	
	/**�������Ʈ ���*/
	public Vector getMEMBERList(){
		
		Vector data = new Vector();  //Jtable�� ���� ���� �ִ� ��� 1. 2�����迭   2. Vector �� vector�߰�
		
			
		Connection con = null; 		 //����
		PreparedStatement ps = null; //���
		ResultSet rs = null;		 //���
		
		try{
			
			con = getConn();
			String sql = "select * from MEMBER order by name asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String gender = rs.getString("gender");
				String email = rs.getString("email");
				
				Vector row = new Vector();
				row.add(id);
				row.add(pwd);
				row.add(name);
				row.add(tel);
				row.add(gender);
				row.add(email);
				
				data.add(row);				
			}//while
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}//getMEMBERList()
	


	/**ȸ�� ���*/
	public boolean insertMEMBER(MemberDTO dto){
		
		boolean ok = false;
		
		Connection con = null; 		 //����
		PreparedStatement ps = null; //���
		
		try{
			
			con = getConn();
			String sql = "insert into MEMBER(" +
						"id,pwd,name,tel,gender,email) "+
						"values(?,?,?,?,?,?)";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPwd());
			ps.setString(3, dto.getName());
			ps.setString(4, dto.getTel());
			ps.setString(8, dto.getGender());
			ps.setString(9, dto.getEmail());		
			int r = ps.executeUpdate(); //���� -> ����
			
			
			if(r>0){
				System.out.println("���� ����");	
				ok=true;
			}else{
				System.out.println("���� ����");
			}
			
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ok;
	}//insertMmeber
	
	
	/**ȸ������ ����*/
	public boolean updateMEMBER(MemberDTO vMem){
		System.out.println("dto="+vMem.toString());
		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;
		try{
			
			con = getConn();			
			String sql = "update MEMBER set name=?, tel=?, gender=?" + ", email=?"+ "where id=? and pwd=?";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, vMem.getName());
			ps.setString(2, vMem.getTel());
			ps.setString(6, vMem.getGender());
			ps.setString(7, vMem.getEmail());
			ps.setString(9, vMem.getId());
			ps.setString(10, vMem.getPwd());
			
			int r = ps.executeUpdate(); //���� -> ����
			// 1~n: ���� , 0 : ����
			
			if(r>0) ok = true; //������ �����Ǹ� ok���� true�� ����
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ok;
	}
	
	/**ȸ������ ���� :
	 *tip: �ǹ������� ȸ�������� Delete ���� �ʰ� Ż�𿩺θ� üũ�Ѵ�.*/
	public boolean deleteMEMBER(String id, String pwd){
		
		boolean ok =false ;
		Connection con =null;
		PreparedStatement ps =null;
		
		try {
			con = getConn();
			String sql = "delete from MEMBER where id=? and pwd=?";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pwd);
			int r = ps.executeUpdate(); // ���� -> ����
			
			if (r>0) ok=true; //������;
			
		} catch (Exception e) {
			System.out.println(e + "-> �����߻�");
		}		
		return ok;
	}
	
	
	/**DB������ �ٽ� �ҷ�����*/	
	public void userSelectAll(DefaultTableModel model) {
		
		
    	Connection con = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	try {
            con = getConn();
            String sql = "select * from MEMBER order by name asc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            // DefaultTableModel�� �ִ� ������ �����
            for (int i = 0; i < model.getRowCount();) {
                model.removeRow(0);
            }
 
            while (rs.next()) {
                Object data[] = { 
                		rs.getString(1), 
                		rs.getString(2),
                        rs.getString(3), 
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10)};
 
                model.addRow(data);                
            }
 
        } catch (SQLException e) {
            System.out.println(e + "=> userSelectAll fail");
        } finally{
			
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
    }
}
