from flair.data import Sentence
from flair.models import SequenceTagger

# make a sentence
sentence = Sentence(u'He half closed his eyes and searched the horizon.')

# load the NER tagger
tagger = SequenceTagger.load('pos')

# run NER over sentence
result = tagger.predict(sentence)

print(result)
