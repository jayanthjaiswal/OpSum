package opsum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ScoreA3 implements ScoreA {
	Summarize summ = null;

	public ScoreA3(Summarize sl) {
		summ = sl;
	}

	public Double getAScore(List<Integer> summSentences) {
		Double res = 0.0;
		/* negative sum and posive sum */
		Map<Aspect, Double> aspScorePos = new HashMap<Aspect, Double>();
		Map<Aspect, Double> aspScoreNeg = new HashMap<Aspect, Double>();

		for (Integer iSentID : summSentences) {
			Sentence sent = summ.sentList[iSentID];
			Aspect asp = sent.aspect;
			Double w = sent.aspect.getWeight();
			Double sPolScore = summ.sentList[iSentID].dPolScore;
			Double sSubjScore = summ.sentList[iSentID].dSubjScore;

			if (sPolScore < 0.0) {
				Double score = aspScoreNeg.get(asp);
				if (score == null)
					score = 0.0;
				score += Math.abs(sSubjScore);
				aspScoreNeg.put(asp, score);
			} else {
				Double score = aspScorePos.get(asp);
				if (score == null)
					score = 0.0;
				score += Math.abs(sSubjScore);
				aspScorePos.put(asp, score);
			}

		}

		Set <Aspect> ts = new HashSet<Aspect>();
		ts.addAll( aspScorePos.keySet() );
		ts.addAll( aspScoreNeg.keySet() );
		
		for (Aspect asp : aspScorePos.keySet()) {
			Double w = asp.getWeight();

			// take positive sum
			Double tScorePos = 0.0;
			if( aspScorePos.containsKey(asp) )
				tScorePos = aspScorePos.get(asp);
			
			// take negative sum
			Double tScoreNeg = 0.0;
			if( aspScoreNeg.containsKey(asp) )
				tScoreNeg = aspScoreNeg.get(asp);
			
			Double tScore = Math.min(tScorePos, asp.getBudget() * w) + Math.min(tScoreNeg, asp.getBudget() * w);
			
			// add budgeted score to result
			res += tScore;

			asp.d_Budget = asp.getBudget() * w;
			asp.d_Score = tScore;
			asp.d_MXScore = Math.max(tScore, asp.d_MXScore);
			
		}


		return res;
	}
}
