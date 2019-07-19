package org.rdorado.cheatsheet.core;

import java.util.ArrayList;

public class TaggedSentence {
	ArrayList<TaggedToken> tokens = new ArrayList<TaggedToken>();

	public void addTaggedToken(String token, String tag) {
		tokens.add(new TaggedToken(token, tag));
	}

	public ArrayList<TaggedToken> getTaggedTokens() {
		return tokens;
	}
}