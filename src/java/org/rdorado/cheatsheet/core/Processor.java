package org.rdorado.cheatsheet.core;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.rdorado.cheatsheet.parser.ConstParserParser;
import org.rdorado.cheatsheet.parser.ConstParserParser.ParserOutputType;
import org.rdorado.cheatsheet.parser.Parser;
import org.rdorado.cheatsheet.segmenter.SenteceSegmenter;
import org.rdorado.cheatsheet.tagger.POSTagger;
import org.rdorado.cheatsheet.tagger.POSTaggerParser;
import org.rdorado.cheatsheet.tagger.POSTaggerParser.POSTaggerOutputType;
import org.rdorado.cheatsheet.tokenizer.Tokenizer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author rdsanchez
 *
 */
public class Processor {

	public static File[] getFiles(String dirpath) throws Exception{
		File infolder = new File(dirpath);		
		if(!infolder.exists() && !infolder.isDirectory()) {
			throw new Exception("Input folder does not exist");
		}
		return infolder.listFiles();
	}
	
	public static void parseSentences(String dirpath, String outdir, Parser parser, ParserOutputType outputType) {
		File outdirfolder  = new File(outdir);
		if(!outdirfolder.exists()) outdirfolder.mkdirs();
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();		
			
			File[] listOfFiles = getFiles(dirpath);
			for (File file : listOfFiles) {
				DefaultHandler handler = new ConstParserParser(outdir+"/"+file.getName(), parser, outputType);
				saxParser.parse(file, handler);
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}			
	}
	
	public static void tagSentences(String dirpath, String outdir, POSTagger posTagger, POSTaggerOutputType outputType) {
		File outdirfolder  = new File(outdir);
		if(!outdirfolder.exists()) outdirfolder.mkdirs();
		
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			File[] listOfFiles = getFiles(dirpath);
			for (File file : listOfFiles) {
				DefaultHandler handler = new POSTaggerParser(outdir+"/"+file.getName(), posTagger, outputType);
				saxParser.parse(file, handler);
			}

		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}

	public static void paragraphToSentences(String dirpath, String outdir, SenteceSegmenter senteceSegmenter) {
		File outdirfolder  = new File(outdir);
		if(!outdirfolder.exists()) outdirfolder.mkdirs();
		
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			File[] listOfFiles = getFiles(dirpath);
			for (File file : listOfFiles) {
				DefaultHandler handler = new ParagraphToSentenceParser(outdir+"/"+file.getName(), senteceSegmenter);
				saxParser.parse(file, handler);
			}

		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	private static ArrayList<String> checkFolders(String rootDir, String[] subdirs) {
		ArrayList<String> resp = new ArrayList<String>();
		for(String subdir : subdirs) {
			Path path = Paths.get(rootDir, subdir);	
			if (path.toFile().exists()) {
				resp.add(path.toFile().getPath());
			}
			else {
				System.out.println("Folder '"+path.toFile().getPath()+"' does not exist, skipping it.");
			}
		}
		return resp;
	}

	private static boolean checkFileInDirs(ArrayList<String> sentencesCheckedDirs, String name) {
		for (String strDir : sentencesCheckedDirs) {
			if( !Paths.get(strDir, name).toFile().exists() ) {
				return false;
			}
		}
		return true;
	}
	
	public static void filterSentences(String rootDir, String outputDir, String[] sentencesDirs) {

		File fileRootDir = new File(rootDir);		
		if(!fileRootDir.exists() || !fileRootDir.isDirectory()) {
			System.out.println("Root folder does not exist");
			return;
		}
		
		File outdirfolder  = new File(outputDir);
		if(!outdirfolder.exists()) outdirfolder.mkdirs();
		
		File fileOutputDir = new File(outputDir);		
		if(!fileOutputDir.exists() || !fileOutputDir.isDirectory()) {
			System.out.println("Output folder does not exist");
			return;
		}
		
		ArrayList<String> sentencesCheckedDirs = checkFolders(rootDir, sentencesDirs);
		if (sentencesCheckedDirs.size()==0) {
			System.out.println("No valid folders were found.");
			return;
		}
		
		File[] listOfFiles = new File(sentencesCheckedDirs.get(0)).listFiles();
		if(listOfFiles.length == 0) {
			System.out.println("No valid files were found in '"+sentencesCheckedDirs.get(0)+"'.");
			return;
		}
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			for(File sentenceFile : listOfFiles) {
				
				if (checkFileInDirs(sentencesCheckedDirs, sentenceFile.getName())) {
					CSDocument doc = new CSDocument();
					for (String strDir : sentencesCheckedDirs) {						
						File xmlFile = Paths.get(strDir, sentenceFile.getName()).toFile();
						System.out.println(xmlFile);
						DefaultHandler handler = new SentenceFileParser(doc);
						saxParser.parse(xmlFile, handler);
					}			
					doc.filterSentencesByCount(3);
					doc.filterByRegularExpression("^[a-zA-Z0-9 .,]*$");
					doc.removeByContainsString("  ");
					doc.filterByMinTokens(6, (sentence) -> sentence.split(" "));
					doc.saveToFile(Paths.get(outputDir, sentenceFile.getName()).toFile());
				}
				System.out.println(sentenceFile.getName());	
			}

			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}


class CSDocument {
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

class Paragraph {
	int start;
	int end;
	ArrayList<Sentence> sentences = new ArrayList<Sentence>();
	Paragraph(){
	}
	Paragraph(int start, int end){
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