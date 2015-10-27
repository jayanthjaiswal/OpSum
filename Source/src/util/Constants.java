package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
* Global constants for this project
* 
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/

public class Constants {
	public static  String CONFIG_BASE_DIR = "/data3/jayaprakash/res/";
	
	public static  String SENTENCE_MODEL = CONFIG_BASE_DIR + "en-sent.bin";
	public static  String TOKENIZER_MODEL = CONFIG_BASE_DIR + "en-token.bin";
	public static  String POSTAG_FILE = CONFIG_BASE_DIR + "en-pos-maxent.bin";

	public static  String WORDNET_DICT_DIR = CONFIG_BASE_DIR + "dict/";
	public static  String SENTI_FILE = CONFIG_BASE_DIR + "SentiWordNet_3.0.0_20130122.txt";
	public static  String IDF_DIR = CONFIG_BASE_DIR + "idf/";
	public static  String ONTOLOGY_FILE = CONFIG_BASE_DIR + "movieOnt.txt";
	
	public static boolean isLoaded = false;
	
	@SuppressWarnings("unused")
	public static void Load() {

		if (isLoaded == true)
			return;

		isLoaded = true;

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("/opsum.properties");
			
			if( input == null ){
				input = new FileInputStream("./opsum.properties");
				if( input == null ){
						return;
				}
			}

			// load a properties file
			prop.load(input);

			Constants.CONFIG_BASE_DIR = prop.getProperty("configdir");
			
			Constants.SENTENCE_MODEL = CONFIG_BASE_DIR + "en-sent.bin";
			Constants.TOKENIZER_MODEL = CONFIG_BASE_DIR + "en-token.bin";
			Constants.POSTAG_FILE = CONFIG_BASE_DIR + "en-pos-maxent.bin";

			Constants.WORDNET_DICT_DIR = CONFIG_BASE_DIR + "dict/";
			Constants.SENTI_FILE = CONFIG_BASE_DIR + "SentiWordNet_3.0.0_20130122.txt";
			Constants.IDF_DIR = CONFIG_BASE_DIR + "idf/";
			Constants.ONTOLOGY_FILE = CONFIG_BASE_DIR + "movieOnt.txt";

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

