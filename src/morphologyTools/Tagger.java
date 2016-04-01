package morphologyTools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vohmm.application.SimpleTagger3;
import vohmm.corpus.AffixFiltering;
import vohmm.corpus.Sentence;
import vohmm.corpus.TokenExt;
import vohmm.lexicon.BGULexicon;

/**
 * BGU POS-tagger 
 * @author HZ
 *
 */
public class Tagger {
	private static SimpleTagger3 m_tagger;
	
	/**
	 * Initiates the tagger
	 * @param taggerHomdir
	 * @throws Exception
	 */
	public static void init(String taggerHomdir) throws Exception{  
		m_tagger = new SimpleTagger3(taggerHomdir,vohmm.application.Defs.TAGGER_OUTPUT_FORMAT_BASIC,false,false,/*false*/true,false,null,AffixFiltering.NONE);
		BGULexicon._bHazal = true;
	}
	
	/**
	 * Tags a string
	 * @param str
	 * @return list of tagged sentences
	 * @throws Exception
	 */
	private static List<Sentence> getTaggedSentences(String str) throws Exception  {
		return m_tagger.getTaggedSentences(str);
	}
	
	/**
	 * Gets the most probable lemma, supports ngrams
	 * @param str
	 * @return a set - containing the most probable lemma - a single string
	 * @throws Exception
	 */
	public static Set<String>  getTaggerLemmas(String str) throws Exception{
		HashSet<String> lemmas = new HashSet<String>();
		String lemma = "";
		for (Sentence sentence : getTaggedSentences(str)) {
			for (TokenExt token : sentence.getTokens()) {
				lemma = lemma + " " + token._token.getSelectedAnal().getLemma().getBaseformStr();
		 }
		}
		if (!lemma.equals(""))
			lemmas.add(lemma.trim());
		return lemmas;
	}
	
	/**
	 * Gets the most probable lemma, supports ngrams
	 * @param str
	 * @return a set - containing the most probable lemma - a single string
	 * @throws Exception
	 */
	public static List<String>  getTaggerLemmasList(String str) throws Exception{
		List<String> lemmas = new ArrayList<String>();
		for (Sentence sentence : getTaggedSentences(str)) {
			for (TokenExt token : sentence.getTokens()) {
				lemmas.add(token._token.getSelectedAnal().getLemma().getBaseformStr());
		 }
		}
		return lemmas;
	}
	
	
	
	/**
	 * Gets the most probable lemma, supports ngrams
	 * @param str
	 * @return a set - containing the POS
	 * @throws Exception
	 */
	public static List<String>  getTaggerPOSList(String str) throws Exception{
		List<String> lemmas = new ArrayList<String>();
		for (Sentence sentence : getTaggedSentences(str)) {
			for (TokenExt token : sentence.getTokens()) {
				token._token.getSelectedAnal().getTag();
					lemmas.add(token._token.getSelectedAnal().getTag().toString());
		 }
		}
		return lemmas;
	}
///////////////////////////////////////////////////////////////////////////////////
	
	public static List<String>  getSuffix(String str) throws Exception{
		ArrayList<String> s = new ArrayList<>();
		for (Sentence sentence : getTaggedSentences(str)) 
			for (TokenExt token : sentence.getTokens()) 
				if(token._token.getSelectedAnal().hasSuffix())
					s.add(token._token.getSelectedAnal().getSuffix().toString());
		return s;
	}
	
	public static List<String>  getPrefix(String str) throws Exception{
		ArrayList<String> s = new ArrayList<>();
		for (Sentence sentence : getTaggedSentences(str)) 
			for (TokenExt token : sentence.getTokens()) 
				s.add(token._token.getSelectedAnal().getPrefixStr().toString());
		return s;
	}
		
///////////////////////////////////////////////////////////////////////////////////	
	/**
	 * Gets the most probable lemma, supports ngrams
	 * @param str
	 * @return a string - containing the POS
	 * @throws Exception
	 */
	public static String  getTaggerPOSString(String str) throws Exception{
		String posString = "";
		for (Sentence sentence : getTaggedSentences(str)) {
			for (TokenExt token : sentence.getTokens()) {
				token._token.getSelectedAnal().getTag();
					posString = posString + " " +token._token.getSelectedAnal().getTag().toString();
		 }
		}
		return posString.trim();
	}
	
}
