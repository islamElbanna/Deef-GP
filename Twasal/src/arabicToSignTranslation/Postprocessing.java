package arabicToSignTranslation;

import edu.stanford.nlp.ling.Word;

public class Postprocessing {


	public Postprocessing (){

	}
	
	private void swap(String [][] words, int index1 , int index2){
		for (int j = 0; j < words[0].length; j++) {
			String temp = words[index1][j];
			words[index1][j] = words[index2][j];
			words[index2][j] = temp;
		}
	}
	
	private void editNumbers(String [][] words){
		for (int i = 0; i < words.length; i++) {
			if(words [i][2] != null && words[i][2].equals("num")){
				int in = i+1;
				while (words [in][2] != null && words[in][2].equals("num")){
					swap(words ,i, in);
					in++;
				}
				swap(words ,i, in);
				i = in;
			}
		}
	}
	
	private void checkHala(String [][] words ,boolean hala) {
		if (hala) {
			for (int i = 0; i < words.length; i++) {
				if(words[i][0].equals("ãÇÐÇ")&& words[i][1].contains("WH")){
					words[i][0] = "åá";
				}
			}
		}
		
	}
	
	public void postprocessing(String [][] words , boolean hala){
		editNumbers(words);
		checkHala(words,hala);
	}
}
