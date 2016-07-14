package com.charlie.volley.bean;

import java.util.List;

import org.xmlpull.v1.XmlPullParser;


import com.charlie.volley.interfaces.XmlObject;

public class City implements XmlObject{
	private String quName;
	private String pyName;
	private String cityname;
	public String getQuName() {
		return quName;
	}
	public City setQuName(String quName) {
		this.quName = quName;
		return this;
		
	}
	public String getPyName() {
		return pyName;
	}
	public City setPyName(String pyName) {
		this.pyName = pyName;
		return this;
	}
	public String getCityname() {
		return cityname;
	}
	public City setCityname(String cityname) {
		this.cityname = cityname;
		return this;
	}
	@Override
	public String toString() {
		return "City [quName=" + quName + ", pyName=" + pyName + ", cityname="
				+ cityname + "]"+"\n";
	}
	@Override
	public void setValueToXmlObject(XmlPullParser response, List<XmlObject> list,
			String nodeName) {
		
		if("city".equals(nodeName)){
			City city=new City()
			.setQuName(response.getAttributeValue(0))
			.setPyName(response.getAttributeValue(1))
			.setCityname(response.getAttributeValue(2));
			list.add(city);
			
		}
		
	}
}
