package index;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;


/**
 * Modern Hebrew document reader
 * Format generated by milaCorpora.GeneratePlainTextCorporaFromXml
 * @author HZ
 */
public class BibleDocReader extends DocReader
{
	private static final String SPACE = "\\s+";
	private File[] corpusFiles;
	private int currId;
	private int currSent;
	private File currFile;
	private BufferedReader currReader;
	private String currSentText;
	private String currSentId;
	private String currSentPath;
	private String inputDir;
	private String currSource = null;
	private Iterator<String> termIter;

	/**
	 * @param dir folder containing files to read
	 * @throws IndexerException
	 * @throws UnsupportedEncodingException 
	 */
	public BibleDocReader(File dir) throws IndexerException, UnsupportedEncodingException
	{
		super();
		if (dir == null || !dir.exists())
			throw new IndexerException("the directory is null or nonexistant");
		inputDir = dir.getAbsolutePath();
		corpusFiles = dir.listFiles();
		currId = 0;
		currFile = corpusFiles[currId];
		try {
			currReader = new BufferedReader(new InputStreamReader(new FileInputStream(currFile), "UTF8"));
			currSent = 0;
		} catch (FileNotFoundException e) {
			throw new IndexerException("IOException: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see new_search.DocReader#next()
	 */
	
	public boolean next() throws IndexerException
	{
		String line;
		try {
			line = currReader.readLine();
			if (line == null) {
				currReader.close();
				currId++;
				if (currId < corpusFiles.length) {
			        currFile = corpusFiles[currId];
					currReader = new BufferedReader(new InputStreamReader(new FileInputStream(currFile), "UTF8"));
					currSent = 0;
					line = currReader.readLine();
				}
				else
					return false;
			}
			currSentPath = currFile.getAbsolutePath();
			currSent++;
			currSentId = String.valueOf(currSent);
			currSentText = line;
			currSentText = currSentText.replaceAll("[\\x07\t\f\n-]", " ");
			currSentText = currSentText.replaceAll("[^�����������������������������\'\\s\"]", "");
			termIter =  Arrays.asList(currSentText.split(SPACE)).iterator();
			return true;
		} catch (IOException e) {
			throw new IndexerException("IOException: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see index.DocReader#docId()
	 */
	
	public String docId() throws IndexerException
	{
		return currSentPath;
	}

	/* (non-Javadoc)
	 * @see index.DocReader#doc()
	 */
	
	public String doc()
	{
		return currSentText;
	}

	/* (non-Javadoc)
	 * @see index.DocReader#readToken()
	 */
	
	public String readToken() throws IndexerException
	{
		if (termIter == null)
			throw new IndexerException("you must call next() before calling readToken()");
		
		return termIter.hasNext() ? termIter.next() : null;
	}

	/* (non-Javadoc)
	 * @see java.io.Reader#read(char[], int, int)
	 */
	public int read(char[] cbuf, int off, int len) throws IOException
	{
		return currReader.read(cbuf, off, len);
	}

	/* (non-Javadoc)
	 * @see java.io.Reader#close()
	 */
	public void close() throws IOException
	{
		if (currReader != null)
			currReader.close();
	}

	/* (non-Javadoc)
	 * @see index.DocReader#sent()
	 */
	
	public String period() throws IndexerException {
		return currSentId;
	}

	/* (non-Javadoc)
	 * @see index.DocReader#source()
	 */
	
	public String source() throws IndexerException {
		// source name is the upper directory name
		currSource = getSourceName();
		currSource = currSource.replaceAll(".txt", "");
		return currSource;
	}
	
	
	public int length() throws IndexerException {
		// TODO Auto-generated method stub
		return currSentText.trim().split(" ").length;
	}
	
	//
	////////////////////////////////////////////////////////// PRIVATE /////////////////////////////////////////////
	//
	
	
	/**
	 * Gets the source folder
	 * @return String containing upper folder name to be used as a source name
	 */
	private String getSourceName() 
	{
		File p = currFile.getParentFile();
		File d = new File(inputDir);
		File temp = currFile;
		while(!p.equals(d)){
			temp = p;
			p = p.getParentFile();	
		}
		//here, temp is the upper folder of the given file
		String tempStr = temp.getName();
		return tempStr;
	}

	
}
	
