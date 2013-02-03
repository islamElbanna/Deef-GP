package wordsPreprocessingLayer;

import databaseLayer.Database;

public class WordExtractor implements Refinement {

	/**
	 * @param args
	 */
	private String[][] input;
	private String[][] output;
	private String word;
	private String subject;
	private String object;
	private String prefix;
	private String form;
	private final int PAST = 0;
	private final int PRESENT = 1;
	private final int ORDER = 2;
	private final int WORD = 3;
	private Database db;

	public WordExtractor(Database database) {
		word = "";
		subject = "";
		object = "";
		prefix = "";
		form = "single";
		
		db = database;
	}

	public void removeVerbPrefix() {
		prefix = word.substring(0, 1);
		word = "Ì" + word.substring(1);
	}

	public void removeWordPrefix() {
		if (word.length() > 1) {
			String tempPrefix = word.substring(0, 2);
			if (tempPrefix.equals("«·")) {
				prefix = word.substring(0, 2);
				word = word.substring(2);
			}
		}

	}

	public void removePresentObject() {

		if (word.length() > 4) {
			removeObject();
		}
	}

	public void removePastObject() {

		if (word.length() > 3) {
			removeObject();
		}
	}

	public void removeObject() {

		if (word.length() > 3) {
			String suffix1 = word.substring(word.length() - 1);
			String suffix2 = word.substring(word.length() - 2);
			String suffix3 = word.substring(word.length() - 3);

			if (suffix3.equals("Â„«") || suffix3.equals("ﬂ„«")) {

				object = suffix3;
				word = word.substring(0, word.length() - 3);

			} else if (suffix2.equals("Â„") || suffix2.equals("ﬂ„")
					|| suffix2.equals("Â«") || suffix2.equals("‰«")
					|| suffix2.equals("‰Ï")) {

				object = suffix2;
				word = word.substring(0, word.length() - 2);

			} else if (suffix1.equals("Â") || suffix1.equals("ﬂ")) {

				object = suffix1;
				word = word.substring(0, word.length() - 1);

			}
		}
	}

	public void removeType() {
		if (word.length() > 3) {
			String suffix2 = word.substring(word.length() - 2);
			if (suffix2.equals("«‰")) {
				form = "dual";
				word = word.substring(0, word.length() - 2);
			} else if (suffix2.equals("Ê‰") || suffix2.equals("« ")) {
				form = "plural";
				word = word.substring(0, word.length() - 2);
			}

		}
	}

	public void removePastSubject() {

		if (word.length() > 3) {

			String suffix1 = word.substring(word.length() - 1);
			String suffix2 = word.substring(word.length() - 2);
			String suffix3 = word.substring(word.length() - 3);

			if (suffix3.equals(" „Ê") || suffix3.equals(" „«")) {

				subject = suffix3;
				word = word.substring(0, word.length() - 3);

			} else if (suffix2.equals("‰«") || suffix2.equals("Ê«")
					|| suffix2.equals(" „")) {

				subject = suffix2;
				word = word.substring(0, word.length() - 2);

			} else if (suffix1.equals("Ê") || suffix1.equals(" ")
					|| suffix1.equals("«")) {

				subject = suffix1;
				word = word.substring(0, word.length() - 1);

			}
		}
	}

	public void removePresentSubject() {

		if (word.length() > 4) {

			String suffix1 = word.substring(word.length() - 1);
			String suffix2 = word.substring(word.length() - 2);

			if (suffix2.equals("«‰") || suffix2.equals("Ê‰")
					|| suffix2.equals("Ì‰")) {

				subject = suffix2;
				word = word.substring(0, word.length() - 2);

			} else if (suffix1.equals("«") || suffix1.equals("Ê")
					|| suffix1.equals("Ì")) {

				subject = suffix1;
				word = word.substring(0, word.length() - 1);

			}
		}
	}

	public void removeOrderSubject() {

		if (word.length() > 4) {

			String suffix1 = word.substring(word.length() - 1);
			String suffix2 = word.substring(word.length() - 2);

			if (suffix2.equals("Ê«")) {

				subject = suffix2;
				word = word.substring(0, word.length() - 2);

			} else if (suffix1.equals("«") || suffix1.equals("Ê")
					|| suffix1.equals("Ì")) {

				subject = suffix1;
				word = word.substring(0, word.length() - 1);

			}
		}
	}

	public void reformWord() {
		String suffix1 = word.substring(word.length() - 1);
		if (suffix1.equals(" ")) {

			word = word.substring(0, word.length() - 1) + "…";

		}
	}

	public void reformPast() {

		String suffix1 = word.substring(word.length() - 1);
		if (suffix1.equals("Ê") || suffix1.equals(" ") || suffix1.equals("«")) {

			subject = suffix1;
			word = word.substring(0, word.length() - 1);

		}
		word = word.substring(0, word.length() - 1) + "«"
				+ word.substring(word.length() - 1);

	}

	public void reformOrder() {

		if (word.length() > 2) {
			removeObject();
			String suffix1 = word.substring(word.length() - 1);
			String suffix2 = word.substring(word.length() - 2);

			if (suffix2.equals("Ê«")) {

				subject = suffix2;
				word = word.substring(0, word.length() - 2);

			} else if (suffix1.equals("«") || suffix1.equals("Ê")
					|| suffix1.equals("Ì")) {

				subject = suffix1;
				word = word.substring(0, word.length() - 1);

			}
		}
	}

	public String extractWord(String input, int type) {

		word = input;
		prefix = "";
		object = "";
		subject = "";
		form = "single";

		switch (type) {
		case PAST:
			removePastObject();
			removePastSubject();
			// reformPast();
			break;

		case PRESENT:
			removeVerbPrefix();
			removePresentObject();
			removePresentSubject();
			break;

		case ORDER:
			removePresentObject();
			removeOrderSubject();
			reformOrder();
			break;

		case WORD:
			removeWordPrefix();
			removeObject();
			removeType();
			// reformWord();
			break;

		default:
			break;
		}

		System.out.println("Prefix: " + prefix + "\n" + "Word: " + word + "\n"
				+ "Subject: " + subject + "\n" + "Object: " + object + "\n"
				+ "Form: " + form);

		return word;

	}

	public int getWordType(String word) {

		if (word.contains("VBD")) {
			return 0;
		} else if (word.contains("VBP")) {
			return 1;
		} else if (word.equalsIgnoreCase("somthing")) {
			return 2;
		} else {
			return 3;
		}
	}

	public String[][] extractSentence(String[][] in) {

		input = in;
		output = new String[input.length][3];

		for (int i = 0; i < input.length; i++) {

			output[i][0] = extractWord(input[i][0], getWordType(input[i][1]));
			output[i][1] = form;
			output[i][2] = input[i][1];
		}
		return output;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//WordExtractor w = new WordExtractor();
		//w.extractWord("”Ê›", 3);
		// w.extractSentence(new String [3][3]);

	}

}
