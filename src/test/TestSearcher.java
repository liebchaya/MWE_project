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
	static void get250MWESen () throws FileNotFoundException, IOException{
		Searcher searcher = new Searcher("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
		int i = 0;
		List<String> result = new ArrayList<String>();
		Random rand = new Random();
		boolean b = true;
		while(i<250){
			String sen = getMWE(rand.nextInt(504));
			String sen1;
			if(b){
			 sen1 = getMWE(rand.nextInt(504));
			 b = false;
			}else {
				sen1 = "";
				b = true;
			}
			List<String> s = searcher.getQueryResultsAsStringList(sen+" "+sen1,100,false);
			if(s.size()!=0) {result.add(s.get(rand.nextInt(s.size()-1))); 
			i++;}
		}
		
		PrintWriter writer = new PrintWriter("C:\\Users\\aday\\Desktop\\MWEsentences.txt", "UTF-8");
		i=0;
		while(i<250)
			writer.println(result.get(i++));
		writer.close();
	}
	public static void main(String[] args) throws Exception {
		Searcher searcher = new Searcher("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
//		List<Integer> resultList = searcher.getUnigramQueryResultsAsIntegerList("אכל");
//		System.out.println("Number of sentences: " +  resultList.size());
//		System.out.println("List of sentences: " + resultList);
		
//		IndexInfo indexInfo = new IndexInfo("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
//		Tagger.init("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\jars\\tagger\\");
//		System.out.println(indexInfo.containVerbSentences(20));
//		System.out.println(indexInfo.randomizeSentences(600000, 20));
		
//		System.out.println(searcher.getQueryResultsAsStringList("יצא לפועל אלוהים ישמור", 100, true));

		get250MWESen();

	}

}
