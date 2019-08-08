package org.rdorado.cheatsheet.core.xml;

import java.io.IOException;

import org.rdorado.cheatsheet.core.CSDocument;
import org.rdorado.cheatsheet.core.Paragraph;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SentenceFileParser extends DefaultHandler{

	CSDocument csDocument;
	Paragraph paragraph;
	boolean readSentence;
	StringBuffer textBuffer;
	
	public SentenceFileParser(CSDocument doc) {
		csDocument = doc;
	}

	public void startElement(String namespaceURI,
	        String sName, // simple name
	        String qName, // qualified name
	        Attributes attrs)
	throws SAXException
	{
		if (qName.equals("paragraph")) {
			int start = Integer.parseInt(attrs.getValue("start"));
			int end = Integer.parseInt(attrs.getValue("end"));
			paragraph = new Paragraph(start, end);	
			if (!csDocument.contains(paragraph)) {
				csDocument.addParagraph(paragraph);
			}
			else {
				paragraph = csDocument.getParagraph(paragraph);
			}
		}		
		else if (qName.equals("sentence")) {
			readSentence = true;
		}
		
	}
	
	public void endElement(String namespaceURI,
	        String sName, // simple name
	        String qName  // qualified name
	        )
	throws SAXException
	{
		if (qName.equals("sentence")) {
			if(textBuffer!=null) {
				paragraph.addSentence(""+textBuffer);
			}	
			textBuffer = null;
			readSentence = false;
		}
	}
	
	public void characters(char buf[], int offset, int len)
	throws SAXException
	{
		if(readSentence) {
			  String s = new String(buf, offset, len);
			  if (textBuffer == null) {
			    textBuffer = new StringBuffer(s);
			  } else {
			    textBuffer.append(s);
			  }	
		}
		  

	}
	
}
