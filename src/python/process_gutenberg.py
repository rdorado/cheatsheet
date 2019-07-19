from cheatsheet.core import transformPragraphXMLtoSentenceXML
from cheatsheet.nltk_wrapper import nltk_segment
from cheatsheet.spacy_wrapper import spacy_segment

def main():
    

	#transformPragraphXMLtoSentenceXML("output/gutenberg/paragraphs","output/gutenberg/sentences/nltk", nltk_segment)
	transformPragraphXMLtoSentenceXML("output/gutenberg/paragraphs","output/gutenberg/sentences/spacy", spacy_segment)
if __name__== "__main__":
  main()