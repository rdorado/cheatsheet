package org.rdorado.cheatsheet.core.xml;

import java.io.IOException;

import org.rdorado.cheatsheet.core.CSProject;
import org.rdorado.cheatsheet.core.CSTask;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CSProjectParser extends DefaultHandler {

	CSProject project;
	CSTask currentTask;
	
	public void startElement(String namespaceURI,
	        String sName, // simple name
	        String qName, // qualified name
	        Attributes attrs)
	throws SAXException
	{
		
		if(qName.equals("segmenter")) {
			
			String type = attrs.getValue("type");
			
			if (type.equals("java")) {
				String clazz = attrs.getValue("class");
				
				try {
					Class segClass = Class.forName(clazz);
					segClass.getConstructor();
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
			
			//CSSegmenter segmenter = new CSSegmenter();
			
			//currentTask.addProcess();
		}
		
		/*String strAttrs = "";
		for(int i=0;i<attrs.getLength();i++) {
			strAttrs+=" "+attrs.getQName(i)+"=\""+attrs.getValue(i)+"\"";
		}
		
		try {
			//if (qName.equals("paragraph")) out.write(" <paragraph"+strAttrs+">\n");
		}
		catch (IOException e) {
			throw new SAXException("I/O error", e);
		}*/		
	}
	
}
