package org.rdorado.cheatsheet.core.process;

import java.util.ArrayList;

public class CSTask {
	
	CSInput input; // = new ArrayList<CSInput>();
	ArrayList<CSProcess> processes = new ArrayList<CSProcess>();
	ArrayList<CSMixer> mixers = new ArrayList<CSMixer>();
	ArrayList<CSOutput> outputs = new ArrayList<CSOutput>();
	//ArrayList<CSFilter> filters = new ArrayList<CSFilter>();
	//ArrayList<CSEvalutor> evaluators = new ArrayList<CSEvalutor>();
	
	public void execute() {
		
		for (CSProcess processor : processes) {
			CSOutput output = input.process(processor);
		}
		/*
			processor.execute(input);
		}*/
	}

	public void add(CSProcess process) {
		
		
	}
}
