package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import morphologyTools.Tagger;
import searcher.IndexInfo;
import searcher.Searcher;

public class TestSearcher {

	/**
	 * @param args
	 * @throws IOException 
	 */
	static String getMWE(int index) throws IOException{
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\targetTermsVNC.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		while(--index > 0)
        	br.readLine();
        return br.readLine();
	}

	static void getMWESen (int size) throws FileNotFoundException, IOException{
		String result = "";
		Searcher searcher = new Searcher("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\bin\\milaCorporaWithPunc");
		int i = 0;
		
		List<String> result1 = new ArrayList<String>();
		List<String> result2 = new ArrayList<String>();
		
		Random rand = new Random();
		
		boolean b = true;
		int rIndex1;
		int rIndex2;
		while(i<size){
			
			String sen1 = getMWE(rand.nextInt(204));
			String sen2 = getMWE(rand.nextInt(204));
			
			List<String> ss1 = searcher.getQueryResultsAsStringList(sen1,100,false);
			List<String> s1 = ss1.stream().distinct().collect(Collectors.toList());
			List<String> ss2 = searcher.getQueryResultsAsStringList(sen2,100,false);
			List<String> s2 = ss2.stream().distinct().collect(Collectors.toList());

			
			while (s1.size() == 0){
				sen1 = getMWE(rand.nextInt(504));
				ss1 = searcher.getQueryResultsAsStringList(sen1,100,false);
				s1 = ss1.stream().distinct().collect(Collectors.toList());
			}
			while (s2.size() == 0){
				sen2 = getMWE(rand.nextInt(504));
				ss2 = searcher.getQueryResultsAsStringList(sen2,100,false);
				s2 = ss2.stream().distinct().collect(Collectors.toList());
			}
			if(sen1 == sen2){			
					rIndex1 = (s1.size() > 2)?rand.nextInt(s1.size()-2):0;
					rIndex2 = (s1.size() > 2)?rand.nextInt(s1.size()-2):0;
					while(b){	
						b = false;
						while(rIndex1 == rIndex2 || s1.get(rIndex1).split(" ").length < 6 || result.contains(sen1+":"+rIndex1))	{
							rIndex1 = (s1.size() > 2)?rand.nextInt(s1.size()-2):0;
							b = true;
						}
						while(s2.get(rIndex2).split(" ").length < 6 || result.contains(sen2+":"+rIndex2)){
							rIndex2 = (s1.size() > 2)?rand.nextInt(s1.size()-2):0;
							b = true;
						}		
					}
					result1.add(s1.get(rIndex1)); 
					result2.add(s2.get(rIndex2));
					
					result += sen1+":"+rIndex1;
					result += sen2+":"+rIndex2;
			}
			else{
				rIndex1 = (s1.size() > 2)?rand.nextInt(s1.size()-2):0;	
				rIndex2 = (s2.size() > 2)?rand.nextInt(s2.size()-2):0;
				
				while(b){	
					b = false;
					while(s1.get(rIndex1).split(" ").length < 6 || result.contains(sen1+":"+rIndex1))	{
						rIndex1 = (s1.size() > 2)?rand.nextInt(s1.size()-2):0;
						b = true;
					}
					while(s2.get(rIndex2).split(" ").length < 6 || result.contains(sen2+":"+rIndex2)){
						rIndex2 = (s2.size() > 2)?rand.nextInt(s2.size()-2):0;
						b = true;
					}		
				}
				result1.add(s1.get(rIndex1)); 
				result2.add(s2.get(rIndex2));
				
				result += sen1+":"+rIndex1;
				result += sen2+":"+rIndex1;
			}
			i++;
		}
		
		PrintWriter writer1 = new PrintWriter("C:\\Users\\aday\\Desktop\\MWEsentences1.txt", "UTF-8");
		PrintWriter writer2 = new PrintWriter("C:\\Users\\aday\\Desktop\\MWEsentences2.txt", "UTF-8");
		i=0;
		while(i<200){
			writer1.println(result1.get(i));
			writer2.println(result2.get(i++));
		}
		writer1.close();
		writer2.close();
	}
	static public List<String> getMWElist (int size) throws FileNotFoundException, IOException{
		String result = "";
		Searcher searcher = new Searcher("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\bin\\milaCorporaWithPunc");
		int i = 0;
		
		List<String> result1 = new ArrayList<String>();		
		Random rand = new Random();
		
		boolean b = true;
		int rIndex1;
		while(i<size){
			
			String sen1 = getMWE(rand.nextInt(204));
		
			List<String> ss1 = searcher.getQueryResultsAsStringList(sen1,100,false);
			List<String> s1 = ss1.stream().distinct().collect(Collectors.toList());
	
			
			while (s1.size() == 0){
				sen1 = getMWE(rand.nextInt(504));
				ss1 = searcher.getQueryResultsAsStringList(sen1,100,false);
				s1 = ss1.stream().distinct().collect(Collectors.toList());
			}
			
			rIndex1 = (s1.size() > 2)?rand.nextInt(s1.size()-2):0;			
			while(b){	
				b = false;
				while(s1.get(rIndex1).split(" ").length < 6 || result.contains(sen1+":"+rIndex1))	{
					rIndex1 = (s1.size() > 2)?rand.nextInt(s1.size()-2):0;
					b = true;
				}	
			}
			result1.add(s1.get(rIndex1)); 		
			result += sen1+":"+rIndex1;

			i++;
		}
		return result1;
	}
	public static void main(String[] args) throws Exception {
//		Searcher searcher = new Searcher("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\bin\\withPunc");
//		List<Integer> resultList = searcher.getUnigramQueryResultsAsIntegerList("אכל");
//		System.out.println("Number of sentences: " +  resultList.size());
//		System.out.println("List of sentences: " + resultList);
		
//		IndexInfo indexInfo = new IndexInfo("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
//		Tagger.init("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\jars\\tagger\\");
//		System.out.println(indexInfo.containVerbSentences(20));
//		System.out.println(indexInfo.randomizeSentences(600000, 20));
		
//		System.out.println(searcher.getQueryResultsAsStringList("יצא לפועל אלוהים ישמור", 100, true));

//		Searcher searcher = new Searcher("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\bin\\bible");
//		System.out.println(searcher.getQueryResultsAsStringList("בראשית ברא ", 0, true));


	}

}
