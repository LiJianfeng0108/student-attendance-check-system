package com.ljf.util;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Timer implements Runnable{
	private int s = 500;
	private JFrame frame;
	private JLabel lblNewLabelTimer = new JLabel("");;

	public Timer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u5012\u6570\u8BA1\u65F6\uFF1A");
		lblNewLabel.setBounds(35, 20, 72, 15);
		frame.getContentPane().add(lblNewLabel);
		
		
		lblNewLabelTimer.setBounds(50, 50, 54, 15);
		frame.getContentPane().add(lblNewLabelTimer);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	@Override
	public void run() {
		while(s>-1){
			try {
				lblNewLabelTimer.setText(s+"");
				Thread.sleep(1000);
				s--;
				
				//System.out.println(s--);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(null, "ʱ�䵽");
		frame.setVisible(false);
	}
	
}