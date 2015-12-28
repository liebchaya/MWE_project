package searcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import models.MweExample;
import morphologyTools.Tagger;


public class IndexInfo {

	private IndexSearcher m_indexSearcher = null;

	public IndexInfo(String indexDir) throws IOException {
		Directory directory = FSDirectory.open(new File(indexDir));
		DirectoryReader reader = DirectoryReader.open(directory);  
		m_indexSearcher = new IndexSearcher(reader);  
	}
	
	public int getIndexSize(){ 
		return m_indexSearcher.getIndexReader().numDocs();
	}
	
	public String getSentenceContent(int docId) throws IOException{
		return m_indexSearcher.getIndexReader().document(docId).get("TERM_VECTOR");
	}
	
	public Iterable<Integer> randomizeSentences(int maxValue,int iterableSize) throws Exception{
		
		ArrayList<Integer> sentencesIndexes = new ArrayList<Integer>();
		Random rand = new Random();
		Integer randomNum;
	    
		while(iterableSize > 0){
			randomNum = rand.nextInt(maxValue);
			if(isSuitableDistance(getSentenceContent(randomNum))){  //if sentence has good distance
				sentencesIndexes.add(randomNum);
				iterableSize--;
			}
		}
		
		return sentencesIndexes;
	}
			
	public boolean isSuitableDistance(String sentence)throws Exception{
		
			Tagger.init("c:\\BGUTaggerData\\");
			List<String> posList = Tagger.getTaggerPOSList(sentence);
			
			if(SelectedNoun(posList) != -1)return true;
			else return false;
	}
	
	public Iterable<MweExample> GenerateNegativeExamples(Iterable<Integer> random) throws Exception{
		
		ArrayList<MweExample> mweExamples = new ArrayList<MweExample>();

		for(Integer index : random){
			if(isNotMWE(index)){
				MweExample mweExample = new MweExample();
				mweExample.setSentenceId(index);
				mweExample.setMwe(getMWE(index));
				mweExample.setSentence(getSentenceContent(index));
				mweExamples.add(mweExample);
			}	
		}
		
		return mweExamples;
	}
	
	public boolean isNotMWE(Integer index) throws Exception{
		
		boolean b = false;
		Tagger.init("c:\\BGUTaggerData\\");
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\AppData\\Local\\GitH\\ub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\targetTermsVNC.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
        String strLine;
        
        List<String> posListforCheck = Tagger.getTaggerPOSList(getSentenceContent(index));
        Integer verbIndex = getVerbIndex(posListforCheck);
        Iterable<Integer> nounIndex = getNounIndexes(posListforCheck);
        
        List<String> posList;
        Integer verb;
        Iterable<Integer> nouns;
        
        while ((strLine = br.readLine()) != null) {
        	posList = Tagger.getTaggerPOSList(strLine); 
        	verb = getVerbIndex(posList);
        	nouns = getNounIndexes(posList);
        	if(LongestCommonSubstring(posList.get(verb), posListforCheck.get(verbIndex)) < 3){
        		br.close();
        		return false;
        	}
        	for(Integer noun1 : nouns){
        		for(Integer noun2 : nounIndex)
        			if(posList.get(noun1).equals(noun2))
        				b = true;
        		if(!b){
        			br.close();
        			return false;
        		}
    		}
        }
        
        br.close();
		return true;
		
	} 
	
	public Iterable<Integer> getNounIndexes(List<String> posList){
		
		ArrayList<Integer> NounIndexes = new ArrayList<Integer>();
		
		int nounIndex  =  0;	
		for(String pos : posList){
			if(pos.contains("noun"))
				NounIndexes.add(nounIndex);
			nounIndex++;
		}
			
		return NounIndexes;
	}
	
	public Integer getVerbIndex(List<String> posList){
		
		int verbIndex  =  0;
		
		for(String pos : posList){
			if(pos.contains("verb"))
					break;
			verbIndex++;	
		}
		
		return verbIndex;
	}

	public int LongestCommonSubstring(String str1, String str2)
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

	public int SelectedNoun(List<String> posList){
		int verbIndex  =  getVerbIndex(posList);
		Iterable<Integer> nounIndex = getNounIndexes(posList);
		
		for(Integer index : nounIndex)
			if(Math.abs(index - verbIndex) < 2)
					return index;
		return -1;
	}

	public String getMWE(Integer index) throws IOException, Exception{
		
		Tagger.init("c:\\BGUTaggerData\\");  
        List<String> posList = Tagger.getTaggerPOSList(getSentenceContent(index));
        String mwe = "";
        int start = getVerbIndex(posList);
        int end = SelectedNoun(posList);
        if(start > end) {
        	int temp = start;
        	start = end;
        	end = temp;
        }
        for(int i=start; i<=end; i++)
        	mwe += posList.get(i);
        
        return mwe;
        	
	}
}


