package opsum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ScoreA5 implements ScoreA {
	Summarize summ = null;

	public ScoreA5(Summarize sl) {
		summ = sl;
	}

	public Double getAScore(List<Integer> summSentences) {
		Double res = 0.0;
		/* negative sum and posive sum */
		Map<Aspect, Double> aspScore = new HashMap<Aspect, Double>();

		for (Integer iSentID : summSentences) {
			Sentence sent = summ.sentList[iSentID];
			Aspect asp = sent.aspect;
			Double w = sent.aspect.getWeight();
			Double sScore = summ.sentList[iSentID].dSubjScore;

			Double score = aspScore.get(asp);
			if (score == null)
				score = 0.0;
			score = Math.max(score, Math.abs(sScore));
			aspScore.put(asp, score);

		}

		Set <Aspect> ts = new HashSet<Aspect>();
		ts.addAll( aspScore.keySet() );
		
		for (Aspect asp : aspScore.keySet()) {
			Double w = asp.getWeight();

			// take positive sum
			Double tScore = 0.0;
			if( aspScore.containsKey(asp) )
				tScore = aspScore.get(asp);
			
			res += tScore;

			asp.d_Budget = asp.getBudget() * w;
			asp.d_Score = tScore;
			asp.d_MXScore = Math.max(tScore, asp.d_MXScore);			
		}


		return res;
	}
}
