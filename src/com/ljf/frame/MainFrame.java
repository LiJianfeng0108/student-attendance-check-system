package com.ljf.frame;


import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.ljf.factory.AttendanceDAOFactory;
import com.ljf.factory.PersonDAOFactory;
import com.ljf.util.Authorization;
import com.ljf.util.DayOfWeek;
import com.ljf.util.Timer;
import com.ljf.util.Validation;
import com.ljf.util.XMLAnalysis;
import com.ljf.vo.Admin;
import com.ljf.vo.Advisor;
import com.ljf.vo.Person;
import com.ljf.vo.Student;
import com.ljf.vo.Teacher;

import java.awt.event.MouseMotionAdapter;

public class MainFrame {

	private JFrame frame;
	private JTextField textFieldIdT;
	private JTextField textFieldNameT;
	private JTextField textFieldPhoneT;
	private JTextField textFieldEmailT;
	private JTextField textFieldClassT;
	private JTextField textFieldIdA;
	private JTextField textFieldPhoneA;
	private JTextField textFieldNameA;
	private JTextField textFieldEmailA;
	private JTextField textFieldIdS;
	private JTextField textFieldPhoneS;
	private JTextField textFieldEmailS;
	private JTextField textFieldNameS;
	private JTextField textFieldClassS;
	private JTextField textFieldIdAd;
	private JTextField textFieldPhoneAd;
	private JTextField textFieldEmailAd;
	private JTextField textFieldNameAd;
	private JTextField textFieldIdAc;
	private JTextField textFieldNameAc;
	private JTextField textFieldIdAfl;
	private JTextField textFieldNameAfl;

	private JTabbedPane tabbedPaneInfo = null;
	private JPanel panelAttendance = null;
	private JPanel panelInfo = null;
	private JPanel panelTeacher = null;
	private JPanel panelAdvisor = null;
	private JPanel panelStudent = null;
	private JPanel panelAdmin = null;
	private JPanel panelAskForLeave = null;
	private JPanel panelAttendanceCheck = null;
	private JPanel panelAttendanceInfo = null;
	private JTabbedPane tabbedPaneAttendance = null;
	private JPanel panelAllInfoAd = null;
	private JLabel labelAllInfoAd = null;
	private JLabel lblNewLabelPicture = null;
	private ImageIcon icon = null; 
	private JLabel labelPictureAc = null;
	private JLabel labelTimerAc = null;
	
	private JMenuBar menuBar = null;
	private JMenu menuFirst = null;
	private JMenuItem menuItemTeacher = null;
	private JMenuItem menuItemAdvisor = null;
	private JMenuItem menuItemStudent = null;
	private JMenuItem menuItemAdmin = null;
	private JMenu menuSecond = null;
	private JMenuItem menuItemAttendanceCheck = null;
	private JMenuItem menuItemAskForLeave = null;
	private JMenu menuThird = null;
	private JMenuItem munuItemAttendanceInfo = null;
	private JMenu menuFourth = null;
	private JMenuItem menuItemAbout = null;
	private JMenuItem menuItemClose = null;
	
	private JComboBox comboBoxDepartpT = null;
	private JComboBox comboBoxDepartcT = null;
	private JComboBox comboBoxCourseT = null;
	private JComboBox comboBoxDepartpA = null;
	private JComboBox comboBoxDepartpS = null;
	private JComboBox comboBoxDepartcS = null;
	
	private JRadioButton radioButtonSupreme = null;
	private JRadioButton radioButtonNormal = null;
	private JRadioButton radioButtonMaleAd = null;
	private JRadioButton radioButtonFemaleAd = null;
	private JRadioButton radioButtonMaleT = null;
	private JRadioButton radioButtonFemaleT = null;
	private JRadioButton radioButtonFemaleA = null;
	private JRadioButton radioButtonMaleA = null;
	private JRadioButton radioButtonMaleS = null;
	private JRadioButton radioButtonFemaleS = null;
	private JRadioButton radioButtonLateAc = null;
	private JRadioButton radioButtonAbsentAc = null;
	
	private JButton buttonRetrieveAd  = null;
	private JButton buttonInsertAd = null;
	private JButton buttonDeleteAd  = null;
	private JButton buttonNextAc = null;
	private JButton buttonStartAc = null;
	
	private JTable tableT = null;
	private DefaultTableModel dtmT = null;
	private JTable tableA = null;
	private DefaultTableModel dtmA = null;
	private JTable tableS = null;
	private DefaultTableModel dtmS = null;
	private JTable tableAd = null;
	private DefaultTableModel dtmAd = null;
	private JTable tableAttendance = null;
	private DefaultTableModel dtmAttendance = null;
	
	private boolean isSupremeAdm ;
	private boolean isNormalAdm ;
	private boolean isTeac ;
	private boolean isAdv ;
	private JTextField textFieldCourseT;
	
	private boolean isNewPic = false;
	private String teacCourse = null;
	private String advDepartp = null;
	private int teacClass = 0;
	private String path = null;//学生照片路径
	
	private Map picMap = null;
	private ByteArrayOutputStream baos = null;
	private JTextField textFieldDateAfl;
	
	private List stuAcList = null;
	private ListIterator lit = null;//可以向前或向后
	
