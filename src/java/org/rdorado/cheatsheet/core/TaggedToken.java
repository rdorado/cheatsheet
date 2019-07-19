package org.rdorado.cheatsheet.core;

public class TaggedToken {
	String text;
	String tag;
	public TaggedToken(String token, String tag) {
		this.text = token;
		this.tag = tag;
	}
	@Override
	public String toString() {
		return this.text+"_"+this.tag;
	}
}