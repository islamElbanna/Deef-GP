package animationBuilderLayer;

public interface AnimatorBuilder_Interface {

	/**
	 * connect to database and generate the animator code with timing 
	 * @param stream
	 * @return
	 */
	public Code buildAnimator (String [][] stream);
	
}
