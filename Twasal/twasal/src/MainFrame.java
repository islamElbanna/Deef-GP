import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import parsingLayer.Parser;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class MainFrame {

	public static void main(String[] args) throws IOException {
		LexicalizedParser lp = LexicalizedParser
				.loadModel("edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");
		if (args.length > 0) {
			System.out.println("________if");
			// demoDP(lp, args[0]);
		} else {
			// AutomaticTranslation p = new AutomaticTranslation();
			Parser ap = new Parser();
			while (true) {
				System.out.println("enter gomla");
				InputStreamReader i = new InputStreamReader(System.in);
				BufferedReader b = new BufferedReader(i);
				String o = b.readLine();
				// System.out.println(ap.getWordType(lp, o));
				System.out.println(ap.getPhraseType(lp, o, "«·Ê·œ"));
				// String [][]words = p.translate(lp,o);;
				// Postprocessing post= new Postprocessing(words, false);
				// post.postprocessing();

			}
		}

	}

}
