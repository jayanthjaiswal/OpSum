package opsum;

import java.util.ArrayList;
import java.util.List;

import sun.awt.geom.AreaOp.SubOp;
import util.*;


/**
* Main class of summarization
* 
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/

public class Summarize {

	public static Double tradeAlpha = 10.0; // L(S) + tradeOff*A(S)
	public static Double tradeR = 1.0; // c^tradeR
	public static Double tradeCBudget = 0.1; // L(S) = min( Ci(S), alpha *
												// Ci(V))

	public static Aspect aspectRoot = null;
	public static OntCreate ontCreate = null;
	public static Boolean isSingleDone = false;

	public Integer bucketSize = 200;

	public Sentence sentList[];
	public SimWrapper simWrapper;

	public ScoreL scoreL = null;
	public ScoreA scoreA = null;
	public Clustering clustering = null;
	HardClustering hClustering = null;
	
	public List<SubProblem> lSubP = null;
	public SubProblem maxSubP = null;

	public Summarize() {
		bucketSize = 200;
		clustering = new Clustering(this);
		hClustering = new HardClustering(this);

		if (isSingleDone == false)
			singletonInit();
	}

	/**
	* Singleton Initialization
	* 
	* One time loading of ontology tree, IDF, TF information
	*/
	
	public static void singletonInit() {
		if (isSingleDone == false) {
			ontCreate = new OntCreate();
			aspectRoot = ontCreate.run();
			IDF.loadIDF(Constants.IDF_DIR);
			isSingleDone = true;
		}

		Main.println("\t\t********** Aspect Tree Start **********");
		aspectRoot.print("");
		Main.println("\t\t********** Aspect Tree End **********");
	}

	/**
	* Enumerate all possible sub-problems, as per partial enumeration algorithm
	* List lSubP will have all sub problems after this method is called.
	*/

	public void enumAll(){
		lSubP = new ArrayList<SubProblem>();
		for(int i=0;i<sentList.length;i++){
			SubProblem subp = new SubProblem(this);
			subp.add(i);
			lSubP.add(subp);
		}
		for(int i=0;i<sentList.length;i++){
			for(int j=i+1;j<sentList.length;j++){
				SubProblem subp = new SubProblem(this);
				subp.add(i);
				subp.add(j);
				lSubP.add(subp);
			}
		}
		for(int i=0;i<sentList.length;i++){
			for(int j=i+1;j<sentList.length;j++){
				for(int k=j+1;k<sentList.length;k++){
				SubProblem subp = new SubProblem(this);
				subp.add(i);
				subp.add(j);
				subp.add(k);
				lSubP.add(subp);
				}
			}
		}		
	}
	
	/**
	* Post enumeration algorithm
	* @return a subProblem which has maxmium score.
	*/
	public SubProblem PostEnumerate(){
		maxSubP = null;
		Double maxScore = Double.MIN_VALUE;
		enumAll();
		Main.println("Sentence Length = " + sentList.length);
		Main.println("Subproblems Length = " + lSubP.size());
		int subpid = 0;
		for( SubProblem subp : lSubP ){
			subp.doProcess();
			if( subp.currScore > maxScore ){
				maxSubP = subp;
				maxScore = subp.currScore;
			}
			Main.println("Subp = "+subpid +"\tscore =" + subp.currScore + "\tmaxScore ="+maxScore+ "\tLength ="+subp.currBucketSize);			
			subpid ++;
		}
		return maxSubP;
	}
	

	/**
	* Summarize from file
	* @param String - filename
	* @return List of strings - summary
	*/
	public List<Sentence> doSummarizeFromFile(String fileName) {
		String fileContent = FileW.getFileContents(fileName);
		return doSummarizeFromText(fileContent);
	}

	/**
	* Summarize from text
	* @param String - text
	* @return List of strings - summary
	*/
	public List<Sentence> doSummarizeFromText(String fileContent) {

		List<Sentence> res = new ArrayList<Sentence>();
		String sentArr[] = SentenceDetector.run(fileContent);

		// /
		sentList = new Sentence[sentArr.length];

		Main.println("\t\t**********Sentence Construction");
		/**** Sentence Construction */
		int iSentID = 0;
		for (String sent : sentArr) {
			Sentence s = new Sentence(sent);
			s.setSentID(iSentID);
			sentList[iSentID] = s;
			iSentID++;
		}
		Main.println("\t\t********** Sim Computation Started **********");

		/*** Similarity Computation ***/
		simWrapper = new SimWrapper(sentList, this);
		Main.println("\t\t********** Sim Computation Ended **********");

		Main.println("\t\t********** Clusetering Started **********");
		// clustering.run();
		hClustering.run();
		Main.println("\t\t********** Clusetering Ended **********");

		/*** Run algorithm ***/
		
		/*
			return PostEnumerate().res;
		*/
		
		SubProblem sp = new SubProblem(this);
		sp.doProcess();
		
		return sp.res;
	}

}
