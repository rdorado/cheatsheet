package org.rdorado.cheatsheet.core.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class LineByLineFileProcessor implements InputFileProcessor{

	
	public void processFile(File fileInput, File output, CSProcess process) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		BufferedReader inputbr = new BufferedReader(new InputStreamReader(new FileInputStream(fileInput), "UTF8"));
		String line = null;
		while ((line=inputbr.readLine())!=null) {
			process.execute(input)
		}
		
		
		inputbr.close();
	}
	
	
	
}
