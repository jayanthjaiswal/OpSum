package util;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import sun.security.jca.GetInstance;

/**
* Wrapper for Part of speech implementation
*  
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/

public class PosTag {

	private static POSModel model = null;

	/**
	* Singleton implementation
	*  
	*/
	private static POSModel getInstance() {
		if (model == null) {
			InputStream modelIn = null;
			try {
				modelIn = new FileInputStream(util.Constants.POSTAG_FILE);
				model = new POSModel(modelIn);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (modelIn != null) {
					try {
						modelIn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return model;

	}

	/**
	* Part of speech tagging
	* @return list of tags
	*/
	public static String[] tag(String in[]) {
		POSModel model = PosTag.getInstance();
		POSTaggerME tagger = new POSTaggerME(model);
		String tags[] = tagger.tag(in);
		return tags;
	}

}
