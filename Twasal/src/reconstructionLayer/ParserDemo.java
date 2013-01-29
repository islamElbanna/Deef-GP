
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.CoreLabel;  
import edu.stanford.nlp.ling.HasWord;  
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.LabelFactory;
import edu.stanford.nlp.ling.Sentence;  
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

class ParserDemo {

	private String [][] words;
	
	public static void main(String[] args) throws IOException {
	    LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");
	    if (args.length > 0) {
	      System.out.println("________if");
	    //	demoDP(lp, args[0]);      
	    } else {
	    	ParserDemo p = new ParserDemo();
	    	while (true){
	    	System.out.println("enter gomla");
	    	InputStreamReader i = new InputStreamReader(System.in);
	    	BufferedReader b = new BufferedReader(i);
	    	String o = b.readLine();
	    	p.demoAPI(lp,o);
	    	//System.out.println(p.getPhraseType(lp, o, "ÓãÚÊ"));
	    	}
	    }
	}

  public  ArrayList<String> getGrammerLabels(String g){
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
  int i= 0;
  public Tree root;
  public void demoAPI(LexicalizedParser lp, String in) {
    String sent2 = in;
    String [] a = in.split(" ");
    words = new String [a.length][3];
     i= 0;  
    TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
    List<CoreLabel> rawWords2 = tokenizerFactory.getTokenizer(new StringReader(sent2)).tokenize();
    Tree parse = lp.apply(rawWords2);
    Tree t =parse.firstChild();
    
    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
//    System.out.println(tdl);
//    
//    System.out.println("--------------TREE-------------------------");
//    TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
//    tp.printTree(parse);
//    System.out.println("--------------------------------------------");

    ArrayList<String> grammer = getGrammerLabels(tdl.toString());
    System.out.println("=======================NEW TREE========================================================");
    travaseTree(t, grammer) ;
    System.out.println("=======================================================================================");
    root = t;
    parseTree(0,t);
    System.out.println("========================Sign Tree======================================================");
    printTree(t);
    System.out.println("=======================================================================================");
    
//    for (int i = 0; i < words.length; i++) {
//		for (int j = 0; j < words[i].length; j++) {
//			System.out.print(words [i][j] + " , ");
//		}
//		System.out.println();
//	}
    System.out.println();
  }

//  int dx = 40;
//  int ddx = 1;
//  int dy = 40;
//  int x = 280;
//  int yaa = 245;
//  String s = "";
  public  void travaseTree(Tree t , ArrayList<String> grammer ){
	  if(t.children().length==1 && !t.children()[0].isLeaf()&&t.children()[0].children()[0].isLeaf()){
		  t.setLabel(new nodeValue(t.label().value()+","+t.firstChild().label().value()));
		  t.children()[0] = t.children()[0].children()[0];
		  //t.children()[0].parent(t);
	  }
	  if (t.children().length==1 && t.children()[0].isLeaf()){
		  String value = t.label().value();
//		  System.out.println(" i = " + i);
		  words [i][0] = t.children()[0].label().value();  	// word
		  words [i][1] = value; 				// type
		  if (grammer.contains(t.children()[0].value())){
			  int index = grammer.indexOf(t.children()[0].value());
			  if(!grammer.get(index-1).equalsIgnoreCase("root"))
				  t.setLabel(new nodeValue(value +","+grammer.get(index-1)));
			  words [i][2] = grammer.get(index-1);				// Grammer
		  }
		  i++;
	  }
	  System.out.println(t.label().value());
	  for (int i = 0; i < t.children().length; i++) {
		travaseTree(t.children()[i],grammer);
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
  public void parseTree(int index ,Tree t ){
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
		parseTree(i,t.children()[i]);
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
	  Tree parent = t.parent(root);
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

  public String getWordType( LexicalizedParser lp,String word){
	this.demoAPI(lp,word);
    for (int i = 0; i < words.length; i++) 
		if(words[i][0].equalsIgnoreCase(word))
			return words[i][1];
    return null;
  }

  	public String updateWordType( String word  ,String label ){
  		System.out.println("-----------" + label + "  " + word);
	    for (int i = 0; i < words.length; i++) 
			if(words[i][0].equalsIgnoreCase(word))
				 words[i][1] = label;
	    return null;
	  }  
  public String getPhraseType( LexicalizedParser lp,String sentance ,String word){
		this.demoAPI(lp,sentance);
		Tree wordNode = getNode(word, root);
		if(wordNode != null){
		    while (wordNode.parent(root) !=null){
		    	wordNode =wordNode.parent(root);
		    	if (wordNode.label().value().contains("S")){
		    		if(wordNode.label().value().equalsIgnoreCase("SQ") || wordNode.label().value().equalsIgnoreCase("SBARQ"))
		    			return "SQ";
		    	}
		    }
		    return"S";
		}else{
			System.out.println("not Found");
			return "";
		}
  }

  	private Tree getNode(String word ,Tree t) {
  		if (t.label().value().equalsIgnoreCase(word))
  			return t;
  		for (int i = 0; i < t.children().length; i++) {
			return getNode(word, t.children()[i]);
		}
		return t;
	}
}
