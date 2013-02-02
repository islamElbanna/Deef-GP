package animationBuilderLayer;

import databaseLayer.Database;

public class AnimatorBuilder implements AnimatorBuilder_Interface {

	private Database db;
	
	public AnimatorBuilder(Database database) {
		db = database;
	}
	
	@Override
	public Code buildAnimator(String[][] stream) {
		Code c = new Code();
		
		for (int i = 0; i < stream.length; i++) {
			c.addCode(db.getTranslation(stream[i][0]));
		}
		
		return c;
	}

}
