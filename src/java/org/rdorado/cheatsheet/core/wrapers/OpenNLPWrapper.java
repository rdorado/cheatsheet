package org.rdorado.cheatsheet.core.wrapers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.rdorado.cheatsheet.core.POSTagger;
import org.rdorado.cheatsheet.core.SenteceSegmenter;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class OpenNLPWrapper implements SenteceSegmenter, POSTagger{

	SentenceDetector detector;
	
	public OpenNLPWrapper(){		
		try {
			InputStream inputStream = new FileInputStream("models/opennlp/en-sent.bin");
			SentenceModel model = new SentenceModel(inputStream); 
			detector = new SentenceDetectorME(model);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	
	@Override
	public String[] segment(String paragraph) {
		if (detector != null) {
			return detector.sentDetect(paragraph);
		}
		return null;
	}
	
	
	
	public static void main(String[] args) {
		OpenNLPWrapper wrapper = new OpenNLPWrapper();
		
		for (String string : wrapper.segment("This is a sentence. This is another sentence.")) {
			System.out.println(string);
		}
		
	}

}
