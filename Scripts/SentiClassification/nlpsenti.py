from __future__ import unicode_literals
import os
import nltk
import pickle

from nltk.corpus import wordnet as wn
#from sentiwordnet import SentiWordNetCorpusReader, SentiSynset

#f = open('test.txt', 'r')
#text = f.read()
#text = ""
class Splitter(object):
    def __init__(self):
        self.nltk_splitter = nltk.data.load('tokenizers/punkt/english.pickle')
        self.nltk_tokenizer = nltk.tokenize.TreebankWordTokenizer()
 
    def split(self, text):
        """
        input format: a paragraph of text
        output format: a list of lists of words.
            e.g.: [['this', 'is', 'a', 'sentence'], ['this', 'is', 'another', 'one']]
        """
        sentences = self.nltk_splitter.tokenize(text)
        tokenized_sentences = [self.nltk_tokenizer.tokenize(sent) for sent in sentences]
        return tokenized_sentences

class POSTagger(object):
    def __init__(self):
        pass
        
    def pos_tag(self, sentences):
        """
        input format: list of lists of words
            e.g.: [['this', 'is', 'a', 'sentence'], ['this', 'is', 'another', 'one']]
        output format: list of lists of tagged tokens. Each tagged tokens has a
        form, a lemma, and a list of tags
            e.g: [[('this', 'this', ['DT']), ('is', 'be', ['VB']), ('a', 'a', ['DT']), ('sentence', 'sentence', ['NN'])],
                    [('this', 'this', ['DT']), ('is', 'be', ['VB']), ('another', 'another', ['DT']), ('one', 'one', ['CARD'])]]
        """
        wnl = nltk.WordNetLemmatizer()
        pos = [nltk.pos_tag(sentence) for sentence in sentences]
        #adapt format
        pos = [[(word, wnl.lemmatize(word) if word!="have" and word!="has" else "have" , [postag]) for (word, postag) in sentence] for sentence in pos]
        return pos

#splitter = Splitter()
#postagger = POSTagger()
 
#splitted_sentences = splitter.split(text)
#pos_tagged_sentences = postagger.pos_tag(splitted_sentences)
   
def listadd(a,b):
	return [a[i]+b[i] for i in range(len(a))]
    

    
