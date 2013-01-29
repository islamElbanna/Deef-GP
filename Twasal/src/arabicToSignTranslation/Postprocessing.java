package arabicToSignTranslation;

public class Postprocessing {
	private String [][] words;
	private boolean hala;

	public Postprocessing (String [][] words , boolean hala){
		this.words = words;
		this.hala = hala;
	}
	
	private void swap(int index1 , int index2){
		for (int j = 0; j < words[0].length; j++) {
			String temp = words[index1][j];
			words[index1][j] = words[index2][j];
			words[index2][j] = temp;
		}
	}
	
	private void editNumbers(){
		for (int i = 0; i < words.length; i++) {
			if(words [i][2] != null && words[i][2].equals("num")){
				int in = i+1;
				while (words [in][2] != null && words[in][2].equals("num")){
					swap(i, in);
					in++;
				}
				swap(i, in);
				i = in;
			}
		}
	}
	
	private void checkHala() {
		if (hala) {
			for (int i = 0; i < words.length; i++) {
				if(words[i][0].equals("ãÇÐÇ")&& words[i][1].contains("WH")){
					words[i][0] = "åá";
				}
			}
		}
		
	}
	
	public void postprocessing(){
		editNumbers();
		checkHala();
	}
}
