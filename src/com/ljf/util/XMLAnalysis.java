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
		builder = new SAXBuilder();//建立sax解析
		try {
			doc = builder.build(this.getClass().getClassLoader().getResourceAsStream("depart.xml"));//找到需要解析的xml
			//doc = builder.build("E:\\Eclipse\\workspace\\StudentAttendanceCheckSystem\\src\\depart.xml");
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		e = doc.getRootElement();//读取根元素即depart
	}

	public List getDepartp(){
		List list = e.getChildren("departp");//读取全部departp
		List listNew = new ArrayList();
		for(int i=0; i<list.size(); i++){
			Element departpElement = (Element)list.get(i);//得到第i个departp元素
			String title = departpElement.getChildText("title");//得到子节点title文本
			listNew.add(title);
		}
		return listNew;
	}
	
	public List getDepartc(int i){//下拉列表的序号传过来进行二级联动
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
