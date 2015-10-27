package util;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

/**
* Sentence Detector
* Tokenize file contents into set of sentences
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/

public class SentenceDetector {
	
	/**
	* Runs sentence detector
	* @param String - text
	* @return Array of String - sentences
	*/
	public static String[] run(String paragraph){
		InputStream is;
		SentenceModel model;
		String sentences[] = {null};
		try {
			is = new FileInputStream(Constants.SENTENCE_MODEL);
			model = new SentenceModel(is);
			SentenceDetectorME sdetector = new SentenceDetectorME(model);
			sentences = sdetector.sentDetect(paragraph);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		return sentences;
	}
}
