package inputLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import parsingLayer.Parser;
import reconstructionLayer.PreProcessing;
import arabicToSignTranslation.AutomaticTranslation;
import arabicToSignTranslation.Postprocessing;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class Main {

	public static void main(String[] args) throws SQLException, IOException {
		LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");
		AutomaticTranslation auto =  new AutomaticTranslation();
		Parser parser =  new Parser();
		PreProcessing p = new PreProcessing( lp,parser);
		Postprocessing postPre = new Postprocessing();
		while (true){
	    	System.out.println("enter gomla");
	    	InputStreamReader i = new InputStreamReader(System.in);
	    	BufferedReader b = new BufferedReader(i);
	    	String o = b.readLine();
	    	String afterPreProcessing = p.run(o); 
	    	System.out.println("after preprocessing \n"+afterPreProcessing+"\n");
	    	String [][]words = auto.translate(lp,afterPreProcessing);
	    	postPre.postprocessing(words, p.getHalflag());
	    	for (int j = 0; j < words.length; j++) {
				System.out.print(words[j][0] + " ");
			}
	    	
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
