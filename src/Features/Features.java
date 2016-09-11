package Features;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import edu.ucla.sspace.common.OnDiskSemanticSpace;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.Similarity;
import edu.ucla.sspace.common.Similarity.SimType;
import morphologyTools.Tagger;
import mwe.scorers.BigramsMutualRankRatio;
import mwe.scorers.MutualExpectation;
import mwe.scorers.TrigramsMutualRankRatio;
import searcher.LemmaSearcher;
import searcher.Searcher;


public class Features {
	final static int DISTANCE = 3;
	static Searcher searcher = null;
	static LemmaSearcher searcherlemma = null;
	static Searcher searcherBible = null;
	static SemanticSpace onDiskLemma1 = null;
	static SemanticSpace onDiskLemma2 = null;
	static SemanticSpace onDiskLemma3 = null;
	static SemanticSpace onDiskLemma4 = null;
	static SemanticSpace onDisk1 = null;
	static SemanticSpace onDisk2 = null;
	static SemanticSpace onDisk3 = null;
	static SemanticSpace onDisk4 = null;
	
	public Features() throws IOException{
		searcher = new Searcher("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\bin\\milaCorporaWithPunc");
		searcherlemma = new LemmaSearcher("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\bin\\milaNewsLemmaText");
/*		searcherBible = new Searcher("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\bin\\bible");
		System.out.println(1);
		//onDiskLemma1 = new OnDiskSemanticSpace("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\MilaText1File\\coals-semantic-space.sspace");
		System.out.println(2);
		onDiskLemma2 = new OnDiskSemanticSpace("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\MilaText1File\\hal-semantic-space.sspace");		
		System.out.println(3);
		onDiskLemma3 = new OnDiskSemanticSpace("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\MilaText1File\\random-indexing-4000v-2w-noPermutations.sspace");
		System.out.println(4);
		onDiskLemma4 = new OnDiskSemanticSpace("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\MilaText1File\\reflective-random-indexing-4000v.sspace");
		System.out.println(5);
		//onDisk1 = new OnDiskSemanticSpace("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\MilaText1File\\baseSpaces\\coals-semantic-space.sspace");
		System.out.println(6);
		//onDisk2 = new OnDiskSemanticSpace("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\MilaText1File\\baseSpaces\\hal-semantic-space.sspace");
		System.out.println(7);
		//onDisk3 = new OnDiskSemanticSpace("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\MilaText1File\\baseSpaces\\random-indexing-4000v-2w-noPermutations.sspace");
		System.out.println(8);
		//onDisk4 = new OnDiskSemanticSpace("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\MilaText1File\\baseSpaces\\reflective-random-indexing-4000v.sspace");
		System.out.println("finish");*/
}
	

//////////////////////////////////////////////////////Auxiliary functions////////////////////////////////////////////////////
	
		//get value from index files
		static private double trigramsFiles(String sen,String fileName,boolean trigrams) throws Exception{
				FileInputStream file = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\V\\"+fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(file));
				String index;
				String iSen = "";
				int n = (trigrams)?4:3;
				while((index=br.readLine()) != null){
					iSen = "";
					String[] words = index.split(" ");
					if(words.length == n){
						iSen = words[0]+" "+words[1];
						if(n == 4)
							iSen+=" "+words[2];
						if(iSen.equals(sen)){
							br.close();
							return Double.parseDouble(words[n-1]);
						}
					}
				}
				br.close();
				return -1.0;
		
			}
		
		//convert term to lemma
		static public String convertTOlemma(String sen) throws Exception{
			List<String> lemmas = Tagger.getTaggerLemmasList(sen);
			String s = "";
			for(int i =0 ;i<lemmas.size(); i++){
				s+=lemmas.get(i);
				if(i<lemmas.size()-1)
					s+=" ";
			}
			return s;
		}

		//get all prefixes in verbs of the sentence by lemmas
		static private List<String> prefix_v(String sen) throws Exception{
			List<String> allsens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
			List<String> prefixes = new ArrayList<String>();
			for(String s : allsens){
				List<String> POSstr = Tagger.getTaggerPOSList(s);	
				String[]wordsArray = s.split(" ");
				String tmp = null;
				if(POSstr.size() == wordsArray.length)
					for(int i=0; i<POSstr.size(); i++){
						if(POSstr.get(i).contains("verb")){
							tmp = Tagger.getPrefix(wordsArray[i]);
							if(tmp != null &&tmp.length()>0){
								if(!prefixes.contains(tmp))
									System.out.println(s);
										prefixes.add(tmp);
							}
						}
					}
			}
			return prefixes;
		}

		//get all suffixes in verbs of the sentence by lemmas
		static private List<String> suffix_v(String sen) throws Exception{
			List<String> allsens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
			List<String> suffixes = new ArrayList<String>();
			for(String s : allsens){
				List<String> POSstr = Tagger.getTaggerPOSList(s);	
				String[]wordsArray = s.split(" ");
				String tmp = null;
				if(POSstr.size() == wordsArray.length)
					for(int i=0; i<POSstr.size(); i++){
						if(POSstr.get(i).contains("verb")){
							tmp = Tagger.getSuffix(wordsArray[i]);
							if(tmp != null &&tmp.length()>0){
								if(!suffixes.contains(tmp))
									System.out.println(s);
									suffixes.add(tmp);
							}
						}
					}
			}
			return suffixes;
		}

