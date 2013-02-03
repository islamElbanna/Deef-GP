package databaseLayer;

public interface Database_Interface {

	/**
	 * check if word exist in the database
	 * Return true if found, false if not found
	 * @param word
	 * @return
	 */
	public boolean checkWord (String word);
	
	/**
	 * get translation of word
	 * Return string contain the translation if exist 
	 * Return NULL if the word not exist
	 * @param word
	 * @return
	 */
	public String getTranslation (String word);
	
	/**
	 * insert word with it's translation into the database
	 * get object with the parameters
	 * Return true if successful insertion
	 * @param prams
	 * @return
	 */
	public boolean insert (Parameters prams);
	
	/**
	 * delete word from the database
	 * Return true if successful deletion, false if not 
	 * @param word
	 * @return
	 */
	public boolean delete (String word);
	
	/**
	 * update parameters of word
	 * Return true if successful, false if not
	 * @param prams
	 * @return
	 */
	public boolean update (Parameters prams);
	
}
