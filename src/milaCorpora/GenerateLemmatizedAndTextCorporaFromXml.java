package milaCorpora;


	import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
	
import vohmm.corpus.Anal;
	import vohmm.corpus.Sentence;
import vohmm.corpus.Token;
import vohmm.parser.corpus.FileBasedSentenceExtractor;
import vohmm.parser.corpus.SentenceParser;
import vohmm.parser.corpus.TaggedScoredXMLSentenceParser;
import vohmm.util.Bitmask;

	public class GenerateLemmatizedAndTextCorporaFromXml {
		static final String encoding = "CP1255";

		public static void main(String[] args) {

			String [] inputDirs = new String[] {"H:\\MilaXML\\a7","H:\\MilaXML\\haretz","H:\\MilaXML\\knesset_tagged","H:\\MilaXML\\theMarker"};//,"H:\\MilaXML_omitted\\a7","H:\\MilaXML_omitted\\haretz\\omitted2","H:\\MilaXML_omitted\\knesset_tagged","H:\\MilaXML_omitted\\theMarker"};
//			String [] inputDirs = new String[] {"H:\\MilaXML\\wiki"};
//			String [] inputDirs = new String[] {"H:\\MilaXML_omitted\\a7","H:\\MilaXML_omitted\\haretz\\omitted2","H:\\MilaXML_omitted\\knesset_tagged","H:\\MilaXML_omitted\\theMarker"};
//			String [] inputDirs = new String[] {"H:\\MilaXML\\a7","H:\\MilaXML\\haretz","H:\\MilaXML\\knesset_tagged"};//,"H:\\MilaXML\\theMarker"};//,"H:\\MilaXML_omitted\\a7","H:\\MilaXML_omitted\\haretz\\omitted2","H:\\MilaXML_omitted\\knesset_tagged","H:\\MilaXML_omitted\\theMarker"};

			try {
				
				SentenceParser parser = new TaggedScoredXMLSentenceParser(false,false, false, false);
				int iSent = 0;
				
				for (int j=0; j<inputDirs.length; j++) {
					String inputDir = inputDirs[j];
					FileBasedSentenceExtractor sentenceExtractor = new FileBasedSentenceExtractor(new File(inputDir), parser);
					String dirName = inputDir.substring(inputDir.lastIndexOf("\\")+1,inputDir.length());
					PrintStream out = new PrintStream(new FileOutputStream("F:\\MilaLemmaText\\"+dirName+".txt"), false, encoding);
					
					sentenceExtractor.begin();
					String prevSentenceName = "";
		
					while (sentenceExtractor.hasNext()) {
						Sentence sentence = sentenceExtractor.next();
						if (!sentenceExtractor.getCurrentFile().getName().equals(prevSentenceName)) {
							iSent = 0;
							prevSentenceName = sentenceExtractor.getCurrentFile().getName();
						}
						try {
							if (sentence == null)
								System.out.println("Null sentence");
							else {
								iSent++;
								System.out.println(sentenceExtractor.getCurrentFile().getName() + ": " + iSent);
								String lineInfo = sentenceExtractor.getCurrentFile().getAbsolutePath()+"@@"+iSent+"@@";
								String line = lineInfo;
								String text = "";
								for (int i = 0; i < sentence.size(); i++) {
									
									Token token = sentence.getToken(i);
									if (token.hasAnals() && !token.isNotSelected()) {
										// ignore punctuations
										long pos = token.getSelectedAnalOrFirst()
												.getTag().getBitmask()
												& Bitmask.BASEFORM_POS;
										if (pos == Bitmask.BASEFORM_POS_PUNCUATION) {
											continue;
										}
//										if (pos == Bitmask.BASEFORM_POS_NUMERAL) {
//											long bitmask = token
//													.getSelectedAnalOrFirst().getTag()
//													.getBitmask()
//													& Bitmask.BASEFORM_NUMBERTYPE_LITERAL;
//											if (bitmask == Bitmask.BASEFORM_NUMBERTYPE_LITERAL) {
//												continue;
//											}
//										}
//		//								if ((pos == Bitmask.BASEFORM_POS_PREPOSITION)
//		//										&& (token.getOrigStr().length() == 1)) {
//		//									 System.out.println(token);
//		//									continue;
//		//								}
//										if (token.getSelectedAnalOrFirst().getLemma().getBaseformStr().replaceAll("#", "").equals("NUMBER")){
//											continue;
//										}
										if (token.getOrigStr().equals(",")){
											continue;
										}
										Anal selectedAnal = token.getSelectedAnalOrFirst();
										line += selectedAnal.getLemma().getBaseformStr() + " ";
										text += token.getOrigStr() + " ";
//										Anal selectedAnal = token
//												.getSelectedAnalOrFirst();
//										line += selectedAnal.getLemma()
//												.getBaseformStr() + "###";
//										Iterator<AnalProb> it = token.getAnals().iterator();
//										HashSet<String> lemmas = new HashSet<String>();
//										while (it.hasNext()) {
//											Anal anal = ((AnalProb) it.next())
//													.getAnal();
//											lemmas.add(anal.getLemma().getBaseformStr());
//										}
//										if(!lemmas.isEmpty())
//											line += StringUtils.formatSet2String(lemmas.toString());
//										else
//											line += token.getOrigStr();
//										line = line.trim();
//									} else
//										line += token.getOrigStr() + "###"
//												+ token.getOrigStr() + "###"
//												+ token.getOrigStr();
//									line += "\t";
		
								}
								}
								if (!line.trim().isEmpty()&&(!line.trim().equals(lineInfo))){
									out.println(line.trim()+"@@"+text.trim());
								}
							}
							} catch (Exception e) {
								e.printStackTrace();
								System.exit(1);
							}
					}
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
				
	}

			
		
	
	


