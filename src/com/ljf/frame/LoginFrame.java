package com.ljf.frame;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.Label;
import java.awt.Button;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import com.ljf.factory.AccountDAOFactory;
import com.ljf.factory.PersonDAOFactory;
import com.ljf.util.Authorization;
import com.ljf.vo.Account;

import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("unused")
public class LoginFrame{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initialize();
	}
	
	public JFrame getFrame(){
		return this.frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setType(Type.POPUP);
		frame.setTitle("\u5B66\u751F\u8003\u52E4\u7CFB\u7EDF");
		frame.setBounds(100, 100, 409, 210);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		//屏幕中央显示
		frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width)/2, 
				(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height)/2);
		
		
		Label labelAcccount = new Label("\u5E10\u53F7\uFF1A");
		labelAcccount.setBounds(67, 27, 42, 23);
		frame.getContentPane().add(labelAcccount);
		
		Label labelPassword = new Label("\u5BC6\u7801\uFF1A");
		labelPassword.setBounds(67, 75, 42, 23);
		frame.getContentPane().add(labelPassword);

		/*private JTextField textFieldAccount;
		private JPasswordField passwordFieldLogin;*/
		JTextField textFieldAccount = new JTextField();
		textFieldAccount.setText("40000000");
		textFieldAccount.setBounds(141, 27, 138, 21);
		frame.getContentPane().add(textFieldAccount);
		textFieldAccount.setColumns(10);
		
		JPasswordField passwordFieldLogin = new JPasswordField();
		passwordFieldLogin.setBounds(141, 77, 138, 21);
		frame.getContentPane().add(passwordFieldLogin);
		passwordFieldLogin.setText("666666");
		
		Button buttonLogin = new Button("\u767B\u5F55");
		buttonLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//点击登录按钮
				//验证
				int flag = -1;
				int id = 0;
				String password = null;
				Account ac = null;
				try {
					id = Integer.parseInt(textFieldAccount.getText());
					password = new String(passwordFieldLogin.getPassword());
					ac = new Account(id, password);
					flag = AccountDAOFactory.getAccountProxy().doLogin(ac);
				} catch (Exception e1) {}
				//关闭，打开主界面
				if(flag>0){
					if(flag ==1){
						Authorization.setAuthorization(true, false, false, false);
					}
					if(flag ==2){
						Authorization.setAuthorization(false, true, false, false);
					}
					if(flag ==4){
						try {
							boolean isSup = PersonDAOFactory.getAdminProxy().isSupremeAdmin(id+"");
							if(isSup){
								Authorization.setAuthorization(false, false, true, false);//最高权限的管理员
							}else{
								Authorization.setAuthorization(false, false, false, true);//一般权限的管理员
							}
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					frame.dispose();
					MainFrame mf = new MainFrame(id);
				}else{
					JOptionPane.showMessageDialog(null, "账号密码错误");
				}
				
			}
		});
		buttonLogin.setBounds(201, 127, 76, 23);
		frame.getContentPane().add(buttonLogin);
		
	}
}
