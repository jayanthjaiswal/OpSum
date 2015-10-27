import networkx as nx
import numpy as np
import glob, os
import sys
import json
import nltk
import numpy
import requests

 
from nltk.tokenize.punkt import PunktSentenceTokenizer
from sklearn.feature_extraction.text import TfidfTransformer, CountVectorizer
 
name=os.environ['FILE']
outFILE = "";
lineFILE="";
pattern="/home/zerone/opsum/"+name+"/*.txt";

def textrank(document):
	sentence_tokenizer = PunktSentenceTokenizer()
	sentences = sentence_tokenizer.tokenize(document)

	bow_matrix = CountVectorizer().fit_transform(sentences)
	normalized = TfidfTransformer().fit_transform(bow_matrix)

	similarity_graph = normalized * normalized.T

	nx_graph = nx.from_scipy_sparse_matrix(similarity_graph)
	scores = nx.pagerank(nx_graph)
	ranked_sent=sorted(((scores[i],s) for i,s in enumerate(sentences)),
		  reverse=True)
	budget=200
	res=[];
	for sentt in ranked_sent:
		sent=sentt[1]
		length=len(sent.split())
		if(budget>=length):
			budget=budget-length;
			res.append(sent);
	return res;	

def score_sentences(sentences, important_words):
	cluster_threshold=5
	scores = []
	sentence_idx = -1

	for s in [nltk.tokenize.word_tokenize(s) for s in sentences]:

		sentence_idx += 1
		word_idx = []

		# For each word in the word list...
		for w in important_words:
			try:
			# Compute an index for important words in each sentence

				word_idx.append(s.index(w))
			except ValueError, e: # w not in this particular sentence
				pass

		word_idx.sort()

		# It is possible that some sentences may not contain any important words
		if len(word_idx)== 0: continue

		# Using the word index, compute clusters with a max distance threshold
		# for any two consecutive words

		clusters = []
		cluster = [word_idx[0]]
		i = 1
		while i < len(word_idx):
			if word_idx[i] - word_idx[i - 1] < cluster_threshold:
				cluster.append(word_idx[i])
			else:
				clusters.append(cluster[:])
				cluster = [word_idx[i]]
				i += 1
		clusters.append(cluster)

		# Score each cluster. The max score for any given cluster is the score 
		# for the sentence.

		max_cluster_score = 0
		for c in clusters:
			significant_words_in_cluster = len(c)
			total_words_in_cluster = c[-1] - c[0] + 1
			score = 1.0 * significant_words_in_cluster \
			* significant_words_in_cluster / total_words_in_cluster

		if score > max_cluster_score:
			max_cluster_score = score

	scores.append((sentence_idx, score))
	return scores    

def baseline4(txt, n=100, cluster_threshold=5):

    # Adapted from "The Automatic Creation of Literature Abstracts" by H.P. Luhn
    #
    # Parameters:
    # * n  - Number of words to consider
    # * cluster_threshold - Distance between words to consider
    # * top_sentences - Number of sentences to return for a "top n" summary
            
    # Begin - nested helper function

    
	# End - nested helper function
	
	sentences = [s for s in nltk.tokenize.sent_tokenize(txt)]
	normalized_sentences = [s.lower() for s in sentences]

	words = [w.lower() for sentence in normalized_sentences for w in
	     nltk.tokenize.word_tokenize(sentence)]

	fdist = nltk.FreqDist(words)

	top_n_words = [w[0] for w in fdist.items() 
	    if w[0] not in nltk.corpus.stopwords.words('english')][:n]

	scored_sentences = score_sentences(normalized_sentences, top_n_words)

	# Summarization Approach 1:
	# Filter out nonsignificant sentences by using the average score plus a
	# fraction of the std dev as a filter

	avg = numpy.mean([s[1] for s in scored_sentences])
	std = numpy.std([s[1] for s in scored_sentences])
	mean_scored = [(sent_idx, score) for (sent_idx, score) in scored_sentences
		   if score > avg + 0.5 * std]

	# Summarization Approach 2:
	# Another approach would be to return only the top N ranked sentences

	ranked_sent=sorted(( (score,sentences[idx]) for idx,score in scored_sentences ),
		  reverse=True)
	print ranked_sent
	print sentences
	budget=200
	res=[];
	for sentt in ranked_sent:
		sent=sentt[1]
		length=len(sent.split())
		wc=len(nltk.word_tokenize(sent.strip()))
		if(budget>=length):
			budget=budget-length;
			res.append(sent);
	return res;	



def main():
	files=glob.glob(pattern);
	files.sort();
	for filename in files:

		print filename

		inFILE=open(filename, 'r')
		document='\n'.join(inFILE.readlines())
		inFILE.close();

		sents=textrank(document);
		summary='\n'.join(sents);

		outfile=filename.replace('.txt', '.txt.summ/TEXTRANK')
		outFILE=open(outfile, 'w')
		outFILE.write(summary);
		outFILE.close();
'''
		sents=baseline4(document);
		print sents
		summary='\n'.join(sents);

		outfile=filename.replace('.txt', '.txt.summ/BASELINE4')
		outFILE=open(outfile, 'w')
		outFILE.write(summary);
		outFILE.close();
'''		

main();
