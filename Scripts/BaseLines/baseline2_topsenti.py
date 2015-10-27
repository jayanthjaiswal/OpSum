from __future__ import unicode_literals
import glob, os, sys,re
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

def splitSentences(text):
	sentenceDelimiters = re.compile(u'[.!?,;:\t\\-\\"\\(\\)\\\'\u2019\u2013]')
	sentenceList = sentenceDelimiters.split(text)
	return sentenceList

def class_1(text):
	#test take arg
	splitted_sentences = splitter.split(text)
	splitted_words = [item for sublist in splitted_sentences for item in sublist]
	postagedsent = nltk.pos_tag(splitted_words)
	words = [u[0] for u in postagedsent if u[1]!="NNP"]
	#print words
	lemwords = [wnl.lemmatize(word) if word!="have" and word!="has" else "have" for word in words]
	feats = dict([(word, True) for word in lemwords + ngrams(lemwords, 2)])
	#print feats
	p = classifier.prob_classify(feats)
	pro = classifier.classify(feats)    
	#print pro, p.prob('pos'), p.prob('neg')
	#print pro
	return p.prob('pos')


alltext={}
allscores={};

def const_summary(text):
	nVertices=1;
	#docs_score=class_1(text)
	for sent in splitSentences(text):
		score=class_1(sent)
		alltext[nVertices]=sent
		allscores[nVertices]=abs(score)
		nVertices=nVertices+1;
          
	subjsents=[x for x in xrange(1,nVertices)]
	subjsents.sort(key=allscores.get, reverse=False)
	
	budget=200;
	avail= 200;
	summary='';
	for x in subjsents:
		sent=alltext[x]
		wc=len(sent.split());
		wc=len(nltk.word_tokenize(sent.strip()));
		if(wc <= avail):
			summary = summary + sent + '.'
			avail = avail - wc;
	return summary



print sys.argv[1], 'open'
text = ''
filename=sys.argv[1]
for line in open(filename, 'r'):
    text = text + ' ' + line

summary=const_summary(text);

filename=sys.argv[2]
FIL=open(filename, 'w')
FIL.write(summary)
FIL.close();
print sys.argv[1], 'done'
