package opsum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* Aspect Tree Management 
* Each aspect have information about level, weight, list of keywords, children and parent
*
* @author  Jayaprakash Sundararaj
* @version 1.0
* @since   2014-03-31 
*/

public class Aspect {
	private Double dWeight;
	private Double dBudget;	
	private List<Aspect> Children = new ArrayList<Aspect>();
	private Aspect parent = null;
	private String nodeName = null;
	private Integer nodeNo = -1;
	private List<String> Keywords = new ArrayList<String>();
	private Integer level = -1;
	
	public Double d_Budget = 0.0, d_Score = 0.0, d_MXScore=0.0;

	public Aspect() {
		
	}

	
	/**
	 * Sets the level of a aspect in ontology tree 
	 * @param l integer level of aspect in a tree
	 * @return Nothing.
	 */

	public void setLevel(Integer l){
		level = l;
	}

	/**
	 * returns the level of a aspect in ontology tree 
	 * @return Integer Level.
	 */
	
	public Integer getLevel(){
		return level ;
	}

	public void setWeight(Double w){
		dWeight = w;
	}

	public void setBudget(Double b){
		dBudget = b;
	}
	
	public Double getWeight(){
		return dWeight;
	}

	public Double getBudget(){
		return dBudget;
	}
	

	public void setName(String name) {
		nodeName = name;
	}

	public void setNumber(Integer no) {
		nodeNo = no;
	}

	/**
	 * Set a parent for current aspect
	 * @param Aspect parent 
	 * @return Nothing.
	 */
	public void setParent(Aspect p) {
		parent = p;
	}

	public Aspect getParent() {
		return parent;
	}

	/**
	 * Returns list of children
	 * @return List of aspects.
	 */
	public List<Aspect> getChildren() {
		return Children;
	}

	/**
	 * Returns list of keywords associated with aspect
	 * @return List of strings.
	 */
	
	public List<String> getKeywordList() {
		return Keywords;
	}

	/**
	 * add a string to keyword list.
	 * @param string - new keyword to be added
	 * @return Nothing.
	 */
	public void addKeyword(String keyword) {
		Keywords.add(keyword);
	}

	/**
	 * add a aspect to children.
	 * @param aspect - new child
	 * @return Nothing.
	 */
	public void addChild(Aspect c) {
		Children.add(c);
	}

	public String getName() {
		return nodeName;
	}	
	
	public String toString() {
		String res = "";
		res += ", Name:" + getName();
		return res;
	}
	
	public void print(String prefix){
		Main.print( prefix );
		Main.print( nodeName + ", w=" + getWeight() + ", b=" + getBudget());
		Main.print("\n");
		
		prefix = prefix + "\t";
		for(Aspect asp: getChildren()){
			asp.print( prefix);
		}
	}
	
	public void printOk(String prefix){
		System.out.print( prefix );
		System.out.print( nodeName + ", w=" + getWeight() + ", b=" + getBudget() +", bud=" + d_Budget+", mscore=" + d_MXScore +", score=" + d_Score);
		System.out.print("\n");
		
		prefix = prefix + "\t";
		for(Aspect asp: getChildren()){
			asp.printOk( prefix);
		}
	}
	
}
