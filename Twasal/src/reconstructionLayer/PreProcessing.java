import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class PreProcessing implements preProcessing {
	public boolean halFlag = false;
	public boolean AnaFlag = false;
	public boolean AntFlag = false;
	public boolean N7noFlag = false;
	private ParserDemo parser ;
	private LexicalizedParser lp;
	public PreProcessing(LexicalizedParser lp,ParserDemo parser) {
		// TODO Auto-generated constructor stub
		this.parser = parser;
		this.lp= lp;
	}
	private String handleFlags(String []words){
		String newS = "";
		for (int i = 0 ; i < words.length ; i++){
			if (words[i].equalsIgnoreCase("هل")){
				newS += "لماذا";
				halFlag = true;
			}
			else if (words[i].equalsIgnoreCase("انا")){
				newS += words[i];
				AnaFlag = true;
			}
			else if (words[i].equalsIgnoreCase("انت") || words[i].equalsIgnoreCase("انتى")){
				newS += words[i];
				AntFlag = true;
			}
			else if (words[i].equalsIgnoreCase("نحن")){
				newS += words[i];
				N7noFlag = true;
			}
			else
				newS += words[i];
			if (i<words.length-1)
				newS += " ";
		}
		return newS;
	}
	private String handlezerofElZaman(String []words){
		String newS = "";
		String temp = "";
		for (int i = 0 ; i < words.length ; i++){
			if (words[i].equalsIgnoreCase("اليوم") ||words[i].equalsIgnoreCase("امس") ||words[i].equalsIgnoreCase("غدا") ||words[i].equalsIgnoreCase("ظهرا") ||words[i].equalsIgnoreCase("عصرا") ||words[i].equalsIgnoreCase("صباحا") ||words[i].equalsIgnoreCase("مساءا")){
				temp = words[i];
			}
			else{
				newS += words[i] + " ";
			}
		}
		newS += temp;
		return newS;
	}
	private String handleAdwatElNafe(String []words){
		String newS = "";
		String temp = "";
		for (int i = 0 ; i < words.length ; i++){
			if (words[i].equalsIgnoreCase("ليس") ||words[i].equalsIgnoreCase("غير") ||words[i].equalsIgnoreCase("لم") ||words[i].equalsIgnoreCase("لما") ||words[i].equalsIgnoreCase("لن") ||words[i].equalsIgnoreCase("ما") ||words[i].equalsIgnoreCase("لا")){
				temp = "لا";
			}
			else{
				newS += words[i] + " ";
			}
		}
		newS += temp;
		return newS;
	}
	private String handleTa2ElFa3l(String word,String sentence){ // انا سمعت
		String newS = "";
		String output = parser.getWordType(lp,word);
		if (output.contains("V")){
			String removeElTa21 = word.substring(0, word.length()-1);
			String output2 = parser.getWordType(lp,removeElTa21);
			if (output2.contains("V")){
				String type = parser.getPhraseType(lp, sentence, word); 
				System.out.println(type);
				if (type.contains("SQ")){
					if (AntFlag)
						newS += word.substring(0, word.length());
					else {
						newS += word.substring(0, word.length()-1);
						newS += " ";
						newS += "انت";
						AntFlag = true;
					}
				}
				else {
					if (AnaFlag)
						newS += word.substring(0, word.length());
					else {
						newS += word.substring(0, word.length()-1);
						newS += " ";
						newS += "انا";
						AnaFlag = true;
					}
				}
			}
			else{
				newS += word;
			}
		}
		else{
			String removeElTa2 = word.substring(0, word.length()-1);
			String output2 = parser.getWordType(lp,removeElTa2);
			if (output2.contains("N")){
				newS += word;
			}
			else {
				if (parser.getPhraseType(lp, sentence, word).contains("SQ")){
					if (AntFlag)
						newS += word.substring(0, word.length());
					else {
						newS += word.substring(0, word.length()-1);
						newS += " ";
						newS += "انت";
						AntFlag = true;
					}
				}
				else {
					if (AnaFlag)
						newS += word.substring(0, word.length());
					else {
						newS += word.substring(0, word.length()-1);
						newS += " ";
						newS += "انا";
						AnaFlag = true;
					}
				}
			}
		}
		return newS;
	}
	private String handleNaElFa3len(String word){ //نحن سمعنا
		String newS = "";
		String output = parser.getWordType(lp,word);
		if (output.contains("V")){
			String removeElNa1 = word.substring(0, word.length()-2);
			String output2 = parser.getWordType(lp,removeElNa1);
			if (output2.contains("V")){
				newS += word.substring(0, word.length()-2);
				newS += " ";
				newS += "نحن";
				N7noFlag = true;
			}
			else{
				newS += word;
			}
		}
		else{
			String removeElNa = word.substring(0, word.length()-2);
			String output2 = parser.getWordType(lp,removeElNa);
			if (output2.contains("V")){
				newS += word.substring(0, word.length()-2);
				newS += " ";
				newS += "نحن";
				N7noFlag = true;
			}
			else {
				newS += word;
			}
		}
		return newS;
	}
	private String handleTa2ElMo5atabInPresent(String word,String sentence){//هل تسمع
		String newS = "";
		String removeElTa2 = word.substring(1, word.length());
		String output = parser.getWordType(lp,removeElTa2);
		if (output.contains("V")){
			if (parser.getPhraseType(lp, sentence, word).contains("SQ")){
				newS += word.substring(0, word.length());
				newS += " ";
				newS += "انت";
				AntFlag = true;
			}
			else {
				newS += word;
			}
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleTa2ElMo5atabInPresentAndEa2ElTa2nes(String word){//هل تسمعي
		String newS = "";
		String removeElTa2WElEa2 = word.substring(1, word.length()-1);
		String output = parser.getWordType(lp,removeElTa2WElEa2);
		if (output.contains("V")){
			newS += word.substring(0, word.length()-1);
			newS += " ";
			newS += "انت";
			AntFlag = true;
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleTa2ElMo5atabInPresentAndEa2ElTa2nesAndNon(String word){//هل تسمعين
		String newS = "";
		String removeElTa2WElEa2WlNon = word.substring(1, word.length()-2);
		String output = parser.getWordType(lp,removeElTa2WElEa2WlNon);
		if (output.contains("V")){
			newS += word.substring(0, word.length()-2);
			newS += " ";
			newS += "انت";
			AntFlag = true;
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleKafElMaf3ol(String word){//سمعك
		String newS = "";
		String removeElKaf = word.substring(0, word.length()-1);
		String output = parser.getWordType(lp,removeElKaf);
		if (output.contains("V")){
			newS += "انت";
			newS += " ";
			newS += word.substring(0, word.length()-1);
		}
		else{
			newS += word;
		}
		return newS;
	}
	/*private String handleNaElMaf3ol(String word){//سمعنا
		String newS = "";
		String removeElTa2WElEa2 = word.substring(0, word.length()-3);
		//parse(removeElNa1);
		//if (output == "VP"){
			newS += "نحن";
			newS += " ";
			newS += word.substring(0, word.length()-3);
		//}
		//else{
			newS += word;
		//}
		return newS;
	}*/
	private String handleNeElMaf3ol(String word){//سمعنى
		String newS = "";
		String removeElNonWlEa2 = word.substring(0, word.length()-2);
		String output = parser.getWordType(lp,removeElNonWlEa2);
		if (output.contains("V")){
			newS += "انا";
			newS += " ";
			newS += word.substring(0, word.length()-2);
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleKafFelNoun(String word){//كتابك
		String newS = "";
		String removeElKaf = word.substring(0, word.length()-1);
		String output = parser.getWordType(lp,removeElKaf);
		if (output.contains("NP") || output.contains("NN")){
			newS += "انت";
			newS += " ";
			newS += word.substring(0, word.length()-1);
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleEa2FelNoun(String word){//كتابى
		String newS = "";
		String removeElEa2 = word.substring(0, word.length()-1);
		String output = parser.getWordType(lp,removeElEa2);
		if (output.contains("NP") || output.contains("NN")){
			newS += "انا";
			newS += " ";
			newS += word.substring(0, word.length()-1);
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleNaFelNoun(String word){//كتابنا
		String newS = "";
		String removeElNa = word.substring(0, word.length()-2);
		String output = parser.getWordType(lp,removeElNa);
		if (output.contains("NP") || output.contains("NN")){
			newS += "نحن";
			newS += " ";
			newS += word.substring(0, word.length()-2);
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleSingleWord(String sentence,String word){
		String newS = "";
		if (word.length()>=2 && word.charAt(word.length()-1) == 'ت'){
			newS = handleTa2ElFa3l(word,sentence);
		}
		else if(word.length()>=2 && word.substring(word.length()-2, word.length()).equalsIgnoreCase("نا")&&!N7noFlag){
			newS = handleNaElFa3len(word);
			if (newS.equalsIgnoreCase(word)){
				newS = handleNaFelNoun(word);
			}
		}
		else if (word.length()>=2 && word.charAt(0) == 'ت' && !AntFlag){
			if (word.length()>=2 && word.substring(word.length()-2, word.length()).equalsIgnoreCase("ين")){
				newS = handleTa2ElMo5atabInPresentAndEa2ElTa2nesAndNon(word);
				if (newS.equalsIgnoreCase(word)){
					if (word.length()>=2 && word.charAt(word.length()-1) == 'ى'){
						newS = handleTa2ElMo5atabInPresentAndEa2ElTa2nes(word);
						if (newS.equalsIgnoreCase(word)){
							newS = handleTa2ElMo5atabInPresent(word,sentence);
						}
					}
				}
			}
			else{
				if (word.length()>=2 && word.charAt(word.length()-1) == 'ى'){
					newS = handleTa2ElMo5atabInPresentAndEa2ElTa2nes(word);
					if (newS.equalsIgnoreCase(word)){
						newS = handleTa2ElMo5atabInPresent(word,sentence);
					}
				}
				else 
					newS = handleTa2ElMo5atabInPresent(word,sentence);
			}
		}
		else if (word.length()>=2 && word.charAt(word.length()-1) == 'ك'){
			newS = handleKafElMaf3ol(word);
			if (!newS.equalsIgnoreCase(word)){
				String []words = newS.split(" ");
				String m = handleSingleWord(sentence, words[1]);
				if (m.equalsIgnoreCase(words[1]))
					newS = word; // narg3ha zae ma hea 3shan mat3mlsh moshkala سمعك
				else{
					String [] words2 = m.split(" ");
					if(AntFlag){
						newS = words2[0] + " " + words2[1];
					}
					else{
						newS = words2[0] + " " + words2[1] + " " + words [0];
					}
				}
			}
			else {
				newS = handleKafFelNoun(word);
			}
		}
		else if (word.length()>=2 && word.substring(word.length()-2, word.length()).equalsIgnoreCase("نى")){
			newS = handleNeElMaf3ol(word);
			System.out.println(newS+"sssssssssssssssssssssssssss");
			if (!newS.equalsIgnoreCase(word)){
				String []words = newS.split(" ");
				String m = handleSingleWord(sentence,words[1]);
				if (m.equalsIgnoreCase(words[1]))
					newS = word; // narg3ha zae ma hea 3shan mat3mlsh moshkala سمعك
				else{
					String [] words2 = m.split(" ");
					if(AnaFlag){
						newS = words2[0] + " " + words2[1];
					}
					else{
						newS = words2[0] + " " + words2[1] + " " + words [0];
					}
				}
			}
			else{
				newS = handleEa2FelNoun(word);
			}
		}
		
		else {
			newS = word;
		}
		return newS;
	}
	private String handleDama2ar(String sentence, String []words){
		String newS = "";
		for (int i = 0; i < words.length ; i++){
			newS += handleSingleWord(sentence,words[i]);
			if (i < words.length-1)
				newS += " ";
		}
		return newS;
	}
	public String run(String sentence){
		String newS = "";
		String [] words = sentence.split(" ");
		newS = handleFlags(words);
		//words = newS.split(" ");
		//newS = handlezerofElZaman(words);
		words = newS.split(" ");
		newS = handleAdwatElNafe(words);
		words = newS.split(" ");
		newS = handleDama2ar(newS,words);
		return newS;
	}
	public static void main(String[] args) throws IOException {
		LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");
		ParserDemo parser =  new ParserDemo();
		PreProcessing p = new PreProcessing( lp,parser); 
		while (true){
	    	System.out.println("enter gomla");
	    	InputStreamReader i = new InputStreamReader(System.in);
	    	BufferedReader b = new BufferedReader(i);
	    	String o = b.readLine();
	    	String afterPreProcessing = p.run(o); 
	    	parser.demoAPI(lp,afterPreProcessing);
	    	System.out.println(afterPreProcessing);
	    	p.AnaFlag = false;
	    	p.AntFlag = false;
	    	p.N7noFlag = false;
	    	p.halFlag = false;
		}	
	}
}
