package preProcessingLayer;

import arabicToSignTranslation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import parsingLayer.Parser;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;


public class PreProcessing implements PreProcessing_Interface{
	public boolean halFlag = false;
	public boolean AnaFlag = false;
	public boolean AntFlag = false;
	public boolean N7noFlag = false;
	private Parser parser ;
	private LexicalizedParser lp;
	
	
	public PreProcessing(LexicalizedParser lp,Parser parser) {
		// TODO Auto-generated constructor stub
		this.parser = parser;
		this.lp= lp;
	}
	public boolean getHalflag(){
		return halFlag;
	}
	private String handleFlags(String []words){
		String newS = "";
		for (int i = 0 ; i < words.length ; i++){
			if (words[i].equalsIgnoreCase("Â·")){
				newS += "·„«–«";
				halFlag = true;
			}
			else if (words[i].equalsIgnoreCase("«‰«")){
				newS += words[i];
				AnaFlag = true;
			}
			else if (words[i].equalsIgnoreCase("«‰ ") || words[i].equalsIgnoreCase("«‰ Ï")){
				newS += words[i];
				AntFlag = true;
			}
			else if (words[i].equalsIgnoreCase("‰Õ‰")){
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
			if (words[i].equalsIgnoreCase("«·ÌÊ„") ||words[i].equalsIgnoreCase("«„”") ||words[i].equalsIgnoreCase("€œ«") ||words[i].equalsIgnoreCase("ŸÂ—«") ||words[i].equalsIgnoreCase("⁄’—«") ||words[i].equalsIgnoreCase("’»«Õ«") ||words[i].equalsIgnoreCase("„”«¡«")){
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
			if (words[i].equalsIgnoreCase("·Ì”") ||words[i].equalsIgnoreCase("€Ì—") ||words[i].equalsIgnoreCase("·„") ||words[i].equalsIgnoreCase("·„«") ||words[i].equalsIgnoreCase("·‰") ||words[i].equalsIgnoreCase("„«") ||words[i].equalsIgnoreCase("·«")){
				temp = "·«";
			}
			else{
				newS += words[i] + " ";
			}
		}
		newS += temp;
		return newS;
	}
	private String handleTa2ElFa3l(String word,String sentence){
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
						newS += "«‰ ";
						AntFlag = true;
					}
				}
				else {
					if (AnaFlag)
						newS += word.substring(0, word.length());
					else {
						newS += word.substring(0, word.length()-1);
						newS += " ";
						newS += "«‰«";
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
						newS += "«‰ ";
						AntFlag = true;
					}
				}
				else {
					if (AnaFlag)
						newS += word.substring(0, word.length());
					else {
						newS += word.substring(0, word.length()-1);
						newS += " ";
						newS += "«‰«";
						AnaFlag = true;
					}
				}
			}
		}
		return newS;
	}
	private String handleNaElFa3len(String word){
		String newS = "";
		String output = parser.getWordType(lp,word);
		if (output.contains("V")){
			String removeElNa1 = word.substring(0, word.length()-2);
			String output2 = parser.getWordType(lp,removeElNa1);
			if (output2.contains("V")){
				newS += word.substring(0, word.length()-2);
				newS += " ";
				newS += "‰Õ‰";
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
				newS += "‰Õ‰";
				N7noFlag = true;
			}
			else {
				newS += word;
			}
		}
		return newS;
	}
	private String handleTa2ElMo5atabInPresent(String word,String sentence){//Â·  ”„⁄
		String newS = "";
		String removeElTa2 = word.substring(1, word.length());
		String output = parser.getWordType(lp,removeElTa2);
		if (output.contains("V")){
			if (parser.getPhraseType(lp, sentence, word).contains("SQ")){
				newS += word.substring(0, word.length());
				newS += " ";
				newS += "«‰ ";
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
	private String handleTa2ElMo5atabInPresentAndEa2ElTa2nes(String word){//Â·  ”„⁄Ì
		String newS = "";
		String removeElTa2WElEa2 = word.substring(1, word.length()-1);
		String output = parser.getWordType(lp,removeElTa2WElEa2);
		if (output.contains("V")){
			newS += word.substring(0, word.length()-1);
			newS += " ";
			newS += "«‰ ";
			AntFlag = true;
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleTa2ElMo5atabInPresentAndEa2ElTa2nesAndNon(String word){//Â·  ”„⁄Ì‰
		String newS = "";
		String removeElTa2WElEa2WlNon = word.substring(1, word.length()-2);
		String output = parser.getWordType(lp,removeElTa2WElEa2WlNon);
		if (output.contains("V")){
			newS += word.substring(0, word.length()-2);
			newS += " ";
			newS += "«‰ ";
			AntFlag = true;
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleKafElMaf3ol(String word){//”„⁄ﬂ
		String newS = "";
		String removeElKaf = word.substring(0, word.length()-1);
		String output = parser.getWordType(lp,removeElKaf);
		if (output.contains("V")){
			newS += "«‰ ";
			newS += " ";
			newS += word.substring(0, word.length()-1);
		}
		else{
			newS += word;
		}
		return newS;
	}
	/*private String handleNaElMaf3ol(String word){//”„⁄‰«
		String newS = "";
		String removeElTa2WElEa2 = word.substring(0, word.length()-3);
		//parse(removeElNa1);
		//if (output == "VP"){
			newS += "‰Õ‰";
			newS += " ";
			newS += word.substring(0, word.length()-3);
		//}
		//else{
			newS += word;
		//}
		return newS;
	}*/
	private String handleNeElMaf3ol(String word){//”„⁄‰Ï
		String newS = "";
		String removeElNonWlEa2 = word.substring(0, word.length()-2);
		String output = parser.getWordType(lp,removeElNonWlEa2);
		if (output.contains("V")){
			newS += "«‰«";
			newS += " ";
			newS += word.substring(0, word.length()-2);
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleKafFelNoun(String word){//ﬂ «»ﬂ
		String newS = "";
		String removeElKaf = word.substring(0, word.length()-1);
		String output = parser.getWordType(lp,removeElKaf);
		if (output.contains("NP") || output.contains("NN")){
			newS += "«‰ ";
			newS += " ";
			newS += word.substring(0, word.length()-1);
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleEa2FelNoun(String word){//ﬂ «»Ï
		String newS = "";
		String removeElEa2 = word.substring(0, word.length()-1);
		String output = parser.getWordType(lp,removeElEa2);
		if (output.contains("NP") || output.contains("NN")){
			newS += "«‰«";
			newS += " ";
			newS += word.substring(0, word.length()-1);
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleNaFelNoun(String word){//ﬂ «»‰«
		String newS = "";
		String removeElNa = word.substring(0, word.length()-2);
		String output = parser.getWordType(lp,removeElNa);
		if (output.contains("NP") || output.contains("NN")){
			newS += "‰Õ‰";
			newS += " ";
			newS += word.substring(0, word.length()-2);
		}
		else{
			newS += word;
		}
		return newS;
	}
	private String handleDama2erKolha(String sentence,String word){
		String newS = "";
		if (word.length()>=2 && word.charAt(word.length()-1) == ' '){
			newS = handleTa2ElFa3l(word,sentence);
		}
		if (newS.length() == 0 || newS.equalsIgnoreCase(word)) {
			if(word.length()>=4 && word.substring(word.length()-2, word.length()).equalsIgnoreCase("‰«")&&!N7noFlag){
				// lenght = 4 3shan ana matb2ash na7no Alef
				newS = handleNaElFa3len(word);
				if (newS.equalsIgnoreCase(word)){
					newS = handleNaFelNoun(word);
				}
			}
		}
		
		if (newS.length() == 0 || newS.equalsIgnoreCase(word)) {
			if (word.length()>=2 && word.charAt(0) == ' ' && !AntFlag){
				if (word.length()>=2 && word.substring(word.length()-2, word.length()).equalsIgnoreCase("Ì‰")){
					newS = handleTa2ElMo5atabInPresentAndEa2ElTa2nesAndNon(word);
					if (newS.equalsIgnoreCase(word)){
						if (word.length()>=2 && word.charAt(word.length()-1) == 'Ï'){
							newS = handleTa2ElMo5atabInPresentAndEa2ElTa2nes(word);
							if (newS.equalsIgnoreCase(word)){
								newS = handleTa2ElMo5atabInPresent(word,sentence);
							}
						}
					}
				}
				else{
					if (word.length()>=2 && word.charAt(word.length()-1) == 'Ï'){
						newS = handleTa2ElMo5atabInPresentAndEa2ElTa2nes(word);
						if (newS.equalsIgnoreCase(word)){
							newS = handleTa2ElMo5atabInPresent(word,sentence);
						}
					}
					else 
						newS = handleTa2ElMo5atabInPresent(word,sentence);
				}
			}
		}
	
		if (newS.length() == 0 || newS.equalsIgnoreCase(word)) {
			if (word.length()>=2 && word.charAt(word.length()-1) == 'ﬂ'){
				newS = handleKafElMaf3ol(word);
				if (!newS.equalsIgnoreCase(word)){
					String []words = newS.split(" ");
					String m = handleDama2erKolha(sentence, words[1]);
					if (m.equalsIgnoreCase(words[1]))
						newS = word; // narg3ha zae ma hea 3shan mat3mlsh moshkala ”„⁄ﬂ
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
			
			if (newS.length() == 0 || newS.equalsIgnoreCase(word)) {
				if (word.length()>=2 && word.substring(word.length()-2, word.length()).equalsIgnoreCase("‰Ï")){	
					newS = handleNeElMaf3ol(word);
					if (!newS.equalsIgnoreCase(word)){
						String []words = newS.split(" ");
						String m = handleDama2erKolha(sentence,words[1]);
						if (m.equalsIgnoreCase(words[1]))
							newS = word; // narg3ha zae ma hea 3shan mat3mlsh moshkala ”„⁄ﬂ
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
			}
		}
		
		if (newS.length() == 0 || newS.equalsIgnoreCase(word)) {
				newS = word;
		}
		
		return newS;
	}
	private String handleDama2erElfa3l(String sentence, String word){
		String newS = "";
		if (word.length()>=2 && word.charAt(word.length()-1) == ' '){
			newS = handleTa2ElFa3l(word,sentence);
		}
		if (newS.length() == 0 || newS.equalsIgnoreCase(word)) {
			if(word.length()>=4 && word.substring(word.length()-2, word.length()).equalsIgnoreCase("‰«")&&!N7noFlag){
				// lenght = 4 3shan ana matb2ash na7no Alef
				newS = handleNaElFa3len(word);
			}
		}
		
		if (newS.length() == 0 || newS.equalsIgnoreCase(word)) {
			if (word.length()>=2 && word.charAt(0) == ' ' && !AntFlag){
				if (word.length()>=2 && word.substring(word.length()-2, word.length()).equalsIgnoreCase("Ì‰")){
					newS = handleTa2ElMo5atabInPresentAndEa2ElTa2nesAndNon(word);
					if (newS.equalsIgnoreCase(word)){
						if (word.length()>=2 && word.charAt(word.length()-1) == 'Ï'){
							newS = handleTa2ElMo5atabInPresentAndEa2ElTa2nes(word);
							if (newS.equalsIgnoreCase(word)){
								newS = handleTa2ElMo5atabInPresent(word,sentence);
							}
						}
					}
				}
				else{
					if (word.length()>=2 && word.charAt(word.length()-1) == 'Ï'){
						newS = handleTa2ElMo5atabInPresentAndEa2ElTa2nes(word);
						if (newS.equalsIgnoreCase(word)){
							newS = handleTa2ElMo5atabInPresent(word,sentence);
						}
					}
					else 
						newS = handleTa2ElMo5atabInPresent(word,sentence);
				}
			}
		}
	
		if (newS.length() == 0 || newS.equalsIgnoreCase(word)) {
			if (word.length()>=2 && word.charAt(word.length()-1) == 'ﬂ'){
				newS = handleKafElMaf3ol(word);
				if (!newS.equalsIgnoreCase(word)){
					String []words = newS.split(" ");
					String m = handleDama2erElfa3l(sentence, words[1]);
					if (m.equalsIgnoreCase(words[1]))
						newS = word; // narg3ha zae ma hea 3shan mat3mlsh moshkala ”„⁄ﬂ
					else{
						String [] words2 = m.split(" ");
						newS = words2[0]+"ﬂ" + " " + words2[1];
					}
				}
			}
		}
			
		if (newS.length() == 0 || newS.equalsIgnoreCase(word)) {
			if (word.length()>=2 && word.substring(word.length()-2, word.length()).equalsIgnoreCase("‰Ï")){	
				newS = handleNeElMaf3ol(word);
				if (!newS.equalsIgnoreCase(word)){
					String []words = newS.split(" ");
					String m = handleDama2erElfa3l(sentence,words[1]);
					if (m.equalsIgnoreCase(words[1]))
						newS = word; // narg3ha zae ma hea 3shan mat3mlsh moshkala ”„⁄ﬂ
					else{
						String [] words2 = m.split(" ");
						newS = words2[0]+"‰Ï" + " " + words2[1];
					}
				}
			}
		}
		
		if (newS.length() == 0 || newS.equalsIgnoreCase(word)) {
				newS = word;
		}
		
		return newS;
	}
	private String handleDama2ar(String sentence, String []words){
		String newS = "";
		for (int i = 0; i < words.length ; i++){
			newS += handleDama2erElfa3l(sentence,words[i]);
			if (i < words.length-1)
				newS += " ";
		}
		return newS;
	}
	public String preProcessing(String sentence){
		String newS = "";
		String [] words = sentence.split(" ");
		newS = handleFlags(words);
		words = newS.split(" ");
		newS = handlezerofElZaman(words);
		words = newS.split(" ");
		//newS = handleAdwatElNafe(words);
		//words = newS.split(" ");
		newS = handleDama2ar(newS,words);
		return newS;
	}
	public static void main(String[] args) throws IOException {
		LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");
		AutomaticTranslation auto =  new AutomaticTranslation();
		Parser parser =  new Parser();
		PreProcessing p = new PreProcessing( lp,parser); 
		while (true){
	    	System.out.println("enter gomla");
	    	InputStreamReader i = new InputStreamReader(System.in);
	    	BufferedReader b = new BufferedReader(i);
	    	String o = b.readLine();
	    	String afterPreProcessing = p.preProcessing(o); 
	    	auto.translate(lp,afterPreProcessing);
	    	System.out.println(afterPreProcessing);
	    	p.AnaFlag = false;
	    	p.AntFlag = false;
	    	p.N7noFlag = false;
	    	p.halFlag = false;
		}
	}
}