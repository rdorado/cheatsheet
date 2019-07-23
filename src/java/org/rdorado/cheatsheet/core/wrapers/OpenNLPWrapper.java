package org.rdorado.cheatsheet.core.wrapers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.rdorado.cheatsheet.core.POSTagger;
import org.rdorado.cheatsheet.core.SenteceSegmenter;
import org.rdorado.cheatsheet.core.TaggedSentence;
import org.rdorado.cheatsheet.core.TaggedToken;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class OpenNLPWrapper implements SenteceSegmenter, POSTagger{

	SentenceDetector detector;
	POSTaggerME tagger;
	Tokenizer tokenizer;
			
	public OpenNLPWrapper(){		
		try {
			InputStream inputStream = new FileInputStream("models/opennlp/en-sent.bin");
			detector = new SentenceDetectorME(new SentenceModel(inputStream));
			
			//inputStream = new FileInputStream("models/opennlp/en-pos-maxent.bin");
			inputStream = new FileInputStream("models/opennlp/en-pos-maxent-gutenberg.model");
			tagger = new POSTaggerME(new POSModel(inputStream));
			
			inputStream = new FileInputStream("models/opennlp/en-token.bin");
			tokenizer = new TokenizerME(new TokenizerModel(inputStream));
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
	
	@Override
	public TaggedSentence tag(String sentence) {
		 
		if (tokenizer != null && tagger != null) {
			TaggedSentence result = new TaggedSentence(); 
			String[] tokens  = tokenizer.tokenize(sentence);
			String[] tags= tagger.tag(tokens);
			for (int i=0;i<tokens.length;i++) {
				result.addTaggedToken(tokens[i], tags[i]);
			}
			return result;
		}
		return null;
	}
	
	public static void main(String[] args) {
		OpenNLPWrapper wrapper = new OpenNLPWrapper();
		
		/*for (String string : wrapper.segment("This is a sentence. This is another sentence.")) {
			System.out.println(string);
		}*/
		
		for(TaggedToken token : (wrapper.tag("The dog ate chocolate.")).getTaggedTokens()) {
			System.out.println(token);
		};
		
		
	}


}
