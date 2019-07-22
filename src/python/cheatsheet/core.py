from xml.etree.ElementTree import Element, ElementTree, SubElement
from os.path import isfile, join
from enum import Enum
import os
import abc

class POSTaggerOutputType(Enum):
	Text = 1
	XML = 2


class POSTagger(abc.ABC):

	@abc.abstractmethod
	def tag(self, sentece):
		pass

class SentenceSegmenter(abc.ABC):

	@abc.abstractmethod
	def segment(self, sentece):
		pass


class Processor():

	def __init__(self):
		pass

	@staticmethod
	def tagSentences(srcFolder, destFolder, posTagger, outputType):
		if not os.path.exists(destFolder):
			os.makedirs(destFolder)

		for filename in os.listdir(srcFolder):
			if isfile(join(srcFolder, filename)):
				fullname = join(srcFolder, filename) 
				tree = ElementTree() 
				tree.parse(fullname)
				root = tree.getroot()

				if outputType == POSTaggerOutputType.Text:
					with open(join(destFolder,filename), 'w') as outfile:
						for paragraph in root.findall('paragraph'):
							for sentence in paragraph.findall('sentence'):
								tagged = posTagger.tag(sentence.text)
								outfile.write(str(tagged)+"\n")
							


def transformPragraphXMLtoSentenceXML(srcFolder, destFolder, segmenter):
        
	if not os.path.exists(destFolder):
		os.makedirs(destFolder)

	for filename in os.listdir(srcFolder):
		if isfile(join(srcFolder, filename)):
			fullname = join(srcFolder, filename) 
			tree = ElementTree() 
			tree.parse(fullname)
			root = tree.getroot()
			
			resultxml = Element('document') 
			for paragraph in root.findall('paragraph'):							
				nparagr = SubElement(resultxml, 'paragraph')
				nparagr.attrib = paragraph.attrib
				sentences = segmenter.segment(paragraph.text)
				for sentence in sentences:
					nsentence = SubElement(nparagr, 'sentence')
					nsentence.text = sentence				
     
			indent(resultxml)
			ElementTree(resultxml).write(join(destFolder,filename), encoding='UTF-8', xml_declaration=True)
			


def indent(elem, level=0):
    i = "\n" + level*" "
    if len(elem):
        if not elem.text or not elem.text.strip():
            elem.text = i + " "
        if not elem.tail or not elem.tail.strip():
            elem.tail = i
        for elem in elem:
            indent(elem, level+1)
        if not elem.tail or not elem.tail.strip():
            elem.tail = i
    else:
        if level and (not elem.tail or not elem.tail.strip()):
            elem.tail = i
