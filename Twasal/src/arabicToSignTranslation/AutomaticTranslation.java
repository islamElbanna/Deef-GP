package arabicToSignTranslation;

import parsingLayer.*;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;  
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class AutomaticTranslation {

	public String [][] words;
	private Tree arabicTree ;
	private Tree signTree ;  
	private Parser arabicParser = new Parser();
	int i= 0;
	
	public static void main(String[] args) throws IOException {
	    LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");
	    if (args.length > 0) {
	      System.out.println("________if");
	    //	demoDP(lp, args[0]);      
	    } else {
	    	AutomaticTranslation p = new AutomaticTranslation();
	    	while (true){
	    	System.out.println("enter gomla");
	    	InputStreamReader i = new InputStreamReader(System.in);
	    	BufferedReader b = new BufferedReader(i);
	    	String o = b.readLine();
	    	String [][]words = p.translate(lp,o);;
	    	Postprocessing post= new Postprocessing();
	    	post.postprocessing(words, false);
		    	
	    		for (int j = 0; j < words.length; j++) {
					for (int j2 = 0; j2 < words[j].length; j2++) {
						System.out.print(words[j][j2]+" ");
					}
					System.out.println("");
				}
	    	}
	    }
	}

  private ArrayList<String> getGrammerLabels(String g){
  	g = g.replace(",", "");
  	g = g.replace(")", "");
  	g = g.replace("(", " ");
  	g = g.replace("[", "");
  	g = g.replace("]", "");
  	String [] a = g.split(" ");
  	ArrayList<String> aee = new ArrayList<String>();
  	for (int i = 0; i < a.length; i++) {
  		if (i%3 ==0 || i%3==2){
  			if (a[i].contains("-"))
  				aee.add(a[i].substring(0,a[i].indexOf("-")));
  			else
  				aee.add(a[i]);
  		} 
	}
  	System.out.println();    	
  	System.out.println(aee.toString());
  	return aee;
  }
  

  private void parseAndrefineArabic(LexicalizedParser lp, String sent2){
	  	String [] a = sent2.split(" ");
	    words = new String [a.length][3];
	     i= 0;  
	    arabicTree = arabicParser.parseArabic(lp, sent2);
	    ArrayList<String> grammer = getGrammerLabels(arabicParser.grammer.toString());
	    System.out.println("=======================NEW TREE========================================================");
	    refineArabicTree( arabicTree ,grammer) ;
	    System.out.println("=======================================================================================");  
  }
  
  public String [][] translate(LexicalizedParser lp, String sent2) {
    parseAndrefineArabic(lp, sent2);
    signTree = arabicTree;
    ApplySignRules(0,signTree);
    System.out.println("========================Sign Tree======================================================");
    printTree(signTree);
   // arabicParser.printTree(t);
    System.out.println("=======================================================================================");
    System.out.println();
    
    return getSignWords();
  }

  public String[][] getSignWords() {
	List<Tree> leaves= signTree.getLeaves();
	String [][] signWords = new String [leaves.size()][3];
	for (int i = 0; i < signWords.length; i++) {
		for (int j = 0; j < words.length; j++) {
			if(words[j][0].equalsIgnoreCase(leaves.get(i).label().value())){
				signWords[i] [0]= words[j][0];
				signWords[i] [1]= words[j][1];
				signWords[i] [2]= words[j][2];
				break;
			}
		}
	}
	return signWords;
  }

private  void refineArabicTree(Tree arabicTree,ArrayList<String> grammer ){ // annotate the tree with grammar and  reshape the tree  
	  if(arabicTree.children().length==1 && !arabicTree.children()[0].isLeaf()&&arabicTree.children()[0].children()[0].isLeaf()){
		  arabicTree.setLabel(new nodeValue(arabicTree.label().value()+","+arabicTree.firstChild().label().value()));
		  arabicTree.children()[0] = arabicTree.children()[0].children()[0];
		  //t.children()[0].parent(t);
	  }
	  if (arabicTree.children().length==1 && arabicTree.children()[0].isLeaf()){
		  String value = arabicTree.label().value();
//		  System.out.println(" i = " + i);
		  words [i][0] = arabicTree.children()[0].label().value();  	// word
		  words [i][1] = value; 				// type
		  if (grammer.contains(arabicTree.children()[0].value())){
			  int index = grammer.indexOf(arabicTree.children()[0].value());
			  if(!grammer.get(index-1).equalsIgnoreCase("root"))
				  arabicTree.setLabel(new nodeValue(value +","+grammer.get(index-1)));
			  words [i][2] = grammer.get(index-1);				// Grammer
		  }
		  i++;
	  }
	  System.out.println(arabicTree.label().value());
	  for (int i = 0; i < arabicTree.children().length; i++) {
		  refineArabicTree(arabicTree.children()[i],grammer);
	  }
  }

  public void printTree(Tree t ){
	  if (t.isLeaf())
	  System.out.println(t.label().value());
	//  System.out.println("( ");
	  for (int i = 0; i < t.children().length; i++) {
		  printTree(t.children()[i]);
	  }
	//  System.out.println(" ) ");
  } 
  private void ApplySignRules(int index ,Tree t ){
	  if (t.label().value().contains("VP")){
		  	parseVerbPhrase(t);
	  }else if (t.label().value().equals("SBAR")){
		  if (t.firstChild().label().value().contains("WH"))
			parseWasel(index,t);
		  else if (t.firstChild().label().value().contains("IN") && t.firstChild().label().value().contains("mark"))
			parseShart(t);
	  }else if (t.label().value().contains("SBARQ") || t.label().value().contains("SQ")){
		  if (t.firstChild().label().value().contains("WH"))
			parseQuestion(t);
	  }else if (t.label().value().equals("PP")){
		  if(t.firstChild().label().value().contains("IN"))
			  t.removeChild(0);
	  }
	  for (int i = 0; i < t.children().length; i++) {
		  ApplySignRules(i,t.children()[i]);
	  }
  }

private void parseShart(Tree t) {
	// TODO Auto-generated method stub
	if (t.children().length == 2){
		Tree newNode  = t.firstChild();
		t.removeChild(0); // adat el shart 
		Tree sent  = t.firstChild();
		if(sent.children().length == 2){ // el sentence 
			System.out.println("In Shart");
			newNode.setLabel(new nodeValue("Shart"));
			newNode.firstChild().setLabel(new nodeValue("Fe"));
			sent.addChild(1, newNode);
			sent.addChild(newNode);
		}else
			System.err.println("Error In parse Tree -shart- ");			
	}else{
		System.err.println("Error In parse Tree -shart- ");
	}
  }

private void parseQuestion(Tree t) {
	// TODO Auto-generated method stub
	System.out.println("in question");
	if (t.children().length == 2){
		Tree temp = t.children()[0];
		t.children()[0] = t.children()[1];
		t.children()[1] = temp;
	}else{
		t.addChild(t.children()[0]);
		t.removeChild(0);
	}
}

private void parseWasel(int index,Tree t) {
	  Tree parent = t.parent(arabicTree);
	  t.removeChild(0);					// remove adat el wasl
	  if (parent != null){
	  	parent.removeChild(index);
  		parent.addChild(0, t);			// set the wasel phrase in first; 
	  }
  }

private void parseVerbPhrase(Tree t){
	  Tree [] childrens = t.children();
	  boolean fa3le = false;

	  if (childrens[0].label().value().contains("PRT")){
		  if (childrens[0].firstChild().label().value().equalsIgnoreCase("ÓæÝ")){
			  t.removeChild(0);
			  String label = t.firstChild().label().value();
			  t.firstChild().setLabel(new nodeValue("VBF"+label.substring(3, label.length())));
			  updateWordType(t.firstChild().firstChild().label().value(),t.firstChild().label().value());
			  childrens = t.children();
		  }
	  }
	  
	  for (int i = 0; i < childrens.length; i++) {
		if (childrens[i].label().value().contains("VBD") || childrens[i].label().value().contains("VBP")|| childrens[i].label().value().contains("VBF")){
			System.out.println("in VERB");
			t.addChild(childrens[i]);
			t.removeChild(i);
			break;
		}
	  }
	  childrens = t.children();
	  for (int i = 0; i < childrens.length; i++) {
	   if (childrens[i].label().value().contains("iobj")){
		   System.out.println("in FA3el");
		   Tree iobj  = childrens[i]; 
			t.removeChild(i);
			t.addChild(0,iobj);
			fa3le = true;
			break;
		}
	  }
	  childrens = t.children();
	  int pos = 1;
	  for (int i = 0; i < childrens.length; i++) {
	   if (childrens[i].label().value().contains("dobj")){
		   System.out.println("IN DOBJ");
		   if (!fa3le)
			   pos = 0 ;
		   	Tree dobj  = childrens[i]; 
			t.removeChild(i);
			t.addChild(pos,dobj);
			break;
		}
	  }
  }

	private String updateWordType( String word  ,String label ){
		System.out.println("-----------" + label + "  " + word);
	    for (int i = 0; i < words.length; i++) 
			if(words[i][0].equalsIgnoreCase(word))
				 words[i][1] = label;
	    return null;
	  }  
}
