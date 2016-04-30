package searcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.List;


import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import models.MweExample;
import morphologyTools.Tagger;


public class IndexInfo {

	private static IndexSearcher m_indexSearcher = null;
	static String MWE = null;

	public IndexInfo(String indexDir) throws IOException {
		Directory directory = FSDirectory.open(new File(indexDir));
		DirectoryReader reader = DirectoryReader.open(directory);  
		m_indexSearcher = new IndexSearcher(reader);  
	}
	
	public int getIndexSize(){ 
		return m_indexSearcher.getIndexReader().numDocs();
	}
	
	public static String getSentenceContent(int docId) throws IOException{
		return m_indexSearcher.getIndexReader().document(docId).get("TERM_VECTOR");
	}
	
	public static Iterable<Integer> randomizeSentences(int maxValue,int iterableSize) throws Exception{
		
		ArrayList<Integer> sentencesIndexes = new ArrayList<Integer>();
		Random rand = new Random();
		Integer randomNum;
	    
		while(iterableSize > 0){
			randomNum = rand.nextInt(maxValue);
			if(!sentencesIndexes.contains(randomNum)){
				if(isSuitableDistance(getSentenceContent(randomNum)) != null){  //if sentence has good distance
					sentencesIndexes.add(randomNum);
					iterableSize--;
				}
			}
		}
		
		return sentencesIndexes;
	}
	
	public static Iterable<Integer> containVerbSentences(int iterableSize) throws Exception{
		
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\goodTargetTermsVNC.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		Searcher searcher = new Searcher("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
		List<Integer> sentencesIndexes = new ArrayList<Integer>();
		Random rand = new Random();
		Integer randomNum;
		
		while(iterableSize > 0){	
			randomNum = rand.nextInt(204);
	        String sen;
	        while(--randomNum > 0)
	        	br.readLine();
	        sen = br.readLine();
	        if(sen!= null){
			String[]wordsArray = sen.split(" ");
			Iterable<Integer> verbIndexex = getVerbIndexes(Tagger.getTaggerPOSList(sen));
			for(Integer integer : verbIndexex){
				List<Integer> resultList = searcher.getUnigramQueryResultsAsIntegerList(wordsArray[integer]);
				for(Integer integer2 : resultList) {
					if(iterableSize > 0){
						String str = getSentenceContent(integer2);
						if(!sentencesIndexes.contains(integer2))
							if(isSuitableDistance(str) != null){  //if sentence has good distance
								sentencesIndexes.add(integer2);
								iterableSize--;
							}
					}
					else break;
				}
				
			}
		}
		}
		br.close();
		return sentencesIndexes;
	}
	
	public static Iterable<MweExample> GenerateNegativeExamples(Iterable<Integer> random) throws Exception{
		
		ArrayList<MweExample> mweExamples = new ArrayList<MweExample>();
		int i=1;
		for(Integer index : random){
			System.out.println(i++ +")");
			if(isNotMWE(index)){
				MweExample mweExample = new MweExample();
				mweExample.setSentenceId(index);
				mweExample.setMwe(MWE);
				mweExample.setSentence(getSentenceContent(index));
				mweExamples.add(mweExample);
			}	
			else 
				System.out.println("/////////////////////////"+getSentenceContent(index));
		}
		
		return mweExamples;
	}
	
	private static int getLength(Iterable<Integer> it){
		 if (it instanceof Collection)
			    return ((Collection<Integer>)it).size();
		  int i = 0;
		  for (@SuppressWarnings("unused") Object obj : it) i++;
		  return i;
	}
	
	private static Integer[] isSuitableDistance(String sentence)throws Exception{
		List<String> posList = Tagger.getTaggerPOSList(sentence);
	    
		
		Iterable<Integer> verbIndex =  getVerbIndexes(posList);
		Iterable<Integer> nounIndex = getNounIndexes(posList);
		Integer[] verbNoun = new Integer[getLength(verbIndex)*getLength(nounIndex)*2];
		if(getLength(verbIndex) == 0)
		   return null;
		int i=0;
		for(Integer verb : verbIndex)
		for(Integer noun : nounIndex)
			if(Math.abs(verb - noun) < 2){
				verbNoun[i++] = verb;
				verbNoun[i++] = noun;
			}
		int size=0;
		for(int j=0;j<verbNoun.length;j++)
			if(verbNoun[j]!= null)
				size++;
		Integer[] vn = new Integer[size];
		for(int j=0; j<size; j++)
			vn[j] = verbNoun[j];
		return (vn.length == 0)?null:vn;

}
	
	private static boolean isNotMWE(Integer index) throws Exception{
		
		boolean b = false;		
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\goodTargetTermsVNC.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
        String strLine;
        String sen = getSentenceContent(index);
        String[]wordsArray = sen.split(" ");
        Integer[] nounVerbforCheck = isSuitableDistance(sen);
        
        String[]words;
        Integer[] nounVerb;
           
        while ((strLine = br.readLine()) != null) {
			words = strLine.split(" ");
        	nounVerb = isSuitableDistance(strLine);
        	if(nounVerb !=null && nounVerb.length > 0)
        	for(int i =0; i<nounVerbforCheck.length; i++){
        		String verb = wordsArray[nounVerbforCheck[i++]];
        		String noun = wordsArray[nounVerbforCheck[i]];
        		if(LongestCommonSubstring(words[nounVerb[0]], verb) >= 3){
    				if(LongestCommonSubstring(words[nounVerb[1]], noun) >= words[nounVerb[1]].length()-1)
        					b = true;
        			} 
        		MWE = "verb: " + verb+ " noun: " +noun; 
        	}
    		if(b){
    			br.close();
    			return false;	
			     }
            }
           
        br.close();
		return true;		
	} 
	
	private static Iterable<Integer> getNounIndexes(List<String> posList){
		
		ArrayList<Integer> NounIndexes = new ArrayList<Integer>();
		
		int nounIndex  =  0;	
		for(String pos : posList){
			if(pos.contains("noun"))
				NounIndexes.add(nounIndex);
			nounIndex++;
		}
			
		return NounIndexes;
	}
	
	private static Iterable<Integer> getVerbIndexes(List<String> posList){
		
		ArrayList<Integer> verbIndexes = new ArrayList<Integer>();
		
		int verbIndex  =  0;	
		for(String pos : posList){
			if(pos.contains("verb"))
				verbIndexes.add(verbIndex);
			verbIndex++;
		}
			
		return verbIndexes;
	}

	private static int LongestCommonSubstring(String str1, String str2)
    {
        int[][] num = new int[str1.length()][str2.length()];
        int maxlen = 0;

        for (int i = 0; i < str1.length(); i++)
        {
            for (int j = 0; j < str2.length(); j++)
            {
                if (str1.charAt(i) != str2.charAt(j))
                    num[i][j] = 0;
                else
                {
                    if ((i == 0) || (j == 0))
                        num[i][j] = 1;
                    else
                        num[i][j] = 1 + num[i-1][j-1];

                    if (num[i][j] > maxlen)
                    {
                        maxlen = num[i][j];
                    }
                }
            }
        }
        return maxlen;
    }

}


