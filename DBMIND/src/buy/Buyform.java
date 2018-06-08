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
	String[] name = { "품목이름", "남은수량", "가격", "비고" };

	DefaultTableModel dt = new DefaultTableModel(name, 0);
	JTable jt = new JTable(dt);
	JScrollPane jsp = new JScrollPane(jt);
	JButton btnbuy = new JButton("구매");

	BuyDAO dao = new BuyDAO();
	
	
	public Buyform() {
		
		super("상품판매폼");

		p.setBackground(null);
		p.add(jsp);
		p.add(btnbuy);

		add(p, "Center");

		setSize(500, 510);
		setVisible(true);

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 이벤트등록
		btnbuy.addActionListener(this);

		// 모든레코드를 검색하여 DefaultTableModle에 올리기
		dao.userSelectAll(dt);
		
		//첫번행 선택.
		if (dt.getRowCount() > 0)
			jt.setRowSelectionInterval(0, 0);
		
	}
	
	
	
	public static void main(String[] args) {
		new Buyform();	
	}

	/**
	 * 구매 담당 메소드 (버튼을 누르면 재고수량이 1개씩 줄어듬)
	 * */

	public void actionPerformed(ActionEvent e) {
		String btnbuy =e.getActionCommand();//이벤트주체 대한 Label 가져오기
		
		if(btnbuy.equals("구매")){
			int row = jt.getSelectedRow();
			System.out.println("선택행 : " + row);

			Object obj = jt.getValueAt(row, 0);// 행 열에 해당하는 value
			System.out.println("값 : " + obj);

			if (dao.buyBuy(obj.toString()) > 0) {
				UserJDailogGUI.messageBox(this, "상품을 구매하였습니다");
				
				//리스트 갱신
				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);

			} else {
				UserJDailogGUI.messageBox(this, "상품 구매 실패했습니다.");
			}
		
	}

}


}