		//get all prefixes in nouns of the sentence by lemmas
		static private List<String> prefix_n(String sen) throws Exception{
			List<String> allsens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
			List<String> prefixes = new ArrayList<String>();
			for(String s : allsens){
				List<String> POSstr = Tagger.getTaggerPOSList(s);	
				String[]wordsArray = s.split(" ");
				String tmp = null;
				if(POSstr.size() == wordsArray.length)
					for(int i=0; i<POSstr.size(); i++){
						if(POSstr.get(i).contains("noun")){
							tmp = Tagger.getPrefix(wordsArray[i]);
							if(tmp != null &&tmp.length()>0){
								if(!prefixes.contains(tmp))
									System.out.println(s);
										prefixes.add(tmp);
							}
						}
					}
			}
			return prefixes;
		}

		//get all suffixes in nouns of the sentence by lemmas
		static private List<String> suffix_n(String sen) throws Exception{
			List<String> allsens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
			List<String> suffixes = new ArrayList<String>();
			for(String s : allsens){
				List<String> POSstr = Tagger.getTaggerPOSList(s);	
				String[]wordsArray = s.split(" ");
				String tmp = null;
				if(POSstr.size() == wordsArray.length)
					for(int i=0; i<POSstr.size(); i++){
						if(POSstr.get(i).contains("noun")){
							tmp = Tagger.getSuffix(wordsArray[i]);
							if(tmp != null &&tmp.length()>0){
								if(!suffixes.contains(tmp))
									System.out.println(s);
									suffixes.add(tmp);
							}
						}
					}
			}
			return suffixes;
		}
		
		//Organize files
		static private  void improoveFiles(String fileName) throws Exception{
			FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\"+fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
			String filename= ("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\V\\"+fileName);
			FileWriter fw = new FileWriter(filename,true);
			String line;
			String sen="";
			while((line=br.readLine()) != null){
				if(line.length() > 10){
				String[] words = line.split("<>");
				for(int i=0; i<words.length-1; i++)
					sen += words[i]+" ";
				fw.write(sen);
				String[] numbers = words[words.length-1].split(" ");
				double doubleNum = Double.parseDouble(numbers[1]);
				fw.write(doubleNum+"\n");
				sen="";
				}
			}
			br.close();
			fw.close();
		}

		private static double compVectorsLemma(String sen,SimType s,String sspaceName) throws Exception {
			SemanticSpace onDisk = null;
			switch (sspaceName){
			case "coals-semantic-space":onDisk = onDiskLemma1; break;
			case "hal-semantic-space":onDisk = onDiskLemma2; break;
			case "random-indexing-4000v-2w-noPermutations":onDisk = onDiskLemma3; break;
			case "reflective-random-indexing-4000v":onDisk = onDiskLemma4; break;
			}
			List<String> senTags = Tagger.getTaggerPOSList(sen);
			String[] words = sen.split(" ");
			String term1 = null,term2 = null;
			
			
			term1 = new String(words[0].getBytes(Charset.forName("utf-8")));
			term2 = new String(words[1].getBytes(Charset.forName("utf-8")));
			
			/*
			for(int i=0; i<senTags.size(); i++){
				if(senTags.get(i).contains("verb"))
					term1 = new String(words[i].getBytes(Charset.forName("utf-8")));
				if(senTags.get(i).contains("noun"))
					term2 = new String(words[i].getBytes(Charset.forName("utf-8")));
			}*/
						
			edu.ucla.sspace.vector.Vector vec = onDisk.getVector(term1);
			edu.ucla.sspace.vector.Vector vec1 = onDisk.getVector(term2);
			double sem = Similarity.getSimilarity(s, vec1, vec);
			return sem;
			
	
		}
		
		private static double compVectors(String sen,SimType s,String sspaceName) throws Exception {
			SemanticSpace onDisk = null;
			switch (sspaceName){
			case "coals-semantic-space":onDisk = onDisk1; break;
			case "hal-semantic-space":onDisk = onDisk2; break;
			case "random-indexing-4000v-2w-noPermutations":onDisk = onDisk3; break;
			case "reflective-random-indexing-4000v":onDisk = onDisk4; break;
			}
			List<String> senTags = Tagger.getTaggerPOSList(sen);
			String[] words = sen.split(" ");
			String term1 = null,term2 = null;
			
			term1 = new String(words[0].getBytes(Charset.forName("utf-8")));
			if(!sen.contains("קורא"))
				term2 = new String(words[1].getBytes(Charset.forName("utf-8")));
			else term2 = new String(words[2].getBytes(Charset.forName("utf-8")));
			
			edu.ucla.sspace.vector.Vector vec = onDisk.getVector(term1);
			edu.ucla.sspace.vector.Vector vec1 = onDisk.getVector(term2);
			double sem = Similarity.getSimilarity(s, vec1, vec);
			return sem;
		}

		//////////////////////////////////////////////////////Fetures////////////////////////////////////////////////////
		
	//1
	//Returns number of letters which is not Hebrew letters
	static public int non_Hebrew_letters(String sen) throws Exception{
		int nonHebrew=0;
		for(String s : sen.split(" "))
			for(int i=0; i<s.length(); i++)
				if(!Pattern.matches("[א-ת]+", s.substring(i,i+1))) nonHebrew++;	
		return nonHebrew;
	}

	//2
	//Returns subtraction result between sentence with distance and sentence without distance
	static public int syntactic_properties(String sen) throws Exception{
		String senLemma = convertTOlemma(sen);
		int test1 = searcherlemma.countQueryResults(senLemma,DISTANCE, true);
		int test2 = searcherlemma.countQueryResults(senLemma,0, true);
		return test1-test2;
	}

	//3
	//Returns subtraction result between sentence in order and sentence not in `
	static public int constituent_order(String sen) throws Exception{
		String senLemma = convertTOlemma(sen);
		int test1 = searcherlemma.countQueryResults(senLemma,0, true);
		int test2 = searcherlemma.countQueryResults(senLemma,0, false);
		return test1-test2;
	}
								
