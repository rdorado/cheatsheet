package org.rdorado.cheatsheet.core;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.rdorado.cheatsheet.tokenizer.Tokenizer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CSDocument {
	ArrayList<Paragraph> paragraphs = new ArrayList<Paragraph>();

	public void filterByRegularExpression(String regex) {
		for (Paragraph paragraph : paragraphs) {
			ArrayList<Sentence> toRemove = new ArrayList<Sentence>();
			for (Sentence sentence : paragraph.sentences) {				
				if(!sentence.text.matches(regex)) {
					toRemove.add(sentence);
				}					
			}
			for(Sentence sentence : toRemove) {
				paragraph.sentences.remove(sentence);
			}
		}
	}

	public void removeByContainsString(String string) {
		for (Paragraph paragraph : paragraphs) {
			ArrayList<Sentence> toRemove = new ArrayList<Sentence>();
			for (Sentence sentence : paragraph.sentences) {				
				if(sentence.text.contains(string)) {
					toRemove.add(sentence);
				}					
			}
			for(Sentence sentence : toRemove) {
				paragraph.sentences.remove(sentence);
			}
		}
	}

	public void filterByMinTokens(int mintokens, Tokenizer tokenizer) {
		for (Paragraph paragraph : paragraphs) {
			ArrayList<Sentence> toRemove = new ArrayList<Sentence>();
			for (Sentence sentence : paragraph.sentences) {				
				if(tokenizer.tokenize(sentence.text).length < mintokens) {
					toRemove.add(sentence);
				}					
			}
			for(Sentence sentence : toRemove) {
				paragraph.sentences.remove(sentence);
			}
		}
	}

	public void filterSentencesByCount(int mincount) {
		for (Paragraph paragraph : paragraphs) {
			ArrayList<Sentence> toRemove = new ArrayList<Sentence>();
			for (Sentence sentence : paragraph.sentences) {				
				if(sentence.count < mincount) {
					toRemove.add(sentence);
				}					
			}
			for(Sentence sentence : toRemove) {
				paragraph.sentences.remove(sentence);
			}
		}
	}

	public void saveToFile(File fileName) {
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
			Document document = icBuilder.newDocument();
			//document.setXmlStandalone(true);
			DOMSource source = new DOMSource(document);
			
			Element mainRootElement = document.createElement("document");
			document.appendChild(mainRootElement);
			
			for(Paragraph paragraph : paragraphs) {
				Element elParagraph = document.createElement("paragraph");
				elParagraph.setAttribute("end", Integer.toString(paragraph.end));	
				elParagraph.setAttribute("start", Integer.toString(paragraph.start));			
				mainRootElement.appendChild(elParagraph);
				
				for(Sentence sentence : paragraph.sentences) {
					Element elSentence = document.createElement("sentence");
					elSentence.setAttribute("count", Integer.toString(sentence.count));
					elSentence.setTextContent(sentence.text);
					elParagraph.appendChild(elSentence);
				}				
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");			
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
			
			FileWriter writer = new FileWriter(fileName);
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addParagraph(Paragraph paragraph) {
		paragraphs.add(paragraph);		
	}

	public boolean contains(Paragraph paragraph) {
		return paragraphs.contains(paragraph);
	}

	public Paragraph getParagraph(Paragraph paragraph) {
		int indx = paragraphs.indexOf(paragraph);
		if (indx==-1) return null;
		return paragraphs.get(indx);
	}	
}

