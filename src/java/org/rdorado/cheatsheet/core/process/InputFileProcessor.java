package org.rdorado.cheatsheet.core.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface InputFileProcessor {

	void processFile(File fileInput, File output, CSProcess process)  throws UnsupportedEncodingException, FileNotFoundException, IOException;

	
}
