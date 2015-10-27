package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
* File Wrapper
* 
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/

public class FileW {
	
	/**
	* Reads the file
	* @param String - file name
	* @return String - content of the file
	*/
	public static String getFileContents(String fileName){
		   String content = null;
		   File file = new File(fileName);
		   try {
		       FileReader reader = new FileReader(file);
		       char[] chars = new char[(int) file.length()];
		       reader.read(chars);
		       content = new String(chars);
		       reader.close();
		   } catch (IOException e) {
		       e.printStackTrace();
		   }
		   return content;
		}
}
