package statistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

import morphologyTools.Tagger;

public class GenerateVNCPatterns {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("F:\\new505terms.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\freqData\\mwePosPatternNew505.txt"));
		
		Tagger.init("F:\\tagger\\");
		String line = reader.readLine();
		while(line!=null){
			String output = line+"\t";
			output = output + Tagger.getTaggerLemmasList(line) + "\t";
			List<String> posList = Tagger.getTaggerPOSList(line);
//			output = output + posList + "\t";
			for(String pos:posList) {
				String posStr = "";
				int posIndex = pos.indexOf("verb");
				if (posIndex!=-1)
					posStr = pos.substring(0,posIndex+4);
				else {
					posIndex = pos.indexOf("noun");
					if (posIndex!=-1)
						posStr = pos.substring(0,posIndex+4);
				}
				if(posStr.isEmpty())
					posStr = pos.trim().split(" ")[0];
				posStr = posStr.trim();
				output = output + posStr + "+";
			}
			output = output.substring(0,output.length()-1);
			writer.write(output+"\n");
			line = reader.readLine();
		}
		reader.close();
		writer.close();

	}

	private static boolean containsVN(String sentence) throws Exception{
		String posStr = Tagger.getTaggerPOSString(sentence);
		posStr = posStr.replaceAll("adverb", "");
		if(posStr.contains("noun")&&posStr.contains("verb"))
			return true;
		return false;
	}
}
