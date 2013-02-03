package wordsPreprocessingLayer;

//import reconstructionLayer.TokenStream;

public interface Refinement {

	
	/**
	 * refine the token stream and return words stream
	 * @param str
	 * @return
	 */
	public String [][] extractSentence (String [][] words);
	
}
