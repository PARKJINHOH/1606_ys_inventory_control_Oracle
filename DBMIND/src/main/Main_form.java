package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import buy.Buyform;
import index.MenuJTabaleExam;
import member_package.Member_List;

public class Main_form extends JFrame {
   private JPanel toppanel;
   public Main_form() {

      setSize(500, 200);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("메인");
      setLayout(new FlowLayout());
      JPanel Panel = new JPanel();
      
      toppanel = new JPanel();
      JLabel label = new JLabel("순siri 관리 메인폼에 오신것을 환영합니다." );
      toppanel.add(label);
      add(toppanel, BorderLayout.NORTH);

      JButton button1 = new JButton("회원관리");
      Panel.add(button1);
      button1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new Member_List();
         }
      });

      JButton button2 = new JButton("재고관리");
      Panel.add(button2);
      button2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new MenuJTabaleExam();
         }
      });
      
      JButton button3 = new JButton("물품구매");
      Panel.add(button3);
      button3.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new Buyform();
         }
      });

      this.add(Panel);
      this.setVisible(true);

   }

}