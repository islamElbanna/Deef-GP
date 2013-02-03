package animationBuilderLayer;

import databaseLayer.Database;

public class AnimatorBuilder implements AnimatorBuilder_Interface {

	private Database db;
	
	public AnimatorBuilder(Database database) {
		db = database;
	}
	
	@Override
	public String buildAnimator(String[][] stream) {
		String c = "";
		
		for (int i = 0; i < stream.length; i++) {
			System.out.println(db.getTranslation(stream[i][0]));
			c += db.getTranslation(stream[i][0]);
		}
		
		return c;
	}

}
