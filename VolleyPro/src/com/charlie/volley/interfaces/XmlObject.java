package com.charlie.volley.interfaces;

import java.util.List;

import org.xmlpull.v1.XmlPullParser;


public  interface XmlObject {
	 void setValueToXmlObject(XmlPullParser response,List<XmlObject> list, String nodeName);
}