	/**
	 * Create the application.
	 */
	public MainFrame(int id) {//非管理员负责考勤功能，管理员负责所有基本信息的CRUD等；由于只有管理员登陆时会加载自己的信息，故传入管理员id
		//System.out.print(id);
		isSupremeAdm = Authorization.getSupremAdmAuthorization();
		isNormalAdm = Authorization.getNormalAdmAuthorization();
		isTeac = Authorization.getTeacAuthorization();
		isAdv = Authorization.getAdvAuthorization();
		loadConcernedCourseOrDepartp(id);//考勤管理，老师需要对应科目，辅导员需要对应院名
		initialize();
		setAuth();//不同用户登录不同操作权限
		if(isSupremeAdm||isNormalAdm){//管理员登陆后初始化基本信息加载
			loadAdminInfo(id);
		}
		
		loadComboxDepartp(1);//调用xml解析，进行两depart级联
		loadComboxDepartp(2);
		loadComboxDepartp(3);
	}
	/*考勤管理时要通过老师所教的课程或辅导员所在的院进行操作*/
	private void loadConcernedCourseOrDepartp(int id){
		if(isTeac){
			try {
				Teacher t = (Teacher) PersonDAOFactory.getTeacherProxy().findById(id);
				teacCourse = t.getCourse();
				teacClass = t.getClazz();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(isAdv){
			try {
				Advisor adv = (Advisor) PersonDAOFactory.getAdvisorProxy().findById(id);
				advDepartp = adv.getDepartp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/*调用xml解析，进行两depart级联，1：老师，2：辅导员，3：学生*/
	private void loadComboxDepartp(int type){
		List departp = new ArrayList();
		XMLAnalysis anls = new XMLAnalysis();
		departp = anls.getDepartp();
		if(type==1){
			for(Object x:departp){
				comboBoxDepartpT.addItem(x);
			}
		}else if(type==2){
			for(Object x:departp){
				comboBoxDepartpA.addItem(x);
			}
		}else{
			for(Object x:departp){
			comboBoxDepartpS.addItem(x);
		}
	}
		
		
	}
	/*调用xml解析，进行depart二级联动;type=1:老师信息面板的departc刷新，2:学生*/
	private void loadComboxDepartc(int i, int type){
		//先清空上次加载的
		if(type==1){
			comboBoxDepartcT.removeAllItems();
		}
		if(type==2){
			comboBoxDepartcS.removeAllItems();
		}
		List departc = new ArrayList();
		XMLAnalysis anls = new XMLAnalysis();
		departc = anls.getDepartc(i);//获取第i个departp下的departc
		for(Object x:departc){
			if(type==1){
				comboBoxDepartcT.addItem(x);
			}
			if(type==2){
				comboBoxDepartcS.addItem(x);
			}
			
		}
	}
	/*对于menu，不同用户登录不同操作权限*/
	private void setAuth(){
		if(isSupremeAdm){//最高权限的管理员才能查找 并修改其他管理员，任何管理员都不能修改自己的权限，但是最高的可以修改其他人的
			menuBar.getMenu(0).setEnabled(true);
			menuBar.getMenu(1).setEnabled(false);
			menuBar.getMenu(2).setEnabled(false);
			menuBar.getMenu(3).setEnabled(true);
			ShowOrHide(true, 0, -1);
			labelAllInfoAd.setVisible(true);
			panelAllInfoAd.setVisible(true);
			buttonsNormalAdminCantUse();//如果是最高权限管理员，只是不能修改自己的权限
			loadInfoIntoTable(1);//加载信息到jtable中
			loadInfoIntoTable(2);
			loadInfoIntoTable(3);
		}
		if(isNormalAdm){
			menuBar.getMenu(0).setEnabled(true);
			menuBar.getMenu(1).setEnabled(false);
			menuBar.getMenu(2).setEnabled(false);
			menuBar.getMenu(3).setEnabled(true);
			ShowOrHide(true, 0, -1);
			labelAllInfoAd.setVisible(false);
			panelAllInfoAd.setVisible(false);
			buttonsNormalAdminCantUse();//如果是一般管理员，几个按钮和单选不能用
			loadInfoIntoTable(1);//加载信息到jtable中
			loadInfoIntoTable(2);
			loadInfoIntoTable(3);
		}
		if(isTeac){
			menuBar.getMenu(0).setEnabled(false);
			menuBar.getMenu(1).setEnabled(true);
			menuItemAttendanceCheck.setEnabled(true);
			menuItemAskForLeave.setEnabled(false);
			menuBar.getMenu(2).setEnabled(true);
			munuItemAttendanceInfo.setEnabled(true);
			menuBar.getMenu(3).setEnabled(true);
			buttonNextAc.setVisible(false);
			buttonStartAc.setVisible(true);
			textFieldIdAc.setEnabled(false);
			textFieldNameAc.setEnabled(false);
			ShowOrHide(false, -1, 0);
			loadInfoIntoTable(5);
		}
		if(isAdv){
			menuBar.getMenu(0).setEnabled(false);
			menuBar.getMenu(1).setEnabled(true);
			menuItemAttendanceCheck.setEnabled(false);
			menuItemAskForLeave.setEnabled(true);
			menuBar.getMenu(2).setEnabled(true);
			munuItemAttendanceInfo.setEnabled(true);
			menuBar.getMenu(3).setEnabled(true);
			ShowOrHide(false, -1, 1);
			loadInfoIntoTable(6);
		}
	}
	/*最高权限和一般权限的管理员，单选、按钮使用的权限*/
	private void buttonsNormalAdminCantUse(){
		if(isSupremeAdm){
			buttonRetrieveAd.setEnabled(true);
			buttonInsertAd.setEnabled(true);
			buttonDeleteAd.setEnabled(true);
			if(radioButtonNormal.isSelected()){//是最高权限的管理员，并且当前信息是自己的不能改权限
				radioButtonSupreme.setEnabled(true);
				radioButtonNormal.setEnabled(true);
			}else{//是最高权限的管理员，但是当前信息不是自己的可以改他的权限
				radioButtonSupreme.setEnabled(false);
				radioButtonNormal.setEnabled(false);
			}
		}
		if(isNormalAdm){//一般管理员
			buttonRetrieveAd.setEnabled(false);
			buttonInsertAd.setEnabled(false);
			buttonDeleteAd.setEnabled(false);
			radioButtonSupreme.setEnabled(false);
			radioButtonNormal.setEnabled(false);
		}
		
	}
	/*管理员登陆后初始化基本信息加载*/
	private void loadAdminInfo(int adminId){
		try {
			Admin findAdm = (Admin) PersonDAOFactory.getAdminProxy().findById(adminId);
			textFieldIdAd.setText(findAdm.getId()+"");
			textFieldNameAd.setText(findAdm.getName());
			if(findAdm.getSex().equals("男")){
				radioButtonMaleAd.setSelected(true);
				radioButtonFemaleAd.setSelected(false);
			}else{
				radioButtonMaleAd.setSelected(false);
				radioButtonFemaleAd.setSelected(true);
			}
			textFieldPhoneAd.setText(findAdm.getPhone());
			textFieldEmailAd.setText(findAdm.getEmail());
			if(findAdm.getAuthorization()==1){
				radioButtonSupreme.setSelected(true);
				radioButtonNormal.setSelected(false);
			}else{
				radioButtonSupreme.setSelected(false);
				radioButtonNormal.setSelected(true);
			}
		} catch (Exception e1) {
			
		}
	}
	/*向Jtable更新数据*/
	private void loadInfoIntoTable(int type){//1-4为基本信息，5为考勤信息
		if(type==1){
			int rowsCount = dtmT.getRowCount();//行数不能写在for()里，因为每次移除一行行数就变了
			for(int i=0; i<rowsCount; i++){
				dtmT.removeRow(0);//每次都移除第一行
			}
			
			Vector v = null;
			List tl = null;
			try {
				tl = PersonDAOFactory.getTeacherProxy().doRetrieve();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Iterator it = tl.iterator();
			while(it.hasNext()){
				Teacher t = (Teacher) it.next();
				v = new Vector();
				v.add(t.getId());
				v.add(t.getName());
				v.add(t.getSex());
				v.add(t.getPhone());
				v.add(t.getEmail());
				v.add(t.getDepartp());
				v.add(t.getDepartc());
				v.add(t.getClazz());
				v.add(t.getCourse());
				dtmT.addRow(v);
			}
		}else if(type==2){
			int rowsCount = dtmA.getRowCount();//行数不能写在for()里，因为每次移除一行行数就变了
			for(int i=0; i<rowsCount; i++){
				dtmA.removeRow(0);//每次都移除第一行
			}
			Vector v = null;
			List al = null;
			try {
				al = PersonDAOFactory.getAdvisorProxy().doRetrieve();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Iterator it = al.iterator();
			while(it.hasNext()){
				Advisor a = (Advisor) it.next();
				v = new Vector();
				v.add(a.getId());
				v.add(a.getName());
				v.add(a.getSex());
				v.add(a.getPhone());
				v.add(a.getEmail());
				v.add(a.getDepartp());
				dtmA.addRow(v);
			}
		}else if(type==3){
			int rowsCount = dtmS.getRowCount();//行数不能写在for()里，因为每次移除一行行数就变了
			for(int i=0; i<rowsCount; i++){
				dtmS.removeRow(0);//每次都移除第一行
			}
			Vector v = null;
			List sl = null;
			try {
				sl = PersonDAOFactory.getStudentProxy().doRetrieve();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Iterator it = sl.iterator();
			while(it.hasNext()){
				Student s = (Student) it.next();
				v = new Vector();
				v.add(s.getId());
				v.add(s.getName());
				v.add(s.getSex());
				v.add(s.getPhone());
				v.add(s.getEmail());
				v.add(s.getDepartp());
				v.add(s.getDepartc());
				v.add(s.getClazz());
				v.add(s.getPicture());
				
				/*byte[] pic = new byte[66560];//mysql blob最大值
				try {
					s.getPicture().read(pic);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//先读到byte数组，再封装到imageicon
				icon = new ImageIcon(pic); */
				
				/*picMap = new HashMap();
				picMap.put(s.getId(), icon);
				baos = new ByteArrayOutputStream();  
				byte[] buffer = new byte[1024];  
				int len;  
				try {
					while ((len = s.getPicture().read(buffer)) > -1 ) {  
					    baos.write(buffer, 0, len);
					    
					}
					picMap.put(s.getId()+"", baos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  */

				dtmS.addRow(v);
			}
		}else if(type==4){
			int rowsCount = dtmAd.getRowCount();//行数不能写在for()里，因为每次移除一行行数就变了
			for(int i=0; i<rowsCount; i++){
				dtmAd.removeRow(0);//每次都移除第一行
			}
			Vector v = null;
			List al = null;
			try {
				al = PersonDAOFactory.getAdminProxy().doRetrieve();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Iterator it = al.iterator();
			while(it.hasNext()){
				Admin ad = (Admin) it.next();
				v = new Vector();
				v.add(ad.getId());
				v.add(ad.getName());
				v.add(ad.getSex());
				v.add(ad.getPhone());
				v.add(ad.getEmail());
				v.add(ad.getAuthorization());
				dtmAd.addRow(v);
			}
		}else if(type==5){
			int rowsCount = dtmAttendance.getRowCount();//行数不能写在for()里，因为每次移除一行行数就变了
			for(int i=0; i<rowsCount; i++){
				dtmAttendance.removeRow(0);//每次都移除第一行
			}
			Vector v = null;
			Map hm = null;
			try {
				hm = AttendanceDAOFactory.getAttendanceProxy().doRetrieveByClassAndCourse(teacClass, teacCourse);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Set set = hm.entrySet();
			Iterator it = set.iterator();
			while(it.hasNext()){
				Map.Entry me = (Entry) it.next();
				v = new Vector();
				v.add(me.getValue().toString().split(" ")[0]);
				v.add(me.getValue().toString().split(" ")[1]);
				v.add(me.getValue().toString().split(" ")[2]);
				v.add(me.getValue().toString().split(" ")[3]);
				v.add(me.getValue().toString().split(" ")[4]);
				dtmAttendance.addRow(v);
			}
		}else{
			int rowsCount = dtmAttendance.getRowCount();//行数不能写在for()里，因为每次移除一行行数就变了
			for(int i=0; i<rowsCount; i++){
				dtmAttendance.removeRow(0);//每次都移除第一行
			}
			Vector v = null;
			Map hm = null;
			try {
				hm = AttendanceDAOFactory.getAttendanceProxy().doRetrieveByDepartp(advDepartp);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Set set = hm.entrySet();
			Iterator it = set.iterator();
			while(it.hasNext()){
				Map.Entry me = (Entry) it.next();
				v = new Vector();
				v.add(me.getValue().toString().split(" ")[0]);
				v.add(me.getValue().toString().split(" ")[1]);
				v.add(me.getValue().toString().split(" ")[2]);
				v.add(me.getValue().toString().split(" ")[3]);
				v.add(me.getValue().toString().split(" ")[4]);
				dtmAttendance.addRow(v);
			}
		}
	}

	private void doCheckNextStudent(){
		ImageIcon ii = null;
		byte[] pic = new byte[66560];
		try {
			if(lit.hasNext()){
				Student s = (Student) lit.next();
				textFieldIdAc.setText(s.getId()+"");
				textFieldNameAc.setText(s.getName());
				s.getPicture().read(pic);
				ii = new ImageIcon(pic);
				labelPictureAc.setIcon(ii);
			}else{
				JOptionPane.showMessageDialog(null, "这是最后一名学生");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 756, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//屏幕中央显示
		frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width)/2, 
				(Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().height)/2);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 669, 31);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 249, 21);
		panel.add(menuBar);
		
		menuFirst = new JMenu("\u57FA\u672C\u4FE1\u606F");
		menuBar.add(menuFirst);
		
		menuItemTeacher = new JMenuItem("\u6559\u5E08\u57FA\u672C\u4FE1\u606F");
		
		menuFirst.add(menuItemTeacher);
		
		menuItemAdvisor = new JMenuItem("\u8F85\u5BFC\u5458\u57FA\u672C\u4FE1\u606F");
		
		menuFirst.add(menuItemAdvisor);
		
		menuItemStudent = new JMenuItem("\u5B66\u751F\u57FA\u672C\u4FE1\u606F");
		menuFirst.add(menuItemStudent);
		
		menuItemAdmin = new JMenuItem("\u7BA1\u7406\u5458\u57FA\u672C\u4FE1\u606F");
		menuFirst.add(menuItemAdmin);
		
		menuSecond = new JMenu("\u8003\u52E4\u529F\u80FD");
		menuBar.add(menuSecond);
		
		menuItemAttendanceCheck = new JMenuItem("\u70B9\u540D");
		menuSecond.add(menuItemAttendanceCheck);
		
		menuItemAskForLeave = new JMenuItem("\u8BF7\u5047");
		menuSecond.add(menuItemAskForLeave);
		
		menuThird = new JMenu("\u8003\u52E4\u4FE1\u606F");
		menuBar.add(menuThird);
		
		munuItemAttendanceInfo = new JMenuItem("\u67E5\u770B");
		menuThird.add(munuItemAttendanceInfo);
		
		menuFourth = new JMenu("\u5176\u4ED6\u529F\u80FD");
		menuBar.add(menuFourth);
		
		menuItemAbout = new JMenuItem("\u5173\u4E8E");
		menuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "学生考勤管理系统"+"\r\n\t ---作者:李剑锋\r\n\t --- 学号:201512172162", "关于", JOptionPane.NO_OPTION);
			}
		});
		menuFourth.add(menuItemAbout);
		
		menuItemClose = new JMenuItem("\u9000\u51FA");
		menuItemClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//frame.dispose();
				System.exit(0);
			}
		});
		menuFourth.add(menuItemClose);
		
		panelAttendance = new JPanel();
		panelAttendance.setBounds(10, 41, 725, 445);
		frame.getContentPane().add(panelAttendance);
		panelAttendance.setLayout(null);
		
		tabbedPaneAttendance = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneAttendance.setBounds(0, 0, 713, 440);
		panelAttendance.add(tabbedPaneAttendance);
		
		panelAttendanceCheck = new JPanel();
		tabbedPaneAttendance.addTab("点名", null, panelAttendanceCheck, null);
		panelAttendanceCheck.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("\u5B66\u53F7\uFF1A");
		lblNewLabel_3.setBounds(187, 59, 76, 29);
		panelAttendanceCheck.add(lblNewLabel_3);
		
		textFieldIdAc = new JTextField();
		textFieldIdAc.setBounds(257, 63, 106, 21);
		panelAttendanceCheck.add(textFieldIdAc);
		textFieldIdAc.setColumns(10);
		
		JLabel label_31 = new JLabel("\u59D3\u540D\uFF1A");
		label_31.setBounds(187, 91, 76, 29);
		panelAttendanceCheck.add(label_31);
		
		textFieldNameAc = new JTextField();
		textFieldNameAc.setColumns(10);
		textFieldNameAc.setBounds(257, 95, 106, 21);
		panelAttendanceCheck.add(textFieldNameAc);
		
		JButton buttonInsertAc = new JButton("\u65F7\u8BFE");
		buttonInsertAc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Person p = null;
				try {
					Person findS = PersonDAOFactory.getStudentProxy().findById(Integer.parseInt(textFieldIdAc.getText()));
					
					if((findS.getName().equals(textFieldNameAc.getText()))&&(((Student) findS).getClazz()==teacClass)){//根据id name class能查到此人继续操作
						
						try {
							p = new Student();
							p.setId(Integer.parseInt((textFieldIdAc.getText())));
							p.setName(textFieldNameAc.getText());
							AttendanceDAOFactory.getAttendanceProxy().doInsertForCheck(p, teacCourse);//添加迟到或缺勤
							JOptionPane.showMessageDialog(null, "提交完毕");
							doCheckNextStudent();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "失败");
						}
					}else{
						JOptionPane.showMessageDialog(null, "不存在该学生");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "不存在该学生");
				} 
			}
		});
		buttonInsertAc.setBounds(238, 192, 135, 23);
		panelAttendanceCheck.add(buttonInsertAc);
		
		JButton buttonUpdateAc = new JButton("\u67E5\u627E\u5E76\u6539\u4E3A\u8FDF\u5230");
		buttonUpdateAc.addMouseListener(new MouseAdapter() {//旷课改为迟到
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean flag = false;
				try {
					int sid = Integer.parseInt(JOptionPane.showInputDialog("请输入要更改学生的学号"));
					flag = AttendanceDAOFactory.getAttendanceProxy().doUpdateFromAbToLa(sid, teacCourse);
					if(flag){
						JOptionPane.showMessageDialog(null, "修改成功");
					}else{
						JOptionPane.showMessageDialog(null, "没有该学生旷课信息");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "没有该学生旷课信息");
				}
			}
		});
		buttonUpdateAc.setBounds(238, 225, 135, 23);
		panelAttendanceCheck.add(buttonUpdateAc);
		
		JLabel lblNewLabelCourseName = new JLabel("");
		lblNewLabelCourseName.setBounds(9, 10, 217, 15);
		panelAttendanceCheck.add(lblNewLabelCourseName);
		lblNewLabelCourseName.setText(teacCourse);
		
		ButtonGroup attendanceAc = new ButtonGroup();
		
		buttonStartAc = new JButton("\u70B9\u540D\u5F00\u59CB");
		buttonStartAc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Timer t = new Timer();//计时器
				new Thread(t).start();//多线程
				
				ImageIcon ii = null;
				byte[] pic = new byte[66560];
				try {
					stuAcList = AttendanceDAOFactory.getAttendanceProxy().doRetrieveForCheck(teacClass, teacCourse);
					lit = stuAcList.listIterator();
					if(lit.hasNext()){
						Student s = (Student) lit.next();
						textFieldIdAc.setText(s.getId()+"");
						textFieldNameAc.setText(s.getName());
						s.getPicture().read(pic);
						ii = new ImageIcon(pic);
						labelPictureAc.setIcon(ii);
					}
					buttonNextAc.setVisible(true);
					buttonStartAc.setVisible(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonStartAc.setBounds(238, 159, 135, 23);
		panelAttendanceCheck.add(buttonStartAc);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(439, 31, 130, 149);
		panelAttendanceCheck.add(panel_1);
		
		labelPictureAc = new JLabel("");
		labelPictureAc.setBounds(10, 10, 110, 129);
		panel_1.add(labelPictureAc);
		
		buttonNextAc = new JButton("\u4E0B\u4E00\u4E2A");
		buttonNextAc.addMouseListener(new MouseAdapter() {//点名，加载下一个
			@Override
			public void mouseClicked(MouseEvent e) {
				doCheckNextStudent();
			}
		});
		buttonNextAc.setBounds(238, 159, 135, 23);
		panelAttendanceCheck.add(buttonNextAc);
		
		panelAskForLeave = new JPanel();
		tabbedPaneAttendance.addTab("请假", null, panelAskForLeave, null);
		panelAskForLeave.setLayout(null);
		
		JLabel label_32 = new JLabel("\u5B66\u53F7\uFF1A");
		label_32.setBounds(234, 30, 76, 29);
		panelAskForLeave.add(label_32);
		
		textFieldIdAfl = new JTextField();
		textFieldIdAfl.setEnabled(false);
		textFieldIdAfl.setColumns(10);
		textFieldIdAfl.setBounds(304, 34, 106, 21);
		panelAskForLeave.add(textFieldIdAfl);
		
		JLabel label_33 = new JLabel("\u59D3\u540D\uFF1A");
		label_33.setBounds(234, 65, 76, 29);
		panelAskForLeave.add(label_33);
		
		textFieldNameAfl = new JTextField();
		textFieldNameAfl.setEnabled(false);
		textFieldNameAfl.setColumns(10);
		textFieldNameAfl.setBounds(304, 69, 106, 21);
		panelAskForLeave.add(textFieldNameAfl);
		
		JButton buttonInsertAfl = new JButton("\u63D0\u4EA4");
		buttonInsertAfl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//把请假信息添加到数据库时会根据请假日期所包含的课程信息也加入
				List courseList = new ArrayList();
				Person p = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date d = null;
				try {
					Person findS = PersonDAOFactory.getStudentProxy().findById(Integer.parseInt(textFieldIdAfl.getText()));
					if((findS.getName().equals(textFieldNameAfl.getText()))&&(((Student) findS).getDepartp().equals(advDepartp))){//根据id name departp能查到此人继续操作
						try{
							d = sdf.parse(textFieldDateAfl.getText());
						}catch(Exception ee){
							JOptionPane.showMessageDialog(null, "日期格式：yyyy-MM-dd");
							return;
						}
						try {
							p = new Student();
							p.setId(Integer.parseInt((textFieldIdAfl.getText())));
							p.setName(textFieldNameAfl.getText());							
							Calendar cal = Calendar.getInstance();
							cal.setTime(d);
							int dow = cal.get(Calendar.DAY_OF_WEEK)-1;//获取星期几
							//System.out.print(dow);
							switch(dow){
							case 0:for(DayOfWeek.ESun x:DayOfWeek.ESun.values()){//若上面的方法返回0代表周日，把enum周日的课程加入arraylist，下同
								if(x.name()!=null){
									courseList.add(x.name());
								}
								
							}
							break;
							case 1:for(DayOfWeek.EMon x:DayOfWeek.EMon.values()){
								if(x.name()!=null){
									courseList.add(x.name());
								}
							}
							break;
							case 2:for(DayOfWeek.ETue x:DayOfWeek.ETue.values()){
								if(x.name()!=null){
									courseList.add(x.name());
								}
							}
							break;
							case 3:for(DayOfWeek.EWed x:DayOfWeek.EWed.values()){
								if(x.name()!=null){
									courseList.add(x.name());
								}
							}
							break;
							case 4:for(DayOfWeek.EThu x:DayOfWeek.EThu.values()){
								if(x.name()!=null){
									courseList.add(x.name());
								}
							}
							break;
							case 5:for(DayOfWeek.EFri x:DayOfWeek.EFri.values()){
								if(x.name()!=null){
									courseList.add(x.name());
								}
							}
							break;
							default:for(DayOfWeek.ESat x:DayOfWeek.ESat.values()){
								if(x.name()!=null){
									courseList.add(x.name());
								}
							}
							}
							AttendanceDAOFactory.getAttendanceProxy().doInsertForLeave(p, courseList, d);//添加请假
							JOptionPane.showMessageDialog(null, "请假成功");
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "失败");
						}
					}else{
						JOptionPane.showMessageDialog(null, "不存在该学生");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "不存在该学生");
				} 
			}
		});
		buttonInsertAfl.setBounds(234, 163, 76, 23);
		panelAskForLeave.add(buttonInsertAfl);
		
		JButton buttonAfl = new JButton("\u5220\u9664");
		buttonAfl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Person p = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date d = null;
				try {
					Person findS = PersonDAOFactory.getStudentProxy().findById(Integer.parseInt(textFieldIdAfl.getText()));
					if((findS.getName().equals(textFieldNameAfl.getText()))&&(((Student) findS).getDepartp().equals(advDepartp))){//根据id name departp能查到此人继续操作
						try{
							d = sdf.parse(textFieldDateAfl.getText());
						}catch(Exception ee){
							JOptionPane.showMessageDialog(null, "日期格式：yyyy-MM-dd");
							return;
						}
						try {
							p = new Student();
							p.setId(Integer.parseInt((textFieldIdAfl.getText())));
							p.setName(textFieldNameAfl.getText());
													
							boolean flag = AttendanceDAOFactory.getAttendanceProxy().doDeleteForLeave(p, d);//添加请假
							
							if(flag){
								JOptionPane.showMessageDialog(null, "删除成功");
							}else{
								JOptionPane.showMessageDialog(null, "不存在该记录");
							}
							
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "失败");
						}
					}else{
						JOptionPane.showMessageDialog(null, "不存在该学生");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "不存在该学生");
				} 
			}
		});
		buttonAfl.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				
			}
		});
		buttonAfl.setBounds(334, 163, 76, 23);
		panelAskForLeave.add(buttonAfl);
		
		JLabel label_30 = new JLabel("\u65F6\u95F4\uFF1A");
		label_30.setBounds(234, 104, 76, 29);
		panelAskForLeave.add(label_30);
		
		textFieldDateAfl = new JTextField();
		textFieldDateAfl.setEnabled(false);
		textFieldDateAfl.setColumns(10);
		textFieldDateAfl.setBounds(304, 108, 106, 21);
		panelAskForLeave.add(textFieldDateAfl);
		
		JLabel lblNewLabelDepartp = new JLabel("");
		lblNewLabelDepartp.setBounds(10, 10, 190, 15);
		panelAskForLeave.add(lblNewLabelDepartp);
		lblNewLabelDepartp.setText(advDepartp);
		
		panelAttendanceInfo = new JPanel();
		tabbedPaneAttendance.addTab("考勤信息", null, panelAttendanceInfo, null);
		panelAttendanceInfo.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("\u7F3A\u52E4\u5B66\u751F\u4FE1\u606F\uFF1A");
		lblNewLabel_4.setBounds(188, 20, 200, 37);
		panelAttendanceInfo.add(lblNewLabel_4);
		
		JPanel panelAllInfoQ = new JPanel();
		panelAllInfoQ.setBounds(109, 67, 504, 229);
		panelAttendanceInfo.add(panelAllInfoQ);
		
		tableAttendance = new MyTableModel();
		panelAllInfoQ.add(tableAttendance);
		
		JButton buttonReflash = new JButton("\u5237\u65B0");
		buttonReflash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonReflash.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isTeac){//是老师只显示该课程的学生
					loadInfoIntoTable(5);
				}
				if(isAdv){//是辅导员只显示该院的学生
					loadInfoIntoTable(6);
				}
			}
		});
		buttonReflash.setBounds(340, 27, 93, 23);
		panelAttendanceInfo.add(buttonReflash);
		tableAttendance.isCellEditable(0,0);
		String[] tableHeadsAttendance = new String[]{"编号","姓名","类型","课程","日期"}; //列标识符
		dtmAttendance = (DefaultTableModel)tableAttendance.getModel(); //模型实例
		dtmAttendance.setColumnIdentifiers(tableHeadsAttendance);//设置模型中的列标识符。
		//System.out.print(isTeac +" "+ isAdv);
		
		
		
		panelInfo = new JPanel();
		panelInfo.setBounds(10, 41, 725, 445);
		frame.getContentPane().add(panelInfo);
		panelInfo.setLayout(null);
		
		tabbedPaneInfo = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneInfo.setBounds(0, 0, 713, 440);
		panelInfo.add(tabbedPaneInfo);
		
		panelTeacher = new JPanel();
		tabbedPaneInfo.addTab("教师基本信息", null, panelTeacher, null);
		panelTeacher.setLayout(null);
		
		JLabel label = new JLabel("\u7F16\u53F7\uFF1A");
		label.setBounds(10, 10, 59, 23);
		panelTeacher.add(label);
		
		JLabel label_1 = new JLabel("\u59D3\u540D\uFF1A");
		label_1.setBounds(10, 43, 59, 23);
		panelTeacher.add(label_1);
		
		JLabel label_2 = new JLabel("\u6027\u522B\uFF1A");
		label_2.setBounds(10, 76, 59, 23);
		panelTeacher.add(label_2);
		
		JLabel label_3 = new JLabel("\u624B\u673A\u53F7\u7801\uFF1A");
		label_3.setBounds(321, 10, 72, 23);
		panelTeacher.add(label_3);
		
		JLabel label_4 = new JLabel("\u7535\u5B50\u90AE\u7BB1\uFF1A");
		label_4.setBounds(321, 43, 72, 23);
		panelTeacher.add(label_4);
		
		JLabel label_5 = new JLabel("\u9662\u7CFB\uFF1A");
		label_5.setBounds(10, 109, 59, 23);
		panelTeacher.add(label_5);
		
		JLabel label_6 = new JLabel("\u79D1\u76EE\uFF1A");
		label_6.setBounds(10, 171, 59, 23);
		panelTeacher.add(label_6);
		
		JLabel label_7 = new JLabel("\u6388\u8BFE\u73ED\u7EA7\uFF1A");
		label_7.setBounds(10, 204, 72, 23);
		panelTeacher.add(label_7);
		
		textFieldIdT = new JTextField();
		textFieldIdT.setEnabled(false);
		textFieldIdT.setBounds(100, 11, 118, 21);
		panelTeacher.add(textFieldIdT);
		textFieldIdT.setColumns(10);
		
		textFieldNameT = new JTextField();
		textFieldNameT.setColumns(10);
		textFieldNameT.setBounds(100, 44, 118, 21);
		panelTeacher.add(textFieldNameT);
		
		textFieldPhoneT = new JTextField();
		textFieldPhoneT.setColumns(10);
		textFieldPhoneT.setBounds(416, 11, 183, 21);
		panelTeacher.add(textFieldPhoneT);
		
		textFieldEmailT = new JTextField();
		textFieldEmailT.setColumns(10);
		textFieldEmailT.setBounds(416, 44, 183, 21);
		panelTeacher.add(textFieldEmailT);
		
		textFieldClassT = new JTextField();
		textFieldClassT.setColumns(10);
		textFieldClassT.setBounds(100, 205, 118, 21);
		panelTeacher.add(textFieldClassT);
		
		ButtonGroup sexT = new ButtonGroup();
		radioButtonMaleT = new JRadioButton("\u7537");
		radioButtonMaleT.setSelected(true);
		radioButtonMaleT.setBounds(98, 76, 47, 23);
		panelTeacher.add(radioButtonMaleT);
		
		radioButtonFemaleT = new JRadioButton("\u5973");
		radioButtonFemaleT.setBounds(171, 76, 47, 23);
		panelTeacher.add(radioButtonFemaleT);
		
		sexT.add(radioButtonMaleT);
		sexT.add(radioButtonFemaleT);
		
		JPanel panelAllInfoT = new JPanel();
		panelAllInfoT.setBounds(10, 255, 688, 146);
		panelTeacher.add(panelAllInfoT);
		
		tableT = new MyTableModel();
		panelAllInfoT.add(tableT);
		tableT.isCellEditable(0,0);
		String[] tableHeadsT = new String[]{"编号","姓名","性别","电话","邮箱","院","系","班级","课程"}; //列标识符
		dtmT = (DefaultTableModel)tableT.getModel(); //模型实例
		dtmT.setColumnIdentifiers(tableHeadsT);//设置模型中的列标识符。
		
		
		
		
		tableT.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mouseClicked(MouseEvent e) {
				//获取点击行的每个数据放到text
				textFieldIdT.setText(dtmT.getValueAt(tableT.getSelectedRows()[0], 0).toString());
				textFieldNameT.setText(dtmT.getValueAt(tableT.getSelectedRows()[0], 1).toString());
				if("男".equals(dtmT.getValueAt(tableT.getSelectedRows()[0], 2).toString())){
					radioButtonMaleT.setSelected(true);
					radioButtonFemaleT.setSelected(false);
				}else{
					radioButtonMaleT.setSelected(false);
					radioButtonFemaleT.setSelected(true);
				}
				textFieldPhoneT.setText(dtmT.getValueAt(tableT.getSelectedRows()[0], 3).toString());
				textFieldEmailT.setText(dtmT.getValueAt(tableT.getSelectedRows()[0], 4).toString());
				textFieldCourseT.setText(dtmT.getValueAt(tableT.getSelectedRows()[0], 8).toString());
				textFieldClassT.setText(dtmT.getValueAt(tableT.getSelectedRows()[0], 7).toString());
				comboBoxDepartpT.setSelectedItem(dtmT.getValueAt(tableT.getSelectedRows()[0], 5).toString());
				comboBoxDepartcT.setSelectedItem(dtmT.getValueAt(tableT.getSelectedRows()[0], 6).toString());
				

				
			}
		});
		
		JLabel lblNewLabel = new JLabel("\u5168\u90E8\u6559\u5E08\u4FE1\u606F\uFF1A");
		lblNewLabel.setBounds(10, 223, 183, 32);
		panelTeacher.add(lblNewLabel);
		
		JButton buttonInsertT = new JButton("\u6DFB\u52A0");
		buttonInsertT.addMouseListener(new MouseAdapter() {//教师基本信息：增
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if((textFieldIdT.getText()==null)||("".equals(textFieldIdT.getText()))){//textid为空才能添加新数据
					String name = textFieldNameT.getText();
					String sex = null;
					if(radioButtonMaleT.isSelected()){
						sex = "男";
					}else{
						sex = "女";
					}
					String phone = textFieldPhoneT.getText();
					String email = textFieldEmailT.getText();
					String course = textFieldCourseT.getText();
					String clazz = textFieldClassT.getText();
					String departp = comboBoxDepartpT.getSelectedItem().toString();
					String departc = comboBoxDepartcT.getSelectedItem().toString();
					
					String errMess = Validation.getTeacherErrorMess(name, phone, email, course, clazz);//返回的错误信息
					if("".equals(errMess)){
						Teacher t = new Teacher(name, sex, phone, email, departp, departc, Integer.parseInt(clazz), course);
						try {
							PersonDAOFactory.getTeacherProxy().doCreate(t);
							JOptionPane.showMessageDialog(null, "添加成功", "添加成功",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(1);
							loadInfoIntoTable(1);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, "失败，已存在", "添加失败", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					doClearInfo(1);
					JOptionPane.showMessageDialog(null, "请不要插入已存在信息");
				}
				
			}
		});
		buttonInsertT.setBounds(348, 90, 92, 23);
		panelTeacher.add(buttonInsertT);
		
		JButton buttonUpdateT = new JButton("\u4FEE\u6539");
		buttonUpdateT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonUpdateT.addMouseListener(new MouseAdapter() {//教师基本信息：改
			@Override
			public void mouseClicked(MouseEvent e) {
				String tid = textFieldIdT.getText();
				if(!("".equals(tid))){
					String name = textFieldNameT.getText();
					String sex = null;
					if(radioButtonMaleT.isSelected()){
						sex = "男";
					}else{
						sex = "女";
					}
					String phone = textFieldPhoneT.getText();
					String email = textFieldEmailT.getText();
					String course = textFieldCourseT.getText();
					String clazz = textFieldClassT.getText();
					String departp = comboBoxDepartpT.getSelectedItem().toString();
					String departc = comboBoxDepartcT.getSelectedItem().toString();
					
					String errMess = Validation.getTeacherErrorMess(name, phone, email, course, clazz);//返回的错误信息
					if("".equals(errMess)){
						Teacher t = new Teacher(Integer.parseInt(tid), name, sex, phone, email, departp, departc, Integer.parseInt(clazz), course);
						try {
							PersonDAOFactory.getTeacherProxy().doUpdate(t);
							JOptionPane.showMessageDialog(null, "修改成功", "修改成功",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(1);
							loadInfoIntoTable(1);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "请先选择要修改的记录", "修改成功",JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		buttonUpdateT.setBounds(348, 139, 92, 23);
		panelTeacher.add(buttonUpdateT);
		
		JButton buttonDeleteT = new JButton("\u5220\u9664");
		buttonDeleteT.addMouseListener(new MouseAdapter() {////教师基本信息：删
			@Override
			public void mouseClicked(MouseEvent e) {
				String tid = textFieldIdT.getText();
				if(!("".equals(tid))){
					try {
						PersonDAOFactory.getTeacherProxy().doDelete(Integer.parseInt(tid));
						JOptionPane.showMessageDialog(null, "删除成功", "删除成功",JOptionPane.INFORMATION_MESSAGE);
						doClearInfo(1);
						loadInfoIntoTable(1);
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "请先选择要删除的记录", "删除失败",JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}
		});
		buttonDeleteT.setBounds(477, 139, 92, 23);
		panelTeacher.add(buttonDeleteT);
		
		JButton buttonPasswordT = new JButton("\u4FEE\u6539\u5BC6\u7801");
		buttonPasswordT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!"".equals(textFieldIdT.getText())){
					new PasswordFrame(textFieldIdT.getText());
				}else{
					JOptionPane.showMessageDialog(null, "请先选择一条记录");
				}
				
			}
		});
		buttonPasswordT.setBounds(348, 189, 92, 23);
		panelTeacher.add(buttonPasswordT);
		
		comboBoxDepartpT = new JComboBox();
		comboBoxDepartpT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadComboxDepartc(comboBoxDepartpT.getSelectedIndex(),1);//1表示老师的departc刷新
			}
		});
		comboBoxDepartpT.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
			}
		});
		comboBoxDepartpT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		comboBoxDepartpT.setBounds(100, 110, 118, 21);
		panelTeacher.add(comboBoxDepartpT);
		
		comboBoxDepartcT = new JComboBox();
		comboBoxDepartcT.setBounds(100, 143, 118, 21);
		panelTeacher.add(comboBoxDepartcT);
		
		textFieldCourseT = new JTextField();
		textFieldCourseT.setColumns(10);
		textFieldCourseT.setBounds(100, 172, 118, 21);
		panelTeacher.add(textFieldCourseT);
		
		JButton buttonRetrieveT = new JButton("\u67E5\u627E");
		buttonRetrieveT.addMouseListener(new MouseAdapter() {//查找对应编号的教师
			@Override
			public void mouseClicked(MouseEvent e) {
				String findId = JOptionPane.showInputDialog("请输入要查找的编号");
				if(findId==null){
					return;
				}
				if(findId.matches("\\d{8}")){
					try {
						Teacher findT = (Teacher) PersonDAOFactory.getTeacherProxy().findById(Integer.parseInt(findId));
						if(findT!=null){
							textFieldIdT.setText(findT.getId()+"");
							textFieldNameT.setText(findT.getName());
							if(findT.getSex().equals("男")){
								radioButtonMaleT.setSelected(true);
								radioButtonFemaleT.setSelected(false);
							}else{
								radioButtonMaleT.setSelected(false);
								radioButtonFemaleT.setSelected(true);
							}
							textFieldPhoneT.setText(findT.getPhone());
							textFieldEmailT.setText(findT.getEmail());
							textFieldCourseT.setText(findT.getCourse());
							textFieldClassT.setText(findT.getClazz()+"");
							comboBoxDepartpT.setSelectedItem(findT.getDepartp());
							comboBoxDepartcT.setSelectedItem(findT.getDepartc());
						}else{
							JOptionPane.showMessageDialog(null, "没找到对应编号的教师", "查找失败",JOptionPane.INFORMATION_MESSAGE);
						}
					
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "没找到对应编号的教师", "查找失败",JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "没找到对应编号的教师", "查找失败",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonRetrieveT.setBounds(477, 90, 92, 23);
		panelTeacher.add(buttonRetrieveT);
		
		JButton buttonClearT = new JButton("\u6E05\u7A7A");
		buttonClearT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doClearInfo(1);
			}
		});
		buttonClearT.setBounds(476, 189, 93, 23);
		panelTeacher.add(buttonClearT);
		
		panelAdvisor = new JPanel();
		tabbedPaneInfo.addTab("辅导员基本信息", null, panelAdvisor, null);
		panelAdvisor.setLayout(null);
		
		JLabel label_9 = new JLabel("\u7F16\u53F7\uFF1A");
		label_9.setBounds(10, 10, 59, 23);
		panelAdvisor.add(label_9);
		
		textFieldIdA = new JTextField();
		textFieldIdA.setEnabled(false);
		textFieldIdA.setColumns(10);
		textFieldIdA.setBounds(100, 11, 118, 21);
		panelAdvisor.add(textFieldIdA);
		
		textFieldPhoneA = new JTextField();
		textFieldPhoneA.setColumns(10);
		textFieldPhoneA.setBounds(370, 11, 183, 21);
		panelAdvisor.add(textFieldPhoneA);
		
		JLabel label_10 = new JLabel("\u59D3\u540D\uFF1A");
		label_10.setBounds(10, 43, 59, 23);
		panelAdvisor.add(label_10);
		
		textFieldNameA = new JTextField();
		textFieldNameA.setColumns(10);
		textFieldNameA.setBounds(100, 44, 118, 21);
		panelAdvisor.add(textFieldNameA);
		
		JLabel label_11 = new JLabel("\u7535\u5B50\u90AE\u7BB1\uFF1A");
		label_11.setBounds(275, 43, 72, 23);
		panelAdvisor.add(label_11);
		
		textFieldEmailA = new JTextField();
		textFieldEmailA.setColumns(10);
		textFieldEmailA.setBounds(370, 44, 183, 21);
		panelAdvisor.add(textFieldEmailA);
		
		radioButtonFemaleA = new JRadioButton("\u5973");
		radioButtonFemaleA.setBounds(171, 76, 47, 23);
		panelAdvisor.add(radioButtonFemaleA);
		
		radioButtonMaleA = new JRadioButton("\u7537");
		radioButtonMaleA.setSelected(true);
		radioButtonMaleA.setBounds(98, 76, 47, 23);
		panelAdvisor.add(radioButtonMaleA);
		
		ButtonGroup sexA = new ButtonGroup();
		sexA.add(radioButtonMaleA);
		sexA.add(radioButtonFemaleA);
		
		JLabel label_13 = new JLabel("\u6027\u522B\uFF1A");
		label_13.setBounds(10, 76, 59, 23);
		panelAdvisor.add(label_13);
		
		JButton buttonInsertA = new JButton("\u6DFB\u52A0");
		buttonInsertA.addMouseListener(new MouseAdapter() {//辅导员基本信息：增
			@Override
			public void mouseClicked(MouseEvent e) {
				if((textFieldIdA.getText()==null)||("".equals(textFieldIdA.getText()))){//textid为空才能添加新数据
					String name = textFieldNameA.getText();
					String sex = null;
					if(radioButtonMaleA.isSelected()){
						sex = "男";
					}else{
						sex = "女";
					}
					String phone = textFieldPhoneA.getText();
					String email = textFieldEmailA.getText();
					String departp = comboBoxDepartpA.getSelectedItem().toString();
					
					String errMess = Validation.getAdvisorOrAdminErrorMess(name, phone, email);//返回的错误信息
					if("".equals(errMess)){
						Advisor adv = new Advisor(name, sex, phone, email, departp);
						try {
							PersonDAOFactory.getAdvisorProxy().doCreate(adv);
							JOptionPane.showMessageDialog(null, "添加成功", "添加成功",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(2);
							loadInfoIntoTable(2);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, "失败，已存在", "添加失败", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					doClearInfo(2);
					JOptionPane.showMessageDialog(null, "请不要插入已存在信息");
				}
				
			}
		});
		buttonInsertA.setBounds(275, 109, 86, 23);
		panelAdvisor.add(buttonInsertA);
		
		JLabel label_14 = new JLabel("\u9662\u7CFB\uFF1A");
		label_14.setBounds(10, 109, 59, 23);
		panelAdvisor.add(label_14);
		
		JButton buttonUpdateA = new JButton("\u4FEE\u6539");
		buttonUpdateA.addMouseListener(new MouseAdapter() {//辅导员基本信息：改
			@Override
			public void mouseClicked(MouseEvent e) {
				String advid = textFieldIdA.getText();
				if(!("".equals(advid))){
					String name = textFieldNameA.getText();
					String sex = null;
					if(radioButtonMaleA.isSelected()){
						sex = "男";
					}else{
						sex = "女";
					}
					String phone = textFieldPhoneA.getText();
					String email = textFieldEmailA.getText();
					String departp = comboBoxDepartpA.getSelectedItem().toString();
					
					String errMess = Validation.getAdvisorOrAdminErrorMess(name, phone, email);//返回的错误信息
					if("".equals(errMess)){
						Advisor adv = new Advisor(Integer.parseInt(advid), name, sex, phone, email, departp);
						try {
							PersonDAOFactory.getAdvisorProxy().doUpdate(adv);
							JOptionPane.showMessageDialog(null, "修改成功", "修改成功",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(2);
							loadInfoIntoTable(2);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "请先选择要修改的记录", "修改成功",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonUpdateA.setBounds(381, 109, 90, 23);
		panelAdvisor.add(buttonUpdateA);
		
		JButton buttonDeleteA = new JButton("\u5220\u9664");
		buttonDeleteA.addMouseListener(new MouseAdapter() {//辅导员基本信息：删
			@Override
			public void mouseClicked(MouseEvent e) {
				String advid = textFieldIdA.getText();
				if(!("".equals(advid))){
					try {
						PersonDAOFactory.getAdvisorProxy().doDelete(Integer.parseInt(advid));
						JOptionPane.showMessageDialog(null, "删除成功", "删除成功",JOptionPane.INFORMATION_MESSAGE);
						doClearInfo(2);
						loadInfoIntoTable(2);
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "请先选择要删除的记录", "删除失败",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonDeleteA.setBounds(481, 108, 86, 23);
		panelAdvisor.add(buttonDeleteA);
		
		JLabel label_17 = new JLabel("\u5168\u90E8\u8F85\u5BFC\u5458\u4FE1\u606F\uFF1A");
		label_17.setBounds(10, 174, 183, 32);
		panelAdvisor.add(label_17);
		
		JPanel panelAllInfoA = new JPanel();
		panelAllInfoA.setBounds(10, 205, 461, 196);
		panelAdvisor.add(panelAllInfoA);
		
		tableA = new MyTableModelA();
		tableA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//获取点击行的每个数据放到text
				textFieldIdA.setText(dtmA.getValueAt(tableA.getSelectedRows()[0], 0).toString());
				textFieldNameA.setText(dtmA.getValueAt(tableA.getSelectedRows()[0], 1).toString());
				if("男".equals(dtmA.getValueAt(tableA.getSelectedRows()[0], 2).toString())){
					radioButtonMaleA.setSelected(true);
					radioButtonFemaleA.setSelected(false);
				}else{
					radioButtonMaleA.setSelected(false);
					radioButtonFemaleA.setSelected(true);
				}
				textFieldPhoneA.setText(dtmA.getValueAt(tableA.getSelectedRows()[0], 3).toString());
				textFieldEmailA.setText(dtmA.getValueAt(tableA.getSelectedRows()[0], 4).toString());
				comboBoxDepartpA.setSelectedItem(dtmA.getValueAt(tableA.getSelectedRows()[0], 5).toString());
			}
		});
		panelAllInfoA.add(tableA);
		tableA.isCellEditable(0,0);
		String[] tableHeadsA = new String[]{"编号","姓名","性别","电话","邮箱","院"}; //列标识符
		dtmA = (DefaultTableModel)tableA.getModel(); //模型实例
		dtmA.setColumnIdentifiers(tableHeadsA);//设置模型中的列标识符。
		
		
		JLabel label_18 = new JLabel("\u624B\u673A\u53F7\u7801\uFF1A");
		label_18.setBounds(275, 10, 72, 23);
		panelAdvisor.add(label_18);
		
		JButton buttonPasswordA = new JButton("\u4FEE\u6539\u5BC6\u7801");
		buttonPasswordA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!"".equals(textFieldIdA.getText())){
					new PasswordFrame(textFieldIdA.getText());
				}else{
					JOptionPane.showMessageDialog(null, "请先选择一条记录");
				}
			}
		});
		buttonPasswordA.setBounds(275, 76, 86, 23);
		panelAdvisor.add(buttonPasswordA);
		
		comboBoxDepartpA = new JComboBox();
		comboBoxDepartpA.setBounds(100, 110, 118, 21);
		panelAdvisor.add(comboBoxDepartpA);
		
		JButton buttonRetrieveA = new JButton("\u67E5\u627E");
		buttonRetrieveA.addMouseListener(new MouseAdapter() {//超找对应编号辅导员
			@Override
			public void mouseClicked(MouseEvent e) {
				String findId = JOptionPane.showInputDialog("请输入要查找的编号");
				if(findId==null){
					return;
				}
				if(findId.matches("\\d{8}")){
					try {
						Advisor findAdv = (Advisor) PersonDAOFactory.getAdvisorProxy().findById(Integer.parseInt(findId));
						if(findAdv!=null){
							textFieldIdA.setText(findAdv.getId()+"");
							textFieldNameA.setText(findAdv.getName());
							if(findAdv.getSex().equals("男")){
								radioButtonMaleA.setSelected(true);
								radioButtonFemaleA.setSelected(false);
							}else{
								radioButtonMaleA.setSelected(false);
								radioButtonFemaleA.setSelected(true);
							}
							textFieldPhoneA.setText(findAdv.getPhone());
							textFieldEmailA.setText(findAdv.getEmail());
							comboBoxDepartpA.setSelectedItem(findAdv.getDepartp());
						}else{
							JOptionPane.showMessageDialog(null, "没找到对应编号的辅导员", "查找失败",JOptionPane.INFORMATION_MESSAGE);
						}
					
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "没找到对应编号的辅导员", "查找失败",JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "没找到对应编号的辅导员", "查找失败",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonRetrieveA.setBounds(380, 76, 91, 23);
		panelAdvisor.add(buttonRetrieveA);
		
		JButton buttonClearA = new JButton("\u6E05\u7A7A");
		buttonClearA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonClearA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doClearInfo(2);
			}
		});
		buttonClearA.setBounds(481, 75, 86, 23);
		panelAdvisor.add(buttonClearA);
		
		panelStudent = new JPanel();
		tabbedPaneInfo.addTab("学生基本信息", null, panelStudent, null);
		panelStudent.setLayout(null);
		
		JLabel label_15 = new JLabel("\u5B66\u53F7\uFF1A");
		label_15.setBounds(10, 10, 59, 23);
		panelStudent.add(label_15);
		
		textFieldIdS = new JTextField();
		textFieldIdS.setEnabled(false);
		textFieldIdS.setColumns(10);
		textFieldIdS.setBounds(100, 11, 118, 21);
		panelStudent.add(textFieldIdS);
		
		JLabel label_16 = new JLabel("\u624B\u673A\u53F7\u7801\uFF1A");
		label_16.setBounds(10, 204, 72, 23);
		panelStudent.add(label_16);
		
		textFieldPhoneS = new JTextField();
		textFieldPhoneS.setColumns(10);
		textFieldPhoneS.setBounds(100, 205, 183, 21);
		panelStudent.add(textFieldPhoneS);
		
		textFieldEmailS = new JTextField();
		textFieldEmailS.setColumns(10);
		textFieldEmailS.setBounds(364, 205, 183, 21);
		panelStudent.add(textFieldEmailS);
		
		JLabel label_19 = new JLabel("\u7535\u5B50\u90AE\u7BB1\uFF1A");
		label_19.setBounds(295, 204, 72, 23);
		panelStudent.add(label_19);
		
		textFieldNameS = new JTextField();
		textFieldNameS.setColumns(10);
		textFieldNameS.setBounds(100, 44, 118, 21);
		panelStudent.add(textFieldNameS);
		
		JLabel label_20 = new JLabel("\u59D3\u540D\uFF1A");
		label_20.setBounds(10, 43, 59, 23);
		panelStudent.add(label_20);
		
		JLabel label_21 = new JLabel("\u6027\u522B\uFF1A");
		label_21.setBounds(10, 76, 59, 23);
		panelStudent.add(label_21);
		
		radioButtonMaleS = new JRadioButton("\u7537");
		radioButtonMaleS.setSelected(true);
		radioButtonMaleS.setBounds(98, 76, 47, 23);
		panelStudent.add(radioButtonMaleS);
		
		radioButtonFemaleS = new JRadioButton("\u5973");
		radioButtonFemaleS.setBounds(171, 76, 47, 23);
		panelStudent.add(radioButtonFemaleS);
		
		ButtonGroup sexS = new ButtonGroup();
		sexS.add(radioButtonMaleS);
		sexS.add(radioButtonFemaleS);
		
		JButton buttonInsertS = new JButton("\u6DFB\u52A0");
		buttonInsertS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonInsertS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if((textFieldIdS.getText()==null)||("".equals(textFieldIdS.getText()))){//textid为空才能添加新数据
					String name = textFieldNameS.getText();
					String sex = null;
					if(radioButtonMaleS.isSelected()){
						sex = "男";
					}else{
						sex = "女";
					}
					String phone = textFieldPhoneS.getText();
					String email = textFieldEmailS.getText();
					String clazz = textFieldClassS.getText();
					String departp = comboBoxDepartpS.getSelectedItem().toString();
					String departc = comboBoxDepartcS.getSelectedItem().toString();
					
					String errMess = Validation.getStudentErrorMess(name, phone, email, clazz);//返回的错误信息
					if("".equals(errMess)){
						
						try {
							Student s = null;
							if(isNewPic){//若上传了图片则向数据库添加，否则不添加
								s = new Student(name, sex, phone, email, departp, departc, Integer.parseInt(clazz), new FileInputStream(new File(path)));
							}else{
								s = new Student(name, sex, phone, email, departp, departc, Integer.parseInt(clazz));
							}
							
							PersonDAOFactory.getStudentProxy().doCreate(s);
							isNewPic = false;
							JOptionPane.showMessageDialog(null, "添加成功", "添加成功",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(3);
							loadInfoIntoTable(3);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, "失败，已存在", "添加失败", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					doClearInfo(3);
					JOptionPane.showMessageDialog(null, "请不要插入已存在信息");
				}
				
			}
		});
		buttonInsertS.setBounds(454, 61, 93, 23);
		panelStudent.add(buttonInsertS);
		
		JButton buttonUpdateS = new JButton("\u4FEE\u6539");
		buttonUpdateS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonUpdateS.addMouseListener(new MouseAdapter() {//学生基本信息：改
			@Override
			public void mouseClicked(MouseEvent e) {
				String sid = textFieldIdS.getText();
				if(!("".equals(sid))){
					String name = textFieldNameS.getText();
					String sex = null;
					if(radioButtonMaleS.isSelected()){
						sex = "男";
					}else{
						sex = "女";
					}
					String phone = textFieldPhoneS.getText();
					String email = textFieldEmailS.getText();
					String clazz = textFieldClassS.getText();
					String departp = comboBoxDepartpS.getSelectedItem().toString();
					String departc = comboBoxDepartcS.getSelectedItem().toString();
					InputStream is = null;
					try {
						if(path!=null){
							is = new FileInputStream(new File(path));
						}
						
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					}
					
					String errMess = Validation.getStudentErrorMess(name, phone, email, clazz);//返回的错误信息
					if("".equals(errMess)){
						try {
							Student t = null;
							if(isNewPic){//有新照片调用update，没有新照片调用UpdateWithoutPicture
								t = new Student(Integer.parseInt(sid), name, sex, phone, email, departp, departc, Integer.parseInt(clazz), is);
								PersonDAOFactory.getStudentProxy().doUpdate(t);
								System.out.print(isNewPic);
							}else{
								t = new Student(Integer.parseInt(sid), name, sex, phone, email, departp, departc, Integer.parseInt(clazz));
								PersonDAOFactory.getStudentProxy().doUpdateWithoutPicture(t);
								System.out.print(isNewPic);
							}
							isNewPic = false;
							JOptionPane.showMessageDialog(null, "修改成功", "修改成功",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(3);
							loadInfoIntoTable(3);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "请先选择要修改的记录", "修改成功",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonUpdateS.setBounds(454, 94, 93, 23);
		panelStudent.add(buttonUpdateS);
		
		JLabel label_23 = new JLabel("\u9662\u7CFB\uFF1A");
		label_23.setBounds(10, 109, 59, 23);
		panelStudent.add(label_23);
		
		JLabel label_25 = new JLabel("\u73ED\u7EA7\uFF1A");
		label_25.setBounds(10, 171, 72, 23);
		panelStudent.add(label_25);
		
		textFieldClassS = new JTextField();
		textFieldClassS.setColumns(10);
		textFieldClassS.setBounds(100, 172, 118, 21);
		panelStudent.add(textFieldClassS);
		
		JButton buttonDeleteS = new JButton("\u5220\u9664");
		buttonDeleteS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sid = textFieldIdS.getText();
				if(!("".equals(sid))){
					try {
						PersonDAOFactory.getStudentProxy().doDelete(Integer.parseInt(sid));
						isNewPic = false;
						JOptionPane.showMessageDialog(null, "删除成功", "删除成功",JOptionPane.INFORMATION_MESSAGE);
						doClearInfo(3);
						loadInfoIntoTable(3);
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "请先选择要删除的记录", "删除失败",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonDeleteS.setBounds(454, 127, 93, 23);
		panelStudent.add(buttonDeleteS);
		
		JPanel panelAllInfoS = new JPanel();
		panelAllInfoS.setBounds(10, 260, 688, 141);
		panelStudent.add(panelAllInfoS);
		
		panelAllInfoS.add(new JScrollPane(tableS));
		/*JScrollPane scrollPane = new JScrollPane();
		panelAllInfoS.add(scrollPane);
	    scrollPane.setViewportView(tableS);*/
	    tableS = new MyTableModelS();
		tableS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		
		
		tableS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//获取点击行的每个数据放到text
				textFieldIdS.setText(dtmS.getValueAt(tableS.getSelectedRows()[0], 0).toString());
				textFieldNameS.setText(dtmS.getValueAt(tableS.getSelectedRows()[0], 1).toString());
				if("男".equals(dtmS.getValueAt(tableS.getSelectedRows()[0], 2).toString())){
					radioButtonMaleS.setSelected(true);
					radioButtonFemaleS.setSelected(false);
				}else{
					radioButtonMaleS.setSelected(false);
					radioButtonFemaleS.setSelected(true);
				}
				textFieldPhoneS.setText(dtmS.getValueAt(tableS.getSelectedRows()[0], 3).toString());
				textFieldEmailS.setText(dtmS.getValueAt(tableS.getSelectedRows()[0], 4).toString());
				textFieldClassS.setText(dtmS.getValueAt(tableS.getSelectedRows()[0], 7).toString());
				comboBoxDepartpS.setSelectedItem(dtmS.getValueAt(tableS.getSelectedRows()[0], 5).toString());
				comboBoxDepartcS.setSelectedItem(dtmS.getValueAt(tableS.getSelectedRows()[0], 6).toString());
				
				InputStream is = (InputStream) dtmS.getValueAt(tableS.getSelectedRows()[0], 8);	//读出输入流
				/*ByteArrayOutputStream baos2 = (ByteArrayOutputStream)picMap.get(dtmS.getValueAt(tableS.getSelectedRows()[0], 6).toString());
				InputStream is = new ByteArrayInputStream(baos2.toByteArray());  */
				//BufferedInputStream bis = new BufferedInputStream(is);
				try{
					/*ImageIcon noicon = null;//先清空照片
					lblNewLabelPicture.setIcon(noicon);*/
					
					byte[] pic = new byte[66560];//mysql blob最大值
					is.read(pic);//先读到byte数组，再封装到imageicon
					
					icon = new ImageIcon(pic); 
					
					lblNewLabelPicture.setIcon(icon);
					loadInfoIntoTable(3);//此方法可以实现jtable刷新从头读inputstream
				}catch(Exception e2){
					
				}
				
			}
		});
		panelAllInfoS.add(tableS);
		tableS.isCellEditable(0,0);
		String[] tableHeadsS = new String[]{"编号","姓名","性别","电话","邮箱","院","系","班级","照片"}; //列标识符
		dtmS = (DefaultTableModel)tableS.getModel(); //模型实例
		dtmS.setColumnIdentifiers(tableHeadsS);//设置模型中的列标识符。
		
		
		JLabel label_26 = new JLabel("\u5168\u90E8\u5B66\u751F\u4FE1\u606F\uFF1A");
		label_26.setBounds(10, 228, 183, 32);
		panelStudent.add(label_26);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(283, 10, 130, 149);
		panelStudent.add(panel_3);
		panel_3.setLayout(null);
		
		lblNewLabelPicture = new JLabel("");
		lblNewLabelPicture.setBounds(10, 10, 110, 129);
		panel_3.add(lblNewLabelPicture);
		
		comboBoxDepartpS = new JComboBox();
		comboBoxDepartpS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadComboxDepartc(comboBoxDepartpS.getSelectedIndex(), 2);
			}
		});
		comboBoxDepartpS.setBounds(100, 108, 118, 21);
		panelStudent.add(comboBoxDepartpS);
		
		comboBoxDepartcS = new JComboBox();
		comboBoxDepartcS.setBounds(100, 141, 118, 21);
		panelStudent.add(comboBoxDepartcS);
		
		JButton buttonRetrieveS = new JButton("\u67E5\u627E");
		buttonRetrieveS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonRetrieveS.addMouseListener(new MouseAdapter() {//查找对应编号学生
			@Override
			public void mouseClicked(MouseEvent e) {
				String findId = JOptionPane.showInputDialog("请输入要查找的编号");
				if(findId==null){
					return;
				}
				if(findId.matches("\\d{8}")){
					try {
						Student findS = (Student) PersonDAOFactory.getStudentProxy().findById(Integer.parseInt(findId));
						if(findS!=null){
							textFieldIdS.setText(findS.getId()+"");
							textFieldNameS.setText(findS.getName());
							if(findS.getSex().equals("男")){
								radioButtonMaleS.setSelected(true);
								radioButtonFemaleS.setSelected(false);
							}else{
								radioButtonMaleS.setSelected(false);
								radioButtonFemaleS.setSelected(true);
							}
							textFieldPhoneS.setText(findS.getPhone());
							textFieldEmailS.setText(findS.getEmail());
							textFieldClassS.setText(findS.getClazz()+"");
							comboBoxDepartpS.setSelectedItem(findS.getDepartp());
							comboBoxDepartpS.setSelectedItem(findS.getDepartc());
							InputStream is = findS.getPicture();
							byte[] pic = new byte[66560];//mysql blob最大值
							try{
								is.read(pic);//先读到byte数组，再封装到imageicon
								icon = new ImageIcon(pic); 
								lblNewLabelPicture.setIcon(icon);
								
							}catch(Exception e2){
								
							}
							isNewPic = false;
						}else{
							JOptionPane.showMessageDialog(null, "没找到对应编号的学生", "查找失败",JOptionPane.INFORMATION_MESSAGE);
						}
					
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "没找到对应编号的学生", "查找失败",JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "没找到对应编号的学生", "查找失败",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonRetrieveS.setBounds(454, 28, 93, 23);
		panelStudent.add(buttonRetrieveS);
		
		JButton buttonClearS = new JButton("\u6E05\u7A7A");
		buttonClearS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doClearInfo(3);
			}
		});
		buttonClearS.setBounds(454, 160, 93, 23);
		panelStudent.add(buttonClearS);
		
		JButton buttonUploadPictureS = new JButton("\u4E0A\u4F20\u7167\u7247");
		buttonUploadPictureS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonUploadPictureS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser=new JFileChooser();//文件保存对话框
				chooser.setCurrentDirectory(new File("pic"));//打开默认路径，当前项目pic目录下
				 if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
					 File sf=chooser.getSelectedFile();
					 String prefix = sf.getName().substring(sf.getName().lastIndexOf(".")+1);//获取文件后缀名,如：jpg，+代表从“.”后算起
					 if("jpg".equals(prefix)||"jpeg".equals(prefix)||"bmp".equals(prefix)){
						 path = sf.getPath();
						 if(sf.length()>66560){
							 JOptionPane.showMessageDialog(null, "图片不能大于64k");
							 return;
						 }
					 }else{
						 JOptionPane.showMessageDialog(null, "请选择普通图片类型");
						 return;
					 }
						 
					 
				 }
				 if(path != null){
					 Icon icon=new ImageIcon(path); 
					 lblNewLabelPicture.setIcon(icon);
					 isNewPic = true;
				 }
			}
		});
		buttonUploadPictureS.setBounds(305, 171, 93, 23);
		panelStudent.add(buttonUploadPictureS);
		
		panelAdmin = new JPanel();
		tabbedPaneInfo.addTab("管理员基本信息", null, panelAdmin, null);
		panelAdmin.setLayout(null);
		
		JLabel label_22 = new JLabel("\u7F16\u53F7\uFF1A");
		label_22.setBounds(10, 10, 59, 23);
		panelAdmin.add(label_22);
		
		textFieldIdAd = new JTextField();
		textFieldIdAd.setEnabled(false);
		textFieldIdAd.setColumns(10);
		textFieldIdAd.setBounds(100, 11, 118, 21);
		panelAdmin.add(textFieldIdAd);
		
		JLabel label_24 = new JLabel("\u624B\u673A\u53F7\u7801\uFF1A");
		label_24.setBounds(10, 109, 72, 23);
		panelAdmin.add(label_24);
		
		textFieldPhoneAd = new JTextField();
		textFieldPhoneAd.setColumns(10);
		textFieldPhoneAd.setBounds(100, 110, 183, 21);
		panelAdmin.add(textFieldPhoneAd);
		
		textFieldEmailAd = new JTextField();
		textFieldEmailAd.setColumns(10);
		textFieldEmailAd.setBounds(100, 143, 183, 21);
		panelAdmin.add(textFieldEmailAd);
		
		JLabel label_27 = new JLabel("\u7535\u5B50\u90AE\u7BB1\uFF1A");
		label_27.setBounds(10, 142, 72, 23);
		panelAdmin.add(label_27);
		
		textFieldNameAd = new JTextField();
		textFieldNameAd.setColumns(10);
		textFieldNameAd.setBounds(100, 44, 118, 21);
		panelAdmin.add(textFieldNameAd);
		
		JLabel label_28 = new JLabel("\u59D3\u540D\uFF1A");
		label_28.setBounds(10, 43, 59, 23);
		panelAdmin.add(label_28);
		
		JLabel label_29 = new JLabel("\u6027\u522B\uFF1A");
		label_29.setBounds(10, 76, 59, 23);
		panelAdmin.add(label_29);
		
		radioButtonMaleAd = new JRadioButton("\u7537");
		radioButtonMaleAd.setSelected(true);
		radioButtonMaleAd.setBounds(98, 76, 47, 23);
		panelAdmin.add(radioButtonMaleAd);
		
		radioButtonFemaleAd = new JRadioButton("\u5973");
		radioButtonFemaleAd.setBounds(171, 76, 47, 23);
		panelAdmin.add(radioButtonFemaleAd);
		
		ButtonGroup sexAd = new ButtonGroup();
		sexAd.add(radioButtonMaleAd);
		sexAd.add(radioButtonFemaleAd);
		
		buttonInsertAd = new JButton("\u6DFB\u52A0");
		buttonInsertAd.addMouseListener(new MouseAdapter() {//管理员基本信息：增
			@Override
			public void mouseClicked(MouseEvent e) {
				if((textFieldIdAd.getText()==null)||("".equals(textFieldIdAd.getText()))){//textid为空才能添加新数据
					String name = textFieldNameAd.getText();
					String sex = null;
					if(radioButtonMaleAd.isSelected()){
						sex = "男";
					}else{
						sex = "女";
					}
					int authorization;
					if(radioButtonSupreme.isSelected()){
						authorization = 1;
					}else{
						authorization = 2;
					}
					String phone = textFieldPhoneAd.getText();
					String email = textFieldEmailAd.getText();
					
					String errMess = Validation.getAdvisorOrAdminErrorMess(name, phone, email);//返回的错误信息
					if("".equals(errMess)){
						Admin adm = new Admin(name, sex, phone, email, authorization);
						try {
							PersonDAOFactory.getAdminProxy().doCreate(adm);
							doClearInfo(4);
							JOptionPane.showMessageDialog(null, "添加成功", "添加成功",JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, "失败，已存在", "添加失败", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					doClearInfo(4);
					JOptionPane.showMessageDialog(null, "请不要插入已存在信息");
				}
				
			}
		});
		buttonInsertAd.setBounds(366, 43, 88, 23);
		panelAdmin.add(buttonInsertAd);
		
		JButton buttonUpdateAd = new JButton("\u4FEE\u6539");
		buttonUpdateAd.addMouseListener(new MouseAdapter() {//管理员基本信息：改
			@Override
			public void mouseClicked(MouseEvent e) {
				String advid = textFieldIdAd.getText();
				if(!("".equals(advid))){
					String name = textFieldNameAd.getText();
					String sex = null;
					if(radioButtonMaleAd.isSelected()){
						sex = "男";
					}else{
						sex = "女";
					}
					int authorization;
					if(radioButtonSupreme.isSelected()){
						authorization = 1;
					}else{
						authorization = 2;
					}
					String phone = textFieldPhoneAd.getText();
					String email = textFieldEmailAd.getText();
					
					String errMess = Validation.getAdvisorOrAdminErrorMess(name, phone, email);//返回的错误信息
					if("".equals(errMess)){
						Admin adv = new Admin(Integer.parseInt(advid), name, sex, phone, email, authorization);
						try {
							PersonDAOFactory.getAdminProxy().doUpdate(adv);
							JOptionPane.showMessageDialog(null, "修改成功", "修改成功",JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "输入有误", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "请先选择要修改的记录", "修改成功",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonUpdateAd.setBounds(366, 76, 88, 23);
		panelAdmin.add(buttonUpdateAd);
		
		buttonDeleteAd = new JButton("\u5220\u9664");
		buttonDeleteAd.addMouseListener(new MouseAdapter() {//管理员基本信息：删
			@Override
			public void mouseClicked(MouseEvent e) {
				String advid = textFieldIdAd.getText();
				if(!("".equals(advid))){
					try {
						PersonDAOFactory.getAdminProxy().doDelete(Integer.parseInt(advid));
						doClearInfo(4);
						JOptionPane.showMessageDialog(null, "删除成功", "删除成功",JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "请先选择要删除的记录", "删除失败",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonDeleteAd.setBounds(366, 110, 88, 23);
		panelAdmin.add(buttonDeleteAd);
		
		JButton button = new JButton("\u4FEE\u6539\u5BC6\u7801");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!"".equals(textFieldIdAd.getText())){
					new PasswordFrame(textFieldIdAd.getText());
				}else{
					JOptionPane.showMessageDialog(null, "请先选择一条记录");
				}
			}
		});
		button.setBounds(366, 142, 88, 23);
		panelAdmin.add(button);
		
		buttonRetrieveAd = new JButton("\u67E5\u627E");
		buttonRetrieveAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonRetrieveAd.addMouseListener(new MouseAdapter() {//查找对应编号的管理员
			@Override
			public void mouseClicked(MouseEvent e) {
				String findId = JOptionPane.showInputDialog("请输入要查找的编号");
				if(findId==null){
					return;
				}
				if(findId.matches("\\d{8}")){
					try {
						Admin findAdm = (Admin) PersonDAOFactory.getAdminProxy().findById(Integer.parseInt(findId));
						if(findAdm!=null){
							textFieldIdAd.setText(findAdm.getId()+"");
							textFieldNameAd.setText(findAdm.getName());
							if(findAdm.getSex().equals("男")){
								radioButtonMaleAd.setSelected(true);
								radioButtonFemaleAd.setSelected(false);
							}else{
								radioButtonMaleAd.setSelected(false);
								radioButtonFemaleAd.setSelected(true);
							}
							textFieldPhoneAd.setText(findAdm.getPhone());
							textFieldEmailAd.setText(findAdm.getEmail());
							if(findAdm.getAuthorization()==1){
								radioButtonSupreme.setSelected(true);
								radioButtonNormal.setSelected(false);
								radioButtonSupreme.setEnabled(false);//如果最高管理员找到自己，不能修改自己的权限
								radioButtonNormal.setEnabled(false);
							}else{
								radioButtonSupreme.setSelected(false);
								radioButtonNormal.setSelected(true);
							}
							buttonsNormalAdminCantUse();
							
						}else{
							JOptionPane.showMessageDialog(null, "没找到对应编号的管理员", "查找失败",JOptionPane.INFORMATION_MESSAGE);
						}
					
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "没找到对应编号的管理员", "查找失败",JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "没找到对应编号的管理员", "查找失败",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonRetrieveAd.setBounds(366, 10, 88, 23);
		panelAdmin.add(buttonRetrieveAd);
		
		JLabel label_8 = new JLabel("\u6743\u9650\uFF1A");
		label_8.setBounds(10, 175, 72, 23);
		panelAdmin.add(label_8);
		
		radioButtonSupreme = new JRadioButton("\u6700\u9AD8");
		radioButtonSupreme.setSelected(true);
		radioButtonSupreme.setBounds(98, 175, 59, 23);
		panelAdmin.add(radioButtonSupreme);
		
		radioButtonNormal = new JRadioButton("\u666E\u901A");
		radioButtonNormal.setBounds(171, 175, 59, 23);
		panelAdmin.add(radioButtonNormal);
		
		ButtonGroup authAd = new ButtonGroup();
		authAd.add(radioButtonSupreme);
		authAd.add(radioButtonNormal);
		
		JButton buttonClearAd = new JButton("\u6E05\u7A7A");
		buttonClearAd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doClearInfo(4);
			}
		});
		buttonClearAd.setBounds(366, 175, 88, 23);
		panelAdmin.add(buttonClearAd);
		
		panelAllInfoAd = new JPanel();
		panelAllInfoAd.setBounds(10, 240, 460, 92);
		panelAdmin.add(panelAllInfoAd);
		
		tableAd = new MyTableModelAd();
		tableAd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldIdAd.setText(dtmAd.getValueAt(tableAd.getSelectedRows()[0], 0).toString());
				textFieldNameAd.setText(dtmAd.getValueAt(tableAd.getSelectedRows()[0], 1).toString());
				if("男".equals(dtmAd.getValueAt(tableAd.getSelectedRows()[0], 2).toString())){
					radioButtonMaleAd.setSelected(true);
					radioButtonFemaleAd.setSelected(false);
				}else{
					radioButtonMaleAd.setSelected(false);
					radioButtonFemaleAd.setSelected(true);
				}
				textFieldPhoneAd.setText(dtmAd.getValueAt(tableAd.getSelectedRows()[0], 3).toString());
				textFieldEmailAd.setText(dtmAd.getValueAt(tableAd.getSelectedRows()[0], 4).toString());
				if(Integer.parseInt(dtmAd.getValueAt(tableAd.getSelectedRows()[0], 5).toString())==1){
					radioButtonSupreme.setSelected(true);
					radioButtonNormal.setSelected(false);
					radioButtonSupreme.setEnabled(false);
					radioButtonNormal.setEnabled(false);
				}else{
					radioButtonSupreme.setSelected(false);
					radioButtonNormal.setSelected(true);
					radioButtonSupreme.setEnabled(true);
					radioButtonNormal.setEnabled(true);
				}
			}
		});
		panelAllInfoAd.add(tableAd);
		tableT.isCellEditable(0,0);
		String[] tableHeadsAd = new String[]{"编号","姓名","性别","电话","邮箱","权限"}; //列标识符
		dtmAd = (DefaultTableModel)tableAd.getModel(); //模型实例
		dtmAd.setColumnIdentifiers(tableHeadsAd);//设置模型中的列标识符。
		
		loadInfoIntoTable(4);
		
		labelAllInfoAd = new JLabel("\u5168\u90E8\u6559\u5E08\u4FE1\u606F\uFF1A");
		labelAllInfoAd.setBounds(10, 208, 432, 32);
		panelAdmin.add(labelAllInfoAd);
		
		/*所有菜单项单机事件
		 * 参数1：是否显示基本信息面板 参数2：控制基本信息四个面板显示 参数3：控制考勤面板显示
		 * */
		menuItemTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowOrHide(true, 0, -1);
			}
		});
		menuItemAdvisor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowOrHide(true, 1, -1);
			}
		});
		menuItemStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowOrHide(true, 2, -1);
			}
		});
		menuItemAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowOrHide(true, 3, -1);
			}
		});
		menuItemAttendanceCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowOrHide(false, -1, 0);
			}
		});
		menuItemAskForLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowOrHide(false, -1, 1);
			}
		});
		munuItemAttendanceInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowOrHide(false, -1, 2);
			}
		});
		
		
	}
	/*菜单项对应不同显示面板
	 * 参数1：是否显示基本信息面板 参数2：控制基本信息四个面板显示 参数3：控制考勤面板显示
	 * */
	public void ShowOrHide(boolean isInfo, int infoType, int attendanceType){
		
		//基本信息面板
		if(isInfo){
			panelInfo.setVisible(true);
			panelAttendance.setVisible(false);
		}
		else{
			panelInfo.setVisible(false);
			panelAttendance.setVisible(true);
		}
		if((!isSupremeAdm)&&(!isNormalAdm)){//不是管理员
			//基本信息面板的4个子面板
			if(infoType==0){
				tabbedPaneInfo.setSelectedIndex(0);
				for(int i = 0; i<panelTeacher.getComponentCount();i++){
					panelTeacher.getComponent(i).setEnabled(true);
				}
				
				for(int i = 0; i<panelAdvisor.getComponentCount();i++){
					panelAdvisor.getComponent(i).setEnabled(false);
				}
				for(int i = 0; i<panelStudent.getComponentCount();i++){
					panelStudent.getComponent(i).setEnabled(false);
				}
				for(int i = 0; i<panelAdmin.getComponentCount();i++){
					panelAdmin.getComponent(i).setEnabled(false);
				}
			}
			else if(infoType==1){
				tabbedPaneInfo.setSelectedIndex(1);
				for(int i = 0; i<panelTeacher.getComponentCount();i++){
					panelTeacher.getComponent(i).setEnabled(false);
				}
				
				for(int i = 0; i<panelAdvisor.getComponentCount();i++){
					panelAdvisor.getComponent(i).setEnabled(true);
				}
				for(int i = 0; i<panelStudent.getComponentCount();i++){
					panelStudent.getComponent(i).setEnabled(false);
				}
				for(int i = 0; i<panelAdmin.getComponentCount();i++){
					panelAdmin.getComponent(i).setEnabled(false);
				}
			}
			else if(infoType==2){
				tabbedPaneInfo.setSelectedIndex(2);
				for(int i = 0; i<panelTeacher.getComponentCount();i++){
					panelTeacher.getComponent(i).setEnabled(false);
				}
				
				for(int i = 0; i<panelAdvisor.getComponentCount();i++){
					panelAdvisor.getComponent(i).setEnabled(false);
				}
				for(int i = 0; i<panelStudent.getComponentCount();i++){
					panelStudent.getComponent(i).setEnabled(true);
				}
				for(int i = 0; i<panelAdmin.getComponentCount();i++){
					panelAdmin.getComponent(i).setEnabled(false);
				}
			}else{
				tabbedPaneInfo.setSelectedIndex(3);
				for(int i = 0; i<panelTeacher.getComponentCount();i++){
					panelTeacher.getComponent(i).setEnabled(false);
				}
				
				for(int i = 0; i<panelAdvisor.getComponentCount();i++){
					panelAdvisor.getComponent(i).setEnabled(false);
				}
				for(int i = 0; i<panelStudent.getComponentCount();i++){
					panelStudent.getComponent(i).setEnabled(false);
				}
				for(int i = 0; i<panelAdmin.getComponentCount();i++){
					panelAdmin.getComponent(i).setEnabled(true);
				}
			}
			
			//点名请假考勤面板，老师和辅导员都可以查看考勤记录
			if(attendanceType==0){
				tabbedPaneAttendance.setSelectedIndex(0);
				for(int i = 0; i<panelAttendanceCheck.getComponentCount();i++){
					panelAttendanceCheck.getComponent(i).setEnabled(true);
				}
				
				for(int i = 0; i<panelAskForLeave.getComponentCount();i++){
					panelAskForLeave.getComponent(i).setEnabled(false);
				}
				for(int i = 0; i<panelAttendanceInfo.getComponentCount();i++){
					panelAttendanceInfo.getComponent(i).setEnabled(true);
				}
			}else if(attendanceType==1){
				tabbedPaneAttendance.setSelectedIndex(1);
				for(int i = 0; i<panelAttendanceCheck.getComponentCount();i++){
					panelAttendanceCheck.getComponent(i).setEnabled(false);
				}
				
				for(int i = 0; i<panelAskForLeave.getComponentCount();i++){
					panelAskForLeave.getComponent(i).setEnabled(true);
				}
				for(int i = 0; i<panelAttendanceInfo.getComponentCount();i++){
					panelAttendanceInfo.getComponent(i).setEnabled(true);
				}
			}else{
				tabbedPaneAttendance.setSelectedIndex(2);
				if(isTeac){
					for(int i = 0; i<panelAttendanceCheck.getComponentCount();i++){
						panelAttendanceCheck.getComponent(i).setEnabled(true);
					}
					
					for(int i = 0; i<panelAskForLeave.getComponentCount();i++){
						panelAskForLeave.getComponent(i).setEnabled(false);
					}
				}
				if(isAdv){
					for(int i = 0; i<panelAttendanceCheck.getComponentCount();i++){
						panelAttendanceCheck.getComponent(i).setEnabled(false);
					}
					
					for(int i = 0; i<panelAskForLeave.getComponentCount();i++){
						panelAskForLeave.getComponent(i).setEnabled(true);
					}
				}
				for(int i = 0; i<panelAttendanceInfo.getComponentCount();i++){
					panelAttendanceInfo.getComponent(i).setEnabled(true);
				}
			}
		}
		else{//是管理员：信息面板均可显示，考勤面板均不显示
			if(infoType==0){//显示对应基本信息面板
				tabbedPaneInfo.setSelectedIndex(0);
			}else if(infoType==1){
				tabbedPaneInfo.setSelectedIndex(1);
			}else if(infoType==2){
				tabbedPaneInfo.setSelectedIndex(2);
			}else{
				tabbedPaneInfo.setSelectedIndex(3);
			}
			for(int i = 0; i<panelTeacher.getComponentCount();i++){
				panelTeacher.getComponent(i).setEnabled(true);
			}
			
			for(int i = 0; i<panelAdvisor.getComponentCount();i++){
				panelAdvisor.getComponent(i).setEnabled(true);
			}
			for(int i = 0; i<panelStudent.getComponentCount();i++){
				panelStudent.getComponent(i).setEnabled(true);
			}
			for(int i = 0; i<panelAdmin.getComponentCount();i++){
				panelAdmin.getComponent(i).setEnabled(true);
			}
			for(int i = 0; i<panelAttendanceCheck.getComponentCount();i++){
				panelAttendanceCheck.getComponent(i).setEnabled(false);
			}
			
			for(int i = 0; i<panelAskForLeave.getComponentCount();i++){
				panelAskForLeave.getComponent(i).setEnabled(false);
			}
			for(int i = 0; i<panelAttendanceInfo.getComponentCount();i++){
				panelAttendanceInfo.getComponent(i).setEnabled(false);
			}
			buttonsNormalAdminCantUse();//如果是一般管理员，几个单选、按钮不能用；若不在此设置，选择menu后就能用了
		}
	
		
		
		textFieldIdT.setEnabled(false);
		textFieldIdA.setEnabled(false);
		textFieldIdS.setEnabled(false);
		textFieldIdAd.setEnabled(false);
		textFieldIdAc.setEnabled(false);
		textFieldNameAc.setEnabled(false);
	}
	/*清空基本信息*/
	private void doClearInfo(int type){
		if(type==1){
			textFieldIdT.setText("");
			textFieldNameT.setText("");
			radioButtonMaleT.setSelected(true);
			radioButtonFemaleT.setSelected(false);
			textFieldPhoneT.setText("");
			textFieldEmailT.setText("");
			textFieldCourseT.setText("");
			textFieldClassT.setText("");
			comboBoxDepartpT.setSelectedIndex(0);;
			comboBoxDepartcT.setSelectedIndex(0);
		}else if(type==2){
			textFieldIdA.setText("");
			textFieldNameA.setText("");
			radioButtonMaleA.setSelected(true);
			radioButtonFemaleA.setSelected(false);
			textFieldPhoneA.setText("");
			textFieldEmailA.setText("");
			comboBoxDepartpA.setSelectedIndex(0);
		}else if(type==3){
			textFieldIdS.setText("");
			textFieldNameS.setText("");
			radioButtonMaleS.setSelected(true);
			radioButtonFemaleS.setSelected(false);
			textFieldPhoneS.setText("");
			textFieldEmailS.setText("");
			textFieldClassS.setText("");
			comboBoxDepartpS.setSelectedIndex(0);
			comboBoxDepartcS.setSelectedIndex(0);
			ImageIcon noicon = null;
			lblNewLabelPicture.setIcon(noicon);
			isNewPic = false;
		}else{
			textFieldIdAd.setText("");
			textFieldNameAd.setText("");
			radioButtonMaleAd.setSelected(true);
			radioButtonFemaleAd.setSelected(false);
			radioButtonSupreme.setSelected(false);
			radioButtonNormal.setSelected(true);
			textFieldPhoneAd.setText("");
			textFieldEmailAd.setText("");
		}
	}
}


@SuppressWarnings("serial")
class MyTableModel extends JTable{
	@Override 
	public boolean isCellEditable(int row, int column) {
		return false; 
	}
}
@SuppressWarnings("serial")
class MyTableModelA extends JTable{
	@Override 
	public boolean isCellEditable(int row, int column) {
		return false; 
	}
}
@SuppressWarnings("serial")
class MyTableModelS extends JTable{
	@Override 
	public boolean isCellEditable(int row, int column) {
		return false; 
	}
}
@SuppressWarnings("serial")
class MyTableModelAd extends JTable{
	@Override 
	public boolean isCellEditable(int row, int column) {
		return false; 
	}
}

