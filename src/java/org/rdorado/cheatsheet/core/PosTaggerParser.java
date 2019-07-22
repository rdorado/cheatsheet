package org.rdorado.cheatsheet.core;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.rdorado.cheatsheet.utils.Utils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PosTaggerParser extends DefaultHandler{

	public enum POSTaggerOutputType {XML, Text}
	
	POSTagger tagger;
	POSTaggerOutputType outputType;
	
	BufferedWriter out;
	StringBuffer textBuffer;

	public PosTaggerParser(String outfilename, POSTagger posTagger, POSTaggerOutputType outputType) throws Exception {
		this.tagger = posTagger;
		this.outputType = outputType;
		if (outputType.equals(POSTaggerOutputType.Text)) {
			outfilename = outfilename.replaceAll(".xml", ".txt");
		}
		
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfilename), "UTF8")); 
	}

	public void startElement(String namespaceURI,
	        String sName, // simple name
	        String qName, // qualified name
	        Attributes attrs)
	throws SAXException
	{
		
		if (qName.equals("sentence")) {
			textBuffer = null;
		}
		/*String strAttrs = "";
		for(int i=0;i<attrs.getLength();i++) {
			strAttrs+=" "+attrs.getQName(i)+"=\""+attrs.getValue(i)+"\"";
		}
		
		try {
			if (this.outputType.equals(POSTaggerOutputType.XML)) {
				//if (qName.equals("paragraph")) out.write(" <paragraph"+strAttrs+">\n");
			}
			else if (this.outputType.equals(POSTaggerOutputType.Text)) {
				
			}
			//out.newLine();
		}
		catch (IOException e) {
			throw new SAXException("I/O error", e);
		}		*/
	}
	
	public void endElement(String namespaceURI,
	        String sName, // simple name
	        String qName  // qualified name
	        )
	throws SAXException
	{
		try {			
			if (qName.equals("sentence")) {
				
				  if (textBuffer != null) {
					  
					  TaggedSentence ts = tagger.tag(""+textBuffer);
					  
					  if (this.outputType.equals(POSTaggerOutputType.XML)) {
						 
					  }
					  else if (this.outputType.equals(POSTaggerOutputType.Text)) {
						  ArrayList<String> strTt = new ArrayList<>();
						  for (TaggedToken ttoken : ts.tokens) {
							  strTt.add(ttoken.text+"_"+ttoken.tag);
						  }
						  out.write( String.join(" ", strTt) );
						  out.newLine();
						  
					  }
						  
						  
					  }
					  textBuffer = null;
				  }
				 
			
			//out.newLine();
		}
		catch (IOException e) {
			throw new SAXException("I/O error", e);
		}		
	}
	
	
	public void characters(char buf[], int offset, int len)
	throws SAXException
	{
		
		  String s = new String(buf, offset, len);
		  if (textBuffer == null) {
		    textBuffer = new StringBuffer(s);
		  } else {
		    textBuffer.append(s);
		  }	
	}
	
	/*public void startDocument()
	throws SAXException {
		try {
			out.write("<?xml version='1.0' encoding='UTF-8'?>");
			out.newLine();	
			out.write("<document>");
			out.newLine();
		}
		catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}*/

	
	public void endDocument() 
	throws SAXException  {
		try {
			//out.write("</document>");
			//out.newLine();						
			out.close();
		} 
		catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}

	
}
