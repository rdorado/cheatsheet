from spacy.lang.en import English


def spacy_segment(sentence):
	nlp = English()
	
	sentencizer = nlp.create_pipe("sentencizer")
	nlp.add_pipe(sentencizer)
	doc = nlp(sentence)
	return list(doc.sents)

