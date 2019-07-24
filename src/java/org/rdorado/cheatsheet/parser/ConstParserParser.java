package org.rdorado.cheatsheet.parser;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.rdorado.cheatsheet.tagger.TaggedSentence;
import org.rdorado.cheatsheet.tagger.TaggedToken;
import org.rdorado.cheatsheet.tagger.POSTaggerParser.POSTaggerOutputType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ConstParserParser  extends DefaultHandler{

	public enum ParserOutputType {XML, Text}

	Parser parser;
	ParserOutputType outputType;
	BufferedWriter out;	
	StringBuffer textBuffer;

	public ConstParserParser(String outfilename, Parser parser, ParserOutputType outputType) throws Exception {
		this.parser = parser;
		this.outputType = outputType;
		if (outputType.equals(ParserOutputType.Text)) {
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
	}

	void transformToString(ParseTree parseTree, StringBuffer buffer) {
		buffer.append("(");
		buffer.append(String.join(" ", new String[] {parseTree.getTag(), parseTree.getText()}));
		for(ParseTree pt : parseTree.children) {
			transformToString(pt, buffer);
		}
		buffer.append(")");
	}
	
	String transformToString(ParseTree parseTree) {
		StringBuffer tmpBuffer = new StringBuffer();
		transformToString(parseTree, tmpBuffer);
		return tmpBuffer.toString();
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

					ParseTree pt = parser.parse(""+textBuffer);

					if (this.outputType.equals(ParserOutputType.XML)) {

					}
					else if (this.outputType.equals(ParserOutputType.Text)) {
						out.write( transformToString(pt) );
						out.newLine();
					}

				}
				textBuffer = null;
			}

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
	

}
