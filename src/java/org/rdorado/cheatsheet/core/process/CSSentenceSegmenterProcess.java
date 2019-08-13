package org.rdorado.cheatsheet.core.process;

import org.rdorado.cheatsheet.segmenter.SentenceSegmenter;

public class CSSentenceSegmenterProcess implements CSProcess {

	SentenceSegmenter segmenter;
	
	public CSSentenceSegmenterProcess(SentenceSegmenter segmenter) {
		this.segmenter=segmenter;
	}

	@Override
	public CSOutput execute(CSInput input) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
