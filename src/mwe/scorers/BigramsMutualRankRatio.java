package mwe.scorers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;



import obj.Pair;

import org.ardverk.collection.StringKeyAnalyzer;
import org.ardverk.collection.Trie;
import org.ardverk.collection.PatriciaTrie;

public class BigramsMutualRankRatio implements MWEScorer{

	public static void main(String[] args) throws IOException{
		BigramsMutualRankRatio bMRR = new BigramsMutualRankRatio("C:\\responsa2.cnt");
		System.out.println("��� ����� " + bMRR.score("��� �����"));
		System.out.println("��� ����� " + bMRR.score("��� �����"));
		System.out.println("��� ��� " + bMRR.score("��� ���"));
		System.out.println("��� ��� " + bMRR.score("��� ���"));
	}
	
	
	public String getName() {
		return m_name;
	}
	
	//mutualRankRatio
	public double score(String bigram){
		double score = Math.pow(rankRatio(bigram, 0)*rankRatio(bigram, 1),0.5);
		return score;
	}
	
	public boolean exist(String bigram){ 
		if (m_prefixTrie.containsKey(bigram))
			return true;
		return false;
		
	}
	
	private double rankRatio(String bigram, int pos){
		double score = 0.0;
		Map<String, Integer> ERcontex = new LinkedHashMap<String, Integer>();
		Map<String, Integer> ARcontex = new HashMap<String, Integer>();
		String prefix = bigram.split(" ")[pos]+ " ";
		for(String entry:m_prefixTrie.prefixMap(prefix).keySet()){
			ARcontex.put(entry, m_prefixTrie.get(entry).key());
			ERcontex.put(entry, m_prefixTrie.get(entry).value());
		}
		for(String entry:m_sufixTrie.prefixMap(prefix).keySet()){
			String [] bigramSplit = entry.split(" ");
			String revBigram = bigramSplit[1]+ " " + bigramSplit[0];
			ARcontex.put(revBigram, m_sufixTrie.get(entry).key());
			ERcontex.put(revBigram, m_sufixTrie.get(entry).value());
		}
//		System.out.println("Finished loading contex data for " + bigram + " " + pos);
		ARcontex = utils.MapUtils.sortByValue(ARcontex);
		double ARrank = 0;
		int rankCounter = 1;
		int prevFreq = 0;
		boolean bFound = false;
		for (String key:ARcontex.keySet()){
			int freq = ARcontex.get(key);
			if (bFound) {
				if (freq != prevFreq){
					if (rankCounter > 1)
						ARrank = (ARrank-1) + 1/(double)(rankCounter-1);
					break;
				} else {
					rankCounter++;
				}
			}
			if (key.equals(bigram))
				bFound = true;
			if (freq != prevFreq){
				ARrank++;
				rankCounter = 1;
			}
			else {
				rankCounter++;
			}
			prevFreq = freq;
		}
			
		ERcontex = utils.MapUtils.sortByValue(ERcontex);
		double ERrank = 0;
		rankCounter = 1;
		prevFreq = 0;
		bFound = false;
		for (String key:ERcontex.keySet()){
			int freq = ERcontex.get(key);
			if (bFound) {
				if (freq != prevFreq){
					if (rankCounter > 1)
						ERrank = (ERrank-1) + 1/(double)(rankCounter-1);
					break;
				} else {
					rankCounter++;
				}
			}
			if (key.equals(bigram))
				bFound = true;
			if (freq != prevFreq){
				ERrank++;
				rankCounter = 1;
			}
			else {
				rankCounter++;
			}
			prevFreq = freq;
		}
		if(bFound)
			score = ERrank/ARrank;
		return score;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public BigramsMutualRankRatio(String bigramsFile) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(bigramsFile), "UTF-8"));
		m_prefixTrie = new PatriciaTrie<String, Pair<Integer,Integer>>(StringKeyAnalyzer.INSTANCE);
		m_sufixTrie = new PatriciaTrie<String, Pair<Integer,Integer>>(StringKeyAnalyzer.INSTANCE);
		String line = reader.readLine();
		int lineNum = 1;
//		int bigramsNum = Integer.parseInt(line);
		line = reader.readLine();
		while (line!=null){
			lineNum++;
			String[] tokens = line.split("<>");
			String[] numbers = tokens[tokens.length-1].split(" ");
			if(!numbers[0].equals("1")){
			m_prefixTrie.put(tokens[0] + " " + tokens[1], new Pair<Integer,Integer>(Integer.parseInt(tokens[2].split(" ")[0]),Integer.parseInt(tokens[2].split(" ")[2])));
			m_sufixTrie.put(tokens[1] + " " + tokens[0], new Pair<Integer,Integer>(Integer.parseInt(tokens[2].split(" ")[0]),Integer.parseInt(tokens[2].split(" ")[1])));
			line = reader.readLine();
			if (lineNum%10000==0)
				System.out.println("line: " + lineNum);
			}
			else line=null;
		}
		reader.close();
		System.out.println("Finished loading trees");
	}
	
	private Trie<String, Pair<Integer,Integer>> m_prefixTrie = null;
	private Trie<String, Pair<Integer,Integer>> m_sufixTrie = null;
	static private String m_name="BigramsMutualRankRatio";
	

}
