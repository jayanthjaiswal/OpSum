package opsum;

/**
* Clustering 
* Assigns sentences to most suitable aspect in a ontology tree
*
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/

public class Clustering {
	Summarize summ = null;
	
	
	public Clustering(Summarize sum){
		summ	=	sum; 
	}
	
	/**
	 *  Run the clustering
	 */
	
	public void run(){
		for(int i=0;i<summ.sentList.length;i++)
			clusterSentenceRandom(i);
	}

	/**
	 *  To be overridden by subclass, chek Hardclustering class
	 */
	
	public void clusterSentenceRandom(int sentID){
	}
}
