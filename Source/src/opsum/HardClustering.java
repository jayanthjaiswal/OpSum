package opsum;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
* Hard Clustering 
* Assigns sentences to a most suitable aspect in a ontology tree
*
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/

public class HardClustering {
	Summarize summ = null;
	
	public HardClustering(Summarize sum){
		summ	=	sum; 
	}
	
	/**
	* Runs clustering on list of sentences   
	* @return Nothing.
	*/
	public void run(){
		List<Aspect> lasp = new ArrayList<Aspect>();
		Stack<Aspect> stack = new Stack<Aspect>();
		stack.push(summ.aspectRoot);
		while(stack.empty() == false){
			Aspect node = stack.peek();
			stack.pop();
			lasp.add(node);
			for( Aspect asp: node.getChildren() )
				stack.push(asp);
		}
				
		for(int i=0;i<summ.sentList.length;i++)
			clusterSentenceRandom(i, lasp);
	}

	/**
	* Assigned sentID to one aspects in the list  
	* @param sentID - sentence id
	* @param lasp - list of aspects
	* @return Nothing.
	*/
	public void clusterSentenceRandom(int sentID, List<Aspect> lasp){
		Sentence sent = summ.sentList[sentID]; // sent
		sent.aspect = summ.aspectRoot;
		Double maxScore = 0.0;
		for( Aspect asp : lasp ){
			Double score = getMatch( sent, asp.getKeywordList() );
			if(score >= maxScore){
				sent.aspect = asp;
				maxScore = score;
			}
		}
	}
	
	/**
	* Scores for overlapping words in a sentence and list of clue words in aspect tree  
	* @param sent - sentence object
	* @param KeywordList - list of clue words of a aspect
	* @return matching score
	*/
	
	public Double getMatch( Sentence sent , List<String> keywordList ){
		Double res = 0.0;
		for(String w: keywordList){
			res += sent.getTF(w);
		}
		return res;
	}
}
