package org.rdorado.cheatsheet.core.wrapers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.rdorado.cheatsheet.core.SenteceSegmenter;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

//import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class StanfordCoreNLPWrapper implements SenteceSegmenter{

	StanfordCoreNLP pipeline;
	
	public StanfordCoreNLPWrapper() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit"); //, pos, lemma, ner, parse, dcoref
		pipeline = new StanfordCoreNLP(props);
	}

	@Override
	public String[] segment(String paragraph) {
		Annotation document = new Annotation(paragraph);
		pipeline.annotate(document);
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		String[] result = new String[sentences.size()];
		for(int i=0;i<sentences.size();i++) {
			  CoreMap sentence = sentences.get(i);
			  result[i] = sentence.toString();
		}
		
		return result;
	}

	public static void main(String[] args){

		/*MaxentTagger maxentTagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");

    String tag = maxentTagger.tagString("He half closed his eyes and searched the horizon.");
    String[] eachTag = tag.split("\\s+");

    for(int i = 0; i< eachTag.length; i++) {
      System.out.print(eachTag[i].split("_")[0] +"_"+ eachTag[i].split("_")[1]+" ");
    }*/

	}




}
