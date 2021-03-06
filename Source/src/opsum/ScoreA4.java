package opsum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreA4 implements ScoreA {
	Summarize summ = null;

	public ScoreA4(Summarize sl) {
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

		for (Aspect asp : aspScorePos.keySet()) {

			Double w = asp.getWeight();

			// take maximum of positive sum and negative sum
			Double tScore = aspScorePos.get(asp);
			if (aspScoreNeg.containsKey(asp)) {
				tScore = Math.max(tScore, aspScoreNeg.get(asp));
			}

			// add budgeted score to result
			res += Math.min(tScore, asp.getBudget() * w);
			
			asp.d_Budget = asp.getBudget() * w;
			asp.d_Score = tScore;
			asp.d_MXScore = Math.max(tScore, asp.d_MXScore);

		}

		return res;
	}
}
