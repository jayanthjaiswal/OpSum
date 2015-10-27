package opsum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreA2 implements ScoreA {
	Summarize summ = null;
	
	public ScoreA2( Summarize sl ){
		summ = sl;
	}
	
	public Double getAScore(List<Integer> summSentences){
		Double res = 0.0;
		Map<Aspect, Double> aspScore = new HashMap<Aspect, Double>();
		
		for(Integer iSentID : summSentences){
			Sentence sent =  summ.sentList[iSentID];
			Aspect asp = sent.aspect;
			Double w = sent.aspect.getWeight();
			Double sScore = summ.sentList[iSentID].dSubjScore;
			
			Double score = aspScore.get(asp);
			if(score == null)
				score = 0.0;
			score += Math.abs(sScore);
			aspScore.put(asp, score);
		}
		
		for(Aspect asp: aspScore.keySet()){
			Double w = asp.getWeight();
			Double tScore = aspScore.get(asp); 
			res += Math.min(tScore, asp.getBudget()) * w ;
			
			asp.d_Budget = asp.getBudget() * w;
			asp.d_Score = tScore;
			asp.d_MXScore = Math.max(tScore, asp.d_MXScore);
		}
		
		return res;
	}
}
