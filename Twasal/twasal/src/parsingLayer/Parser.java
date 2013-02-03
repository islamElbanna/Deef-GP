package parsingLayer;

import java.io.StringReader;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class Parser implements parser_Interface{
	public List<TypedDependency> grammer;
	
	public Parser() {
		
	}
	
	public Tree parseArabic(LexicalizedParser lp, String sent2){
	    TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
	    List<CoreLabel> rawWords2 = tokenizerFactory.getTokenizer(new StringReader(sent2)).tokenize();
	    Tree parse = lp.apply(rawWords2);
	    Tree t =parse.firstChild();
	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
	    grammer = gs.typedDependenciesCCprocessed();
	    return t;
	}
	public void printTree(Tree parse){
	    System.out.println("--------------TREE-------------------------");
	    TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
	    tp.printTree(parse);
		System.out.println("--------------------------------------------");
	}
	
	public String getWordType(LexicalizedParser lp, String word){
		Tree t =this.parseArabic(lp, word);
		//printTree(t);
		Tree node =getNode(word, t);
		Tree parent = node.parent(t);
		return parent.label().value();
	}
	
	public String getPhraseType( LexicalizedParser lp,String sentance ,String word){
		Tree arabicTree = parseArabic(lp, sentance);
		Tree wordNode = getNode(word, arabicTree);
		if(wordNode != null){
		    while (wordNode.parent(arabicTree) !=null){
		    	wordNode =wordNode.parent(arabicTree);
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