	//4
	//Returns number of words in a sentence
	static public int number_of_words(String sen){		
		return sen.split(" ").length;
	}
	
	//5
	//Returns number of characters in a sentence
	static public int number_of_characters(String sen){
		return sen.length() - number_of_words(sen) + 1;
	}

	//6
	//Returns average characters for word in a sentence.
	static public double average_characters_for_word(String sen){
		return (double)number_of_characters(sen) / number_of_words(sen);
	}
	
	//7
	//return number of prefix in sentence verb
	static public int prefix_verb(String sen) throws Exception{
		return prefix_v(sen).size();
	}
	
	//8-14
	//Returns number of prefix in sentence verb starts with the letter ב,ה,ל,כ,מ,ש,ו 
	static public int B_prefix_verb(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).substring(0,1).equals("ב"))
				numOfP++;
		return numOfP;
	}
	static public int H_prefix_verb(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("ה"))
				numOfP++;
		return numOfP;
	}
	static public int V_prefix_verb(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("ו"))
				numOfP++;
		return numOfP;
	}
	static public int C_prefix_verb(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("כ"))
				numOfP++;
		return numOfP;
	}
	static public int L_prefix_verb(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("ל"))
				numOfP++;
		return numOfP;
	}
	static public int M_prefix_verb(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("מ"))
				numOfP++;
		return numOfP;
	}
	static public int S_prefix_verb(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("ש"))
				numOfP++;
			return numOfP;
	}
	
	//15	
	//Returns number of suffixes in sentence verb
	static public int suffix_verb(String sen)throws Exception{
		return suffix_v(sen).size();
	}

	//16
	//Returns number of prefix in sentence noun
	static public int prefix_noun(String sen)throws Exception{
		return prefix_n(sen).size();
	}
	
	//17-23
	//Returns number of prefix in sentence noun starts with the letter ב,ה,ל,כ,מ,ש,ו 
	static public int B_prefix_noun(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("ב"))
				numOfP++;
		return numOfP;
	}
	static public int H_prefix_noun(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("ה"))
				numOfP++;
		return numOfP;
	}
	static public int V_prefix_noun(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("ו"))
				numOfP++;
		return numOfP;
	}
	static public int C_prefix_noun(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("כ"))
				numOfP++;
		return numOfP;
	}
	static public int L_prefix_noun(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("ל"))
				numOfP++;
		return numOfP;
	}
	static public int M_prefix_noun(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("מ"))
				numOfP++;
		return numOfP;
	}
	static public int S_prefix_noun(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(0)).substring(0,1).equals("ש"))
				numOfP++;
		return numOfP;
	}

	//24
	//Returns number of suffixes in sentence noun
	static public int suffix_noun(String sen)throws Exception{
		return suffix_n(sen).size();
	}
	
	//25-31
	//Returns number of sentences which satisfy the pattern
	//1) verb + noun
	static public int POSpattern1(String sen)throws Exception{
		int numOfP = 0;
		List<String> allSen = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen) , 1, true);
		for(String s : allSen){
			List<String> POSstr = Tagger.getTaggerPOSList(s);
			for(int i=0; i<POSstr.size(); i++)
				if(POSstr.get(i).contains("verb"))
					for(int j=i; j<POSstr.size(); j++)
						if(POSstr.get(i).contains("noun"))
							numOfP++;
		}
		return numOfP;
	}
	//2) verb + preposition noun
	static public int POSpattern2(String sen)throws Exception{
		int numOfP = 0;
		List<String> allSen = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen) , 1, true);
		for(String s : allSen){
			List<String> POSstr = Tagger.getTaggerPOSList(s);
			for(int i=0; i<POSstr.size(); i++)
				if(POSstr.get(i).contains("verb"))
					for(int j=i; j<POSstr.size(); j++)
						if(POSstr.get(i).contains("preposition noun"))
							numOfP++;	
		}
		return numOfP;
	}
	//3) verb + preposition noun + definiteArticle noun
	static public int POSpattern3(String sen)throws Exception{
		int numOfP = 0;
		List<String> allSen = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen) , 1, true);
		for(String s : allSen){
			List<String> POSstr = Tagger.getTaggerPOSList(s);
			for(int i=0; i<POSstr.size(); i++)
				if(POSstr.get(i).contains("verb"))
					for(int j=i; j<POSstr.size(); j++)
						if(POSstr.get(i).contains("preposition noun"))
							for(int m=j; m<POSstr.size(); m++)
								if(POSstr.get(i).contains("definiteArticle noun"))
									numOfP++;
		}
		return numOfP;
	}
	//4) verb + preposition + noun
	static public int POSpattern4(String sen)throws Exception{
		int numOfP = 0;
		List<String> allSen = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen) , 1, true);
		for(String s : allSen){
			List<String> POSstr = Tagger.getTaggerPOSList(s);
			for(int i=0; i<POSstr.size(); i++)
				if(POSstr.get(i).contains("verb"))
					for(int j=i; j<POSstr.size(); j++)
						if(POSstr.get(i).contains("preposition"))
							for(int m=j; m<POSstr.size(); m++)
								if(POSstr.get(i).contains("noun"))
									numOfP++;
		}
		return numOfP;
	}
	//5) verb + noun + preposition noun
	static public int POSpattern5(String sen)throws Exception{
		int numOfP = 0;
		List<String> allSen = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen) , 1, true);
		for(String s : allSen){
			List<String> POSstr = Tagger.getTaggerPOSList(s);
			for(int i=0; i<POSstr.size(); i++)
				if(POSstr.get(i).contains("verb"))
					for(int j=i; j<POSstr.size(); j++)
						if(POSstr.get(i).contains("noun"))
							for(int m=j; m<POSstr.size(); m++)
								if(POSstr.get(i).contains("preposition noun"))
									numOfP++;
		}
		return numOfP;
	}
	//6) verb + preposition definitionArticle noun
	static public int POSpattern6(String sen)throws Exception{
		int numOfP = 0;
		List<String> allSen = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen) , 1, true);
		for(String s : allSen){
			List<String> POSstr = Tagger.getTaggerPOSList(s);
			for(int i=0; i<POSstr.size(); i++)
				if(POSstr.get(i).contains("verb"))
					for(int j=i; j<POSstr.size(); j++)
						if(POSstr.get(i).contains("preposition definitionArticle noun"))
							numOfP++;
		}
		return numOfP;
	}
	//7) verb + preposition noun + noun
	static public int POSpattern7(String sen)throws Exception{
		int numOfP = 0;
		List<String> allSen = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen) , 1, true);
		for(String s : allSen){
			List<String> POSstr = Tagger.getTaggerPOSList(s);
			for(int i=0; i<POSstr.size(); i++)
				if(POSstr.get(i).contains("verb"))
					for(int j=i; j<POSstr.size(); j++)
						if(POSstr.get(i).contains("preposition noun"))
							for(int m=j; m<POSstr.size(); m++)
								if(POSstr.get(i).contains("noun"))
									numOfP++;
		}
		return numOfP;
	}

	//32-37
	//Returns number of sentences which has the stop word
	static public int ARTICLEstopWord(String sen)throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
			List<String> POSstr = Tagger.getTaggerPOSList(s);
			for(String pos : POSstr)
				if(pos.contains("article"))
					numOfS++;
		}
		return numOfS;	
	}
	static public int PRONOUNstopWord(String sen)throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		for(String pos : POSstr)
			if(pos.contains("pronoun"))
				numOfS++;
		}
		return numOfS;	
	}
	static public int PARTICLEstopWord(String sen)throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		for(String pos : POSstr)
			if(pos.contains("particle"))
				numOfS++;
		}
		return numOfS;	
	}
	static public int COUNJUNCTIONSstopWord(String sen)throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		for(String pos : POSstr)
			if(pos.contains("conjunction"))
				numOfS++;
		}
		return numOfS;		
	}
	static public int AUXILIARYstopWord(String sen)throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		for(String pos : POSstr)
			if(pos.contains("auxiliary"))
				numOfS++;
		}
		return numOfS;	
	}
	static public int NEGATIONstopWord(String sen)throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		for(String pos : POSstr)
			if(pos.contains("negation") || pos.contains("negative"))
				numOfS++;
		}
		return numOfS;	
	}
	
	//38
	//Returns frequency of sentence
	static public int senFrequency(String sen) throws Exception{
		return searcher.countQueryResults(sen, 1, true);
	}
	
	//39
	//Returns frequency of lemma sentence
	static public int lemmasFrequency(String sen) throws Exception{
		String lemma  = convertTOlemma(sen);
		return searcherlemma.countQueryResults(lemma, 1, true);
	}
	
	//40
	//Returns frequency of lemma's verb
	static public int verbLFrequency(String sen) throws Exception{
		List<String> posTags = Tagger.getTaggerLemmasList(convertTOlemma(sen));
		for(String w : posTags)
			if(w.contains("verb"))
				return searcher.countQueryResults(sen, 1, true);
		return -1;
	}
	
	//41
	//Returns frequency of lemma's noun
	static public int nounLFrequency(String sen) throws Exception{
		List<String> posTags = Tagger.getTaggerLemmasList(convertTOlemma(sen));
		for(String w : posTags)
			if(w.contains("noun"))
				return searcher.countQueryResults(sen, 1, true);
		return -1;
	}
	
	//42
	//Returns frequency of sentence's verb
	static public int verbSFrequency(String sen) throws Exception{
		List<String> posTags = Tagger.getTaggerLemmasList(sen);
		for(String w : posTags)
			if(w.contains("verb"))
				return searcher.countQueryResults(sen, 1, true);
		return -1;
	}
	
	//43
	//Returns frequency of sentence's noun
	static public int nounSFrequency(String sen) throws Exception{
		List<String> posTags = Tagger.getTaggerLemmasList(sen);
		for(String w : posTags)
			if(w.contains("noun"))
				return searcher.countQueryResults(sen, 1, true);
		return -1;
	}
	
	//44
	//Returns number of occurrences in the bible
	static public int biblicalSen(String sen)throws Exception{
		return searcherBible.countQueryResults(sen, 0, true);
	}
	
	//45
	//Returns number of stop word
	static public int HebStopWord(String sen)throws Exception{
		int stopW = 0;
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\hebStopWords.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		String[] wordsSen = sen.split(" ");
		String stopWord;
		while((stopWord=br.readLine())!=null)
			for(int i=0; i<wordsSen.length;i++)
				if(wordsSen[i].equals(stopWord))
					stopW++;
		br.close();
		return stopW;
	}
	
	//46-81
	//Returns number of sentences which their verb contain one of the Hebrew prefixes
	static public int prefix_verb_1(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כשמה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_2(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("שמה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_3(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("שכש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_4(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כשב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_5(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כשה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_6(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כשל"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_7(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כשמ"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_8(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("לכש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_9(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ושב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_10(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ושה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_11(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ושל"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_12(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ושמ"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_13(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("וכש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_14(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ומה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_15(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ומש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_16(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("וש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_17(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("וה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_18(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ומ"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_19(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ול"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_20(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("וב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_21(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_22(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כל"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_23(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_24(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("מב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_25(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("מה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_26(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("מש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_27(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("של"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_28(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("שמ"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_29(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("שב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_30(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("שה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_31(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_32(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_33(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ו"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_34(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ל"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_35(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("מ"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_verb_36(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_v(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ש"))
				numOfP++;
		return numOfP;
	}
	
	//82-118
	//Returns number of sentences which their noun contain one of the Hebrew prefixes
	static public int prefix_noun_1(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כשמה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_2(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("שמה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_3(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("שכש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_4(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כשב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_5(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כשה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_6(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כשל"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_7(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כשמ"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_8(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("לכש"))
				numOfP++;
		return numOfP;
	}
	//90
	static public int prefix_noun_9(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ושב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_10(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ושה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_11(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ושל"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_12(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ושמ"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_13(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("וכש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_14(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ומה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_15(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ומש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_16(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("וש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_17(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("וה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_18(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ומ"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_19(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ול"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_20(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("וב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_21(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_22(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כל"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_23(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("כש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_24(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("מב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_25(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("מה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_26(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("מש"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_27(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("של"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_28(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("שמ"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_29(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("שב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_30(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("שה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_31(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ב"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_32(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ה"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_33(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ו"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_34(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ל"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_35(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("מ"))
				numOfP++;
		return numOfP;
	}
	static public int prefix_noun_36(String sen) throws Exception{
		int numOfP = 0;
		List<String> prefixes = prefix_n(sen);
		for(int j=0; j<prefixes.size(); j++)
			if((prefixes.get(j)).contains("ש"))
				numOfP++;
		return numOfP;
	}

	//118
	//Returns number of sentences which has plural verb
	static public int plural_verb(String sen) throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		for(String pos : POSstr)
			if(pos.contains("verb"))
				if(pos.contains("plural"))
					numOfS++;
		}
		return numOfS;	
	}
	
	//119
	//Returns number of sentences which has singular verb
	static public int singular_verb(String sen) throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		for(String pos : POSstr)
			if(pos.contains("verb"))
				if(pos.contains("singular"))
					numOfS++;
		}
		return numOfS;	
	}

	//120
	//Returns number of sentences which has plural noun
	static public int plural_noun(String sen) throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		for(String pos : POSstr)
			if(pos.contains("noun"))
				if(pos.contains("plural"))
					numOfS++;
		}
		return numOfS;		
	}
	
	//121
	//Returns number of sentences which has singular noun
	static public int singular_noun(String sen) throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		for(String pos : POSstr)
			if(pos.contains("noun"))
				if(pos.contains("singular"))
					numOfS++;
		}
		return numOfS;		
	}
	
	//122
	//Returns number of sentences which has singular noun and plural verb
	static public int plural_verb_singular_noun(String sen) throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		boolean b1=false,b2 = false;
		for(String pos : POSstr){
			if(pos.contains("noun"))
				if(pos.contains("singular"))
					b1 = true;
			if(pos.contains("verb"))
				if(pos.contains("plural"))
					b2 = true;
			}
			if(b1&&b2)
				numOfS++;
		}
		
		return numOfS;		
	}
	
	//123
	//Returns number of sentences which has plural noun and plural verb
	static public int plural_verb_plural_noun(String sen) throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		boolean b1=false,b2 = false;
		for(String pos : POSstr){
			if(pos.contains("noun"))
				if(pos.contains("plural"))
					b1 = true;
			if(pos.contains("verb"))
				if(pos.contains("plural"))
					b2 = true;
		}
		if(b1&&b2)
			numOfS++;
		}
		
		return numOfS;			
	}
	
	//124
	//Returns number of sentences which has singular verb and singular noun
	static public int singular_verb_singular_noun(String sen) throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		boolean b1=false,b2 = false;
		for(String pos : POSstr){
			if(pos.contains("noun"))
				if(pos.contains("singular"))
					b1 = true;
			if(pos.contains("verb"))
				if(pos.contains("singular"))
					b2 = true;
		}
		if(b1&&b2)
			numOfS++;
		}
		
		return numOfS;		
	}
	
	//125
	//Returns number of sentences which has singular verb and plural noun
	static public int singualr_verb_plural_noun(String sen) throws Exception{
		int numOfS = 0;
		List<String> allSens = searcherlemma.getQueryResultsAsStringList(convertTOlemma(sen), 0, true);
		for(String s : allSens){
		List<String> POSstr = Tagger.getTaggerPOSList(s);
		boolean b1=false,b2 = false;
		for(String pos : POSstr){
			if(pos.contains("verb"))
				if(pos.contains("singular"))
					b1 = true;
			if(pos.contains("noun"))
				if(pos.contains("plural"))
					b2 = true;
		}
		if(b1&&b2)
			numOfS++;
		}
		
		return numOfS;			
	}

	//126
	static public double trigramsLL(String sen) throws Exception{
		return trigramsFiles(sen,"trigramsLL",true);
	}
	
	//127
	static public double trigramsLLLemma(String sen) throws Exception{
		return trigramsFiles(convertTOlemma(sen),"trigramsLLLemma",true);
	}
	
	//128
	static public double trigramsPMI(String sen) throws Exception{
		return trigramsFiles(sen,"trigramsPMI",true);
	}
	
	//129
	static public double trigramsPMILemma(String sen) throws Exception{
		return trigramsFiles(convertTOlemma(sen),"trigramsPMILemma",true);
	}
	
	//130
	static public double trigramsPS(String sen) throws Exception{
		return trigramsFiles(sen,"trigramsPS",true);
	}
	
	//131
	static public double trigramsPSLemma(String sen) throws Exception{
		return trigramsFiles(convertTOlemma(sen),"trigramsPSLemma",true);
	}
	
	//132
	static public double trigramsTMI(String sen) throws Exception{
		return trigramsFiles(sen,"trigramsTMI",true);
	}
	
	//133
	static public double trigramsTMILemma(String sen) throws Exception{
		return trigramsFiles(convertTOlemma(sen),"trigramsTMILemma",true);
	}
	
	//134
	static public double bigramsLL(String sen) throws Exception{
		return trigramsFiles(sen,"bigramsLL",false);
	}
	
	//135
	static public double bigramsLLLemma(String sen) throws Exception{
		return trigramsFiles(convertTOlemma(sen),"bigramsLLLemma",false);
	}
	
	//136
	static public double bigramsPMI(String sen) throws Exception{
		return trigramsFiles(sen,"bigramsPMI",false);
	}
	
	//137
	static public double bigramsPMILemma(String sen) throws Exception{
		return trigramsFiles(convertTOlemma(sen),"bigramsPMILemma",false);
	}
	
	//138
	static public double bigramsPS(String sen) throws Exception{
		return trigramsFiles(sen,"bigramsPS",false);
	}
	
	//139
	static public double bigramsPSLemma(String sen) throws Exception{
		return trigramsFiles(convertTOlemma(sen),"bigramsPSLemma",false);
	}
	
	//140
	static public double bigramsTMI(String sen) throws Exception{
		return trigramsFiles(sen,"bigramsTMI",false);
	}
	
	//141
	static public double bigramsTMILemma(String sen) throws Exception{
		return trigramsFiles(convertTOlemma(sen),"bigramsTMILemma",false);
	}
	
	//142
	static public double compVectorsLemma11(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.COSINE, "coals-semantic-space");
	}
	
	//143
	static public double compVectorsLemma12(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.LIN, "coals-semantic-space");
	}
	
//	//144
//	static public double compVectorsLemma13(String sen) throws Exception{
//		return compVectorsLemma(convertTOlemma(sen), SimType.KL_DIVERGENCE, "coals-semantic-space");
//	}
	
	//145
	static public double compVectorsLemma14(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.EUCLIDEAN, "coals-semantic-space");
	}
	
	//146
	static public double compVectorsLemma15(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.PEARSON_CORRELATION, "coals-semantic-space");
	}
	
	//147
	static public double compVectorsLemma16(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.AVERAGE_COMMON_FEATURE_RANK, "coals-semantic-space");
	}
	
	//148
	static public double compVectorsLemma17(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.JACCARD_INDEX, "coals-semantic-space");
	}
	
	//149
	static public double compVectorsLemma18(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.KENDALLS_TAU, "coals-semantic-space");
	}
	
	//150
	static public double compVectorsLemma19(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.TANIMOTO_COEFFICIENT, "coals-semantic-space");
	}
	
//	//151
//	static public double compVectorsLemma110(String sen) throws Exception{
//		return compVectorsLemma(convertTOlemma(sen), SimType.SPEARMAN_RANK_CORRELATION, "coals-semantic-space");
//	}	

	//152
	static public double compVectorsLemma21(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.COSINE, "hal-semantic-space");
	}
	
	//153
	static public double compVectorsLemma22(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.LIN, "hal-semantic-space");
	}

//	//154
//	static public double compVectorsLemma23(String sen) throws Exception{
//		return compVectorsLemma(convertTOlemma(sen), SimType.KL_DIVERGENCE, "hal-semantic-space");
//	}
	
	//155
	static public double compVectorsLemma24(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.EUCLIDEAN, "hal-semantic-space");
	}
	
	//156
	static public double compVectorsLemma25(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.PEARSON_CORRELATION, "hal-semantic-space");
	}
	
	//157
	static public double compVectorsLemma26(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.AVERAGE_COMMON_FEATURE_RANK, "hal-semantic-space");
	}
	
	//158
	static public double compVectorsLemma27(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.JACCARD_INDEX, "hal-semantic-space");
	}
	
	//159
	static public double compVectorsLemma28(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.KENDALLS_TAU, "hal-semantic-space");
	}
	
	//160
	static public double compVectorsLemma29(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.TANIMOTO_COEFFICIENT, "hal-semantic-space");
	}
	
//	//161
//	static public double compVectorsLemma210(String sen) throws Exception{
//		return compVectorsLemma(convertTOlemma(sen), SimType.SPEARMAN_RANK_CORRELATION, "hal-semantic-space");
//	}
	
	//162
	static public double compVectorsLemma31(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.COSINE, "random-indexing-4000v-2w-noPermutations");
	}
	
	//163
	static public double compVectorsLemma32(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.LIN, "random-indexing-4000v-2w-noPermutations");
	}
	
//	//164
//	static public double compVectorsLemma33(String sen) throws Exception{
//		return compVectorsLemma(convertTOlemma(sen), SimType.KL_DIVERGENCE, "random-indexing-4000v-2w-noPermutations");
//	}
	
	//165
	static public double compVectorsLemma34(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.EUCLIDEAN, "random-indexing-4000v-2w-noPermutations");
	}
	
	//166
	static public double compVectorsLemma35(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.PEARSON_CORRELATION, "random-indexing-4000v-2w-noPermutations");
	}
	
	//167
	static public double compVectorsLemma36(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.AVERAGE_COMMON_FEATURE_RANK, "random-indexing-4000v-2w-noPermutations");
	}
	
	//168
	static public double compVectorsLemma37(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.JACCARD_INDEX, "random-indexing-4000v-2w-noPermutations");
	}
	
//	//169
//	static public double compVectorsLemma38(String sen) throws Exception{
//		return compVectorsLemma(convertTOlemma(sen), SimType.KENDALLS_TAU, "random-indexing-4000v-2w-noPermutations");
//	}
	
	//170
	static public double compVectorsLemma39(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.TANIMOTO_COEFFICIENT, "random-indexing-4000v-2w-noPermutations");
	}
	
//	//171
//	static public double compVectorsLemma310(String sen) throws Exception{
//		return compVectorsLemma(convertTOlemma(sen), SimType.SPEARMAN_RANK_CORRELATION, "random-indexing-4000v-2w-noPermutations");
//	}
	
	//172
	static public double compVectorsLemma41(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.COSINE, "reflective-random-indexing-4000v");
	}
	
	//173
	static public double compVectorsLemma42(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.LIN, "reflective-random-indexing-4000v");
	}
	
//	//174
//	static public double compVectorsLemma43(String sen) throws Exception{
//		return compVectorsLemma(convertTOlemma(sen), SimType.KL_DIVERGENCE, "reflective-random-indexing-4000v");
//	}
	
	//175
	static public double compVectorsLemma44(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.EUCLIDEAN, "reflective-random-indexing-4000v");
	}
	
	//176
	static public double compVectorsLemma45(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.PEARSON_CORRELATION, "reflective-random-indexing-4000v");
	}
	
	//177
	static public double compVectorsLemma46(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.AVERAGE_COMMON_FEATURE_RANK, "reflective-random-indexing-4000v");
	}
	
	//178
	static public double compVectorsLemma47(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.JACCARD_INDEX, "reflective-random-indexing-4000v");
	}
	
//	//179
//	static public double compVectorsLemma48(String sen) throws Exception{
//		return compVectorsLemma(convertTOlemma(sen), SimType.KENDALLS_TAU, "reflective-random-indexing-4000v");
//	}
	
	//180
	static public double compVectorsLemma49(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.TANIMOTO_COEFFICIENT, "reflective-random-indexing-4000v");
	}
	
	//181
	static public double compVectorsLemma410(String sen) throws Exception{
		return compVectorsLemma(convertTOlemma(sen), SimType.SPEARMAN_RANK_CORRELATION, "reflective-random-indexing-4000v");
	}

	//182
	static public double compVectors11(String sen) throws Exception{
		return compVectors(sen, SimType.COSINE, "coals-semantic-space");
	}
	
	//183
	static public double compVectors12(String sen) throws Exception{
		return compVectors(sen, SimType.LIN, "coals-semantic-space");
	}
	
//	//184
//	static public double compVectors13(String sen) throws Exception{
//		return compVectors(sen, SimType.KL_DIVERGENCE, "coals-semantic-space");
//	}
	
	//185
	static public double compVectors14(String sen) throws Exception{
		return compVectors(sen, SimType.EUCLIDEAN, "coals-semantic-space");
	}
	
	//186
	static public double compVectors15(String sen) throws Exception{
		return compVectors(sen, SimType.PEARSON_CORRELATION, "coals-semantic-space");
	}
	
	//187
	static public double compVectors16(String sen) throws Exception{
		return compVectors(sen, SimType.AVERAGE_COMMON_FEATURE_RANK, "coals-semantic-space");
	}
	
	//188
	static public double compVectors17(String sen) throws Exception{
		return compVectors(sen, SimType.JACCARD_INDEX, "coals-semantic-space");
	}
	
	//189
	static public double compVectors18(String sen) throws Exception{
		return compVectors(sen, SimType.KENDALLS_TAU, "coals-semantic-space");
	}
	
	//190
	static public double compVectors19(String sen) throws Exception{
		return compVectors(sen, SimType.TANIMOTO_COEFFICIENT, "coals-semantic-space");
	}
	
//	//191
//	static public double compVectors110(String sen) throws Exception{
//		return compVectors(sen, SimType.SPEARMAN_RANK_CORRELATION, "coals-semantic-space");
//	}	

	//192
	static public double compVectors21(String sen) throws Exception{
		return compVectors(sen, SimType.COSINE, "hal-semantic-space");
	}
	
	//193
	static public double compVectors22(String sen) throws Exception{
		return compVectors(sen, SimType.LIN, "hal-semantic-space");
	}
	
//	//194
//	static public double compVectors23(String sen) throws Exception{
//		return compVectors(sen, SimType.KL_DIVERGENCE, "hal-semantic-space");
//	}
	
	//195
	static public double compVectors24(String sen) throws Exception{
		return compVectors(sen, SimType.EUCLIDEAN, "hal-semantic-space");
	}
	
	//196
	static public double compVectors25(String sen) throws Exception{
		return compVectors(sen, SimType.PEARSON_CORRELATION, "hal-semantic-space");
	}
	
	//197
	static public double compVectors26(String sen) throws Exception{
		return compVectors(sen, SimType.AVERAGE_COMMON_FEATURE_RANK, "hal-semantic-space");
	}
	
	//198
	static public double compVectors27(String sen) throws Exception{
		return compVectors(sen, SimType.JACCARD_INDEX, "hal-semantic-space");
	}
	
	//199
	static public double compVectors28(String sen) throws Exception{
		return compVectors(sen, SimType.KENDALLS_TAU, "hal-semantic-space");
	}
	
	//200
	static public double compVectors29(String sen) throws Exception{
		return compVectors(sen, SimType.TANIMOTO_COEFFICIENT, "hal-semantic-space");
	}
	
//	//201
//	static public double compVectors210(String sen) throws Exception{
//		return compVectors(sen, SimType.SPEARMAN_RANK_CORRELATION, "hal-semantic-space");
//	}
	
	//202
	static public double compVectors31(String sen) throws Exception{
		return compVectors(sen, SimType.COSINE, "random-indexing-4000v-2w-noPermutations");
	}
	
	//203
	static public double compVectors32(String sen) throws Exception{
		return compVectors(sen, SimType.LIN, "random-indexing-4000v-2w-noPermutations");
	}
	
//	//204
//	static public double compVectors33(String sen) throws Exception{
//	  return compVectors(sen, SimType.KL_DIVERGENCE, "random-indexing-4000v-2w-noPermutations");
//	}
	
	//205
	static public double compVectors34(String sen) throws Exception{
		return compVectors(sen, SimType.EUCLIDEAN, "random-indexing-4000v-2w-noPermutations");
	}
	
	//206
	static public double compVectors35(String sen) throws Exception{
		return compVectors(sen, SimType.PEARSON_CORRELATION, "random-indexing-4000v-2w-noPermutations");
	}
	
	//207
	static public double compVectors36(String sen) throws Exception{
		return compVectors(sen, SimType.AVERAGE_COMMON_FEATURE_RANK, "random-indexing-4000v-2w-noPermutations");
	}
	
	//208
	static public double compVectors37(String sen) throws Exception{
		return compVectors(sen, SimType.JACCARD_INDEX, "random-indexing-4000v-2w-noPermutations");
	}
	
//	//209
//	static public double compVectors38(String sen) throws Exception{
//	    return compVectors(sen, SimType.KENDALLS_TAU, "random-indexing-4000v-2w-noPermutations");
//	}
	
	//210
	static public double compVectors39(String sen) throws Exception{
		return compVectors(sen, SimType.TANIMOTO_COEFFICIENT, "random-indexing-4000v-2w-noPermutations");
	}
	
//	//211
//	static public double compVectors310(String sen) throws Exception{
//		return compVectors(sen, SimType.SPEARMAN_RANK_CORRELATION, "random-indexing-4000v-2w-noPermutations");
//	}
	
	//212
	static public double compVectors41(String sen) throws Exception{
		return compVectors(sen, SimType.COSINE, "reflective-random-indexing-4000v");
	}
	
	//213
	static public double compVectors42(String sen) throws Exception{
		return compVectors(sen, SimType.LIN, "reflective-random-indexing-4000v");
	}
	
//	//214
//	static public double compVectors43(String sen) throws Exception{
//		return compVectors(sen, SimType.KL_DIVERGENCE, "reflective-random-indexing-4000v");
//	}
	
	//215
	static public double compVectors44(String sen) throws Exception{
		return compVectors(sen, SimType.EUCLIDEAN, "reflective-random-indexing-4000v");
	}
	
	//216
	static public double compVectors45(String sen) throws Exception{
		return compVectors(sen, SimType.PEARSON_CORRELATION, "reflective-random-indexing-4000v");
	}
	
	//217
	static public double compVectors46(String sen) throws Exception{
		return compVectors(sen, SimType.AVERAGE_COMMON_FEATURE_RANK, "reflective-random-indexing-4000v");
	}
	
	//218
	static public double compVectors47(String sen) throws Exception{
		return compVectors(sen, SimType.JACCARD_INDEX, "reflective-random-indexing-4000v");
	}
	
//	//219
//	static public double compVectors48(String sen) throws Exception{
//		return compVectors(sen, SimType.KENDALLS_TAU, "reflective-random-indexing-4000v");
//	}
	
	//220
	static public double compVectors49(String sen) throws Exception{
		return compVectors(sen, SimType.TANIMOTO_COEFFICIENT, "reflective-random-indexing-4000v");
	}
	
//	//221
//	static public double compVectors410(String sen) throws Exception{
//		return compVectors(sen, SimType.SPEARMAN_RANK_CORRELATION, "reflective-random-indexing-4000v");
//	}
	
	//222
	//Returns the score of mutual exception
	static public double mutualScore(String sen) throws Exception{
		MutualExpectation mutualScorer = new MutualExpectation(Arrays.asList("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\bigramsNoFilter.cnt","C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\trigramsNoFilter.cnt"));
		return mutualScorer.score(sen);
	}
	
	//223
	//Returns the score of mutual exception on lemma
	static public double mutualScoreLemma(String sen) throws Exception{
		MutualExpectation mutualScorer = new MutualExpectation(Arrays.asList("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\bigramsNoFilterLemma.cnt","C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\trigramsNoFilterLemma.cnt"));
		return mutualScorer.score(sen);
	}
	
	//224
	//Returns the score of mutual RankRatio 
	static public double mutualRankRatio(String sen) throws Exception{
		if(sen.split(" ").length == 2){
			BigramsMutualRankRatio bMRR = new BigramsMutualRankRatio("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\bigramsNoFilter.cnt");
			return bMRR.score(sen);
		}
		else {
			TrigramsMutualRankRatio tMRR = new TrigramsMutualRankRatio("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\trigramsNoFilter.cnt");
			return tMRR.score(sen);
		}
	}
	
	//225
	//Returns the score of mutual RankRatio on lemma
	static public double mutualRankRatioLemma(String sen) throws Exception{
		String s = convertTOlemma(sen);
		if(sen.split(" ").length == 2){
			BigramsMutualRankRatio bMRR = new BigramsMutualRankRatio("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\bigramsNoFilterLemma.cnt");
			return bMRR.score(s);
		}
		else {
			TrigramsMutualRankRatio tMRR = new TrigramsMutualRankRatio("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\NSPnews\\trigramsNoFilterLemma.cnt");
			return tMRR.score(s);	
		}
	}
	
	
}