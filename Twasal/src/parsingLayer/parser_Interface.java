package parsingLayer;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;

public interface parser_Interface {

	public Tree parseArabic(LexicalizedParser lp, String sent2);
	
	public void printTree(Tree parse);
	
	public String getWordType(LexicalizedParser lp, String word);
	
}
