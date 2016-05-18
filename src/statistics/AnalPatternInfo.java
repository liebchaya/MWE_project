package statistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import utils.MapUtils;


public class AnalPatternInfo {

	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("F:\\freqData\\mwePosPattern.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\freqData\\mwePosPatternAnal.txt"));
		HashMap<String,Integer> patternMap = new HashMap<>();
		HashMap<String,String> wrongPatternMap = new HashMap<>();
		
		String line = reader.readLine();
		while(line!=null){
			String[] tokens = line.split("\t");
			String pattern = tokens[3];
			if (patternMap.containsKey(pattern)){
				int freq = patternMap.get(pattern);
				patternMap.put(pattern,freq+1);
			}
			else
				patternMap.put(pattern,1);
			if (!isSuitablePattern(pattern)){
				if (wrongPatternMap.containsKey(pattern)){
					String exampleList = wrongPatternMap.get(pattern);
					exampleList = exampleList + "#" + tokens[0];
					wrongPatternMap.put(pattern,exampleList);
				}
				else
					wrongPatternMap.put(pattern,tokens[0]);
			}
			line = reader.readLine();
		}
		reader.close();
		
		String wrongData = "";
		int sumWrong = 0;
		for (String pat:MapUtils.sortByValueDesc(patternMap).keySet()){
			int freq = patternMap.get(pat);
			writer.write(pat+"\t"+ freq +"\n");
			if (wrongPatternMap.containsKey(pat)){
				sumWrong += freq;
				wrongData = wrongData + pat + "\t" + freq + "\n" + wrongPatternMap.get(pat) + "\n";
			}
		}
		writer.write("\n\n");
		writer.write("worng POS num: "+sumWrong+"\n");
		writer.write(wrongData);
		writer.close();

	}
	
	public static boolean isSuitablePattern(String pattern){
		pattern = pattern.replaceAll("adverb", "");
		if(pattern.contains("noun")&&pattern.contains("verb"))
			return true;
		return false;
	}


}
