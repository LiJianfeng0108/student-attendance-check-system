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
	private String path = null;//ѧ����Ƭ·��
	
	private Map picMap = null;
	private ByteArrayOutputStream baos = null;
	private JTextField textFieldDateAfl;
	
	private List stuAcList = null;
	private ListIterator lit = null;//������ǰ�����
	
	/**
	 * Create the application.
	 */
	public MainFrame(int id) {//�ǹ���Ա�����ڹ��ܣ�����Ա�������л�����Ϣ��CRUD�ȣ�����ֻ�й���Ա��½ʱ������Լ�����Ϣ���ʴ������Աid
		//System.out.print(id);
		isSupremeAdm = Authorization.getSupremAdmAuthorization();
		isNormalAdm = Authorization.getNormalAdmAuthorization();
		isTeac = Authorization.getTeacAuthorization();
		isAdv = Authorization.getAdvAuthorization();
		loadConcernedCourseOrDepartp(id);//���ڹ�����ʦ��Ҫ��Ӧ��Ŀ������Ա��Ҫ��ӦԺ��
		initialize();
		setAuth();//��ͬ�û���¼��ͬ����Ȩ��
		if(isSupremeAdm||isNormalAdm){//����Ա��½���ʼ��������Ϣ����
			loadAdminInfo(id);
		}
		
		loadComboxDepartp(1);//����xml������������depart����
		loadComboxDepartp(2);
		loadComboxDepartp(3);
	}
	/*���ڹ���ʱҪͨ����ʦ���̵Ŀγ̻򸨵�Ա���ڵ�Ժ���в���*/
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
	/*����xml������������depart������1����ʦ��2������Ա��3��ѧ��*/
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
	/*����xml����������depart��������;type=1:��ʦ��Ϣ����departcˢ�£�2:ѧ��*/
	private void loadComboxDepartc(int i, int type){
		//������ϴμ��ص�
		if(type==1){
			comboBoxDepartcT.removeAllItems();
		}
		if(type==2){
			comboBoxDepartcS.removeAllItems();
		}
		List departc = new ArrayList();
		XMLAnalysis anls = new XMLAnalysis();
		departc = anls.getDepartc(i);//��ȡ��i��departp�µ�departc
		for(Object x:departc){
			if(type==1){
				comboBoxDepartcT.addItem(x);
			}
			if(type==2){
				comboBoxDepartcS.addItem(x);
			}
			
		}
	}
	/*����menu����ͬ�û���¼��ͬ����Ȩ��*/
	private void setAuth(){
		if(isSupremeAdm){//���Ȩ�޵Ĺ���Ա���ܲ��� ���޸���������Ա���κι���Ա�������޸��Լ���Ȩ�ޣ�������ߵĿ����޸������˵�
			menuBar.getMenu(0).setEnabled(true);
			menuBar.getMenu(1).setEnabled(false);
			menuBar.getMenu(2).setEnabled(false);
			menuBar.getMenu(3).setEnabled(true);
			ShowOrHide(true, 0, -1);
			labelAllInfoAd.setVisible(true);
			panelAllInfoAd.setVisible(true);
			buttonsNormalAdminCantUse();//��������Ȩ�޹���Ա��ֻ�ǲ����޸��Լ���Ȩ��
			loadInfoIntoTable(1);//������Ϣ��jtable��
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
			buttonsNormalAdminCantUse();//�����һ�����Ա��������ť�͵�ѡ������
			loadInfoIntoTable(1);//������Ϣ��jtable��
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
	/*���Ȩ�޺�һ��Ȩ�޵Ĺ���Ա����ѡ����ťʹ�õ�Ȩ��*/
	private void buttonsNormalAdminCantUse(){
		if(isSupremeAdm){
			buttonRetrieveAd.setEnabled(true);
			buttonInsertAd.setEnabled(true);
			buttonDeleteAd.setEnabled(true);
			if(radioButtonNormal.isSelected()){//�����Ȩ�޵Ĺ���Ա�����ҵ�ǰ��Ϣ���Լ��Ĳ��ܸ�Ȩ��
				radioButtonSupreme.setEnabled(true);
				radioButtonNormal.setEnabled(true);
			}else{//�����Ȩ�޵Ĺ���Ա�����ǵ�ǰ��Ϣ�����Լ��Ŀ��Ը�����Ȩ��
				radioButtonSupreme.setEnabled(false);
				radioButtonNormal.setEnabled(false);
			}
		}
		if(isNormalAdm){//һ�����Ա
			buttonRetrieveAd.setEnabled(false);
			buttonInsertAd.setEnabled(false);
			buttonDeleteAd.setEnabled(false);
			radioButtonSupreme.setEnabled(false);
			radioButtonNormal.setEnabled(false);
		}
		
	}
	/*����Ա��½���ʼ��������Ϣ����*/
	private void loadAdminInfo(int adminId){
		try {
			Admin findAdm = (Admin) PersonDAOFactory.getAdminProxy().findById(adminId);
			textFieldIdAd.setText(findAdm.getId()+"");
			textFieldNameAd.setText(findAdm.getName());
			if(findAdm.getSex().equals("��")){
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
	/*��Jtable��������*/
	private void loadInfoIntoTable(int type){//1-4Ϊ������Ϣ��5Ϊ������Ϣ
		if(type==1){
			int rowsCount = dtmT.getRowCount();//��������д��for()���Ϊÿ���Ƴ�һ�������ͱ���
			for(int i=0; i<rowsCount; i++){
				dtmT.removeRow(0);//ÿ�ζ��Ƴ���һ��
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
			int rowsCount = dtmA.getRowCount();//��������д��for()���Ϊÿ���Ƴ�һ�������ͱ���
			for(int i=0; i<rowsCount; i++){
				dtmA.removeRow(0);//ÿ�ζ��Ƴ���һ��
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
			int rowsCount = dtmS.getRowCount();//��������д��for()���Ϊÿ���Ƴ�һ�������ͱ���
			for(int i=0; i<rowsCount; i++){
				dtmS.removeRow(0);//ÿ�ζ��Ƴ���һ��
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
				
				/*byte[] pic = new byte[66560];//mysql blob���ֵ
				try {
					s.getPicture().read(pic);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//�ȶ���byte���飬�ٷ�װ��imageicon
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
			int rowsCount = dtmAd.getRowCount();//��������д��for()���Ϊÿ���Ƴ�һ�������ͱ���
			for(int i=0; i<rowsCount; i++){
				dtmAd.removeRow(0);//ÿ�ζ��Ƴ���һ��
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
			int rowsCount = dtmAttendance.getRowCount();//��������д��for()���Ϊÿ���Ƴ�һ�������ͱ���
			for(int i=0; i<rowsCount; i++){
				dtmAttendance.removeRow(0);//ÿ�ζ��Ƴ���һ��
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
			int rowsCount = dtmAttendance.getRowCount();//��������д��for()���Ϊÿ���Ƴ�һ�������ͱ���
			for(int i=0; i<rowsCount; i++){
				dtmAttendance.removeRow(0);//ÿ�ζ��Ƴ���һ��
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
				JOptionPane.showMessageDialog(null, "�������һ��ѧ��");
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
		//��Ļ������ʾ
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
				JOptionPane.showMessageDialog(null, "ѧ�����ڹ���ϵͳ"+"\r\n\t ---����:���\r\n\t --- ѧ��:201512172162", "����", JOptionPane.NO_OPTION);
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
		tabbedPaneAttendance.addTab("����", null, panelAttendanceCheck, null);
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
					
					if((findS.getName().equals(textFieldNameAc.getText()))&&(((Student) findS).getClazz()==teacClass)){//����id name class�ܲ鵽���˼�������
						
						try {
							p = new Student();
							p.setId(Integer.parseInt((textFieldIdAc.getText())));
							p.setName(textFieldNameAc.getText());
							AttendanceDAOFactory.getAttendanceProxy().doInsertForCheck(p, teacCourse);//��ӳٵ���ȱ��
							JOptionPane.showMessageDialog(null, "�ύ���");
							doCheckNextStudent();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "ʧ��");
						}
					}else{
						JOptionPane.showMessageDialog(null, "�����ڸ�ѧ��");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "�����ڸ�ѧ��");
				} 
			}
		});
		buttonInsertAc.setBounds(238, 192, 135, 23);
		panelAttendanceCheck.add(buttonInsertAc);
		
		JButton buttonUpdateAc = new JButton("\u67E5\u627E\u5E76\u6539\u4E3A\u8FDF\u5230");
		buttonUpdateAc.addMouseListener(new MouseAdapter() {//���θ�Ϊ�ٵ�
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean flag = false;
				try {
					int sid = Integer.parseInt(JOptionPane.showInputDialog("������Ҫ����ѧ����ѧ��"));
					flag = AttendanceDAOFactory.getAttendanceProxy().doUpdateFromAbToLa(sid, teacCourse);
					if(flag){
						JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
					}else{
						JOptionPane.showMessageDialog(null, "û�и�ѧ��������Ϣ");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "û�и�ѧ��������Ϣ");
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
				Timer t = new Timer();//��ʱ��
				new Thread(t).start();//���߳�
				
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
		buttonNextAc.addMouseListener(new MouseAdapter() {//������������һ��
			@Override
			public void mouseClicked(MouseEvent e) {
				doCheckNextStudent();
			}
		});
		buttonNextAc.setBounds(238, 159, 135, 23);
		panelAttendanceCheck.add(buttonNextAc);
		
		panelAskForLeave = new JPanel();
		tabbedPaneAttendance.addTab("���", null, panelAskForLeave, null);
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
			public void mouseClicked(MouseEvent e) {//�������Ϣ��ӵ����ݿ�ʱ�������������������Ŀγ���ϢҲ����
				List courseList = new ArrayList();
				Person p = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date d = null;
				try {
					Person findS = PersonDAOFactory.getStudentProxy().findById(Integer.parseInt(textFieldIdAfl.getText()));
					if((findS.getName().equals(textFieldNameAfl.getText()))&&(((Student) findS).getDepartp().equals(advDepartp))){//����id name departp�ܲ鵽���˼�������
						try{
							d = sdf.parse(textFieldDateAfl.getText());
						}catch(Exception ee){
							JOptionPane.showMessageDialog(null, "���ڸ�ʽ��yyyy-MM-dd");
							return;
						}
						try {
							p = new Student();
							p.setId(Integer.parseInt((textFieldIdAfl.getText())));
							p.setName(textFieldNameAfl.getText());							
							Calendar cal = Calendar.getInstance();
							cal.setTime(d);
							int dow = cal.get(Calendar.DAY_OF_WEEK)-1;//��ȡ���ڼ�
							//System.out.print(dow);
							switch(dow){
							case 0:for(DayOfWeek.ESun x:DayOfWeek.ESun.values()){//������ķ�������0�������գ���enum���յĿγ̼���arraylist����ͬ
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
							AttendanceDAOFactory.getAttendanceProxy().doInsertForLeave(p, courseList, d);//������
							JOptionPane.showMessageDialog(null, "��ٳɹ�");
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "ʧ��");
						}
					}else{
						JOptionPane.showMessageDialog(null, "�����ڸ�ѧ��");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "�����ڸ�ѧ��");
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
					if((findS.getName().equals(textFieldNameAfl.getText()))&&(((Student) findS).getDepartp().equals(advDepartp))){//����id name departp�ܲ鵽���˼�������
						try{
							d = sdf.parse(textFieldDateAfl.getText());
						}catch(Exception ee){
							JOptionPane.showMessageDialog(null, "���ڸ�ʽ��yyyy-MM-dd");
							return;
						}
						try {
							p = new Student();
							p.setId(Integer.parseInt((textFieldIdAfl.getText())));
							p.setName(textFieldNameAfl.getText());
													
							boolean flag = AttendanceDAOFactory.getAttendanceProxy().doDeleteForLeave(p, d);//������
							
							if(flag){
								JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
							}else{
								JOptionPane.showMessageDialog(null, "�����ڸü�¼");
							}
							
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "ʧ��");
						}
					}else{
						JOptionPane.showMessageDialog(null, "�����ڸ�ѧ��");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "�����ڸ�ѧ��");
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
		tabbedPaneAttendance.addTab("������Ϣ", null, panelAttendanceInfo, null);
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
				if(isTeac){//����ʦֻ��ʾ�ÿγ̵�ѧ��
					loadInfoIntoTable(5);
				}
				if(isAdv){//�Ǹ���Աֻ��ʾ��Ժ��ѧ��
					loadInfoIntoTable(6);
				}
			}
		});
		buttonReflash.setBounds(340, 27, 93, 23);
		panelAttendanceInfo.add(buttonReflash);
		tableAttendance.isCellEditable(0,0);
		String[] tableHeadsAttendance = new String[]{"���","����","����","�γ�","����"}; //�б�ʶ��
		dtmAttendance = (DefaultTableModel)tableAttendance.getModel(); //ģ��ʵ��
		dtmAttendance.setColumnIdentifiers(tableHeadsAttendance);//����ģ���е��б�ʶ����
		//System.out.print(isTeac +" "+ isAdv);
		
		
		
		panelInfo = new JPanel();
		panelInfo.setBounds(10, 41, 725, 445);
		frame.getContentPane().add(panelInfo);
		panelInfo.setLayout(null);
		
		tabbedPaneInfo = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneInfo.setBounds(0, 0, 713, 440);
		panelInfo.add(tabbedPaneInfo);
		
		panelTeacher = new JPanel();
		tabbedPaneInfo.addTab("��ʦ������Ϣ", null, panelTeacher, null);
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
		String[] tableHeadsT = new String[]{"���","����","�Ա�","�绰","����","Ժ","ϵ","�༶","�γ�"}; //�б�ʶ��
		dtmT = (DefaultTableModel)tableT.getModel(); //ģ��ʵ��
		dtmT.setColumnIdentifiers(tableHeadsT);//����ģ���е��б�ʶ����
		
		
		
		
		tableT.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mouseClicked(MouseEvent e) {
				//��ȡ����е�ÿ�����ݷŵ�text
				textFieldIdT.setText(dtmT.getValueAt(tableT.getSelectedRows()[0], 0).toString());
				textFieldNameT.setText(dtmT.getValueAt(tableT.getSelectedRows()[0], 1).toString());
				if("��".equals(dtmT.getValueAt(tableT.getSelectedRows()[0], 2).toString())){
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
		buttonInsertT.addMouseListener(new MouseAdapter() {//��ʦ������Ϣ����
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if((textFieldIdT.getText()==null)||("".equals(textFieldIdT.getText()))){//textidΪ�ղ������������
					String name = textFieldNameT.getText();
					String sex = null;
					if(radioButtonMaleT.isSelected()){
						sex = "��";
					}else{
						sex = "Ů";
					}
					String phone = textFieldPhoneT.getText();
					String email = textFieldEmailT.getText();
					String course = textFieldCourseT.getText();
					String clazz = textFieldClassT.getText();
					String departp = comboBoxDepartpT.getSelectedItem().toString();
					String departc = comboBoxDepartcT.getSelectedItem().toString();
					
					String errMess = Validation.getTeacherErrorMess(name, phone, email, course, clazz);//���صĴ�����Ϣ
					if("".equals(errMess)){
						Teacher t = new Teacher(name, sex, phone, email, departp, departc, Integer.parseInt(clazz), course);
						try {
							PersonDAOFactory.getTeacherProxy().doCreate(t);
							JOptionPane.showMessageDialog(null, "��ӳɹ�", "��ӳɹ�",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(1);
							loadInfoIntoTable(1);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, "ʧ�ܣ��Ѵ���", "���ʧ��", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					doClearInfo(1);
					JOptionPane.showMessageDialog(null, "�벻Ҫ�����Ѵ�����Ϣ");
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
		buttonUpdateT.addMouseListener(new MouseAdapter() {//��ʦ������Ϣ����
			@Override
			public void mouseClicked(MouseEvent e) {
				String tid = textFieldIdT.getText();
				if(!("".equals(tid))){
					String name = textFieldNameT.getText();
					String sex = null;
					if(radioButtonMaleT.isSelected()){
						sex = "��";
					}else{
						sex = "Ů";
					}
					String phone = textFieldPhoneT.getText();
					String email = textFieldEmailT.getText();
					String course = textFieldCourseT.getText();
					String clazz = textFieldClassT.getText();
					String departp = comboBoxDepartpT.getSelectedItem().toString();
					String departc = comboBoxDepartcT.getSelectedItem().toString();
					
					String errMess = Validation.getTeacherErrorMess(name, phone, email, course, clazz);//���صĴ�����Ϣ
					if("".equals(errMess)){
						Teacher t = new Teacher(Integer.parseInt(tid), name, sex, phone, email, departp, departc, Integer.parseInt(clazz), course);
						try {
							PersonDAOFactory.getTeacherProxy().doUpdate(t);
							JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "�޸ĳɹ�",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(1);
							loadInfoIntoTable(1);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "����ѡ��Ҫ�޸ĵļ�¼", "�޸ĳɹ�",JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		buttonUpdateT.setBounds(348, 139, 92, 23);
		panelTeacher.add(buttonUpdateT);
		
		JButton buttonDeleteT = new JButton("\u5220\u9664");
		buttonDeleteT.addMouseListener(new MouseAdapter() {////��ʦ������Ϣ��ɾ
			@Override
			public void mouseClicked(MouseEvent e) {
				String tid = textFieldIdT.getText();
				if(!("".equals(tid))){
					try {
						PersonDAOFactory.getTeacherProxy().doDelete(Integer.parseInt(tid));
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "ɾ���ɹ�",JOptionPane.INFORMATION_MESSAGE);
						doClearInfo(1);
						loadInfoIntoTable(1);
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��", "ɾ��ʧ��", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "����ѡ��Ҫɾ���ļ�¼", "ɾ��ʧ��",JOptionPane.INFORMATION_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "����ѡ��һ����¼");
				}
				
			}
		});
		buttonPasswordT.setBounds(348, 189, 92, 23);
		panelTeacher.add(buttonPasswordT);
		
		comboBoxDepartpT = new JComboBox();
		comboBoxDepartpT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadComboxDepartc(comboBoxDepartpT.getSelectedIndex(),1);//1��ʾ��ʦ��departcˢ��
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
		buttonRetrieveT.addMouseListener(new MouseAdapter() {//���Ҷ�Ӧ��ŵĽ�ʦ
			@Override
			public void mouseClicked(MouseEvent e) {
				String findId = JOptionPane.showInputDialog("������Ҫ���ҵı��");
				if(findId==null){
					return;
				}
				if(findId.matches("\\d{8}")){
					try {
						Teacher findT = (Teacher) PersonDAOFactory.getTeacherProxy().findById(Integer.parseInt(findId));
						if(findT!=null){
							textFieldIdT.setText(findT.getId()+"");
							textFieldNameT.setText(findT.getName());
							if(findT.getSex().equals("��")){
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
							JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵĽ�ʦ", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
						}
					
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵĽ�ʦ", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵĽ�ʦ", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
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
		tabbedPaneInfo.addTab("����Ա������Ϣ", null, panelAdvisor, null);
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
		buttonInsertA.addMouseListener(new MouseAdapter() {//����Ա������Ϣ����
			@Override
			public void mouseClicked(MouseEvent e) {
				if((textFieldIdA.getText()==null)||("".equals(textFieldIdA.getText()))){//textidΪ�ղ������������
					String name = textFieldNameA.getText();
					String sex = null;
					if(radioButtonMaleA.isSelected()){
						sex = "��";
					}else{
						sex = "Ů";
					}
					String phone = textFieldPhoneA.getText();
					String email = textFieldEmailA.getText();
					String departp = comboBoxDepartpA.getSelectedItem().toString();
					
					String errMess = Validation.getAdvisorOrAdminErrorMess(name, phone, email);//���صĴ�����Ϣ
					if("".equals(errMess)){
						Advisor adv = new Advisor(name, sex, phone, email, departp);
						try {
							PersonDAOFactory.getAdvisorProxy().doCreate(adv);
							JOptionPane.showMessageDialog(null, "��ӳɹ�", "��ӳɹ�",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(2);
							loadInfoIntoTable(2);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, "ʧ�ܣ��Ѵ���", "���ʧ��", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					doClearInfo(2);
					JOptionPane.showMessageDialog(null, "�벻Ҫ�����Ѵ�����Ϣ");
				}
				
			}
		});
		buttonInsertA.setBounds(275, 109, 86, 23);
		panelAdvisor.add(buttonInsertA);
		
		JLabel label_14 = new JLabel("\u9662\u7CFB\uFF1A");
		label_14.setBounds(10, 109, 59, 23);
		panelAdvisor.add(label_14);
		
		JButton buttonUpdateA = new JButton("\u4FEE\u6539");
		buttonUpdateA.addMouseListener(new MouseAdapter() {//����Ա������Ϣ����
			@Override
			public void mouseClicked(MouseEvent e) {
				String advid = textFieldIdA.getText();
				if(!("".equals(advid))){
					String name = textFieldNameA.getText();
					String sex = null;
					if(radioButtonMaleA.isSelected()){
						sex = "��";
					}else{
						sex = "Ů";
					}
					String phone = textFieldPhoneA.getText();
					String email = textFieldEmailA.getText();
					String departp = comboBoxDepartpA.getSelectedItem().toString();
					
					String errMess = Validation.getAdvisorOrAdminErrorMess(name, phone, email);//���صĴ�����Ϣ
					if("".equals(errMess)){
						Advisor adv = new Advisor(Integer.parseInt(advid), name, sex, phone, email, departp);
						try {
							PersonDAOFactory.getAdvisorProxy().doUpdate(adv);
							JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "�޸ĳɹ�",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(2);
							loadInfoIntoTable(2);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "����ѡ��Ҫ�޸ĵļ�¼", "�޸ĳɹ�",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonUpdateA.setBounds(381, 109, 90, 23);
		panelAdvisor.add(buttonUpdateA);
		
		JButton buttonDeleteA = new JButton("\u5220\u9664");
		buttonDeleteA.addMouseListener(new MouseAdapter() {//����Ա������Ϣ��ɾ
			@Override
			public void mouseClicked(MouseEvent e) {
				String advid = textFieldIdA.getText();
				if(!("".equals(advid))){
					try {
						PersonDAOFactory.getAdvisorProxy().doDelete(Integer.parseInt(advid));
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "ɾ���ɹ�",JOptionPane.INFORMATION_MESSAGE);
						doClearInfo(2);
						loadInfoIntoTable(2);
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��", "ɾ��ʧ��", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "����ѡ��Ҫɾ���ļ�¼", "ɾ��ʧ��",JOptionPane.INFORMATION_MESSAGE);
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
				//��ȡ����е�ÿ�����ݷŵ�text
				textFieldIdA.setText(dtmA.getValueAt(tableA.getSelectedRows()[0], 0).toString());
				textFieldNameA.setText(dtmA.getValueAt(tableA.getSelectedRows()[0], 1).toString());
				if("��".equals(dtmA.getValueAt(tableA.getSelectedRows()[0], 2).toString())){
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
		String[] tableHeadsA = new String[]{"���","����","�Ա�","�绰","����","Ժ"}; //�б�ʶ��
		dtmA = (DefaultTableModel)tableA.getModel(); //ģ��ʵ��
		dtmA.setColumnIdentifiers(tableHeadsA);//����ģ���е��б�ʶ����
		
		
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
					JOptionPane.showMessageDialog(null, "����ѡ��һ����¼");
				}
			}
		});
		buttonPasswordA.setBounds(275, 76, 86, 23);
		panelAdvisor.add(buttonPasswordA);
		
		comboBoxDepartpA = new JComboBox();
		comboBoxDepartpA.setBounds(100, 110, 118, 21);
		panelAdvisor.add(comboBoxDepartpA);
		
		JButton buttonRetrieveA = new JButton("\u67E5\u627E");
		buttonRetrieveA.addMouseListener(new MouseAdapter() {//���Ҷ�Ӧ��Ÿ���Ա
			@Override
			public void mouseClicked(MouseEvent e) {
				String findId = JOptionPane.showInputDialog("������Ҫ���ҵı��");
				if(findId==null){
					return;
				}
				if(findId.matches("\\d{8}")){
					try {
						Advisor findAdv = (Advisor) PersonDAOFactory.getAdvisorProxy().findById(Integer.parseInt(findId));
						if(findAdv!=null){
							textFieldIdA.setText(findAdv.getId()+"");
							textFieldNameA.setText(findAdv.getName());
							if(findAdv.getSex().equals("��")){
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
							JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵĸ���Ա", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
						}
					
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵĸ���Ա", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵĸ���Ա", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
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
		tabbedPaneInfo.addTab("ѧ��������Ϣ", null, panelStudent, null);
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
				if((textFieldIdS.getText()==null)||("".equals(textFieldIdS.getText()))){//textidΪ�ղ������������
					String name = textFieldNameS.getText();
					String sex = null;
					if(radioButtonMaleS.isSelected()){
						sex = "��";
					}else{
						sex = "Ů";
					}
					String phone = textFieldPhoneS.getText();
					String email = textFieldEmailS.getText();
					String clazz = textFieldClassS.getText();
					String departp = comboBoxDepartpS.getSelectedItem().toString();
					String departc = comboBoxDepartcS.getSelectedItem().toString();
					
					String errMess = Validation.getStudentErrorMess(name, phone, email, clazz);//���صĴ�����Ϣ
					if("".equals(errMess)){
						
						try {
							Student s = null;
							if(isNewPic){//���ϴ���ͼƬ�������ݿ���ӣ��������
								s = new Student(name, sex, phone, email, departp, departc, Integer.parseInt(clazz), new FileInputStream(new File(path)));
							}else{
								s = new Student(name, sex, phone, email, departp, departc, Integer.parseInt(clazz));
							}
							
							PersonDAOFactory.getStudentProxy().doCreate(s);
							isNewPic = false;
							JOptionPane.showMessageDialog(null, "��ӳɹ�", "��ӳɹ�",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(3);
							loadInfoIntoTable(3);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, "ʧ�ܣ��Ѵ���", "���ʧ��", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					doClearInfo(3);
					JOptionPane.showMessageDialog(null, "�벻Ҫ�����Ѵ�����Ϣ");
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
		buttonUpdateS.addMouseListener(new MouseAdapter() {//ѧ��������Ϣ����
			@Override
			public void mouseClicked(MouseEvent e) {
				String sid = textFieldIdS.getText();
				if(!("".equals(sid))){
					String name = textFieldNameS.getText();
					String sex = null;
					if(radioButtonMaleS.isSelected()){
						sex = "��";
					}else{
						sex = "Ů";
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
					
					String errMess = Validation.getStudentErrorMess(name, phone, email, clazz);//���صĴ�����Ϣ
					if("".equals(errMess)){
						try {
							Student t = null;
							if(isNewPic){//������Ƭ����update��û������Ƭ����UpdateWithoutPicture
								t = new Student(Integer.parseInt(sid), name, sex, phone, email, departp, departc, Integer.parseInt(clazz), is);
								PersonDAOFactory.getStudentProxy().doUpdate(t);
								System.out.print(isNewPic);
							}else{
								t = new Student(Integer.parseInt(sid), name, sex, phone, email, departp, departc, Integer.parseInt(clazz));
								PersonDAOFactory.getStudentProxy().doUpdateWithoutPicture(t);
								System.out.print(isNewPic);
							}
							isNewPic = false;
							JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "�޸ĳɹ�",JOptionPane.INFORMATION_MESSAGE);
							doClearInfo(3);
							loadInfoIntoTable(3);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "����ѡ��Ҫ�޸ĵļ�¼", "�޸ĳɹ�",JOptionPane.INFORMATION_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "ɾ���ɹ�",JOptionPane.INFORMATION_MESSAGE);
						doClearInfo(3);
						loadInfoIntoTable(3);
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��", "ɾ��ʧ��", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "����ѡ��Ҫɾ���ļ�¼", "ɾ��ʧ��",JOptionPane.INFORMATION_MESSAGE);
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
				//��ȡ����е�ÿ�����ݷŵ�text
				textFieldIdS.setText(dtmS.getValueAt(tableS.getSelectedRows()[0], 0).toString());
				textFieldNameS.setText(dtmS.getValueAt(tableS.getSelectedRows()[0], 1).toString());
				if("��".equals(dtmS.getValueAt(tableS.getSelectedRows()[0], 2).toString())){
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
				
				InputStream is = (InputStream) dtmS.getValueAt(tableS.getSelectedRows()[0], 8);	//����������
				/*ByteArrayOutputStream baos2 = (ByteArrayOutputStream)picMap.get(dtmS.getValueAt(tableS.getSelectedRows()[0], 6).toString());
				InputStream is = new ByteArrayInputStream(baos2.toByteArray());  */
				//BufferedInputStream bis = new BufferedInputStream(is);
				try{
					/*ImageIcon noicon = null;//�������Ƭ
					lblNewLabelPicture.setIcon(noicon);*/
					
					byte[] pic = new byte[66560];//mysql blob���ֵ
					is.read(pic);//�ȶ���byte���飬�ٷ�װ��imageicon
					
					icon = new ImageIcon(pic); 
					
					lblNewLabelPicture.setIcon(icon);
					loadInfoIntoTable(3);//�˷�������ʵ��jtableˢ�´�ͷ��inputstream
				}catch(Exception e2){
					
				}
				
			}
		});
		panelAllInfoS.add(tableS);
		tableS.isCellEditable(0,0);
		String[] tableHeadsS = new String[]{"���","����","�Ա�","�绰","����","Ժ","ϵ","�༶","��Ƭ"}; //�б�ʶ��
		dtmS = (DefaultTableModel)tableS.getModel(); //ģ��ʵ��
		dtmS.setColumnIdentifiers(tableHeadsS);//����ģ���е��б�ʶ����
		
		
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
		buttonRetrieveS.addMouseListener(new MouseAdapter() {//���Ҷ�Ӧ���ѧ��
			@Override
			public void mouseClicked(MouseEvent e) {
				String findId = JOptionPane.showInputDialog("������Ҫ���ҵı��");
				if(findId==null){
					return;
				}
				if(findId.matches("\\d{8}")){
					try {
						Student findS = (Student) PersonDAOFactory.getStudentProxy().findById(Integer.parseInt(findId));
						if(findS!=null){
							textFieldIdS.setText(findS.getId()+"");
							textFieldNameS.setText(findS.getName());
							if(findS.getSex().equals("��")){
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
							byte[] pic = new byte[66560];//mysql blob���ֵ
							try{
								is.read(pic);//�ȶ���byte���飬�ٷ�װ��imageicon
								icon = new ImageIcon(pic); 
								lblNewLabelPicture.setIcon(icon);
								
							}catch(Exception e2){
								
							}
							isNewPic = false;
						}else{
							JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵ�ѧ��", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
						}
					
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵ�ѧ��", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵ�ѧ��", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
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
				JFileChooser chooser=new JFileChooser();//�ļ�����Ի���
				chooser.setCurrentDirectory(new File("pic"));//��Ĭ��·������ǰ��ĿpicĿ¼��
				 if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
					 File sf=chooser.getSelectedFile();
					 String prefix = sf.getName().substring(sf.getName().lastIndexOf(".")+1);//��ȡ�ļ���׺��,�磺jpg��+����ӡ�.��������
					 if("jpg".equals(prefix)||"jpeg".equals(prefix)||"bmp".equals(prefix)){
						 path = sf.getPath();
						 if(sf.length()>66560){
							 JOptionPane.showMessageDialog(null, "ͼƬ���ܴ���64k");
							 return;
						 }
					 }else{
						 JOptionPane.showMessageDialog(null, "��ѡ����ͨͼƬ����");
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
		tabbedPaneInfo.addTab("����Ա������Ϣ", null, panelAdmin, null);
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
		buttonInsertAd.addMouseListener(new MouseAdapter() {//����Ա������Ϣ����
			@Override
			public void mouseClicked(MouseEvent e) {
				if((textFieldIdAd.getText()==null)||("".equals(textFieldIdAd.getText()))){//textidΪ�ղ������������
					String name = textFieldNameAd.getText();
					String sex = null;
					if(radioButtonMaleAd.isSelected()){
						sex = "��";
					}else{
						sex = "Ů";
					}
					int authorization;
					if(radioButtonSupreme.isSelected()){
						authorization = 1;
					}else{
						authorization = 2;
					}
					String phone = textFieldPhoneAd.getText();
					String email = textFieldEmailAd.getText();
					
					String errMess = Validation.getAdvisorOrAdminErrorMess(name, phone, email);//���صĴ�����Ϣ
					if("".equals(errMess)){
						Admin adm = new Admin(name, sex, phone, email, authorization);
						try {
							PersonDAOFactory.getAdminProxy().doCreate(adm);
							doClearInfo(4);
							JOptionPane.showMessageDialog(null, "��ӳɹ�", "��ӳɹ�",JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, "ʧ�ܣ��Ѵ���", "���ʧ��", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					doClearInfo(4);
					JOptionPane.showMessageDialog(null, "�벻Ҫ�����Ѵ�����Ϣ");
				}
				
			}
		});
		buttonInsertAd.setBounds(366, 43, 88, 23);
		panelAdmin.add(buttonInsertAd);
		
		JButton buttonUpdateAd = new JButton("\u4FEE\u6539");
		buttonUpdateAd.addMouseListener(new MouseAdapter() {//����Ա������Ϣ����
			@Override
			public void mouseClicked(MouseEvent e) {
				String advid = textFieldIdAd.getText();
				if(!("".equals(advid))){
					String name = textFieldNameAd.getText();
					String sex = null;
					if(radioButtonMaleAd.isSelected()){
						sex = "��";
					}else{
						sex = "Ů";
					}
					int authorization;
					if(radioButtonSupreme.isSelected()){
						authorization = 1;
					}else{
						authorization = 2;
					}
					String phone = textFieldPhoneAd.getText();
					String email = textFieldEmailAd.getText();
					
					String errMess = Validation.getAdvisorOrAdminErrorMess(name, phone, email);//���صĴ�����Ϣ
					if("".equals(errMess)){
						Admin adv = new Admin(Integer.parseInt(advid), name, sex, phone, email, authorization);
						try {
							PersonDAOFactory.getAdminProxy().doUpdate(adv);
							JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "�޸ĳɹ�",JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, errMess, "��������", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "����ѡ��Ҫ�޸ĵļ�¼", "�޸ĳɹ�",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttonUpdateAd.setBounds(366, 76, 88, 23);
		panelAdmin.add(buttonUpdateAd);
		
		buttonDeleteAd = new JButton("\u5220\u9664");
		buttonDeleteAd.addMouseListener(new MouseAdapter() {//����Ա������Ϣ��ɾ
			@Override
			public void mouseClicked(MouseEvent e) {
				String advid = textFieldIdAd.getText();
				if(!("".equals(advid))){
					try {
						PersonDAOFactory.getAdminProxy().doDelete(Integer.parseInt(advid));
						doClearInfo(4);
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "ɾ���ɹ�",JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��", "ɾ��ʧ��", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "����ѡ��Ҫɾ���ļ�¼", "ɾ��ʧ��",JOptionPane.INFORMATION_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "����ѡ��һ����¼");
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
		buttonRetrieveAd.addMouseListener(new MouseAdapter() {//���Ҷ�Ӧ��ŵĹ���Ա
			@Override
			public void mouseClicked(MouseEvent e) {
				String findId = JOptionPane.showInputDialog("������Ҫ���ҵı��");
				if(findId==null){
					return;
				}
				if(findId.matches("\\d{8}")){
					try {
						Admin findAdm = (Admin) PersonDAOFactory.getAdminProxy().findById(Integer.parseInt(findId));
						if(findAdm!=null){
							textFieldIdAd.setText(findAdm.getId()+"");
							textFieldNameAd.setText(findAdm.getName());
							if(findAdm.getSex().equals("��")){
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
								radioButtonSupreme.setEnabled(false);//�����߹���Ա�ҵ��Լ��������޸��Լ���Ȩ��
								radioButtonNormal.setEnabled(false);
							}else{
								radioButtonSupreme.setSelected(false);
								radioButtonNormal.setSelected(true);
							}
							buttonsNormalAdminCantUse();
							
						}else{
							JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵĹ���Ա", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
						}
					
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵĹ���Ա", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "û�ҵ���Ӧ��ŵĹ���Ա", "����ʧ��",JOptionPane.INFORMATION_MESSAGE);
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
				if("��".equals(dtmAd.getValueAt(tableAd.getSelectedRows()[0], 2).toString())){
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
		String[] tableHeadsAd = new String[]{"���","����","�Ա�","�绰","����","Ȩ��"}; //�б�ʶ��
		dtmAd = (DefaultTableModel)tableAd.getModel(); //ģ��ʵ��
		dtmAd.setColumnIdentifiers(tableHeadsAd);//����ģ���е��б�ʶ����
		
		loadInfoIntoTable(4);
		
		labelAllInfoAd = new JLabel("\u5168\u90E8\u6559\u5E08\u4FE1\u606F\uFF1A");
		labelAllInfoAd.setBounds(10, 208, 432, 32);
		panelAdmin.add(labelAllInfoAd);
		
		/*���в˵�����¼�
		 * ����1���Ƿ���ʾ������Ϣ��� ����2�����ƻ�����Ϣ�ĸ������ʾ ����3�����ƿ��������ʾ
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
	/*�˵����Ӧ��ͬ��ʾ���
	 * ����1���Ƿ���ʾ������Ϣ��� ����2�����ƻ�����Ϣ�ĸ������ʾ ����3�����ƿ��������ʾ
	 * */
	public void ShowOrHide(boolean isInfo, int infoType, int attendanceType){
		
		//������Ϣ���
		if(isInfo){
			panelInfo.setVisible(true);
			panelAttendance.setVisible(false);
		}
		else{
			panelInfo.setVisible(false);
			panelAttendance.setVisible(true);
		}
		if((!isSupremeAdm)&&(!isNormalAdm)){//���ǹ���Ա
			//������Ϣ����4�������
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
			
			//������ٿ�����壬��ʦ�͸���Ա�����Բ鿴���ڼ�¼
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
		else{//�ǹ���Ա����Ϣ��������ʾ��������������ʾ
			if(infoType==0){//��ʾ��Ӧ������Ϣ���
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
			buttonsNormalAdminCantUse();//�����һ�����Ա��������ѡ����ť�����ã������ڴ����ã�ѡ��menu���������
		}
	
		
		
		textFieldIdT.setEnabled(false);
		textFieldIdA.setEnabled(false);
		textFieldIdS.setEnabled(false);
		textFieldIdAd.setEnabled(false);
		textFieldIdAc.setEnabled(false);
		textFieldNameAc.setEnabled(false);
	}
	/*��ջ�����Ϣ*/
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

