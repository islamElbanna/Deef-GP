package postProcessing;

public class Postprocessing implements PostProcessing_Interface {

	public Postprocessing() {

	}

	public void postProcessing(String[][] words, boolean hala) {
		handleNumbers(words);
		checkHala(words, hala);
		handleZerofElmakan(words);
		handleModifiers(words);
	}
	
	private void swap(String[][] words, int index1, int index2) {
		for (int j = 0; j < words[0].length; j++) {
			String temp = words[index1][j];
			words[index1][j] = words[index2][j];
			words[index2][j] = temp;
		}
	}

	private void handleNumbers(String[][] words) {
		for (int i = 0; i < words.length; i++) {
			if (words[i][2] != null && words[i][2].equals("num")) {
				int in = i + 1;
				while (words[in][2] != null && words[in][2].equals("num")) {
					swap(words, i, in);
					in++;
				}
				swap(words, i, in);
				i = in;
			}
		}
	}

	private void checkHala(String[][] words, boolean hala) {
		if (hala) {
			for (int i = 0; i < words.length; i++) {
				if (words[i][0].equals("لماذا") && words[i][1].contains("WH")) {
					words[i][0] = "هل";
				}
			}
		}

	}

	private void handleZerofElmakan(String[][] words) { // فوق شنطة احمد !!!!
		for (int i = 0; i < words.length; i++) {
			if (words[i][0].equals("امام") || words[i][0].equals("وراء")
					|| words[i][0].equals("خلف") || words[i][0].equals("قدام")
					|| words[i][0].equals("يمين") || words[i][0].equals("يسار")
					|| words[i][0].equals("فوق") || words[i][0].equals("تحت")
					|| words[i][0].equals("شمال") || words[i][0].equals("اسفل")
					|| words[i][0].equals("اعلى")) {
				int in = i + 1;
				while (in < words.length
						&& (words[in][1].contains("NNP") || words[in][1]
								.contains("DT"))) {
					// checking el zarf followed by noun with DT or proper noun
					swap(words, i, in);
					System.out.println("in modaf");
					i = in++;
				}
			}
		}
	}

	private void handleModifiers(String[][] words) {
		for (int i = 0; i < words.length; i++) {
			if (words[i][1] != null && words[i][1].contains("NN")
					&& !words[i][1].contains("NNP")
					&& !words[i][1].contains("DTNN")) {
				int in = i + 1;
				while (in < words.length
						&& (words[in][1].contains("NNP") || words[in][1]
								.contains("DT"))) {
					// checking el zarf followed by noun with DT or proper noun
					swap(words, i, in);
					System.out.println("in modaf");
					i = in++;
				}
			}
		}
	}
}