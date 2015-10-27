package opsum;

import java.util.List;

public class ScoreA1 implements ScoreA {
	Summarize summ = null;
	
	public ScoreA1( Summarize sl ){
		summ = sl;
	}
	
	public Double getAScore(List<Integer> summSentences){
		Double res = 0.0;
		for(Integer iSentID : summSentences){
			Sentence sent =  summ.sentList[iSentID];
			Double w = sent.aspect.getWeight();
			Double sScore = summ.sentList[iSentID].dSubjScore;
			res += (w* Math.abs(sScore));
		}
		return res;
	}
}
