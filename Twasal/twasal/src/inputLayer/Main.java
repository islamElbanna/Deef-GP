package inputLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import parsingLayer.Parser;
import postProcessing.Postprocessing;
import preProcessingLayer.PreProcessing;
import wordsPreprocessingLayer.WordExtractor;
import animationBuilderLayer.AnimatorBuilder;
import arabicToSignTranslation.AutomaticTranslation;
import avatarLayer.Avatar;
import databaseLayer.Database;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class Main {

	public static void main(String[] args) throws SQLException, IOException {
		// load lib
		LexicalizedParser lp = LexicalizedParser
				.loadModel("edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");

		// create parser
		Parser parser = new Parser();

		Database db = new Database();

		// pre-processing
		PreProcessing p = new PreProcessing(lp, parser);

		// Translation
		AutomaticTranslation auto = new AutomaticTranslation();

		// post-processing
		Postprocessing postPre = new Postprocessing();

		// words refinemants
		WordExtractor wordAnalysis = new WordExtractor(db);

		AnimatorBuilder animatorBuilder = new AnimatorBuilder(db);

		Avatar avatar = new Avatar();

		while (true) {
			System.out.println("enter gomla");
			InputStreamReader i = new InputStreamReader(System.in);
			BufferedReader b = new BufferedReader(i);
			String o = b.readLine();

			// pre-processing
			String afterPreProcessing = p.preProcessing(o);
			System.out.println("after preprocessing \n" + afterPreProcessing
					+ "\n");

			// parsing, translation
			String[][] words = auto.translate(lp, afterPreProcessing);

			// post rocessing
			postPre.postProcessing(words, p.getHalflag());
			for (int j = 0; j < words.length; j++) {
				System.out.print(words[j][1] + " ");
			}

			// words refinements
			String[][] wordsAfterProcessing = wordAnalysis
					.extractSentence(words);

			String code = animatorBuilder.buildAnimator(wordsAfterProcessing);

			avatar.show(code);

			//System.out.println(code);

			System.out.println();
			p.AnaFlag = false;
			p.AntFlag = false;
			p.N7noFlag = false;
			p.halFlag = false;
		}
	}

	/**
	 * String connectionURL = "jdbc:mysql://localhost:3306/twasal"; // Change
	 * the connection string according to your db, ip, username and password
	 * Connection con = null; try {
	 * 
	 * // Load the Driver class. Class.forName("com.mysql.jdbc.Driver"); // If
	 * you are using any other database then load the right driver here.
	 * 
	 * //Create the connection using the static getConnection method con =
	 * DriverManager.getConnection (connectionURL, "twasal", "admin");
	 * 
	 * //Create a Statement class to execute the SQL statement Statement stmt =
	 * con.createStatement();
	 * 
	 * //Execute the SQL statement and get the results in a Resultset
	 * stmt.execute
	 * ("CREATE TABLE Employee11(Emp_code integer, Emp_name varchar(10))");
	 * 
	 * // Iterate through the ResultSet, displaying two values // for each row
	 * using the getString method
	 * 
	 * // while (rs.next()) System.out.println("connection done..."); } catch
	 * (SQLException e) { e.printStackTrace(); } catch (Exception e) {
	 * e.printStackTrace(); } finally { // Close the connection con.close(); }
	 */

}
