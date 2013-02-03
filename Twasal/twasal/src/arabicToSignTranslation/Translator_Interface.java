package arabicToSignTranslation;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;

public interface Translator_Interface {

	public void printTree(Tree t);
	
	public String[][] getSignWords();
	
	public String[][] translate(LexicalizedParser lp, String sent2);
	
}
