package org.rdorado.cheatsheet.parser;

import java.util.ArrayList;

public class ParseTree {

	String tag;
	String text;
	ArrayList<ParseTree> children = new ArrayList<ParseTree>(); 
	
	public ParseTree createChild() {
		ParseTree result = new ParseTree();
		children.add(result);
		return result;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setText(String text) {
		this.text = text;		
	}

	public String getText() {
		return this.text != null ? this.text : "";
	}

	public String getTag() {
		return this.tag != null ? this.tag : "";
	}
	
}
