package org.rdorado.cheatsheet.core;

import java.util.ArrayList;

public class Paragraph {
	int start;
	int end;
	ArrayList<Sentence> sentences = new ArrayList<Sentence>();
	Paragraph(){
	}
	public Paragraph(int start, int end){
		this.start=start;
		this.end=end;
	}
	public void addSentence(String text) {
		int indx = sentences.indexOf(new Sentence(text));
		if(indx==-1){
			sentences.add(new Sentence(text));
		}
		else {
			sentences.get(indx).increment();
		}
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Paragraph) {
			Paragraph other = (Paragraph)obj;
			return this.end == other.end && this.start == other.start;
		}
		return false;
	}

}
 
class Sentence {
	String text;
	int count;
	
	public Sentence(String text) {
		this.text=text;
		this.count=1;
	}
	
	public void increment() {
		this.count+=1;		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Sentence) {
			return this.text.equals(((Sentence)obj).text);
		}
		return false;
	}
}