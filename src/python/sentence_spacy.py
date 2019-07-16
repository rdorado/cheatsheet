from spacy.lang.en import English

nlp = English()

sentencizer = nlp.create_pipe("sentencizer")
#from spacy.pipeline import Sentencizer
#sentencizer = Sentencizer()

nlp.add_pipe(sentencizer)
doc = nlp(u"This is a sentence. This is another sentence.")
for tmp in  list(doc.sents): print(tmp)
assert len(list(doc.sents)) == 2
