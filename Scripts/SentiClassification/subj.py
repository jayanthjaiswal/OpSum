from __future__ import unicode_literals
import glob, os, sys
import nltk, os, pickle
from nltk.util import ngrams
from nltk.tokenize import PunktWordTokenizer
from nlpsenti import Splitter,POSTagger
from nltk.stem import WordNetLemmatizer

pkl_file = open("NBsubj.pickle","rb")
splitter = Splitter()
postagger = POSTagger()
classifier = pickle.load(pkl_file)
wnl = WordNetLemmatizer()


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
	#pro = classifier.classify(feats)    
	#print pro, p.prob('pos'), p.prob('neg')
	#print pro
	return p.prob('sub')

print class_1("it is bad");


