import nltk

tokens = nltk.word_tokenize(u'He half closed his eyes and searched the horizon.')
 

print(" ".join(  map(lambda x:x[0]+"_"+x[1], nltk.pos_tag(tokens))  ))
