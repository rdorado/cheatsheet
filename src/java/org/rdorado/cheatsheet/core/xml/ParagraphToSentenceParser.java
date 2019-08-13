package org.rdorado.cheatsheet.core.xml;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.rdorado.cheatsheet.segmenter.SentenceSegmenter;
import org.rdorado.cheatsheet.utils.Utils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParagraphToSentenceParser extends DefaultHandler{

	BufferedWriter out;
	SentenceSegmenter senteceSegmenter;
	StringBuffer textBuffer;
	
	public ParagraphToSentenceParser(String outfilename, SentenceSegmenter senteceSegmenter) throws Exception{
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfilename), "UTF8")); 
		this.senteceSegmenter = senteceSegmenter;
	}
	
	public void startElement(String namespaceURI,
	        String sName, // simple name
	        String qName, // qualified name
	        Attributes attrs)
	throws SAXException
	{
		String strAttrs = "";
		for(int i=0;i<attrs.getLength();i++) {
			strAttrs+=" "+attrs.getQName(i)+"=\""+attrs.getValue(i)+"\"";
		}
		
		try {
			if (qName.equals("paragraph")) out.write(" <paragraph"+strAttrs+">\n");
			//out.newLine();
		}
		catch (IOException e) {
			throw new SAXException("I/O error", e);
		}		
	}

	
	public void endElement(String namespaceURI,
	        String sName, // simple name
	        String qName  // qualified name
	        )
	throws SAXException
	{
		try {			
			if (qName.equals("paragraph")) {
				  if (textBuffer != null) {
					  
					  String s = ""+textBuffer;		
					  String[] sentences = senteceSegmenter.segment(s);
					  if(sentences != null) {
						  for (String sentence : sentences) {
							  out.write("  <sentence>"+Utils.xmlEscapeText(sentence)+"</sentence>\n" );
						  }					 
					  }					  					  
					  textBuffer = null;
				  }
				  out.write(" </paragraph>\n");
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
	
	public void startDocument()
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
	}

	
	public void endDocument() 
	throws SAXException  {
		try {
			out.write("</document>");
			out.newLine();						
			out.close();
		} 
		catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}
	
}
