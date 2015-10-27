package opsum;

import java.util.List;

/**
* L(S) Scoring
* Implementation for scoring L(S) 
* 
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/

public class ScoreL {
	
	Summarize sum = null;
	List<Integer> summSentences = null;
	
	public ScoreL( Summarize sl ){
		sum = sl;
	}
	
	public Double getCiScore1(int sentID){
		Double res = 0.0;
		for(int i: this.summSentences)
				res += sum.simWrapper.getScore(i, sentID);
		return res;
	}
	
	public Double getCScore1(){
		Double res = 0.0;
		for(int j=0;j<sum.sentList.length;j++)
			res += Math.min( sum.sentList[j].getBudgetScore() , getCiScore1(j) );
		return res;
	}
	
	public Double getCScore2(){
		Double res = 0.0;
		for(int j=0;j<sum.sentList.length;j++){
			Double maxMatch = 0.0;
				for(int i: this.summSentences)
					maxMatch = Math.max(maxMatch, sum.simWrapper.getScore(i, j));
			res += maxMatch ;
		}
		return res;
	}
	
	public Double getCScore(List<Integer> summSentences){
		this.summSentences = summSentences;
		return getCScore1();
		//return getCScore2();
	}

}
