package opsum;

import java.util.ArrayList;
import java.util.List;


/**
* Subproblem
* Creation and implementation as per partial enumeration algorithm
* 
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/


public class SubProblem {
	public Summarize summ;	
	public Integer bucketSize = 200;
	public Integer currBucketSize = 0;
	public List<Integer> summSentences;
	public Boolean isSummSentence[] = null;
	public Boolean isOverSentence[] = null;
	List<Sentence> res = new ArrayList<Sentence>();
	Double currScore = 0.0;		

	
	/**
	* 
	* Creates a empty sub problem for summary object
	*/ 
	public SubProblem(Summarize summ){
		summSentences = new ArrayList<Integer>();
		this.summ = summ;
		currBucketSize = 0;
		isSummSentence = new Boolean[summ.sentList.length];
		isOverSentence = new Boolean[summ.sentList.length];
		for(int i=0;i<summ.sentList.length;i++){
			isSummSentence[i] = false;
			isOverSentence[i] = false;
		}
	}
	
	public boolean isSummSentence(int sentID){
		return isSummSentence[sentID];
	}
	
	/**
	* Adds sentID to summary if it is with in budget.
	* and updates scores L(S) and A(S).
	* 
	* @return Nothing.
	*/ 

	public void add(int sentID){		
		Sentence sent = summ.sentList[sentID];
		if( currBucketSize + sent.wordList.length > bucketSize )
			return;
		res.add(sent); 
		summSentences.add(sentID); 
		isSummSentence[sentID] = true;
		currBucketSize = currBucketSize + sent.wordList.length;
		
		Double csore = summ.scoreL.getCScore(this.summSentences);
		Double dscore = summ.scoreA.getAScore(this.summSentences);
		currScore = Summarize.tradeAlpha * csore +  (1-Summarize.tradeAlpha)* dscore;
	}
	
	/**
	* Runs greedy algorithm for this sub problem
	* 
	* @return List of sentences - summary
	*/ 
	public List<Sentence> doProcess() {
		bucketSize = summ.bucketSize;
		
		if( currBucketSize > bucketSize )
			return res;
		
		while (true) {
			Integer sentID = -1;
			Double score, maxDiffScore = -1.0, scoreDiff = 0.0;

			for (int sentId = 0; sentId < summ.sentList.length; sentId++) {
				Sentence sent = summ.sentList[sentId];

				if (isSummSentence[sentId] == true || isOverSentence[sentId] == true)
					continue;

				if (bucketSize != -1
						&& (currBucketSize + sent.wordList.length > bucketSize)){
					isOverSentence[sentId] = true;
					continue;
				}
				
				if( sent.wordList.length <= 0 )
					continue;

				isSummSentence[sentId] = true;
				summSentences.add(sentId); // mark examined

				Double csore = summ.scoreL.getCScore(this.summSentences);
				Double dscore = summ.scoreA.getAScore(this.summSentences);
				score = Summarize.tradeAlpha * csore +  (1-Summarize.tradeAlpha)* dscore;
				scoreDiff = score - currScore;
				scoreDiff = scoreDiff / Math.pow(sent.wordList.length, Summarize.tradeR);
				Main.println("=>"+sent.getSentString());
				//Main.println("L(S): " + csore +  "\tA(S): " + dscore);
				//Main.println("**" + "f(S)=" + currScore +  "\tf(S+"+sentId+")=" + score + "\tdiff="+scoreDiff);
				
				if (scoreDiff > maxDiffScore) {
					sentID = sentId;
					maxDiffScore = scoreDiff;
				}

				isSummSentence[sentId] = false;
				summSentences.remove(summSentences.size() - 1); // mark examined
			}
			
			if (sentID < 0) {
				break;
			}
			
			add(sentID);
		}
		return res;
	}

}
