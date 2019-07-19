from os import listdir
from os.path import isfile, join
from xml.etree.ElementTree import Element, ElementTree, SubElement


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

def transformPragraphXMLtoSentenceXML(srcFolder, destFolder, segmenter):
	for filename in listdir(srcFolder):
		if isfile(join(srcFolder, filename)):
			fullname = join(srcFolder, filename) 
			print(fullname)
			tree = ElementTree() 
			tree.parse(fullname)
			root = tree.getroot()
			
			resultxml = Element('document') 
			for paragraph in root.findall('paragraph'):							
				nparagr = SubElement(resultxml, 'paragraph')
				nparagr.attrib = paragraph.attrib
				sentences = segmenter(paragraph.text)
				for sentence in sentences:
					nsentence = SubElement(nparagr, 'sentence')
					nsentence.text = sentence				
     
			indent(resultxml)
			ElementTree(resultxml).write(join(destFolder,filename), encoding='UTF-8', xml_declaration=True)
			





