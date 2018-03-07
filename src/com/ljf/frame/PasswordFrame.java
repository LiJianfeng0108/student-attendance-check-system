package com.ljf.frame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.ljf.factory.AccountDAOFactory;
import com.ljf.vo.Account;

public class PasswordFrame {

	private JFrame frame;
	private JPasswordField passwordFieldOld;
	private JPasswordField passwordFieldNew;
	private JPasswordField passwordFieldNew2;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordFrame window = new PasswordFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public PasswordFrame(String id) {
		
		initialize(id);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String id) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u539F\u5BC6\u7801\uFF1A");
		label.setBounds(62, 36, 54, 15);
		frame.getContentPane().add(label);
		
		passwordFieldOld = new JPasswordField();
		passwordFieldOld.setBounds(193, 33, 121, 21);
		frame.getContentPane().add(passwordFieldOld);
		
		JLabel label_1 = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		label_1.setBounds(62, 82, 54, 15);
		frame.getContentPane().add(label_1);
		
		passwordFieldNew = new JPasswordField();
		passwordFieldNew.setBounds(193, 79, 121, 21);
		frame.getContentPane().add(passwordFieldNew);
		
		JLabel label_2 = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		label_2.setBounds(62, 130, 54, 15);
		frame.getContentPane().add(label_2);
		
		passwordFieldNew2 = new JPasswordField();
		passwordFieldNew2.setBounds(193, 127, 121, 21);
		frame.getContentPane().add(passwordFieldNew2);
		
		String thisid = id;
		JButton btnNewButton = new JButton("\u66F4\u6539");
		btnNewButton.addMouseListener(new MouseAdapter() {//修改密码
			@Override
			public void mouseClicked(MouseEvent e) {
				int flag = -1;
				try {
					Account ac = new Account(Integer.parseInt(thisid), new String(passwordFieldOld.getPassword()));
					flag = AccountDAOFactory.getAccountProxy().doLogin(ac);
				} catch (Exception e2) {
				}
				if(flag>0){//原密码输入正确
					if(new String(passwordFieldNew.getPassword()).matches("\\w{6,16}")){//密码长度6到16
						if(new String(passwordFieldNew.getPassword()).equals(new String(passwordFieldNew2.getPassword()))){//两次密码输入一样
							try {
								Account ac = new Account(Integer.parseInt(thisid), new String(passwordFieldNew2.getPassword()));
								AccountDAOFactory.getAccountProxy().doUpdate(ac);
								JOptionPane.showMessageDialog(null, "修改成功");
								frame.setVisible(false);
							} catch (Exception e1) {
							}
						}else{
							JOptionPane.showMessageDialog(null, "两次输入的密码不一致");
						}
					}else{
						JOptionPane.showMessageDialog(null, "密码长度6到16");
					}
				}else{
					JOptionPane.showMessageDialog(null, "原密码输入错误");
				}
				
				
			}
		});
		btnNewButton.setBounds(191, 187, 93, 23);
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
