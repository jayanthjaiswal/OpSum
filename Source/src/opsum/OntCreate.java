package opsum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import util.WordNetStem;

/**
* Ontology Tree - Constructing ontology tree 
* 
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/



public class OntCreate {

	/**
	* Creates ontology tree 
	* 
	*/
	public Aspect run() {
		return loadOntologyTree();
	}


	/**
	* loads ontology tree from ontology file.
	* Please refer manual for location of ontology file.
	*  
	* @return Aspect - root of ontology tree constructed.
	*/
	public Aspect loadOntologyTree() {
		BufferedReader br;
		String line = null;
		Map<Integer, Aspect> map = new HashMap<Integer, Aspect>();
		try {
			br = new BufferedReader(new FileReader( util.Constants.ONTOLOGY_FILE ));
			while ((line = br.readLine()) != null) {
				
				String arr[] = line.split("\t");
				Integer no = Integer.parseInt( arr[0] );
				String keywords[] = arr[1].split(",");
				
				Aspect node = new Aspect();
				node.setName( keywords[0] ); // first keyword as name
				node.setNumber( no ); // no 
				for(String kw:keywords){ // add all keywords
					//node.addKeyword(kw);
					//TODO
					node.addKeyword( WordNetStem.getWordNetStem(kw) );
				}
				Aspect parent = map.get( no/10 ); // get parent
				Integer level  = 0;
				if(parent != null){
					parent.addChild(node);
					level = parent.getLevel();
				}
				node.setParent(parent); // set parent
				level = level+1;
				node.setLevel(level); // set level
				node.setBudget(1.0/level); // Budget
				node.setWeight(1.0/level); // Weight
				
				map.put(no, node); // add current node to hash
				
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map.get(0); // root
	}


}
