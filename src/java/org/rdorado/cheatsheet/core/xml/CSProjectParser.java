package org.rdorado.cheatsheet.core.xml;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.rdorado.cheatsheet.core.CSProject;
import org.rdorado.cheatsheet.core.process.CSProcess;
import org.rdorado.cheatsheet.core.process.CSTask;
import org.rdorado.cheatsheet.segmenter.SenteceSegmenter;
import org.rdorado.cheatsheet.tagger.POSTagger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CSProjectParser extends DefaultHandler {

	CSProject project;
	CSTask currentTask;
	
	<T> T getInstance(Class<T> clazz, String className){
		try {
			Class<?> segClass = Class.forName(className);
			Constructor<?> constr = segClass.getConstructor();
			return (T)constr.newInstance(new Object[] {});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void startElement(String namespaceURI,
	        String sName, // simple name
	        String qName, // qualified name
	        Attributes attrs)
	throws SAXException
	{
		
		if(qName.equals("segmenter")) {
			
			String type = attrs.getValue("type");
			CSProcess process = null;
			if (type.equals("java")) {
				String clazz = attrs.getValue("class");
				
				SenteceSegmenter segmenter = getInstance(SenteceSegmenter.class, clazz);
				process = new SegmenterProcess(segmenter);
				//POSTagger tagger = getInstance(POSTagger.class, clazz);				

			}
			else if (type.equals("python")) {
				//attrs.get
				PythonProcess 
			}
			currentTask.add();
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
