#from os.path import isfile, join
import os


def getValidFolders(folders):
	resp = []
	for folder in folders:
		if os.path.isdir(folder):
			resp.append(folder)
		else:
			print("Not valid directory: '"+folder+". Skipping it.")
	return resp

def calculateScore(weights, ids):
	resp = 0;
	for id in ids:
		resp += weights[id]	
	return resp
	
def mergeFiles(sourceFiles, destFile, weights=None, threshold=None):
	if weights == None:
		weights = [1]*len(sourceFiles)
	if threshold == None:
		threshold = (len(sourceFiles) + 1)/2

	lines = []
	maxline = None
	for sourceFile in sourceFiles:
		with open(sourceFile,"r") as srcfile:
			tmplines = srcfile.readlines()
			lines.append(tmplines)
			if maxline == None or len(tmplines) < maxline: 
				maxline = len(tmplines)
	
	nsources = len(sourceFiles)
	with open(destFile,"w") as outfile:
		for i in range(maxline):
			candidates = {}
			for j in range(nsources):
				try:
					candidates[lines[j][i]].append(j)
				except KeyError:
					candidates[lines[j][i]]=[j]
					
			best = 0
			bestline = None
			for line, sourceindxs in candidates.items():
				score = calculateScore(weights, sourceindxs)
				if score > best:
					best = score
					bestline = line
			if best >=threshold:			
				outfile.write(line)
			#else: outfile.write("\n")
	
		

def merge(sourceFolders, destFolder, allFilename=None):
	validFolders = getValidFolders(sourceFolders)
	if len(validFolders) < 2:
		print("Not enough valid directories to merge.")
		return
	
	if not os.path.exists(destFolder):
		os.makedirs(destFolder)		
	
	if allFilename != None:		
		if not os.path.exists(os.path.dirname(allFilename)):
			os.makedirs(os.path.dirname(allFilename))	
		open(allFilename,"w").close()
	
	
	filetargets = os.listdir(validFolders[0])
	for filename in filetargets:
		filesToMerge = []
		isValidFile = True
		for directory in validFolders:
			tmpFile = os.path.join(directory, filename)
			if not os.path.exists(tmpFile) or not os.path.isfile(tmpFile):
				isValidFile = False
				break
			filesToMerge.append(tmpFile)
			
		if isValidFile:
			outFilename = os.path.join(destFolder, filename)
			mergeFiles(filesToMerge, outFilename)
			if allFilename != None:
				with open(allFilename, "a") as allFile, open(outFilename, "r") as outFile:
					for line in outFile.readlines():
						allFile.write(line)

		
merge(["output/gutenberg/tagged/corenlp/","output/gutenberg/tagged/opennlp/"],"output/gutenberg/tagged/compiled/", "output/gutenberg/tagged/compiled/all.train")
