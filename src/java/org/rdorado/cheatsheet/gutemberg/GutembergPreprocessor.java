package org.rdorado.cheatsheet.gutemberg;

import org.rdorado.cheatsheet.core.SenteceSegmenter;
import org.rdorado.cheatsheet.core.SentenceFileParser;
import org.rdorado.cheatsheet.core.wrapers.OpenNLPWrapper;
import org.rdorado.cheatsheet.core.wrapers.StanfordCoreNLPWrapper;
import org.rdorado.cheatsheet.utils.Utils;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.rdorado.cheatsheet.core.Processor;


/**
 * @author rdsanchez
 *
 */
public class GutembergPreprocessor {

	public static void transformTextToPragraphXML(String dirpath, String outdir) {
		File infolder = new File(dirpath);		
		if(!infolder.exists() && !infolder.isDirectory()) {
			System.out.println("Input folder does not exist");
			return;
		}
		File[] listOfFiles = infolder.listFiles();

		File outdirfolder = new File(outdir);	
		if(!outdirfolder.exists() && !outdirfolder.isDirectory()) {
			System.out.println("Output folder does not exist");
			return;
		}
		
		for (File file : listOfFiles) {
			
			try{
				BufferedReader inputbr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
				String outfilename = file.getName().replaceAll(".txt", ".xml");
				BufferedWriter outputbw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outdir+"/"+outfilename), "UTF8"));
				int nline = 0;
				int iline = -1;
				outputbw.append("<document filename='"+file.getName()+"'>\n");
				boolean startReached = false;
				boolean endReached = false;
				String currentParagraph = "";
				for(String line; (line = inputbr.readLine()) != null; ) {
					nline++;
					
					if (startReached && !endReached) {
						
						if (line.strip().equals("")) {
							if (!currentParagraph.strip().equals("")) {
								outputbw.append(" <paragraph start='"+iline+"' end='"+(nline-1)+"'>"+Utils.xmlEscapeText(currentParagraph.substring(1))+"</paragraph>\n");
								currentParagraph = "";
								iline = -1;
							}
						}
						else {
							currentParagraph=currentParagraph+" "+line.strip();
							if (iline == -1) iline = nline;
						}
					}
					
					if (line.startsWith("*** START")) {
						startReached = true;
					}
					if (line.startsWith("*** END")) {
						endReached = true;
					}
					
				}
				outputbw.append("</document>");
				outputbw.close();
				inputbr.close();
			}
			catch (FileNotFoundException e) {		
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	private static void transformPragraphXMLtoSentenceXML(String srcFolder, String destFolder, SenteceSegmenter openNLPWrapper) {
		Processor.paragraphToSentences(srcFolder, destFolder, openNLPWrapper);
	}
	
	private static void filterSentences(String rootDir, String outputDir, String[] sentencesDirs) {
		Processor.filterSentences(rootDir, outputDir, sentencesDirs);
	}
	
	public static void main(String[] args) {
		
		//transformTextToPragraphXML("data/gutenberg","output/gutenberg/paragraphs");
		//transformPragraphXMLtoSentenceXML("output/gutenberg/paragraphs","output/gutenberg/sentences/opennlp", new OpenNLPWrapper());
		//transformPragraphXMLtoSentenceXML("output/gutenberg/paragraphs","output/gutenberg/sentences/corenlp", new StanfordCoreNLPWrapper());
		filterSentences("output/gutenberg/sentences", "output/gutenberg/sentences/filtered", new String[]{"corenlp", "nltk", "opennlp", "xx"});
		
	}
	



}



