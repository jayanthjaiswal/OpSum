package opsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import util.FileW;
import util.SentenceDetector;

public class Baseline {
	Sentence[] sentList = null;
	
	public void doSummarizeFromFile(String fileName){
		String fileContent = FileW.getFileContents(fileName);
		doSummarizeFromText(fileContent);
	}
	
	public void doSummarizeFromText(String fileContent) {

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
		
		return ;
	}

	public List<Sentence> doProcessBaseLine1() {
		List <Sentence> res = new ArrayList<Sentence>();
		int budget = 200;
		for( Sentence sent: sentList ){
			if( sent.wordList.length <= budget ){
				res.add(sent);
				budget = budget - sent.wordList.length; 
			}
		}
		return res;
	}

	public List<Sentence> doProcessBaseLine2() {
		List <Sentence> res = new ArrayList<Sentence>();
		
		Sentence [] sl = Arrays.copyOf(sentList, sentList.length);
		Comparator<Sentence> comp = new Comparator<Sentence>() {

			public int compare(Sentence arg0, Sentence arg1) {
				Double a = Math.abs(arg0.dSubjScore);
				Double b = Math.abs(arg1.dSubjScore);
				return -1*a.compareTo(b);
			}
		};
		Arrays.sort(sl, comp);
		int budget = 200;
		for( Sentence sent: sl ){
			if( sent.wordList.length <= budget ){
				res.add(sent);
				budget = budget - sent.wordList.length; 
			}
		}
		return res;
	}

}
