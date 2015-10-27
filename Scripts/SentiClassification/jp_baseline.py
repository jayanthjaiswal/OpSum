from __future__ import unicode_literals
import glob, os, sys
import nltk, os, pickle
from nltk.util import ngrams
from nltk.tokenize import PunktWordTokenizer
from nlpsenti import Splitter,POSTagger
from nltk.stem import WordNetLemmatizer

pkl_file = open("NBJaccard.pickle","rb")
splitter = Splitter()
postagger = POSTagger()
classifier = pickle.load(pkl_file)
wnl = WordNetLemmatizer()

name=os.environ['FILE']
outFILE = "";
lineFILE="";

dataX={}
dataY={}

def class_1(text):
	#test take arg
	splitted_sentences = splitter.split(text)
	splitted_words = [item for sublist in splitted_sentences for item in sublist]
	postagedsent = nltk.pos_tag(splitted_words)
	words = [u[0] for u in postagedsent if u[1]!="NNP"]
	lemwords = [wnl.lemmatize(word) if word!="have" and word!="has" else "have" for word in words]
	feats = dict([(word, True) for word in lemwords + ngrams(lemwords, 2)])
	#print feats
	p = classifier.prob_classify(feats)
	pro = classifier.classify(feats)    
	#print pro, p.prob('pos'), p.prob('neg')
	#print pro
	return p.prob('pos')


def func(dirs):
	global dataX
	global dataY

	dirs=dirs.encode('ascii', 'ignore')
	document=dirs[0:dirs.rfind('.')] #document
	docscore=0.5
	try:
		fileContent = open(document, "r")
		text=fileContent.read();
		fileContent.close();
		docscore=class_1(text);
	except (OSError, IOError) as e:
		docscore=0.5;
	
	files=glob.glob(dirs+"/*");
	files.sort();
	filename=dirs[dirs.rfind('/')+1:] 
	filename=filename[0:filename.rfind('.')]

	resStr = ""+filename+','+str(docscore)

	for filePath in files:

		keyfilename=filePath[filePath.rfind('/')+1:]
		keyfilename=keyfilename.encode('ascii', 'ignore')
		keytuple=keyfilename.split('_');
		if( len(keytuple) == 3):
			continue;
		
		score=0.5;
		try:
			fileContent = open(filePath, "r")
			text=fileContent.read();
			fileContent.close();
			score=class_1(text);
		except (OSError, IOError) as e:
			score=0.5

#		print keytuple
		if(len(keytuple)==3):
			k1=keytuple[0]
			k2=keytuple[1]
			k3=keytuple[2]
			if( k1 not in dataX):
				dataX[k1]={};
				dataY[k1]={};
			if( k2 not in dataX[k1]):
				dataX[k1][k2]={};
				dataY[k1][k2]={};
			if( k3 not in dataX[k1][k2]):
				dataX[k1][k2][k3]=[];
				dataY[k1][k2][k3]=[];
			dataX[k1][k2][k3].append(docscore);
			dataY[k1][k2][k3].append(score);
		else:
			if( keyfilename not in dataX):
				dataX[keyfilename]=[];
				dataY[keyfilename]=[];
			dataX[keyfilename].append(docscore);
			dataY[keyfilename].append(score);
			
		resStr = resStr + ','+str(score) ;
		lineFILE.write(filename+","+str(docscore)+","+keyfilename+","+ str(score)+'\n');
	outFILE.write(resStr[:-1]+'\n')
			

outfile="/home/zerone/opsum/"+name+".senti.all.csv";
outFILE=open(outfile, 'w');

linefile="/home/zerone/opsum/"+name+".senti.line.csv";
lineFILE=open(linefile, 'w');

path="/home/zerone/opsum/"+name+"/*.summ";
ldirs=glob.glob(path)
ldirs=sorted(ldirs)
for dirs in ldirs:
	print dirs
	func(dirs);

outFILE.close();
lineFILE.close();

import pickle
with open("/home/zerone/opsum/"+name+'.senti.dataX.pickle', 'wb') as handle:
  pickle.dump(dataX, handle)
with open("/home/zerone/opsum/"+name+'.senti.dataY.pickle', 'wb') as handle:
  pickle.dump(dataY, handle)


