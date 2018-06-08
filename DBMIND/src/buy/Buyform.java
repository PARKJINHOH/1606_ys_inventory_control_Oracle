package buy;

//import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
//import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import index.UserJDailogGUI;


public class Buyform extends JFrame implements ActionListener {
	
	Buyform bf;
	JPanel p = new JPanel();
	String[] name = { "ǰ���̸�", "��������", "����", "���" };

	DefaultTableModel dt = new DefaultTableModel(name, 0);
	JTable jt = new JTable(dt);
	JScrollPane jsp = new JScrollPane(jt);
	JButton btnbuy = new JButton("����");

	BuyDAO dao = new BuyDAO();
	
	
	public Buyform() {
		
		super("��ǰ�Ǹ���");

		p.setBackground(null);
		p.add(jsp);
		p.add(btnbuy);

		add(p, "Center");

		setSize(500, 510);
		setVisible(true);

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// �̺�Ʈ���
		btnbuy.addActionListener(this);

		// ��緹�ڵ带 �˻��Ͽ� DefaultTableModle�� �ø���
		dao.userSelectAll(dt);
		
		//ù���� ����.
		if (dt.getRowCount() > 0)
			jt.setRowSelectionInterval(0, 0);
		
	}
	
	
	
	public static void main(String[] args) {
		new Buyform();	
	}

	/**
	 * ���� ��� �޼ҵ� (��ư�� ������ �������� 1���� �پ��)
	 * */

	public void actionPerformed(ActionEvent e) {
		String btnbuy =e.getActionCommand();//�̺�Ʈ��ü ���� Label ��������
		
		if(btnbuy.equals("����")){
			int row = jt.getSelectedRow();
			System.out.println("������ : " + row);

			Object obj = jt.getValueAt(row, 0);// �� ���� �ش��ϴ� value
			System.out.println("�� : " + obj);

			if (dao.buyBuy(obj.toString()) > 0) {
				UserJDailogGUI.messageBox(this, "��ǰ�� �����Ͽ����ϴ�");
				
				//����Ʈ ����
				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);

			} else {
				UserJDailogGUI.messageBox(this, "��ǰ ���� �����߽��ϴ�.");
			}
		
	}

}


}
