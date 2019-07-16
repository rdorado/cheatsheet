import spacy

nlp = spacy.load('en_core_web_sm')
doc = nlp(u'He half closed his eyes and searched the horizon.')

result = []
for token in doc:
    result.append(token.text+"_"+token.tag_)
    #print(token.text, token.lemma_, token.pos_, token.tag_, token.dep_, token.shape_, token.is_alpha, token.is_stop)

print(" ".join(result))
