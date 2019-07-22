#from os.path import isfile, join
import os


def evaluate(goldfolder, evalfolder):
	ngoldsentences = 0.0
	ncorrectsentences = 0.0
	ngoldtokens = 0.0
	ncorrecttokens = 0.0
	nfiles = 0
	
	for filename in os.listdir(goldfolder):
		if os.path.isfile(os.path.join(evalfolder, filename)):
			nline=0
			with open(os.path.join(goldfolder, filename),"r") as goldfile, open(os.path.join(evalfolder, filename),"r") as evalfile:
				print("Processing file "+filename)
				goldlines = goldfile.readlines()
				evallines = evalfile.readlines()
				ngoldsentences = len(goldlines)
				nfiles+=1
				if ngoldsentences != len(evallines):
					print("File '"+filename+"' differ in number of lines. Skipping it.")
								
				for i in range(ngoldsentences):
					nline+=1
					goldtokens = goldlines[i].split(" ")
					evaltokens = evallines[i].split(" ")
					ngoldtokens+=len(goldtokens)
					if len(goldtokens) != len(evaltokens):
						print("Different number of tokens in line "+str(nline))
						continue
					
					correctSentence = True
					for j in range(len(goldtokens)):
						if goldtokens[j] == evaltokens[j]:
							ncorrecttokens+=1
						else:
							correctSentence = False
					if correctSentence: ncorrectsentences+=1
					#for j in range(min(len(goldtokens),))
		
	if nfiles > 0:
		print(str(ngoldsentences)+" "+str(ncorrectsentences)+" "+str(ngoldtokens)+" "+str(ncorrecttokens))
		print("Sentences in goldstandard: "+str(ngoldsentences)+", correct sentences: "+str(ncorrectsentences)+", accuracy: "+str(ncorrectsentences/ngoldsentences))
		print("Tokens in goldstandard: "+str(ngoldtokens)+", correct tokens: "+str(ncorrecttokens)+", accuracy: "+str(ncorrecttokens/ngoldtokens))
	else:
		print("No files to compare where found in the folder")
		
evaluate("data/treebank/tagged/", "output/treebank/tagged/corenlp/")
evaluate("data/treebank/tagged/", "output/treebank/tagged/opennlp/")