package opsum;

import java.util.List;

/**
* Interface of A(S) Scoring, Each formulation should implements this interface. 
* 
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/

public interface ScoreA {
	public Double getAScore(List<Integer> summSentences);
}
