from __future__ import unicode_literals
import glob, os, sys, re
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


##############################################################################


from pygraph.classes.graph import graph
from pygraph.classes.digraph import digraph

from pygraph.algorithms.searching import depth_first_search
from pygraph.algorithms.minmax import minimal_spanning_tree,\
shortest_path, heuristic_search, shortest_path_bellman_ford, maximum_flow, cut_tree
from pygraph.algorithms.heuristics.chow import chow
from pygraph.classes.exceptions import NegativeWeightCycleError

from copy import deepcopy

gr = digraph()

'''
gr.add_nodes([0,1,2,3])
gr.add_edge((0,1), wt=5)
gr.add_edge((1,2), wt=8)
gr.add_edge((2,3), wt=7)
flows, cuts = maximum_flow(gr, 0, 3)
print cuts
'''

##############################################################################


def splitSentences(text):
	sentenceDelimiters = re.compile(u'[.!?,;:\t\\-\\"\\(\\)\\\'\u2019\u2013]')
	sentenceList = sentenceDelimiters.split(text)
	return sentenceList

alltext={}
allscores={}

def const_graph(text):
	nVertices=1;
	#print text
	for sent in splitSentences(text):
		#print sent
		score=class_1(sent)
		alltext[nVertices]=(score, sent)
		allscores[nVertices]=score
		nVertices=nVertices+1;
	src=0;
	dst=nVertices;
	
        gr.add_nodes(xrange(0,nVertices+1))
        
	for i in xrange(1,nVertices):
	  #print i
	  w=alltext[i][0]
          gr.add_edge((0, i), wt=w )          
          gr.add_edge((i, nVertices), wt=1-w)          

	for i in xrange(1,nVertices):
		for j in xrange(i,nVertices):
			if(i==j):
				continue;
			s = 1.0/abs(j-i)
			s = s*s
          		gr.add_edge((i, j), wt=s)
          
	#print gr
	#print alltext
	flows, cuts = maximum_flow(gr, 0, nVertices)
#	print flows
	#print cuts
	subjsents=[x for x in xrange(1,nVertices) if cuts[x] is 0 ]
	subjsents.sort(key=allscores.get, reverse=True)
	
	budget=200;
	avail= 200;
	summary='';
	for x in subjsents:
		sent=alltext[x][1]
		wc=len(sent.split());
		wc=len(nltk.word_tokenize(sent.strip()))
		if(wc <= avail):
			summary = summary + sent + '.'
			avail = avail - wc;
	#print subjsents
	return summary
	
print sys.argv[1], 'open'
text = ''
filename=sys.argv[1]
for line in open(filename, 'r'):
    text = text + ' ' + line
#text='i amused . i will win. I hate this.  '
summary=const_graph(text);
filename=sys.argv[2]
FIL=open(filename, 'w')
FIL.write(summary)
FIL.close();
print sys.argv[1], 'done'
    

