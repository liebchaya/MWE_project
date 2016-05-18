package mwe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import mwe.scorers.BigramsMutualRankRatio;
import mwe.scorers.FourgramsMutualRankRatio;
import mwe.scorers.MutualExpectation;
import mwe.scorers.TrigramsMutualRankRatio;

public class CalcMWEScores {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BigramsMutualRankRatio bMRR = new BigramsMutualRankRatio(new File("I:\\NgramStatCount\\responsaAll2.cnt"));
		TrigramsMutualRankRatio tMRR = new TrigramsMutualRankRatio(new File("I:\\NgramStatCount\\responsaAll3.cnt")); 
		FourgramsMutualRankRatio fMRR = new FourgramsMutualRankRatio(new File("I:\\NgramStatCount\\responsaAll4.cnt"));
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(""),"UTF-8"));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(""), "UTF-8"));
		
		String line = reader.readLine();
		while (line!=null){
			String term = line.split("\t")[0];
			int n = term.split(" ").length;
			if (n==1)
				writer.write(term+"\t"+0+"\n");
			else if (n==2)
				writer.write(term+"\t"+bMRR.score(term)+"\n");
			else if (n==3)
				writer.write(term+"\t"+tMRR.score(term)+"\n");
			else if (n==4)
				writer.write(term+"\t"+fMRR.score(term)+"\n");
			else
				System.out.println("error - ngram");
			line = reader.readLine();
		}
		reader.close();
		writer.close();
			
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(""),"UTF-8"));
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(""), "UTF-8"));
		MutualExpectation me = new MutualExpectation("I:\\NgramStatCount\\responsaAll", 4);
		line = reader.readLine();
		
		while (line!=null){
			String term = line.split("\t")[0];
			writer.write(term+"\t"+me.score(term)+"\n");
			line = reader.readLine();
		}
	}

}
