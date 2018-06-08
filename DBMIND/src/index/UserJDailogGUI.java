package index;

//UserJDailogGUI.java
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserJDailogGUI extends JDialog implements ActionListener{
	
	JPanel pw=new JPanel(new GridLayout(4,1));
	JPanel pc=new JPanel(new GridLayout(4,1));
	JPanel ps=new JPanel();

	JLabel lable_Name = new JLabel("이름");
	JLabel lable_Remain=new JLabel("재고량");
	JLabel lable_Sales=new JLabel("가격");
	JLabel lable_Etc=new JLabel("비고");


	JTextField name=new JTextField();
	JTextField remain=new JTextField();
	JTextField sales=new JTextField();
	JTextField etc=new JTextField();
	

	JButton confirm;
	JButton reset=new JButton("취소");

 MenuJTabaleExam me;

 JPanel idCkP =new JPanel(new BorderLayout());
 JButton idCkBtn = new JButton("IDCheck");
 
 UserDefaultJTableDAO dao =new UserDefaultJTableDAO();
 

	public UserJDailogGUI(MenuJTabaleExam me, String index){
		super(me,"다이어로그");
		this.me=me;
		if(index.equals("등록")){
			confirm=new JButton(index);
		}else{
			confirm=new JButton("재고수정");	
			
			//text박스에 선택된 레코드의 정보 넣기
			int row = me.jt.getSelectedRow();//선택된 행
			name.setText( me.jt.getValueAt(row, 0).toString() );
			remain.setText( me.jt.getValueAt(row, 1).toString() );
			sales.setText( me.jt.getValueAt(row, 2).toString() );
			etc.setText( me.jt.getValueAt(row, 3).toString() );
			
			//id text박스 비활성
			name.setEditable(false);
	
			//IDCheck버튼 비활성화
			idCkBtn.setEnabled(false);
		}
		
		
		//Label추가부분
		pw.add(lable_Name);//품명
		pw.add(lable_Remain);//재고
		pw.add(lable_Sales);//가격
		pw.add(lable_Etc);//비고
	
		
		idCkP.add(name,"Center");
		idCkP.add(idCkBtn,"East");
		
		//TextField 추가
		pc.add(idCkP);
		pc.add(remain);
		pc.add(sales);
		pc.add(etc);
		
		
		
		ps.add(confirm); 
		ps.add(reset);
	
		add(pw,"West"); 
		add(pc,"Center");
		add(ps,"South");
		
		setSize(300,250);
		setVisible(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//이벤트등록
      confirm.addActionListener(this); //가입/수정 이벤트등록
      reset.addActionListener(this); //취소 이벤트등록
      idCkBtn.addActionListener(this);// ID중복체크 이벤트 등록
		
	}//생성자끝
  
	/**
	 * 가입/수정/삭제 기능에 대한 부분
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
	   String btnLabel =e.getActionCommand();//이벤트주체 대한 Label 가져오기
	    
	   if(btnLabel.equals("등록")){
		   if(dao.userListInsert(this) > 0 ){//상품등록
			   messageBox(this , name.getText()+"상품등록 되었습니다.");
			   dispose();//JDialog 창닫기
			   
			   dao.userSelectAll(me.dt);//모든레코드가져와서 DefaultTableModel에 올리기
			   
			   if(me.dt.getRowCount() > 0)
				   me.jt.setRowSelectionInterval(0, 0);//첫번째 행 선택
			   
		   }else{//가입실패
			   messageBox(this,"상품등록 되지 않았습니다.");
		   }
		   
	   }else if(btnLabel.equals("재고수정")){
		   
		    if( dao.userUpdate(this) > 0){
		    	messageBox(this, "수정완료되었습니다.");
		    	dispose();
		    	dao.userSelectAll(me.dt);
		    	if(me.dt.getRowCount() > 0 ) me.jt.setRowSelectionInterval(0, 0);
		    	
		    }else{
		    	messageBox(this, "수정되지 않았습니다.");
		    }
		   
		   
		   
	   }else if(btnLabel.equals("취소")){
		   dispose();
		   
	   }else if(btnLabel.equals("IDCheck")){
		   //id텍스트박스에 값 없으면 메세지 출력 있으면 DB연동한다.
		   if(name.getText().trim().equals("")){
			   messageBox(this,"상품명을 입력해주세요.");
			   name.requestFocus();//포커스이동
		   }else{
			   
			  if(  dao.getIdByCheck(name.getText()) ){ //중복아니다.(사용가능) 
				  messageBox(this, name.getText()+"는 등록가능합니다.");  
			  }else{ //중복이다.
				  messageBox(this,name.getText()+"는 중복입니다.");
				  
				  name.setText("");//text박스지우기
				  name.requestFocus();//커서놓기
			  }
			   
		   }
		   
	   }
	   
		
	}//actionPerformed끝
	
	/**
	 * 메시지박스 띄워주는 메소드 작성
	 * */
	public static void messageBox(Object obj , String message){
		JOptionPane.showMessageDialog( (Component)obj , message);
	}

}//클래스끝