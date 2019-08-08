package org.rdorado.cheatsheet.core;

import java.util.ArrayList;

public class CSTask {
	
	ArrayList<CSInput> input = new ArrayList<CSInput>();
	ArrayList<CSProcess> processes = new ArrayList<CSProcess>();
	ArrayList<CSProcessor> processors = new ArrayList<CSProcessor>();
	
	public void execute() {
		for (CSProcessor processor : processors) {
			processor.execute();
		}
	}
}
