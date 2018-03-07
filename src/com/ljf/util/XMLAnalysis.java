package com.ljf.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XMLAnalysis {
	private SAXBuilder builder = null;
	private Document doc = null;
	private Element e = null;
	
	public XMLAnalysis() {
		builder = new SAXBuilder();//����sax����
		try {
			doc = builder.build(this.getClass().getClassLoader().getResourceAsStream("depart.xml"));//�ҵ���Ҫ������xml
			//doc = builder.build("E:\\Eclipse\\workspace\\StudentAttendanceCheckSystem\\src\\depart.xml");
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		e = doc.getRootElement();//��ȡ��Ԫ�ؼ�depart
	}

	public List getDepartp(){
		List list = e.getChildren("departp");//��ȡȫ��departp
		List listNew = new ArrayList();
		for(int i=0; i<list.size(); i++){
			Element departpElement = (Element)list.get(i);//�õ���i��departpԪ��
			String title = departpElement.getChildText("title");//�õ��ӽڵ�title�ı�
			listNew.add(title);
		}
		return listNew;
	}
	
	public List getDepartc(int i){//�����б����Ŵ��������ж�������
		List list = e.getChildren("departp");
		List listNew = new ArrayList();
		Element departElement = (Element)list.get(i);
		Element itemElement = departElement.getChild("item");
		List list2 = itemElement.getChildren("departc");
		for(int j=0; j<list2.size(); j++){
			Element departcElement = (Element) list2.get(j);
			String departc = departcElement.getValue();
			listNew.add(departc);
		}
		return listNew;
	}
}
